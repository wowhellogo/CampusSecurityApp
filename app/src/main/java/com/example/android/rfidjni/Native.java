package com.example.android.rfidjni;


import android.util.Log;

public class Native {
	
	public Native() {

	}

	public static void init() {
		load();
		//JNI_rfidPowerOn();	
	}

	private static void load() {
		
		
		try {
			System.loadLibrary("rfidjni");
			
		} catch (Throwable e) {
			e.printStackTrace();
			Log.i("hkh","-------------------Throwable :"+e);
		}
	}
	public static native int JNI_rfidPowerOn();
	public static native int JNI_rfidPowerOff();
	public static native int JNI_sendMessage(String context);
	public static native String JNI_getMessage();

}
