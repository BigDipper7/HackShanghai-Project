package com.eva.me.hackshanghai_diffchoice.view;

import com.eva.me.hackshanghai_diffchoice.Camera2FileActivity;
import com.eva.me.hackshanghai_diffchoice.util.FileUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class MyImageView extends View {
	private Bitmap bitmap = null;
	private int width=0, height=0, sizeHorizontal = 2, sizeVertical = 2;
	private final String TAG = "MyImageView";
//	public static boolean needChanged = false;
	private int WIDTH =1, HIGHT=1;
	

	public MyImageView(Context context) {
		super(context);
		initConstructor();
	}

	public MyImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initConstructor();
	}

	public MyImageView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initConstructor();
	}

	private void initConstructor() {
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		initOnDraw(canvas);
	}

	private void initOnDraw(Canvas canvas) {
		Log.d(TAG, "IN onDraw");
		int xMo = (WIDTH - width)/2;
		int yMo = (HIGHT - height)/2;
		
		if (bitmap != null) {
			Log.d(TAG, "xMo "+xMo+" yMo "+yMo+" WIDTH "+WIDTH+" HIGHT "+HIGHT);
			canvas.drawBitmap(bitmap, 0+xMo, 0+yMo,null);
//			width = 480;
//			height = 800;
			Log.d(TAG, "width "+ width+" height "+height);
		}
		Paint mPaint = new Paint();
		mPaint.setColor(Color.YELLOW);
//		mPaint.setStrokeWidth(2);
		
		int horizontalGap = width/sizeHorizontal;
		for(int i=0; i<sizeHorizontal;i++) {
			//画竖直线
			int realX = i*horizontalGap;
			if(realX != 0) {
				Log.d(TAG, "draw horizontal realX"+realX);
				canvas.drawLine(realX+xMo, 0+yMo, realX+xMo, height+yMo, mPaint);
			}
		}
		int verticalGap = height/sizeVertical;
		for(int i=0; i<sizeVertical; i++) {
			//画水平线
			int realY = i*verticalGap;
			if (realY !=0) {
				canvas.drawLine(0+xMo, realY+yMo, width+xMo, realY+yMo, mPaint);
			}
		}
		
	}

	public int getSizeHorizontal() {
		return sizeHorizontal;
	}

	public void setSizeHorizontal(int sizeHorizontal) {
		this.sizeHorizontal = sizeHorizontal;
		invalidate();
	}

	public int getSizeVertical() {
		return sizeVertical;
	}

	public void setSizeVertical(int sizeVertical) {
		this.sizeVertical = sizeVertical;
		invalidate();
	}

	
	public Bitmap getBitmap() {
		return bitmap;
	}

	
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
		Log.d(TAG, "in setBitmap");
		width = bitmap.getWidth();
		height = bitmap.getHeight();
		//提前设置防止出现bitmap过于大的情况
		invalidate();
	}

	public int getWIDTH() {
		return WIDTH;
	}

	public void setWIDTHHIGHT(int wIDTH, int hIGHT) {
		WIDTH = wIDTH;
		HIGHT = hIGHT;
		invalidate();
	}

	public int getHIGHT() {
		return HIGHT;
	}

	
}
