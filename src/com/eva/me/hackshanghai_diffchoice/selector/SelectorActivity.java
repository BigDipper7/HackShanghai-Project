package com.eva.me.hackshanghai_diffchoice.selector;

import java.util.Iterator;
import java.util.Set;

import com.eva.me.hackshanghai_diffchoice.MainActivity;
import com.eva.me.hackshanghai_diffchoice.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SelectorActivity extends Activity {

	private static final String[] food = {"rice","noodle","meat","vege"};
	private static final String[] travel = {"Beijing","Shanghai","Guangzhou","Jiuzhaigou","USA","Japan"};
	private static final String[] drink = {"water","coffee","milk","tea","cola"};
	private static final String TYPE = "TYPE";
	private static final String NAME = "GroupName";
	private LinearLayout lp;
	private Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_selector);
		init();
	}

	private void init() {
		context = SelectorActivity.this;
		
		Button foodButton = (Button) findViewById(R.id.food);
		foodButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SelectorActivity.this,WheelActivity.class);
				intent.putExtra(TYPE, 1);
				startActivity(intent);
			}
		});
		
		Button travelButton = (Button) findViewById(R.id.travel);
		travelButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SelectorActivity.this,WheelActivity.class);
				intent.putExtra(TYPE, 2);
				startActivity(intent);
			}
		});
		
		Button drinkButton = (Button) findViewById(R.id.drink);
		drinkButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SelectorActivity.this,WheelActivity.class);
				intent.putExtra(TYPE, 3);
				startActivity(intent);
			}
		});
		
		ImageView back = (ImageView) findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(SelectorActivity.this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		});
		
		
		lp = (LinearLayout) findViewById(R.id.lp);
		SharedPreferences groups = getSharedPreferences("groups", 0);
		Set<String> groupsSet = groups.getStringSet("_groups", null);
		
//		Log.d("The Set Number", String.valueOf(groupsSet.size()));
		
		if (groupsSet != null) {
			Iterator<String> iterator = groupsSet.iterator();
			Log.e("hellhsk", "in  group....");
			while (iterator.hasNext()) {
				Log.e("hellhsk", "in  group....2");
				Button button = new Button(SelectorActivity.this);
//				button.setText(iterator.next());
//				iterator.next();
				button.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent intent = new Intent();
						intent.putExtra(NAME, ((Button)v).getText()+"");
						intent.putExtra(TYPE, 5);
						intent.setClass(SelectorActivity.this, WheelActivity.class);
						startActivity(intent);
					}
				});
				button.setText(iterator.next());
				LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 90);
				button.setLayoutParams(layoutParams);
				button.setBackgroundResource(R.drawable.btnbg);
				button.setTextColor(0xff3891B1);
				button.setTextSize(28);
				lp.addView(button);
				View vTemp = new View(context);
				layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 1);
				vTemp.setBackgroundColor(0xff3891B1);
				vTemp.setLayoutParams(layoutParams);
				lp.addView(vTemp);
			}
		}
	}
	
	private void openDialog() {
		/*new AlertDialog.Builder(this)
		.setTitle(R.string.setting_title)
		.setItems(R.array.type, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SelectorActivity.this,CustomerActivity.class);
				startActivity(intent);
			}
		}).show();*/
		LayoutInflater factory = LayoutInflater.from(this);
		final EditText editText = new EditText(this);
		new AlertDialog.Builder(this).setTitle("请输入用户组名").setView(
				editText).setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					@SuppressLint("NewApi") @Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						String data = editText.getText().toString();
						
						if (data != null && data != "" && !data.trim().isEmpty())
						{
							Intent intent = new Intent(SelectorActivity.this,CustomerActivity.class);
							intent.putExtra(NAME, data);
							startActivity(intent);
						}
					}
				})
				.setNegativeButton("取消", null).show();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.selector, menu);
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
