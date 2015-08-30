package com.malingyi.friendtrip.ui.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.malingyi.friendtrip.R;
import com.malingyi.friendtrip.ui.fragment.SuperFragment;

import android.annotation.SuppressLint;
import android.database.DataSetObserver;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.AutoCompleteTextView.OnDismissListener;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SearchActivity extends SuperFragmentActivity {
    
//	PullToRefreshExpandableListView mListView;
	ExpandableListView m_allSpotListView;
	ListView           m_hotSpotListView;
	
	ViewPager viewPager;
	viewpageAdapter pageAdapter;
	ListView           m_resultListView;
	/**分别是 所有景区、热门景区、搜索结果的页卡**/
	View allSpotView,hotSpotvView,resultView;
	
	ArrayList<View> viewList = new ArrayList<View>();
	FrameLayout frameLayout;
    /**控件**/
	ImageButton btn_back;
	ImageButton btn_search;
	AutoCompleteTextView edit_search;
	TextView allSpotsTab;
	TextView hotSpotsTab;
	private static final int MSG_LIST_CHANGDE = 0;
	@SuppressLint("HandlerLeak")
	private Handler mHandler_UI = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case MSG_LIST_CHANGDE:
				
//				mAdapter.notifyDataSetChanged();
				// Call onRefreshComplete when the list has been refreshed.
//				mListView.onRefreshComplete();
				break;

			default:
				break;
			}
		}
		
	};
	
	/**测试数据**/
	private static final String[] GROUP_DATA_STRING = {"四川","湖南","湖北","河南","广东","浙江","江苏","广西"};
	private static final String[][] CHILD_DATA_STRING = {{"成都","峨眉山","雅安","绵阳","德阳"},{"长沙","岳阳","湘西","湘潭","株洲"},
		                                    {"武汉","荆门","恩施"},{"郑州","开封","登封"},{"广州","佛山","深圳","中山"},
		                                    {"杭州","宁波","温州","嘉兴","绍兴"},{"南京","镇江","常州","苏州"}
		                                    ,{"南宁","桂林","北海","玉林"}};
	
	private static final String[] HOT_DATA_STRING = {"成都","峨眉山","武汉","西藏","长白山","桂林","西安","凤凰","泰山"};
	/**Key 是景区名   value 是Id **/
	private List<HashMap<String, String>> ResultList = new ArrayList<HashMap<String, String>>();
	private List<HashMap<String, String>> groupData = new LinkedList< HashMap<String, String>>();
	private List<List<HashMap<String, String>>> childData = new LinkedList<List< HashMap<String, String>>>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		
		initViews();
	}

	private void initViews() {
		// TODO Auto-generated method stub
		frameLayout = (FrameLayout) findViewById( R.id.result_container_search);
		btn_back = (ImageButton) findViewById(R.id.btn_back_search);
		btn_search = (ImageButton) findViewById(R.id.btn_search);
		edit_search = (AutoCompleteTextView) findViewById(R.id.edit_search_search);		
		allSpotsTab = (TextView) findViewById(R.id.txt_all_search);
		hotSpotsTab = (TextView) findViewById(R.id.txt_hot_search);
		
		initViewPager();
		initResultView();
		//默认ViewPager在布局文件里面，因此不用添加
//		frameLayout.addView(viewPager);
		frameLayout.addView(resultView);
		btn_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(mContext, "Back clicked!", Toast.LENGTH_SHORT).show();
			    finish();
			}
		});
		btn_search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(mContext, "Search Button Clicked!", Toast.LENGTH_SHORT).show();
			}
		});
		//
		edit_search.addTextChangedListener(new TextWatcher() {
			int length = 0;
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				length = s.length();
				Toast.makeText(mContext, "Text Changed!", Toast.LENGTH_SHORT).show();
				if(length == 0){
					if(resultView.isShown())
						resultView.setVisibility(View.INVISIBLE);
					if(!viewPager.isShown())
					    viewPager.setVisibility(ViewPager.VISIBLE);
				}
				else {
					if(viewPager.isShown())
					    viewPager.setVisibility(ViewPager.INVISIBLE);			    
					if(!resultView.isShown())
					    resultView.setVisibility(View.VISIBLE);
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
		
	    allSpotsTab.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				viewPager.setCurrentItem(0);
			}
		});
	    hotSpotsTab.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				viewPager.setCurrentItem(1);
			}
		});
	}
    private void initResultView() {
		// TODO Auto-generated method stub
        resultView = View.inflate(mContext, R.layout.include_search_result, null);			
		m_resultListView = (ListView) resultView.findViewById(R.id.searchResult_listView);			    
        m_resultListView.setAdapter(mresultListAdapter);
        //默认不可见
        resultView.setVisibility(View.INVISIBLE);
	}

	private void initViewPager() {
		// TODO Auto-generated method stub
		viewPager = (ViewPager) findViewById(R.id.viewPager_searched);
		
		
		allSpotView = View.inflate(mContext, R.layout.include_expandablelistview, null);
		hotSpotvView = View.inflate(mContext, R.layout.include_hotspots, null);
	    
		m_allSpotListView = (ExpandableListView) allSpotView.findViewById(R.id.expandableListView);
		m_hotSpotListView = (ListView) hotSpotvView.findViewById(R.id.hotSpots_listView);
	    
		
	    m_allSpotListView.setAdapter(mExpandbleAdapter);
	    m_hotSpotListView.setAdapter(mhotListAdapter);
	    //将所有的Group展开
	    for ( int i=0;i< GROUP_DATA_STRING.length;i++) {
	    	m_allSpotListView.expandGroup(i);
		}      
	    viewList.add(allSpotView);
        viewList.add(hotSpotvView);
        pageAdapter = new viewpageAdapter();
		viewPager.setAdapter(pageAdapter);
		
viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				switch (arg0) {
				case 0:
					ChangeTab(0);
					break;
				case 1:
					ChangeTab(1);
					break;
				default:
					break;
				}
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	protected void ChangeTab(int i) {
		// TODO Auto-generated method stub
		CleanTabColor();
		switch (i) {
		case 0:
			allSpotsTab.setBackgroundResource(R.color.grey);
			break;
		case 1:
			hotSpotsTab.setBackgroundResource(R.color.grey);
			break;
		default:
			break;
		}
	}
    protected void CleanTabColor() {
		allSpotsTab.setBackgroundResource(R.color.white);
    	hotSpotsTab.setBackgroundResource(R.color.white);
	}
	private void LoadingData(){
    	//加载数据
    	new GetDataTask().execute();
    }
	@Override
	public void retry() {
		// TODO Auto-generated method stub

	}

	@Override
	public void netError() {
		// TODO Auto-generated method stub

	}
	private class viewpageAdapter extends PagerAdapter{
        
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return viewList.size();
		}
        
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			container.removeView(viewList.get(position));
		}

		@Override
		public int getItemPosition(Object object) {
			// TODO Auto-generated method stub
			return super.getItemPosition(object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			container.addView(viewList.get(position));
			
			return viewList.get(position);
		}
		
	};
     private BaseAdapter               mresultListAdapter = new BaseAdapter() {
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			LinearLayout newView = null;
			if(convertView == null){
				convertView = View.inflate(mContext, R.layout.include_result_child, null);
				
				newView = new LinearLayout(mContext);
//				newView = (LinearLayout) 
				convertView.setTag(newView);
			}
			newView = (LinearLayout) convertView.getTag();
			
			return convertView;
		}
		
		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}
		
		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return ResultList.get(position);
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return ResultList.size();
		}
	};
	private BaseAdapter               mhotListAdapter = new BaseAdapter() {
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			TextView newView = null;
			if(convertView == null){
				convertView = View.inflate(mContext, R.layout.include_result_child, null);
				newView = new TextView(mContext);
				newView = (TextView) convertView.findViewById(R.id.txt_child_search);
				newView.setTextSize(20);
				convertView.setTag(newView);
			}
			newView = (TextView) convertView.getTag();
			newView.setText((String)getItem(position));
			//设置监听器，点击后进入详情页
			return convertView;
		}
		
		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}
		
		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return HOT_DATA_STRING[position];
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return HOT_DATA_STRING.length;
		}
	};
    private BaseExpandableListAdapter mExpandbleAdapter = new BaseExpandableListAdapter() {
			
			@Override
			public boolean isChildSelectable(int groupPosition, int childPosition) {
				// TODO Auto-generated method stub
					
				return true;
			}
			
			@Override
			public boolean hasStableIds() {
				// TODO Auto-generated method stub
				
				return false;
			}
			
			@Override
			public View getGroupView(int groupPosition, boolean isExpanded,
					View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				TextView groupView = null;
				if(convertView == null){
					convertView = View.inflate(mContext, R.layout.include_result_group, null);
					groupView = (TextView) convertView.findViewById(R.id.txt_group_search);
				    convertView.setTag(groupView);
				}
				groupView = (TextView) convertView.getTag();
				groupView.setText((String)getGroup(groupPosition));			
				return convertView;
			}
			
			@Override
			public long getGroupId(int groupPosition) {
				// TODO Auto-generated method stub
				return groupPosition;
			}
			
			@Override
			public int getGroupCount() {
				// TODO Auto-generated method stub
				return GROUP_DATA_STRING.length;
			}
			
			@Override
			public Object getGroup(int groupPosition) {
				// TODO Auto-generated method stub
				return GROUP_DATA_STRING[groupPosition];
			}
			
			@Override
			public int getChildrenCount(int groupPosition) {
				// TODO Auto-generated method stub
				return CHILD_DATA_STRING[groupPosition].length;
			}
			
			@Override
			public View getChildView(int groupPosition, int childPosition,
					boolean isLastChild, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				TextView childView = null;
				if(convertView == null){
					convertView = View.inflate(mContext, R.layout.include_result_child, null);
					childView = (TextView) convertView.findViewById(R.id.txt_child_search);
				    convertView.setTag(childView);
				}
				childView = (TextView) convertView.getTag();
				childView.setText((String)getChild(groupPosition, childPosition));
				childView.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Toast.makeText(mContext, "child Cliked!", Toast.LENGTH_SHORT).show();
					}
				});
				return convertView;
			}
			
			@Override
			public long getChildId(int groupPosition, int childPosition) {
				// TODO Auto-generated method stub
				return childPosition;
			}
			
			@Override
			public Object getChild(int groupPosition, int childPosition) {
				// TODO Auto-generated method stub
				return CHILD_DATA_STRING[groupPosition][childPosition];
			}
		};
	private class GetDataTask extends AsyncTask<Void, Void, String[]> {

		@Override
		protected String[] doInBackground(Void... params) {
			// Simulates a background job.
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
			}
			return null;
		}

		@Override
		protected void onPostExecute(String[] result) {
			
			mHandler_UI.sendEmptyMessage(MSG_LIST_CHANGDE);
			super.onPostExecute(result);
		}
	}
}
