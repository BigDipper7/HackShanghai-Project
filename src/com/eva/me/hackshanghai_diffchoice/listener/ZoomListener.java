package com.eva.me.hackshanghai_diffchoice.listener;

import android.R.integer;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class ZoomListener implements OnTouchListener{
	private int mode = 0;
	private static final String  TAG = "ZoomListener ";
	private float oldDist = 0f;
	private float measureDis  = 10f;//达到一定程度的距离才能够识别
	
	public final static int UpDown = 1;
	public final static int LeftRight = 2;
	public final static int NONE = -1;
	private boolean hasDirection = false;
	
	private static int direction = NONE;
	
	private static onZoomLargeListener mZoomLargeListener = null;
	private static onZoomSmallListener mZoomSmallListener = null;
	
	public static void setOnZoomLargeListener(onZoomLargeListener mOnZoomLargeListener) {
		mZoomLargeListener = mOnZoomLargeListener;
	}
	
	public static void setOnZoomSmallListener(onZoomSmallListener mOnZoomSmallListener) {
		mZoomSmallListener = mOnZoomSmallListener;
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		
		case MotionEvent.ACTION_DOWN:
			mode  = 1;
			break;
			
		case MotionEvent.ACTION_UP:
			mode = 0;
			direction = NONE;
			hasDirection = false;
			break;
			
		case MotionEvent.ACTION_POINTER_UP:
			mode -= 1;
			break;
			
		case MotionEvent.ACTION_POINTER_DOWN:
			oldDist = spaceing(event);
			mode += 1;
			break;
			
		case MotionEvent.ACTION_MOVE:
			if (mode >= 2) {
				float newDist = spaceing(event);
				if (newDist > (oldDist+3+measureDis)) {
					//large
					if (mZoomLargeListener != null) {
						mZoomLargeListener.onZoomLarge(direction);
					}
				}else if (newDist < (oldDist+3+measureDis)) {
					//small
					if (mZoomSmallListener!=null) {
						mZoomSmallListener.onZoomSmall(direction);
					}
				}
			}
			break;

		default:
			break;
		} 
		return true;
	}

	private float spaceing(MotionEvent event) {
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		
		if(!hasDirection) {
			float xAbs = Math.abs(x);
			float yAbs = Math.abs(y);
			
			direction = xAbs>yAbs?LeftRight:UpDown;
			hasDirection = true;
		}
		
		return FloatMath.sqrt(x * x + y * y);
	}

}
