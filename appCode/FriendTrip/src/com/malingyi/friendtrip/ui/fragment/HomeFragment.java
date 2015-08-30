package com.malingyi.friendtrip.ui.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.malingyi.friendtrip.R;
import com.malingyi.friendtrip.model.BannerImage;
import com.malingyi.friendtrip.model.SpotsItem;
import com.malingyi.friendtrip.ui.activity.BasicActivity;
import com.malingyi.friendtrip.ui.activity.SearchActivity;
import com.malingyi.friendtrip.ui.activity.ViewDetailActivity;
import com.malingyi.friendtrip.ui.adapter.spotListAdapter;
import com.malingyi.friendtrip.ui.component.ErrorHintView;
import com.malingyi.friendtrip.ui.component.ErrorHintView.OperateListener;
import com.malingyi.friendtrip.utils.AsyncHttpUtil;
import com.malingyi.friendtrip.utils.JsonUtils;
import com.malingyi.friendwithme.ui.component.jazzviewpager.JazzyViewPager;
import com.malingyi.friendwithme.ui.component.jazzviewpager.JazzyViewPager.TransitionEffect;
import com.malingyi.friendwithme.ui.component.jazzviewpager.OutlineContainer;
import com.squareup.picasso.Picasso;

import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.print.PrintAttributes.Resolution;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("HandlerLeak")
public class HomeFragment extends SuperFragment  {
 
	private ErrorHintView mErrorHintView;
	private ScrollView mScrollView;
	public static final int VIEW_CONTENT = 1;
	/**显示断网**/
	public static final int VIEW_WIFIFAILUER = 2;
	/** 显示加载数据失败 **/
	public static final int VIEW_LOADFAILURE = 3;
	public static final int VIEW_LOADING = 4;

	
	protected static final int MSG_UPREFRESH_LIST = 11;	
	protected static final int MSG_DOWNREFRESH_LIST = 12;
	private Handler mHandler_list = new Handler(){

		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case MSG_UPREFRESH_LIST:
				    //说明List发生了上拉动刷新
				    //改变ShowList
				    LinkedList<SpotsItem> items = (LinkedList<SpotsItem>)msg.obj;
				    for(int i=0 ;i<items.size();i++){
				    	mShowList.addLast(items.get(i));
				    }
				    mSpotAdapter.notifyDataSetChanged();
				    mListView.onRefreshComplete();
				    //刷新完成
				    isRefreshing = false;
				break;
			case MSG_DOWNREFRESH_LIST:
				    //下拉加载
				    LinkedList<SpotsItem> items1 = (LinkedList<SpotsItem>)msg.obj;
				    mShowList.clear();
				    for(int i=0 ;i<items1.size();i++){
				    	mShowList.addFirst(items1.get(i));
				    }
				    mSpotAdapter.notifyDataSetChanged();
				    mListView.onRefreshComplete();
				    //刷新完成
				    isRefreshing = false;
				break;
			default:
				break;
			}
		}
		
	};
	private Handler mHandler_ShowView  = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case VIEW_CONTENT:
				showLoading(VIEW_CONTENT);
				break;
            case VIEW_LOADFAILURE:
				showLoading(VIEW_LOADFAILURE);
				break;
            case VIEW_LOADING:
				showLoading(VIEW_LOADING);
				break;
            case VIEW_WIFIFAILUER:
				showLoading(VIEW_WIFIFAILUER);
				break;
 
			default:
				break;
			}
		}
		
	}; 
	private Handler mHandler_banner = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			
			switch (msg.what) {
			case MSG_CHANGE_PHOTO:
				int index = mViewPager.getCurrentItem();
				if (index == picList.length - 1) {
					index = -1;
				}
				mViewPager.setCurrentItem(index + 1);
				mHandler_banner.sendEmptyMessageDelayed(MSG_CHANGE_PHOTO, PHOTO_CHANGE_TIME);
			  break;
			case MSG_BANNER_CHANGE:
				pageAdapter.notifyDataSetChanged();
				
				break;
			}
			
		}

		@Override
		public boolean sendMessageAtTime(Message msg, long uptimeMillis) {
			// TODO Auto-generated method stub
			return super.sendMessageAtTime(msg, uptimeMillis);
		}
		
	};
    //判断容器活动是否继承了接口
	@Override
	 public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mLoadListener = (OnFragmentLoadCompleteListener)activity;
		} catch (Exception e) {
			// TODO: handle exception
			 throw new ClassCastException(activity.toString() + " must implementOnFragmentLoadCompleteListener");
		}
	};
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		view = inflater.inflate(R.layout.fm_home, container, false);
		// TODO Auto-generated method stub
		
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		//主框架Activity创建之后再加载数据。
		
	    initViews();
		//从网上加载数据
		refreshOnlineStatus(true);
		
		//数据加载完毕之后
		mLoadListener.onLoadComplete();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	
    /**
     * 初始化首页控件
     */
	public void initViews(){
		mErrorHintView = (ErrorHintView) view.findViewById(R.id.hintView);
		mViewPager = (JazzyViewPager) view.findViewById(R.id.banner_jazzyview_home);
		mListView = (PullToRefreshListView) view.findViewById(R.id.spot_list_home);
		mTxt_allSpot = (TextView) view.findViewById(R.id.txt_allSpot);
		mTxt_allSpot.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(mContext, "所有景区 is clicked!", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(mContext,SearchActivity.class);
			    startActivity(intent);
			}
		});
		showLoading(VIEW_LOADING);
		/** 初始化轮播图片 **/
		initViewPagers();
		/** 初始化景点列表 **/
		initListView();
	}
	/**
	 * 初始化ListView
	 */
	public void initListView(){	
        
       mListView.setMode(Mode.BOTH);
       mListView.setScrollingWhileRefreshingEnabled(true);
       mListView.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在刷新。。。");
       mListView.getLoadingLayoutProxy(false, true).setReleaseLabel("释放刷新。。。");
       mListView.getLoadingLayoutProxy(false, true).setPullLabel("拉动就可以刷新。。");
       
       mListView.setOnRefreshListener(new OnRefreshListener<ListView>() {

		@Override
		public void onRefresh(PullToRefreshBase<ListView> refreshView) {
			// TODO Auto-generated method stub
			if(!isRefreshing){
				
			    isRefreshing=true;
			    if(mListView.isHeaderShown()){
			    	Toast.makeText(mContext, "未刷新，执行下拉。。", Toast.LENGTH_LONG).show();
			    	refreshOnlineStatus(true);
			    }
			    else if(mListView.isFooterShown()){
			    	Toast.makeText(mContext, "未刷新，执行上拉。。", Toast.LENGTH_LONG).show();
				    NextPage();
			    }
			}else {
				Toast.makeText(mContext, "正在刷新，亲！请等待！", Toast.LENGTH_LONG).show();
				mListView.onRefreshComplete();
				isRefreshing = false;
			}
		}
	});
	
	   mSpotAdapter = new SpotAdapter();
	   mListView.setAdapter(mSpotAdapter);
	   
	   
	}
	
	
	protected void NextPage() {
		// TODO Auto-generated method stub
		//本地,下一页
				Thread thread= new Thread( new Runnable() {
					LinkedList<SpotsItem> newItems = new LinkedList<SpotsItem>();
					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							Thread.sleep(4000);
							for (int i = 0; i < 4; i++) {
								SpotsItem item= new SpotsItem();
								
								newItems.addFirst(item);
								
							}
							for(int i = 0; i<newItems.size();i++){
								mSpotList.add(newItems.get(i));
							}
							//通知UI线程，数据改变。更新List视图
							mHandler_list.obtainMessage(MSG_UPREFRESH_LIST,newItems).sendToTarget();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
				thread.start();
	}

	protected void refreshOnlineStatus(boolean b) {
		// TODO Auto-generated method stub
	    
		if(b == true){
	       LoadingData();	
	    }	
	    else {
			
		}
	}

	/**
	 * 初始化ViewPager
	 */
	public void initViewPagers(){
		
		//设置广告栏图片数目
		imageViews = new ImageView[picList.length];
		for(int i = 0;i< imageViews.length ; i++){
			imageViews[i] = new ImageView(mContext);			
		}
		//设置mViewPager的当前页，翻页的效果，以及翻页的时间
		mViewPager.setTransitionEffect(TransitionEffect.CubeOut);
		mViewPager.setCurrentItem(0);
		
		//设置适配器，将加载的图片放入mViewPager中
		pageAdapter = new bannerAdapter();
		mViewPager.setAdapter(pageAdapter);
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
//				Toast.makeText(mContext, "page is selected!", Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
//				Toast.makeText(mContext, "page is scrolled!", Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
//				Toast.makeText(mContext, "page's scroll state is changed!", Toast.LENGTH_SHORT).show();
			}
		});
	}
	/**
	 * 从服务器端下载首页推荐信息
	 */
	public void LoadingData(){
		//发送网络请求
		String url="http://www.baidu.com";
		JSONObject object = new JSONObject();
		try {
			object.put("type", "1010");
			object.put("token", "");
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		/*
		RequestParams params = new RequestParams();
		params.put("jsonobject", object);
		AsyncHttpUtil.post(url, params, new JsonHttpResponseHandler("UTF-8"){

			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONObject errorResponse) {
				// TODO Auto-generated method stub
				super.onFailure(statusCode, headers, throwable, errorResponse);
				
                mHandler_ShowView.sendEmptyMessage(VIEW_WIFIFAILUER);
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, headers, response);
				try {
					String status = response.getString("status");
				    if(TextUtils.equals("0", status)){//成功
				    	
				    	JSONArray bannerListArray = response.getJSONArray("bannerList");
				    	JSONArray spotListArray  =  response.getJSONArray("spotList");
				    	LinkedList<SpotsItem> spotList = (LinkedList<SpotsItem>)JsonUtils.getInstance(SpotsItem.class, spotListArray);
				    	LinkedList<BannerImage> bannerList = (LinkedList<BannerImage>)JsonUtils.getInstance(BannerImage.class, bannerListArray);
				    	if(bannerList != null && bannerList.size()>0){
				    		mBannerList.clear();
				    		mBannerList.addAll(bannerList);
				    		mHandler_ShowView.sendEmptyMessage(VIEW_CONTENT);
				            mHandler_banner.sendEmptyMessage(MSG_BANNER_CHANGE);
				    	}
				    	else {
							
						}
				    	if(spotList != null && spotList.size()>0){
				    		mSpotList.clear();
				    		mSpotList.addAll(spotList);
				    		mHandler_ShowView.sendEmptyMessage(VIEW_CONTENT);
				    		mHandler_list.obtainMessage(MSG_DOWNREFRESH_LIST, spotList);
				    	}else {
							
						}
				    	
				    }
				    else {
						mHandler_ShowView.sendEmptyMessage(VIEW_LOADINGFAILURE);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					showLoading(VIEW_LOADFAILURE);
					e.printStackTrace();
				}
			}
			
		});
		*/
		//本地自定义请求
		Thread thread= new Thread( new Runnable() {
			LinkedList<SpotsItem> newItems = new LinkedList<SpotsItem>();
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(4000);
					for (int i = 0; i < 4; i++) {
						SpotsItem item= new SpotsItem();
						
						newItems.addFirst(item);
						
					}
					for(int i = 0; i<newItems.size();i++){						
						mSpotList.add(newItems.get(i));
					}
					mHandler_ShowView.sendEmptyMessage(VIEW_CONTENT);
					//改变BannerList
					mHandler_banner.sendEmptyMessageDelayed(MSG_CHANGE_PHOTO, PHOTO_CHANGE_TIME);
					//通知UI线程，数据改变。更新List视图
					mHandler_list.obtainMessage(MSG_DOWNREFRESH_LIST,newItems).sendToTarget();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		thread.start();
	}
	
	/**
	 * 显示正在加载界面
	 * 
	 * @param i
	 */
	private void showLoading(int i){
		mErrorHintView.setVisibility(View.INVISIBLE);
		mViewPager.setVisibility(View.INVISIBLE);
		mListView.setVisibility(View.INVISIBLE);
		
		
		switch(i){
		case VIEW_CONTENT:
			mErrorHintView.hideLoading();
			mViewPager.setVisibility(View.VISIBLE);
			mListView.setVisibility(View.VISIBLE);
			break;
			
		case 2:
			mErrorHintView.hideLoading();
			mErrorHintView.netError(new OperateListener() {
				@Override
				public void operate() {
					showLoading(VIEW_LOADING);
					LoadingData();
				}
			});
			break;
			
		case 3:
			mErrorHintView.hideLoading();
			mErrorHintView.loadFailure(new OperateListener() {
				@Override
				public void operate() {
					showLoading(VIEW_LOADING);
					LoadingData();
				}
			});
			break;
			
		case 4:
			mErrorHintView.loadingData();
			break;
		}
	}

	
	@Override
	public void retry() {
		// TODO Auto-generated method stub

	}
	/**
	 * 
	 */
	@Override
	public void netError() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pwdError() {
		// TODO Auto-generated method stub

	}
	// 容器活动必须要实现该接口,作为通知容器活动，该碎片加载数据成功
    public interface OnFragmentLoadCompleteListener{
    	public void onLoadComplete();
    }
	
	private class bannerAdapter extends PagerAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return picList.length;
		}

		@Override
		public boolean isViewFromObject(View view, Object obj) {
			// TODO Auto-generated method stub
			if (view instanceof OutlineContainer) {
				return ((OutlineContainer) view).getChildAt(0) == obj;
			} else {
				return view == obj;
			}
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			((ViewPager) container).removeView(mViewPager
					.findViewFromObject(position));
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			//从网络上加载图片
//			BannerImage item = bannerImages.get(position);
//			if ( !TextUtils.isEmpty(item.getPicUrl()) ){
//				Picasso.with(mContext).load(item.getPicUrl()).placeholder(R.color.ECECEC).error(R.color.ECECEC).into(mImageViews[position]);
//			} 
			//加载本地图片
			LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			imageViews[position].setImageResource(picList[position]);
		    imageViews[position].setLayoutParams(layoutParams);
		    imageViews[position].setScaleType(ScaleType.CENTER_CROP);
		    ((ViewPager) container).addView(imageViews[position], 0);
			mViewPager.setObjectForPosition(imageViews[position], position);
			return imageViews[position];
		}
		
	}
	private class SpotAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mShowList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return mShowList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder = null;
			if( convertView == null){
				convertView = View.inflate(mContext, R.layout.include_spot_item, null);
			    holder = new ViewHolder();
			    holder.cover = (ImageView) convertView.findViewById(R.id.img_spotCover);
			    holder.childNum = (TextView) convertView.findViewById(R.id.txt_childViewNum);
			    holder.describe = (TextView) convertView.findViewById(R.id.txt_describe_spot);
			    holder.focusNum = (TextView) convertView.findViewById(R.id.txt_focus_spot);
			    holder.spotName = (TextView) convertView.findViewById(R.id.txt_name_spot);
			    convertView.setTag(holder);
			}
			else {
			    holder = (ViewHolder) convertView.getTag();
			}
			holder.cover.setImageResource(picList[(position%picList.length)]);
	        //加载数据到holder里面
			convertView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Toast.makeText(mContext, "点击了景区", Toast.LENGTH_SHORT).show();
				    SurroundFragment targetFragment = new SurroundFragment();
				    //传URL到targetFragment里，然后加载数据
					((BasicActivity)mContext).changeFragment(targetFragment);
				}
			});
			
			return convertView;
		}
		
	}
	private class ViewHolder {
		ImageView cover;
		TextView spotName;
		TextView focusNum;
		TextView childNum;
		TextView describe;
	}
	
	/** 首页布局视图 */
	private View view;
    /** 轮播图片容器 */
    private JazzyViewPager mViewPager;
    /** 下拉刷新列表视图 */
    private PullToRefreshListView mListView;
    private LinkedList<SpotsItem> mSpotList = new LinkedList<SpotsItem>();
    private LinkedList<SpotsItem> mShowList = new LinkedList<SpotsItem>();
    private LinkedList<BannerImage> mBannerList = new LinkedList<BannerImage>();
    private boolean isRefreshing = false;
    private boolean isLoadSuccess = false;
    private static final int SHOW_NUM=10;  //每一页能够显示的数量
	private static final int UP_OR_LOAD_NUM=5; //每一次加载或者更新的数量
	/** 景点子项适配器 */
	private SpotAdapter mSpotAdapter ;
	/** 首页广告轮播图片数据*/
    private ArrayList<BannerImage>  bannerImages = new ArrayList<BannerImage>();
    /**装入轮播图片容器中的图片*/
    private ImageView imageViews[];
    /** 轮播图片自动切换时间 */
	private static final int PHOTO_CHANGE_TIME = 3000;
	private static final int MSG_CHANGE_PHOTO = 10;
	private static final int MSG_BANNER_CHANGE = 20;

	/** 首页轮播图的适配器 */
    private bannerAdapter pageAdapter;
	
    /** 按钮---“所有景区”*/
	private TextView mTxt_allSpot;
    
    private ImageView mIndexImage;
    
    /** 测试用的数据 **/
    OnFragmentLoadCompleteListener mLoadListener;
    private Integer[] picList = { R.drawable.emei1,R.drawable.emei2,R.drawable.emei3,
    		R.drawable.emei4,R.drawable.emei0}; //广告栏轮播图片
}
