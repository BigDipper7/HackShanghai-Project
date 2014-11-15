package com.eva.me.hackshanghai_diffchoice.util;

import java.io.StreamCorruptedException;

import android.os.Environment;
import android.util.Log;

public class FileUtils {
	private static final String TAG = "FileUtils";
	
	private static final String  desDir = "/0MyPathTwo/";
	private static boolean isSDCardExist = false;
	private static String SDCardDir = "";
	
	public static boolean getSDStatus() {
		isSDCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
		Log.e(TAG, "isSDCardExist  "+isSDCardExist);
		return isSDCardExist;
	}
	
	public static String getSDCardDir() {
		if (getSDStatus()) {
			SDCardDir = Environment.getExternalStorageDirectory().toString();
		}
		return SDCardDir;
	}
	
	
}
