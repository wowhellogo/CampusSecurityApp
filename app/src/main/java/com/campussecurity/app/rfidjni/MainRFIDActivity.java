package com.campussecurity.app.rfidjni;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.campussecurity.app.R;
import com.example.android.rfidjni.Native;


public class MainRFIDActivity extends Activity implements OnClickListener{

	
	Button bt_write,bt_read, stop_read;
	EditText et_name,et_sex,et_age,et_address;
	TextView tx_read;
	String data;
	boolean a= true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wr);
        init();
    }
	private void init() {
		// TODO Auto-generated method stub
		bt_write=(Button) findViewById(R.id.bt_write);
		bt_read=(Button) findViewById(R.id.bt_read);
		et_name=(EditText) findViewById(R.id.et_name);
		//et_sex=(EditText) findViewById(R.id.et_sex);
		//et_age=(EditText) findViewById(R.id.et_age);
		//et_address=(EditText) findViewById(R.id.et_address);
		tx_read=(TextView) findViewById(R.id.tx_read);
		tx_read.setMovementMethod(ScrollingMovementMethod.getInstance()); 
		stop_read=(Button) findViewById(R.id.stop_read);
		
		
		bt_write.setOnClickListener(this);
		bt_read.setOnClickListener(this);
		stop_read.setOnClickListener(this);
		Native.init();
		//Native.JNI_rfidPowerOn();
	}
	
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			    String context=(String)msg.obj;
				Log.i("hkh","---------------read-contex :"+context);
				if("".equals(context)||context==null){
					//tx_read.setText();
					//data = getResources().getString(R.string.tips_noread) + "\n" + data;
					//tx_read.setText(data);没有读到就不要打印显示
				}else{
					data =  context + "\n" + data;
					tx_read.setText(data);
				}
		}
	};
		
	@SuppressLint("ShowToast") @Override
	public void onClick(View vv) {
		// TODO Auto-generated method stub
		
		switch(vv.getId()){
		
		case R.id.bt_write:
			String context_name=et_name.getText().toString();
			String contex=context_name;
			Log.i("hkh","---------------write-contex :"+contex);
			if(contex==null|| "".endsWith(contex)){
				Toast.makeText(this, getResources().getString(R.string.tips), Toast.LENGTH_SHORT);
				
			}else{
				
				Log.i("hkh","----------------onclick write math -------onclick write ");
				Native.JNI_sendMessage(contex);
			}
			break;
		case R.id.bt_read:	
			bt_read.setEnabled(false);
			a = true;
			Native.JNI_rfidPowerOn();
			new Thread(new Runnable() {
			@Override
			public void run() {
			    while(a) {
				String context=Native.JNI_getMessage().toString();
				Log.i("cxw","string = " + context);
				Message msg = Message.obtain();
				msg.obj = context;   //从这里把你想传递的数据放进去就行了
				handler.sendMessage(msg);
				Log.i("cxw","----------------sendMessage ------- ");

				try {
				    Log.i("cxw","----------------befor sleep ------- ");
				    Thread.sleep(500);
				    Log.i("cxw","----------------over sleep ------- ");
				} catch (InterruptedException e) {
				    // TODO Auto-generated catch block
				    e.printStackTrace();
				}

				/*
				if (context.length()>20) {
				    bt_read.setEnabled(true);
				    break;
				}
				*/
			    }

			}
			}).start();			
			break;
		case R.id.stop_read:
			bt_read.setEnabled(true);
			a= false;
			Native.JNI_rfidPowerOff();
			break;
		default:
			break;
		}
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Native.JNI_rfidPowerOff();
	}


}
