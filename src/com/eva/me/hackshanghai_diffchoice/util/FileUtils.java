package com.eva.me.hackshanghai_diffchoice.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StreamCorruptedException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
    
    public static Bitmap getImage(int hh, int ww) {
    	String srcPath = mCurPhotoDir;
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath,newOpts);//此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        Log.d(TAG, "outWidth "+w+" outHeight "+h);
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
//        float hh = 800f;//这里设置高度为800f
//        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = newOpts.outWidth / ww;
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = newOpts.outHeight / hh;
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be+1;//设置缩放比例
        Log.d(TAG, "be "+be);
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
//        return compressImage(bitmap);//压缩好比例大小后再进行质量压缩
        Log.d(TAG, "bitmap w = "+bitmap.getWidth()+" h = "+bitmap.getHeight());
        return bitmap;
    }
    
    public static Bitmap getImage(String srcPath, int hh, int ww) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath,newOpts);//此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
//        float hh = 800f;//这里设置高度为800f
//        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = newOpts.outWidth / ww;
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = newOpts.outHeight / hh;
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
//        return compressImage(bitmap);//压缩好比例大小后再进行质量压缩
        return bitmap;
    }
    

    public static Bitmap getImageFromUri(Uri uri, int hh, int ww, Context context) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        ContentResolver cr = context.getContentResolver();
        Bitmap bitmap =null;
		try {
			bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri),null, newOpts);//此时返回bm为空

	        newOpts.inJustDecodeBounds = false;
	        int w = newOpts.outWidth;
	        int h = newOpts.outHeight;
	        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
	        int be = 1;//be=1表示不缩放
	        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
	            be = newOpts.outWidth / ww;
	        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
	            be = newOpts.outHeight / hh;
	        }
	        if (be <= 0)
	            be = 1;
	        newOpts.inSampleSize = be+1;//设置缩放比例
	        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
	        bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri),null, newOpts);
	        Log.d(TAG, "bitmap w= "+bitmap.getWidth()+" h= "+bitmap.getHeight() );
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return bitmap;
    }
}
