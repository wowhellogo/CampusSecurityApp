/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

#define LOG_NDEBUG 1
#define LOG_TAG "Rfid_JNI"

#include "jni.h"
#include     <stdio.h>      
#include     <stdlib.h>  
#include 	 <string.h>   
#include     <unistd.h>
#include     <sys/ioctl.h>   
#include     <sys/types.h>  
#include     <sys/stat.h>   
#include     <fcntl.h>      
#include     <termios.h>   
#include     <errno.h>   
#include	 <pthread.h>
#define GPIO_PULL_UP	1
#define GPIO_PULL_DOWN	0
#define RFID_PULL_DOWN	0
#define RFID_PULL_UP	1
static pthread_rwlock_t uart_rwlock;
static bool RfidMessageReady = true;
static bool RfidMessageSending = false;
static bool mMessageValid = false;
volatile int fd_Rfid_dev = 0;
static int fd_uart_dev = 0;

static bool RfidPowerState = false;

char power_on_cmd1[] = {0xFF, 0x00, 0x0C, 0x1D, 0x03};
char power_on_cmd2[] = {0xFF, 0x00, 0x04, 0x1D, 0x0B};
char power_on_cmd3[] = {0xFF, 0x01, 0x97, 0x06, 0x4B,0xBB};

char power_on_cmd4[] = {0xFF, 0x02, 0x92, 0x0A, 0x8C, 0x4B, 0xD5};
char power_on_cmd6[] = {0xFF, 0x02, 0x93, 0x00, 0x05,0x51,0x7D};
char power_on_cmd7[] = {0xFF, 0x03, 0x91, 0x02, 0x01,0x01,0x42, 0xC5};
char power_on_cmd9[] = {0xFF, 0x10, 0x2F, 0x00, 0x00,0x01,0x22, 0x00, 0x00, \
0x05, 0x07, 0x22, 0x10, 0x00, 0x1B, 0x03, 0xE8, 0x01, 0xFF, 0xDD, 0x2B};
char power_on_cmd11[] = {0xFF, 0x03, 0x9A, 0x01, 0x0C,0x00,0xA3, 0x5D};
char end_read_cmd[] = {0xFF, 0x03, 0x2F, 0x00, 0x00, 0x02, 0x5E, 0x86};
int state = -1;
static int config_serial(int fd,int nSpeed,int nBits,char nEvent,int nStop)
{
	struct termios newtio;
	
	bzero( &newtio, sizeof( newtio ) );
	newtio.c_cflag |= CLOCAL | CREAD;
	newtio.c_cflag &= ~CSIZE;
	//set speed
	switch( nSpeed )
	{
		case 115200:
			cfsetispeed(&newtio,B115200);
			cfsetospeed(&newtio,B115200);
			break;
		case 38400:
			cfsetispeed(&newtio,B38400);
			cfsetospeed(&newtio,B38400);

			break;
		case 19200:
			cfsetispeed(&newtio,B19200);
			cfsetospeed(&newtio,B19200);
			printf("UART Speed = 19200 !\n");
			break;
		default :
			break;
	}
	
	switch ( nBits )
	{	
		case 7:
			newtio.c_cflag |= CS7;
			break;

		case 8:
			newtio.c_cflag |= CS8;
			break;
	}
	//set verify
	switch ( nEvent )
	{
		case 'o':
		case 'O': 	//odd
			newtio.c_cflag |= PARENB;
			newtio.c_cflag |= PARODD;
			newtio.c_iflag |= ( INPCK | ISTRIP );
			break;

		case 'e':
		case 'E':	//even
			newtio.c_iflag |= ( INPCK |ISTRIP );
			newtio.c_cflag |= PARENB;
			newtio.c_cflag &= ~PARODD;
			break;

		case 'n':
		case 'N':	//no
			newtio.c_cflag &= ~PARENB;
			newtio.c_iflag &= ~INPCK;
			break;
	}	
	//set stop bit
	if( nStop ==1 ){
		newtio.c_cflag &= ~CSTOPB;
	}
	else if( nStop ==2 ){
		newtio.c_cflag |= CSTOPB;
	}
	

	if( ( tcsetattr(fd,TCSANOW,&newtio) )!=0 )
	{
		return -1;
	}
	
	else
		tcflush (fd, TCIOFLUSH);

	return 0; 
}

static int open_uart_dev()
{
	//if uart_dev is closed ,open it 
	printf("Open fd_uart_dev*********************");
	if(fd_uart_dev<=0){
		
		fd_uart_dev = open("/dev/ttyMT0", O_RDWR | O_NOCTTY);
		//if open uart_dev sucessful,config it
		if (fd_uart_dev>0){
			config_serial(fd_uart_dev,115200,8,'n',1); 
			printf("Open fd_uart_dev = %d !\n",fd_uart_dev);
		}
		else{
			printf("Open /dev/ttyMT0 fail!\n");
			exit(1);
		}
		printf("guixing Open /dev/ttyMT0 sucessful!\n");
	}
	printf("Open fd_uart_dev*********************end\n");
  	tcflush (fd_uart_dev, TCIOFLUSH);	//clear the buffer
	return 0;
}

static void close_uart_dev()
{	
	//if uart_dev is open ,close it 
	if(fd_uart_dev){
		close(fd_uart_dev);
		printf("close /dev/ttyMT0 sucessful!\n");
	}
	fd_uart_dev = 0;
}

static int read_print_data(int fd, int cmd)
{
	const int bufferLength = 4096;
	char readMessageBuffer[bufferLength];
	int i;
	int nread;

	memset(readMessageBuffer, '\0', bufferLength);
	nread = read(fd,readMessageBuffer,bufferLength);
	if (nread < 0)
	{
		printf("cmd%d read fail nread = %d\n", cmd,nread);
	}
	printf("cmd%d read sucessful! nread = %d\n", cmd, nread);
	
	printf("rfid = ");
	for (i=0; i<nread; i++)
	{
		printf("%x ", readMessageBuffer[i]);
		if ((cmd == 1) && (i == 5))
		{
			state = readMessageBuffer[5]& 0x1;
		}
	}
	printf("\n");
	
	return 0;
}

static void rfid_Start_Read(void)
{
	int nread = 0;
	int nwrite = 0;
	
	nwrite = write(fd_uart_dev, power_on_cmd1, sizeof(power_on_cmd1));
	if (nwrite < 0)
	{
		printf("write fail nread = %d\n", nwrite);
	}else
	{
		read_print_data(fd_uart_dev, 1);
	}
	
	usleep(100000);

	if (state == 1)
	{

		nwrite = write(fd_uart_dev, power_on_cmd2, sizeof(power_on_cmd2));
		if (nwrite < 0)
		{
			printf("write fail nread = %d\n", nwrite);
		}else
		{
			read_print_data(fd_uart_dev, 2);
		}
		usleep(1000000);
	}
	
	nwrite = write(fd_uart_dev, power_on_cmd3, sizeof(power_on_cmd3));
	if (nwrite < 0)
	{
		printf("write fail nread = %d\n", nwrite);
	}else
	{
		read_print_data(fd_uart_dev, 3);
	}
	usleep(100000);
	nwrite = write(fd_uart_dev, power_on_cmd4, sizeof(power_on_cmd4));
	if (nwrite < 0)
	{
		printf("write fail nread = %d\n", nwrite);
	}else
	{
		read_print_data(fd_uart_dev, 4);
	}
	usleep(100000);
	
	nwrite = write(fd_uart_dev, power_on_cmd6, sizeof(power_on_cmd6));
	usleep(100000);
	if (nwrite < 0)
	{
		printf("write fail nread = %d\n", nwrite);
	}else
	{
		read_print_data(fd_uart_dev, 6);
	}
	
	nwrite = write(fd_uart_dev, power_on_cmd7, sizeof(power_on_cmd7));
	if (nwrite < 0)
	{
		printf("write fail nread = %d\n", nwrite);
	}else
	{
		read_print_data(fd_uart_dev, 7);
	}
	usleep(100000);
	
	/*nwrite = write(fd_uart_dev, power_on_cmd8, sizeof(power_on_cmd8));
	if (nwrite < 0)
	{
		printf("write fail nread = %d\n", nwrite);
	}else
	{
		read_print_data(fd_uart_dev, 8);
	}
	usleep(100000);*/

	
	nwrite = write(fd_uart_dev, power_on_cmd11, sizeof(power_on_cmd11));
	if (nwrite < 0)
	{
		printf("write fail nread = %d\n", nwrite);
	}else
	{
		read_print_data(fd_uart_dev, 11);
	}
	usleep(100000);

	nwrite = write(fd_uart_dev, power_on_cmd9, sizeof(power_on_cmd9));
	if (nwrite < 0)
	{
		printf("write fail nread = %d\n", nwrite);
	}else
	{
		read_print_data(fd_uart_dev, 9);
	}
	usleep(100000);
	
	printf("write sucessful! nwrite = %d\n", nwrite);

}


static jint android_hardware_rfidPowerOn
  (JNIEnv *env, jobject object)
{
	int fd_tmp=0,temp = 0;	
	printf("Call jni_uart_rfidPowerOn!\n");
	RfidPowerState = true;
	
		fd_tmp = open("/dev/uart_rfid", 0);
		printf("cxw************************************\n");
		if(fd_tmp<0){
			printf("Open /dev/uart_rfid fail!\n");
			exit(1);
		}
		else{
			printf("fd_tmp = %d !\n",fd_tmp);
			printf("Open /dev/uart_rfid sucessful!\n");
		}
	//open_rfid_dev();
	temp = ioctl(fd_tmp,RFID_PULL_UP,0);
	printf("call ioctl return temp = %d !\n",temp);
	close(fd_tmp);
	usleep(100000);
	
	open_uart_dev();	//open uart for Rfid to set parameter or data transfer.
	
	//init rwlock.
	if(pthread_rwlock_init(&uart_rwlock,NULL) != 0)
	{
		printf("uart_rwlock initialization failed.");
	}
	printf("Call jni_uart_rfidPowerOn end!!!!!!!\n");

	rfid_Start_Read();
	
	return 0; 
}



static jint android_hardware_rfidPowerOff
  (JNIEnv *env, jobject object)
{
	int fd_tmp=0,temp=0,nwrite=0;
	printf("Call jni_uart_rfidPowerOff!\n");
	RfidPowerState = false;
	//open_Rfid_dev();
	nwrite = write(fd_uart_dev, end_read_cmd, sizeof(end_read_cmd));
	if (nwrite < 0)
	{
		printf("write fail nread = %d\n", nwrite);
	}else
	{
		read_print_data(fd_uart_dev, 11);
	}
	usleep(100000);
		fd_tmp = open("/dev/uart_rfid", 0);
		if(fd_tmp<0){
			printf("Open /dev/uart_rfid fail!\n");
			exit(1);
		}
		else{
			printf("fd_tmp = %d !\n",fd_tmp);
			printf("Open /dev/uart_rfid sucessful!\n");
		}
	printf("cxw    8***************************\n");
	
	temp=ioctl(fd_tmp,RFID_PULL_DOWN,0);
	printf("call ioctl return temp = %d !\n",temp);
	close(fd_tmp);
	
	close_uart_dev();//close uart when RFID PowerDown.
	pthread_rwlock_destroy(&uart_rwlock);//destroy rwlock.
	return 0;
}


static jint android_hardware_sendMessage(JNIEnv *env, jobject thiz, jstring message)
{
	printf("android_hardware_sendMessage!");

	if(RfidPowerState)
	{
		int i = 0, j = 0;
	    const char* utfChars = NULL;
		unsigned int mLength = 0;
		const char *utfMessage = NULL;
		unsigned int nwrite = 0;
		unsigned char messageBuffer[100]={0};
		unsigned char tempBuffer[100]={0};
		unsigned char temp = 0;
		
		printf("cxw RfidPowerState ***************************\n");

	    utfChars = env->GetStringUTFChars(message, NULL);
		mLength = strlen(utfChars);
		utfMessage = utfChars;

		for(j=0,i=0;j<mLength;j++) 
		{
			temp = *(utfMessage++);
			printf("temp = %x", temp);
			
		    if ((temp >= 'a') && (temp <= 'f'))
			{
				messageBuffer[i++] = (temp - 'a' + 0xa);
			}		
			else if ((temp >= 'A') && (temp <= 'F'))
			{
				messageBuffer[i++] = (temp - 'A' + 0xa);
			}			
			else if ((temp >= '0') && (temp <= '9'))
			{
				messageBuffer[i++] = (temp - '0');
			}
			
		}

	    printf("  utfChars is '%s', \n", (const char*) utfChars );
	    printf("  String length is '%d', \n",mLength );

		for (j=0; j< i; j++)
		{
			printf("messageBuffer[%d] = %x",j, messageBuffer[j]);
		}

		mLength = i;
		mLength = mLength/2+ mLength%2;

		
		for(i=0,j=0; i < mLength; i++)
		{
			tempBuffer[i] = (messageBuffer[j] << 4) + messageBuffer[j+1];
			j += 2; 
			printf("tempBuffer = %X", tempBuffer[i]);
		}

		//Write messageBuffer to Rfid Module.
		pthread_rwlock_wrlock(&uart_rwlock);
		nwrite = write(fd_uart_dev,tempBuffer,mLength);
		pthread_rwlock_unlock(&uart_rwlock);
		//After write a message to Rfid Module,it must sleep for 2 second
		printf("Write utfMessage = %s to Rfid Module;nwrite = %d .\n",utfMessage,nwrite);
		sleep(3);
	
		//End sendMessage
	    env->ReleaseStringUTFChars(message, utfChars);
		printf("Exit android_hardware_senMessage(xxx) !\n");
	}
	return 0;
}

static jstring android_hardware_getMessage(JNIEnv *env, jobject thiz)
{
	if(RfidPowerState)
	{
		int nread = 0,i = 0,j=0;
		const int bufferLength = 4096;
		char readMessageBuffer[bufferLength] = {'\0'};
		char pBuffer[bufferLength] = {'\0'};
		char *tempReadMessage = NULL;
		unsigned char temp = 0;

		tempReadMessage = pBuffer;
	
		printf("Enter android_hardware_getMessage(xxx) !\n");
		
		//initialize readMessageBuffer
		memset(readMessageBuffer,'\0',bufferLength);
		memset(pBuffer,'\0',bufferLength);
		pthread_rwlock_rdlock(&uart_rwlock);
		nread = read(fd_uart_dev,readMessageBuffer,bufferLength);
		pthread_rwlock_unlock(&uart_rwlock);
		printf("nread = %d,readMessageBuffer = %s .\n",nread,(const char*)readMessageBuffer);

		for (i=0; i < nread; i++)
		{
			temp = readMessageBuffer[i];
			if (temp <= 0xf)
			{
				tempReadMessage += sprintf(tempReadMessage, "0%x", temp);
			}
			else
			{
				tempReadMessage += sprintf(tempReadMessage, "%x", temp);
			}
		}

		printf("nread = %d,pBuffer = %s .\n",nread,(const char*)pBuffer);
				
		jstring mStringMessage =  env->NewStringUTF((const char*)pBuffer);
			
		return mStringMessage;
		
	
		//return env->NewStringUTF(" ");
	}
	return 0;
}


static const char *classPathName = "com/campussecurity/app/rfidjni/Native";

static JNINativeMethod methods[] = {
	{"JNI_rfidPowerOn","()I",(void*)android_hardware_rfidPowerOn},
  	{"JNI_rfidPowerOff","()I",(void*)android_hardware_rfidPowerOff},
  	{"JNI_sendMessage","(Ljava/lang/String;)I",(void*)android_hardware_sendMessage },
	{"JNI_getMessage","()Ljava/lang/String;",(void*)android_hardware_getMessage },
};

/*
 * Register several native methods for one class.
 */
static int registerNativeMethods(JNIEnv* env, const char* className,
    JNINativeMethod* gMethods, int numMethods)
{
    jclass clazz;

    clazz = env->FindClass(className);
    if (clazz == NULL) {
        printf("Native registration unable to find class '%s'", className);
        return JNI_FALSE;
    }
    if (env->RegisterNatives(clazz, gMethods, numMethods) < 0) {
        printf("RegisterNatives failed for '%s'", className);
        return JNI_FALSE;
    }

    return JNI_TRUE;
}

/*
 * Register native methods for all classes we know about.
 *
 * returns JNI_TRUE on success.
 */
static int registerNatives(JNIEnv* env)
{
  if (!registerNativeMethods(env, classPathName,
                 methods, sizeof(methods) / sizeof(methods[0]))) {
    return JNI_FALSE;
  }

  return JNI_TRUE;
}


// ----------------------------------------------------------------------------

/*
 * This is called by the VM when the shared library is first loaded.
 */
 
typedef union {
    JNIEnv* env;
    void* venv;
} UnionJNIEnvToVoid;

jint JNI_OnLoad(JavaVM* vm, void* reserved)
{
    UnionJNIEnvToVoid uenv;
    uenv.venv = NULL;
    jint result = -1;
    JNIEnv* env = NULL;
    
    printf("JNI_OnLoad");

    if (vm->GetEnv(&uenv.venv, JNI_VERSION_1_4) != JNI_OK) {
        printf("ERROR: GetEnv failed");
        goto bail;
    }
    env = uenv.env;

    if (registerNatives(env) != JNI_TRUE) {
        printf("ERROR: registerNatives failed");
        goto bail;
    }
    
    result = JNI_VERSION_1_4;
    
bail:
    return result;
}
