package com.eva.me.hackshanghai_diffchoice;

import java.io.File;
import java.io.IOException;

import com.eva.me.hackshanghai_diffchoice.util.FileUtils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class Camera2FileActivity extends Activity {

	private Button btnCamera;
	private ImageView ivReveal;
	private String TAG = "Camera2FileActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera2_file);
		init();
	}

	private void init() {
		//init views
		btnCamera = (Button) findViewById( R.id.button1);
		ivReveal = (ImageView) findViewById( R.id.imageView1);
		
		Log.e(TAG, "jin rule ");
		
		btnCamera.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent iStartCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				File f = null;
				f = FileUtils.getPhotoDir();
				iStartCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
//				startActivityForResult(iStartCamera, 1);
				startActivity(iStartCamera);
			}
			
		});
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			Bundle bundle = data.getExtras();
			Bitmap bm = (Bitmap) bundle.get("data");
			ivReveal.setImageBitmap(bm);
			
		}
		
		super.onActivityResult(requestCode, resultCode, data);
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.camera2_file, menu);
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
