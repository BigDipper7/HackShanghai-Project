package com.eva.me.hackshanghai_diffchoice.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StreamCorruptedException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

public class FileUtils {
	private static final String TAG = "FileUtils";
	
	private static final String  desDir = "/0MyPathTwo/";
	private static boolean isSDCardExist = false;
	private static String SDCardDir = "";
	private static String mCurPhotoDir= "";
	
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
	
	public static File getPhotoDir() {
		Log.d(TAG, "1 "+mCurPhotoDir);
		File file = createImgFile();
		Log.d(TAG, "2 "+mCurPhotoDir);
		mCurPhotoDir = file.getAbsolutePath();
		Log.d(TAG, "current dir path is ---> "+mCurPhotoDir);
		return file;
	}

	private static File createImgFile() {
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String imgFileName = "IMG_"+timeStamp+"_";
		
		File mAlbumFile = new File(getSDCardDir()+desDir);//album dir
		if (!mAlbumFile.exists()) {
			mAlbumFile.mkdir();
			Log.d(TAG, "create success~~");
		}
		
		File totalFile = null;
		try {
			totalFile = File.createTempFile(imgFileName, ".jpg", mAlbumFile);
			Log.d(TAG, ""+totalFile.getAbsolutePath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return totalFile;
	}
	
 /**
    * 加载本地图片
    * @param url
    * @return
    */
    public static Bitmap getLoacalBitmap(String url) {
         try {
              FileInputStream fis = new FileInputStream(url);
              return BitmapFactory.decodeStream(fis);  ///把流转化为Bitmap图片        

           } catch (FileNotFoundException e) {
              e.printStackTrace();
              return null;
         }
    }
	    
/**
    * 加载本地图片
    * @param url
    * @return
    */
    public static Bitmap getLoacalBitmap() {
         try {
        	 String url = mCurPhotoDir;
             FileInputStream fis = new FileInputStream(url);
             return BitmapFactory.decodeStream(fis);  ///把流转化为Bitmap图片        

           } catch (FileNotFoundException e) {
              e.printStackTrace();
              return null;
         }
    }
    
}
