package com.eva.me.hackshanghai_diffchoice;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.BreakIterator;
import java.util.List;

import com.eva.me.hackshanghai_diffchoice.listener.ZoomListener;
import com.eva.me.hackshanghai_diffchoice.listener.onZoomLargeListener;
import com.eva.me.hackshanghai_diffchoice.listener.onZoomSmallListener;
import com.eva.me.hackshanghai_diffchoice.selector.WheelActivity;
import com.eva.me.hackshanghai_diffchoice.util.FileUtils;
import com.eva.me.hackshanghai_diffchoice.util.ImagePiece;
import com.eva.me.hackshanghai_diffchoice.util.ImageUtils;
import com.eva.me.hackshanghai_diffchoice.util.TempForList;
import com.eva.me.hackshanghai_diffchoice.view.MyImageView;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Camera2FileActivity extends Activity {

	private int WIDTH, HIGHT;
	
	
	private Button btnCamera, btnConfirm, btnSelectFile;
	private Bitmap bm = null;
	private ImageView ivReveal;
	private TextView tvRight, tvTop;
	private String TAG = "Camera2FileActivity";
	private static String TYPE = "TYPE";
	private Context context;
	private MyImageView myImageView;
	
	public static int sizeVertical = 2;
	public static int sizeHorizontal = 2;
	//max value 
	private final int maxHorizontalSize  = 20;
	private final int maxVerticalSize = 20;
	
	private boolean hasResult  = false;
	
	//TEMP
	Handler mHandler;
	List<ImagePiece> lPieces;
	Runnable runnable;
	int label = 0;
	
	private void showToast(Context context, String str) {
		Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera2_file);
		init();
	}

	private void init() {
		DisplayMetrics mDisplayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
		WIDTH = mDisplayMetrics.widthPixels;
		HIGHT = mDisplayMetrics.heightPixels;
		Log.d(TAG, "WIDTH: "+WIDTH+" HIGHT: "+HIGHT);
		
		context = Camera2FileActivity.this;
		//init views
		btnCamera = (Button) findViewById( R.id.btnCamera);
		btnConfirm = (Button) findViewById(R.id.btnConfirm);
		btnSelectFile = (Button) findViewById(R.id.btnSelectFile);
		ivReveal = (ImageView) findViewById( R.id.imageView1);
		tvRight = (TextView) findViewById(R.id.tvRight);
		tvTop = (TextView) findViewById(R.id.tvTop);
		myImageView = (MyImageView) findViewById(R.id.myImageView1);
		myImageView.setWIDTHHIGHT(WIDTH, HIGHT);
		
		btnCamera.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent iStartCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				File f = null;
				f = FileUtils.getPhotoDir();
				iStartCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
				startActivityForResult(iStartCamera, 1);
//				startActivity(iStartCamera);
			}
			
		});
		
		btnConfirm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (bm != null) {
					lPieces = ImageUtils.split(bm, sizeHorizontal, sizeVertical);
					Intent intent = new Intent();
					intent.setClass(Camera2FileActivity.this, WheelActivity.class);
					TempForList.lPieces = lPieces;
					intent.putExtra(TYPE, 6);
					startActivity(intent);
//					mHandler.obtainMessage(1223).sendToTarget();
				}else {
					showToast(context, "没有图片~ 请先拍摄...");
				}
			}
		});
		
		btnSelectFile.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
                Intent intent = new Intent();  
                /* 开启Pictures画面Type设定为image */  
                intent.setType("image/*");  
                /* 使用Intent.ACTION_GET_CONTENT这个Action */  
                intent.setAction(Intent.ACTION_GET_CONTENT);   
                /* 取得相片后返回本画面 */  
                startActivityForResult(intent, 2);  
				
			}
		});
		
		//TEMP
		mHandler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				switch (msg.what) {
				case 1223:
					mHandler.postDelayed(runnable, 1000);
					break;

				default:
					break;
				}
			};
		};
		
		runnable = new Runnable() {
			
			@Override
			public void run() {
				if (label >= lPieces.size()) {
					return;
				}
				ImagePiece temp = lPieces.get(label);
//				ivReveal.setImageBitmap(temp.bitmap);
				myImageView.setBitmap(temp.bitmap);
				label++;
				mHandler.postDelayed(runnable, 1000);
			}
		};
		
		ZoomListener mZoomListener = new ZoomListener();
		mZoomListener.setOnZoomLargeListener(new onZoomLargeListener() {
			
			@Override
			public void onZoomLarge(int direction) {
				switch (direction) {
				case ZoomListener.LeftRight:
//					sizeHorizontal+=1;
					sizeHorizontal = (sizeHorizontal+1)>maxHorizontalSize? maxHorizontalSize: (sizeHorizontal+1) ;
					tvTop.setText("横向格子数："+sizeHorizontal);
					myImageView.setSizeHorizontal(sizeHorizontal);
					break;
				case ZoomListener.UpDown:
//					sizeVertical+=1;
					sizeVertical = (sizeVertical+1) > maxVerticalSize?maxVerticalSize:(sizeVertical+1);
					tvRight.setText("纵向格子数："+sizeVertical);
					myImageView.setSizeVertical(sizeVertical);
					break;

				default:
					break;
				}
			}
		});
		mZoomListener.setOnZoomSmallListener(new  onZoomSmallListener() {
			
			@Override
			public void onZoomSmall(int direction) {
				switch (direction) {
				case ZoomListener.LeftRight:
//					sizeHorizontal-=1;
					sizeHorizontal = (sizeHorizontal-1)<2?2:(sizeHorizontal-1);
					tvTop.setText("当前横向格子数："+sizeHorizontal);  
					myImageView.setSizeHorizontal(sizeHorizontal);
					break;
				case ZoomListener.UpDown:
//					sizeVertical-=1;
					sizeVertical = (sizeVertical-1)<2?2:(sizeVertical-1);
					tvRight.setText("当前纵向格子数："+sizeVertical);
					myImageView.setSizeVertical(sizeVertical);
					break;

				default:
					break;
				}
			}
		});
//		ivReveal.setOnTouchListener(mZoomListener);
		myImageView.setOnTouchListener(mZoomListener);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == 1) {

//				Bundle bundle = data.getExtras();//用这两句就有问题 妈蛋。。。也是醉了
//				Bitmap bm = (Bitmap) bundle.get("data");
				Log.d(TAG, "onActivityResult in");//曲线救国喽~
				bm = FileUtils.getImage(HIGHT, WIDTH);//压缩bitmap
//				ivReveal.setImageBitmap(bm);
				myImageView.setBitmap(bm);
				
				sizeHorizontal = 2;
				sizeVertical = 2;
				hasResult = true;
				
			} else if (requestCode == 2) {
	            Uri uri = data.getData();  
	            Log.e("uri", uri.toString());  
	            bm = FileUtils.getImageFromUri(uri, HIGHT, WIDTH, context);
				myImageView.setBitmap(bm);

				sizeHorizontal = 2;
				sizeVertical = 2;
			} 
			
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
