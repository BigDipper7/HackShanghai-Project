package com.eva.me.hackshanghai_diffchoice.selector;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.eva.me.hackshanghai_diffchoice.R;
import com.eva.me.hackshanghai_diffchoice.util.ImagePiece;
import com.eva.me.hackshanghai_diffchoice.util.TempForList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import antistatic.spinnerwheel.AbstractWheel;
import antistatic.spinnerwheel.OnWheelChangedListener;
import antistatic.spinnerwheel.OnWheelScrollListener;
import antistatic.spinnerwheel.WheelVerticalView;
import antistatic.spinnerwheel.adapters.AbstractWheelTextAdapter;
import antistatic.spinnerwheel.adapters.ArrayWheelAdapter;
import antistatic.spinnerwheel.adapters.NumericWheelAdapter;



public class WheelActivity extends Activity {
	
	private static final String[] food = {"rice","noodle","meet","vege"};
	private static final String[] travel = {"Beijing","Shanghai","Guangzhou","Jiuzhaigou","USA","Japan"};
	private static final String[] drink = {"water","coffee","milk","tea","cola"};
	
	private static final String TYPE = "TYPE";
	private static final String SAVE = "SAVE";
	private static final String NAME = "GroupName";
	private ImageView image;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_wheel);
		image = (ImageView) findViewById(R.id.scale);  
		
		ImageView back = (ImageView) findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
//				Intent intent = new Intent();
//				intent.setClass(WheelActivity.this, SelectorActivity.class);
//				startActivity(intent);
				finish();
			}
		});
		initWheel(R.id.option);

        
        Button mix = (Button)findViewById(R.id.random);
        mix.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mixWheel(R.id.option);
            }
        });
        
    }
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
		image.setBackgroundResource(R.drawable.frame);  
        AnimationDrawable anim = (AnimationDrawable) image.getBackground();  
        anim.start();  
	}
	
    
    // Wheel scrolled flag
    private boolean wheelScrolled = false;
    
    // Wheel scrolled listener
    OnWheelScrollListener scrolledListener = new OnWheelScrollListener() {
        public void onScrollingStarted(AbstractWheel wheel) {
            wheelScrolled = true;
        }
        public void onScrollingFinished(AbstractWheel wheel) {
            wheelScrolled = false;
        }
    };
    
    // Wheel changed listener
    private OnWheelChangedListener changedListener = new OnWheelChangedListener() {
        public void onChanged(AbstractWheel wheel, int oldValue, int newValue) {

        }
    };
    
    /**
     * Updates entered PIN status
     */

    /**
     * Initializes spinnerwheel
     * @param id the spinnerwheel wheel Id
     */
    private void initWheel(int id) {
        AbstractWheel wheel = getWheel(id);
        
        int type = getIntent().getIntExtra(TYPE,0);
        String groupname = getIntent().getStringExtra(NAME);
        
        switch (type) {
		case 1:
	        wheel.setViewAdapter(new ArrayWheelAdapter<String>(this, food));
	        wheel.setCurrentItem((int) (Math.random() * food.length));
			break;
		case 2:
	        wheel.setViewAdapter(new ArrayWheelAdapter<String>(this, travel));
	        wheel.setCurrentItem((int) (Math.random() * travel.length));
	        break;
		case 3:
	        wheel.setViewAdapter(new ArrayWheelAdapter<String>(this, drink));
	        wheel.setCurrentItem((int) (Math.random() * drink.length));
	        break;
		case 4:
			ArrayList<String> save = getIntent().getStringArrayListExtra(SAVE);
			String[] saveStrings;
			Set<String> saveSet;
	        if (groupname != null) {
				SharedPreferences group = getSharedPreferences(groupname, 0);
				SharedPreferences.Editor editor = group.edit();
				if( save != null){
					saveStrings = new String[save.size()];
					saveSet = new HashSet<String>();
					for (int i = 0; i < save.size(); i++) {
						saveStrings[i] = (String)save.get(i);
						saveSet.add((String)save.get(i));
					}
					wheel.setViewAdapter(new ArrayWheelAdapter<String>(this, saveStrings));
					wheel.setCurrentItem((int)(Math.random() * saveStrings.length));
					editor.putStringSet(groupname, saveSet);
					editor.commit();
				}
			}
			break;
		case 5:
			Set<String> saveSet2;
			String[] saveStrings2;
			if (groupname != null) {
				SharedPreferences group = getSharedPreferences(groupname, 0);
				SharedPreferences.Editor editor = group.edit();
				saveSet2 = group.getStringSet(groupname, null);
				if (saveSet2 != null) {
					saveStrings2 = new String[saveSet2.size()];
					Iterator<String> iterator = saveSet2.iterator();
					int i = 0;
					while (iterator.hasNext()) {
						saveStrings2[i] = iterator.next();
						i++;
					}
					wheel.setViewAdapter(new ArrayWheelAdapter<String>(this, saveStrings2));
					wheel.setCurrentItem(0);
				}
			}
			break;
		case 6:

			wheel.setVisibleItems(3);
			wheel.setViewAdapter(new imgAdapter(this));
			wheel.setCurrentItem(0);
		default:
			break;
		}
        
        wheel.addChangingListener(changedListener);
        wheel.addScrollingListener(scrolledListener);
        wheel.setCyclic(true);
        wheel.setInterpolator(new AnticipateOvershootInterpolator());
        
    }
    
    /**
     * Returns spinnerwheel by Id
     * @param id the spinnerwheel Id
     * @return the spinnerwheel with passed Id
     */
    private AbstractWheel getWheel(int id) {
        return (AbstractWheel) findViewById(id);
    }
    
    /**
     * Tests entered PIN
     * @param v1
     * @param v2
     * @param v3
     * @param v4
     * @return true 
     */
    /*private boolean testPin(int v1, int v2, int v3, int v4) {
        return testWheelValue(R.id.passw_1, v1) && testWheelValue(R.id.passw_2, v2) &&
                testWheelValue(R.id.passw_3, v3) && testWheelValue(R.id.passw_4, v4);
    }*/
    
    /**
     * Tests spinnerwheel value
     * @param id the spinnerwheel Id
     * @param value the value to test
     * @return true if spinnerwheel value is equal to passed value
     */
    private boolean testWheelValue(int id, int value) {
        return getWheel(id).getCurrentItem() == value;
    }
    
    /**
     * Mixes spinnerwheel
     * @param id the spinnerwheel id
     */
    private void mixWheel(int id) {
        AbstractWheel wheel = getWheel(id);
        wheel.scroll(-25 + (int)(Math.random() * 50), 2000);
    }
    
    private class imgAdapter extends AbstractWheelTextAdapter {

    	List<ImagePiece> imgList = TempForList.lPieces;
    	
		protected imgAdapter(Context context) {
			super(context, R.layout.img_item, NO_RESOURCE);
			// TODO Auto-generated constructor stub
		}

		@Override
		public int getItemsCount() {
			// TODO Auto-generated method stub
			return imgList.size();
		}

		@Override
		protected CharSequence getItemText(int index) {
			// TODO Auto-generated method stub
			return String.valueOf(imgList.get(index));
		}
		
		@Override
		public View getItem(int index, View cachedView, ViewGroup parent) {
			View view = super.getItem(index, cachedView, parent);
			ImageView img = (ImageView) view.findViewById(R.id.flag);
			Bitmap bmBitmap = imgList.get(index).bitmap;
			img.setImageBitmap(bmBitmap);
			return view;
		}
    	
    }
}
     