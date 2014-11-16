package com.eva.me.hackshanghai_diffchoice;

import com.eva.me.hackshanghai_diffchoice.selector.CustomerActivity;
import com.eva.me.hackshanghai_diffchoice.selector.SelectorActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {

	private Button btnCamera;
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		init();
		scale();
	}

	
	private void scale(){
			
			ImageView scale = (ImageView) findViewById(R.id.menu_scale);
			ImageView circle = (ImageView) findViewById(R.id.menu_circle);
			Animation animation_scale = new TranslateAnimation(0, 0, -600, 0);
			animation_scale.setDuration(2000);
			scale.setAnimation(animation_scale);
				
			Animation animation_circle = new TranslateAnimation(0, 0, -300, 0);
			animation_circle.setDuration(1000);
			circle.setAnimation(animation_circle);
				
			animation_circle.start();
	            
	        animation_scale.start();
		}

	private void init() {
		context = MainActivity.this;
		//init views
		btnCamera = (Button) findViewById(R.id.menu_photo);
		btnCamera.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, Camera2FileActivity.class);
				startActivity(intent);
			}
		});

		Button nativeButton = (Button) findViewById(R.id.menu_native);
		nativeButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,SelectorActivity.class);
				startActivity(intent);
			}
		});
		
		Button cusButton = (Button) findViewById(R.id.menu_add); 
		cusButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,CustomerActivity.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
