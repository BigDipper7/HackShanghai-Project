package com.eva.me.hackshanghai_diffchoice;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.eva.me.hackshanghai_diffchoice.util.FileUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class CameraActivity extends Activity {

	private static final String TAG = "CameraActivity";
	
	private Context context;
	private Button btnCamera;
	private ImageView ivReveal;
	
	private void showToast(Context context, String str) {
		Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera);
		init();
		
	}

	private void init() {
		context = CameraActivity.this;
		//init views
		btnCamera = (Button) findViewById(R.id.button1);
		ivReveal = (ImageView) findViewById(R.id.imageView1);
		
		
		btnCamera.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent  = new Intent();
				intent.setAction("android.media.action.STILL_IMAGE_CAMERA");
//				startActivity(intent);
				//another func
				startActivityForResult(intent, 0);
				
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			//现在进行相关信息的返回
			showToast(context, "onActivityResult --> if()");
			initOnActRe(data);
		}
	}
	
	
	private void initOnActRe(Intent data) {
		Bundle bundle = data.getExtras();
		Bitmap bitmap = (Bitmap) bundle.get("data");
//		byte[] data = bundle.get("data");
		saveImgToSD(bitmap);
		ivReveal.setImageBitmap(bitmap);
	}

	private void saveImgToSD(Bitmap bitmap) {
		if (FileUtils.getSDStatus()) {
			String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
			String imgName = "MYIMG_"+timeStamp+".jpg"; 
			String sdPath = FileUtils.getSDCardDir();
			Log.e(TAG, "sd path:"+sdPath);
			
			String totalPath = sdPath+"/0MyPath/";
			File file = new File(totalPath);
			if (!file.exists()) {
				file.mkdir();
				Log.v(TAG, "创建文件夹...");
			}
			
			FileOutputStream fOutputStream = null;
			try {
				fOutputStream = new FileOutputStream(totalPath+imgName);
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOutputStream);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} finally {
				try {
					Log.e(TAG, "save success");
					fOutputStream.flush();
					fOutputStream.close();
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}else {
			Log.e(TAG, "no sd card? status: "+FileUtils.getSDStatus());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.camera, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
