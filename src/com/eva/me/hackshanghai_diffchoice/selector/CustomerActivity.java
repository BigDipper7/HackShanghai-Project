package com.eva.me.hackshanghai_diffchoice.selector;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.eva.me.hackshanghai_diffchoice.R;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment.SavedState;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class CustomerActivity extends Activity {

	private ListView listview; 
	private Button addButton;
	private Button submitButton;
	public MyAdapter adapter;
	private String name;
	
	private static final String SAVE = "SAVE";
	private static final String TYPE = "TYPE";
	private static final String NAME = "GroupName";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_customer);
		
		init();	
	}
	
	@SuppressLint("NewApi") 
	private void init() {
		SharedPreferences groups = getSharedPreferences("groups", 0);
		listview = (ListView) findViewById(R.id.listView1);
		addButton = (Button) findViewById(R.id.add);
		submitButton = (Button) findViewById(R.id.submit);
		adapter = new MyAdapter(this);
		
		name = getIntent().getStringExtra(NAME);
		
		SharedPreferences.Editor editor = groups.edit();
		Set<String> groupSet = groups.getStringSet("_groups", new HashSet<String>());
		groupSet.add(name);
		editor.putStringSet("_groups", groupSet);
		editor.commit();
		
		Set<String> groupSet2 = groups.getStringSet("_groups", new HashSet<String>());
		Log.d("The Set Num", String.valueOf(groupSet2.size()));
		Iterator<String> iterator = groupSet2.iterator();
		while (iterator.hasNext()) {
			Toast.makeText(this, iterator.next(), TRIM_MEMORY_COMPLETE).show();
			
		}
		
		//Toast.makeText(this, name, TRIM_MEMORY_COMPLETE).show();
		
		listview.setAdapter(adapter);
		
		addButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				adapter.arr.add("");
				adapter.notifyDataSetChanged();
			}
		});
		
		submitButton.setOnClickListener(new OnClickListener() {
			
			@SuppressLint("NewApi") @Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Toast.makeText(v.getContext(), String.valueOf(adapter.getCount()),BIND_IMPORTANT).show();
				
				int count = 0;
				ArrayList<String> save = new ArrayList<String>();
				
				for (int i = 0; i < adapter.getCount(); i++) {
						if ((!adapter.arr.get(i).isEmpty()) && (!adapter.arr.get(i).trim().isEmpty())) {
							save.add(adapter.arr.get(i));
							count++;
						}
				}
				//Toast.makeText(v.getContext(), String.valueOf(count), TRIM_MEMORY_COMPLETE).show();
				if ( count != 0) {
					Intent intent = new Intent(CustomerActivity.this,WheelActivity.class);
					intent.putExtra(SAVE, save);
					intent.putExtra(TYPE, 4);
					intent.putExtra(NAME, name);
					startActivity(intent);
				}else {
					Toast.makeText(v.getContext(), "请填写内容！", TRIM_MEMORY_COMPLETE).show();
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.customer, menu);
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
	
	private class MyAdapter extends BaseAdapter {  
		  
        private Context context;  
        private LayoutInflater inflater;  
        public ArrayList<String> arr;  
        public MyAdapter(Context context) {  
            super();  
            this.context = context;  
            inflater = LayoutInflater.from(context);  
            arr = new ArrayList<String>();  
            for(int i=0;i<1;i++){    //listview初始化3个子项  
                arr.add("");  
            }  
        }  
        @Override  
        public int getCount() {  
            // TODO Auto-generated method stub  
            return arr.size();  
        }  
        @Override  
        public Object getItem(int arg0) {  
            // TODO Auto-generated method stub  
            return arg0;  
        }  
        @Override  
        public long getItemId(int arg0) {  
            // TODO Auto-generated method stub  
            return arg0;  
        }  
        @Override  
        public View getView(final int position, View view, ViewGroup arg2) {  
            // TODO Auto-generated method stub  
            if(view == null){  
                view = inflater.inflate(R.layout.listview_item, null);  
            }  
            final EditText edit = (EditText) view.findViewById(R.id.edit);  
            edit.setText(arr.get(position));    //在重构adapter的时候不至于数据错乱  
            Button del = (Button) view.findViewById(R.id.del);  
            edit.setOnFocusChangeListener(new OnFocusChangeListener() {  
                @Override  
                public void onFocusChange(View v, boolean hasFocus) {  
                    // TODO Auto-generated method stub  
                    if(arr.size()>0){  
                        arr.set(position, edit.getText().toString());  
                    }  
                }  
            });  
            del.setOnClickListener(new OnClickListener() {  
                @Override  
                public void onClick(View arg0) {  
                    // TODO Auto-generated method stub  
                    //从集合中删除所删除项的EditText的内容  
                    arr.remove(position);  
                    adapter.notifyDataSetChanged();  
                }  
            });  
            return view;  
        }  
    }  
}
