package com.malingyi.friendtrip.ui.activity;

import java.util.ArrayList;

import com.malingyi.friendtrip.R;
import com.malingyi.friendtrip.model.BannerImage;
import com.malingyi.friendwithme.ui.component.jazzviewpager.JazzyViewPager;
import com.malingyi.friendwithme.ui.component.jazzviewpager.OutlineContainer;
import com.malingyi.friendwithme.ui.component.jazzviewpager.JazzyViewPager.TransitionEffect;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;

public class ViewDetailActivity extends SuperActivity {

	/**适配器**/
	private bannerAdapter mBannerAdapter;
	/**布局控件**/
	private JazzyViewPager mBannerViewPager;   //轮播图
	private LinearLayout mComNum;          //评论
	private LinearLayout mGoneNum;         //去过
	private LinearLayout mWantNum;         //想去	
	private TextView mSpotName;      //景区名
	private TextView mDescribe;      //景区描述
	/** 广告轮播图片数据*/
    private ArrayList<BannerImage>  bannerImages = new ArrayList<BannerImage>();
    /**装入轮播图片容器中的图片*/
    private ImageView imageViews[];
	protected boolean isRefreshing = false;
    /** 轮播图片自动切换时间 */
	private static final int PHOTO_CHANGE_TIME = 3000;	
	/**自定义消息**/
	private static final int MSG_BANNER_CHANGE = 2;
	
	/**测试用的数据**/
	public int[] picList = {R.drawable.chengdu1,R.drawable.chengdu2,R.drawable.chengdu3,
			R.drawable.chengdu4,R.drawable.chengdu5,R.drawable.chengdu6,R.drawable.chengdu7,};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_viewdetail);
		initViews();
		LoadingData();
	}

	private void LoadingData() {
		// TODO Auto-generated method stub
		
	}

	private void initViews() {
		// TODO Auto-generated method stub
		mBannerViewPager = (JazzyViewPager) findViewById(R.id.banner_jazzyview_viewDetail);
		mSpotName = (TextView) findViewById(R.id.txt_name_viewDetail);
		mDescribe = (TextView) findViewById(R.id.txt_describe_viewDetail);
		mComNum   = (LinearLayout) findViewById(R.id.linear_comNum_viewDetail);
		mGoneNum  = (LinearLayout) findViewById(R.id.linear_goneNum_viewDetail);
		mWantNum  = (LinearLayout) findViewById(R.id.linear_wantNum_viewDetail);
		initViewPagers();
		setListener();
	}

	private void setListener() {
		// TODO Auto-generated method stub
        mBannerViewPager.setOnClickListener(new OnClickListener() {
			
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

	private void initViewPagers() {
		// TODO Auto-generated method stub
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
//						Toast.makeText(mContext, "page is selected!", Toast.LENGTH_SHORT).show();
					}
					
					@Override
					public void onPageScrolled(int arg0, float arg1, int arg2) {
						// TODO Auto-generated method stub
//						Toast.makeText(mContext, "page is scrolled!", Toast.LENGTH_SHORT).show();
					}
					
					@Override
					public void onPageScrollStateChanged(int arg0) {
						// TODO Auto-generated method stub
//						Toast.makeText(mContext, "page's scroll state is changed!", Toast.LENGTH_SHORT).show();
					}
				});
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
	protected void obtainInfo() {
		// TODO Auto-generated method stub

	}
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
}
