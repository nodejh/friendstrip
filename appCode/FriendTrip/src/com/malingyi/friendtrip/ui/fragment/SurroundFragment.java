package com.malingyi.friendtrip.ui.fragment;

import java.util.ArrayList;
import java.util.LinkedList;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.malingyi.friendtrip.R;
import com.malingyi.friendtrip.model.BannerImage;
import com.malingyi.friendtrip.model.SpotsItem;
import com.malingyi.friendtrip.model.ViewItem;
import com.malingyi.friendtrip.ui.activity.ViewDetailActivity;
import com.malingyi.friendtrip.ui.activity.WantgoActivity;
import com.malingyi.friendwithme.ui.component.jazzviewpager.JazzyViewPager;
import com.malingyi.friendwithme.ui.component.jazzviewpager.OutlineContainer;
import com.malingyi.friendwithme.ui.component.jazzviewpager.JazzyViewPager.TransitionEffect;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;

@SuppressLint("HandlerLeak")
public class SurroundFragment extends SuperFragment {
    
	private View view;
	/**布局控件**/
	private JazzyViewPager mBannerViewPager;   //轮播图
	private PullToRefreshListView mListView;//景点刷新列表
	private LinearLayout mComNum;          //评论
	private LinearLayout mGoneNum;         //去过
	private LinearLayout mWantNum;         //想去	
	private TextView mSpotName;      //景区名
	private TextView mDescribe;      //景区描述
	
	/**适配器**/
	private bannerAdapter mBannerAdapter;
	private ViewAdapter mListAdapter;
	
	/**数据类**/
	private LinkedList<ViewItem> viewList = new LinkedList<ViewItem>();
	private LinkedList<ViewItem>  showList = new LinkedList<ViewItem>();
	/** 广告轮播图片数据*/
    private ArrayList<BannerImage>  bannerImages = new ArrayList<BannerImage>();
    /**装入轮播图片容器中的图片*/
    private ImageView imageViews[];
	protected boolean isRefreshing = false;
    /** 轮播图片自动切换时间 */
	private static final int PHOTO_CHANGE_TIME = 3000;	
	/**自定义消息**/
	private static final int MSG_BANNER_CHANGE = 2;
    protected static final int MSG_UPREFRESH_LIST = 3;	
	protected static final int MSG_DOWNREFRESH_LIST = 4;
	
	/**测试用的数据**/
	public int[] picList = {R.drawable.chengdu1,R.drawable.chengdu2,R.drawable.chengdu3,
			R.drawable.chengdu4,R.drawable.chengdu5,R.drawable.chengdu6,R.drawable.chengdu7,};
	public int[] coverList ={R.drawable.chengdu8,R.drawable.chengdu2,R.drawable.chengdu10,
			R.drawable.chengdu11,R.drawable.chengdu6,R.drawable.chengdu5,R.drawable.chengdu14};
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		initViews();
		//从网上加载数据
		refreshOnlineStatus(true);
	}



	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fm_surround,container,false);
		return view;
	    
	}
	private void initViews() {
		// TODO Auto-generated method stub
		mBannerViewPager = (JazzyViewPager) view.findViewById(R.id.banner_surround);		
		mListView = (PullToRefreshListView) view.findViewById(R.id.spot_list_surround);
		mSpotName = (TextView) view.findViewById(R.id.txt_name_surround);
		mDescribe = (TextView) view.findViewById(R.id.txt_describe_surround);
		mComNum   = (LinearLayout) view .findViewById(R.id.linear_comNum_surround);
		mGoneNum  = (LinearLayout) view.findViewById(R.id.linear_goneNum_surround);
		mWantNum  = (LinearLayout) view.findViewById(R.id.linear_wantNum_surround);
	    initViewPagers();
	    initListView();
		setListener();
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
			if(!isRefreshing ){
				
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
	
	   mListAdapter = new ViewAdapter();
	   mListView.setAdapter(mListAdapter);	   
	   
	}
	protected void NextPage() {
		// TODO Auto-generated method stub
		//本地,下一页
		Thread thread= new Thread( new Runnable() {
			LinkedList<ViewItem> newItems = new LinkedList<ViewItem>();
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(4000);
					for (int i = 0; i < 4; i++) {
						ViewItem item= new ViewItem();
						
						newItems.addFirst(item);
						
					}
					for(int i = 0; i<newItems.size();i++){
						viewList.add(newItems.get(i));
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
		mBannerViewPager.setTransitionEffect(TransitionEffect.CubeOut);
		mBannerViewPager.setCurrentItem(0);
		mHandler_banner.sendEmptyMessageDelayed(MSG_BANNER_CHANGE, PHOTO_CHANGE_TIME);
		
		//设置适配器，将加载的图片放入mViewPager中
		mBannerAdapter = new bannerAdapter();
		mBannerViewPager.setAdapter(mBannerAdapter);
		mBannerViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
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
	private void setListener(){
		mBannerViewPager.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
	    mListView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
	    mDescribe.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
	    mComNum.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
	    mGoneNum.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
	    mWantNum.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(mContext, "点击想去", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(mContext,WantgoActivity.class);
				startActivity(intent);
			}
		});
	
	}
	private void LoadingData() {
		// TODO Auto-generated method stub
		//发送网络请求
				String url;
//				AsyncHttpUtil.post(url, params, new JsonHttpResponseHandler("UTF-8"){
					
//				});
				
				//本地自定义请求
				Thread thread= new Thread( new Runnable() {
					LinkedList<ViewItem> newItems = new LinkedList<ViewItem>();
					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							Thread.sleep(4000);
							for (int i = 0; i < 4; i++) {
								ViewItem item= new ViewItem();
								
								newItems.addFirst(item);
								
							}
							for(int i = 0; i<newItems.size();i++){						
								viewList.add(newItems.get(i));
							}
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

	@Override
	public void retry() {
		// TODO Auto-generated method stub

	}

	@Override
	public void netError() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pwdError() {
		// TODO Auto-generated method stub

	}
	
	
	private Handler mHandler_list = new Handler(){
		
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case MSG_DOWNREFRESH_LIST:
				//说明List发生了上拉动刷新
			    //改变ShowList
			    LinkedList<ViewItem> items = (LinkedList<ViewItem>)msg.obj;
			    for(int i=0 ;i<items.size();i++){
			    	showList.addLast(items.get(i));
			    }
			    mListAdapter.notifyDataSetChanged();
			    mListView.onRefreshComplete();
			    //刷新完成
			    isRefreshing = false;
				break;
			case MSG_UPREFRESH_LIST:
				//下拉加载
			    LinkedList<ViewItem> items1 = (LinkedList<ViewItem>)msg.obj;
			    for(int i=0 ;i<items1.size();i++){
			    	showList.addFirst(items1.get(i));
			    }
			    mListAdapter.notifyDataSetChanged();
			    mListView.onRefreshComplete();
			    //刷新完成
			    isRefreshing = false;
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
			case MSG_BANNER_CHANGE:
				  int index = mBannerViewPager.getCurrentItem();
				  if(index == picList.length - 1){//最后一张
					  index = -1;
				  }
				  mBannerViewPager.setCurrentItem(index + 1);
				  mHandler_banner.sendEmptyMessageDelayed(MSG_BANNER_CHANGE, PHOTO_CHANGE_TIME);
				break;

			default:
				break;
			}
		}

		
		
	};
	
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
			((ViewPager) container).removeView(mBannerViewPager
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
			mBannerViewPager.setObjectForPosition(imageViews[position], position);
			return imageViews[position];
		}
		
	}
	
	private class ViewAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return showList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return showList.get(position);
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
				convertView = View.inflate(mContext, R.layout.include_view_item, null);
			    holder = new ViewHolder();
			    holder.cover = (ImageView) convertView.findViewById(R.id.img_coverImage_view);
			    holder.price = (TextView) convertView.findViewById(R.id.txt_price_view);
			    holder.describe = (TextView) convertView.findViewById(R.id.txt_describe_view);
			    holder.focusNum = (TextView) convertView.findViewById(R.id.txt_focus_view);
			    holder.viewName = (TextView) convertView.findViewById(R.id.txt_name_view);
			    convertView.setTag(holder);
			}
			else {
			    holder = (ViewHolder) convertView.getTag();
			}
			holder.cover.setImageResource(coverList[position%coverList.length]);
	        
			//加载数据到holder里面
			convertView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Toast.makeText(mContext, "点击了景点", Toast.LENGTH_SHORT).show();
				    Intent intent = new Intent(mContext,ViewDetailActivity.class);
				    startActivity(intent);
				}
			});
			
			return convertView;
		}
		
	}
	class ViewHolder {
		ImageView cover;
		TextView viewName;
		TextView focusNum;
		TextView price;
		TextView describe;
	}
}
