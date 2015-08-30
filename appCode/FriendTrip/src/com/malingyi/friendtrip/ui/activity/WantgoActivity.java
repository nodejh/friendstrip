package com.malingyi.friendtrip.ui.activity;

import java.util.LinkedList;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.malingyi.friendtrip.R;
import com.malingyi.friendtrip.model.TripItem;
import com.malingyi.friendtrip.model.ViewItem;
import com.umeng.socialize.net.l;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class WantgoActivity extends SuperActivity {
    
	/**控件**/
	TextView mDesName;  //目的地名
	TextView mPeopleNum; //想去的人数
	WebView  mMap;      //地图
	TextView mTripNum;  //出行记录数目
	PullToRefreshListView mListView;  //出行记录列表
	
	
	TripAdapter mTripAdapter;   //出行列表适配器
	LinkedList<TripItem>  showList =new LinkedList<TripItem>();
	private LinkedList<TripItem> tripList = new LinkedList<TripItem>();
	/**自定义消息**/
	private static final int MSG_BANNER_CHANGE = 2;
    protected static final int MSG_UPREFRESH_LIST = 3;	
	protected static final int MSG_DOWNREFRESH_LIST = 4;
	protected boolean isRefreshing = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wantgo);
		initViews();
		//从网上加载数据
		refreshOnlineStatus(true);
	}

	private void LoadingData() {
		// TODO Auto-generated method stub
		//发送网络请求
		String url;
//		AsyncHttpUtil.post(url, params, new JsonHttpResponseHandler("UTF-8"){
			
//		});
		
		//本地自定义请求
		Thread thread= new Thread( new Runnable() {
			LinkedList<TripItem> newItems = new LinkedList<TripItem>();
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(4000);
					for (int i = 0; i < 4; i++) {
						TripItem item= new TripItem();
						
						newItems.addFirst(item);
						
					}
					for(int i = 0; i<newItems.size();i++){						
						tripList.add(newItems.get(i));
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

	private void initViews() {
		// TODO Auto-generated method stub
		mDesName = (TextView) findViewById(R.id.txt_desName_WantGo);
		mPeopleNum = (TextView) findViewById(R.id.txt_peopleNum_WantGo);
		mMap     = (WebView) findViewById(R.id.web_map_WantGo);
		mTripNum = (TextView) findViewById(R.id.txt_tripNum_WantGo);
		mListView =(PullToRefreshListView) findViewById(R.id.trip_list_WantGo);
		
		initListView();
	}

	private void initListView() {
		// TODO Auto-generated method stub
		  
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
		
		   mTripAdapter = new TripAdapter();
		   mListView.setAdapter(mTripAdapter);	
	}
    
	protected void NextPage() {
		// TODO Auto-generated method stub
		//本地,下一页
		Thread thread= new Thread( new Runnable() {
			LinkedList<TripItem> newItems = new LinkedList<TripItem>();
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(4000);
					for (int i = 0; i < 4; i++) {
						TripItem item= new TripItem();
						
						newItems.addFirst(item);
						
					}
					for(int i = 0; i<newItems.size();i++){
						tripList.add(newItems.get(i));
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
private Handler mHandler_list = new Handler(){
		
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case MSG_DOWNREFRESH_LIST:
				//说明List发生了上拉动刷新
			    //改变ShowList
			    LinkedList<TripItem> items = (LinkedList<TripItem>)msg.obj;
			    for(int i=0 ;i<items.size();i++){
			    	showList.addLast(items.get(i));
			    }
			    mTripAdapter.notifyDataSetChanged();
			    mListView.onRefreshComplete();
			    //刷新完成
			    isRefreshing = false;
				break;
			case MSG_UPREFRESH_LIST:
				//下拉加载
			    LinkedList<TripItem> items1 = (LinkedList<TripItem>)msg.obj;
			    for(int i=0 ;i<items1.size();i++){
			    	showList.addFirst(items1.get(i));
			    }
			    mTripAdapter.notifyDataSetChanged();
			    mListView.onRefreshComplete();
			    //刷新完成
			    isRefreshing = false;
				break;
			default:
				break;
			}
		}
	};
	private class TripAdapter extends BaseAdapter{

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
			if(convertView == null){
				convertView= View.inflate(mContext, R.layout.include_trip_record, null);
				holder = new ViewHolder();
				holder.btn_join = (Button) convertView.findViewById(R.id.btn_join);
				holder.dest = (TextView) convertView.findViewById(R.id.txt_dest);
				holder.goDate = (TextView) convertView.findViewById(R.id.txt_goDate);
				holder.joinNum = (TextView) convertView.findViewById(R.id.txt_joinNum);
				holder.joinPeople = (TextView) convertView.findViewById(R.id.txt_peoplelist);
				holder.oriFrom = (TextView) convertView.findViewById(R.id.txt_from);
				holder.phone = (TextView) convertView.findViewById(R.id.txt_phone);
				holder.releaseUserName = (TextView) convertView.findViewById(R.id.txt_releasePeople);
				holder.expectNum = (TextView) convertView.findViewById(R.id.txt_expectNum);
			    convertView.setTag(holder);
			}
			else{
				holder = (ViewHolder) convertView.getTag();
			}
			//监听加入事件
			final int id=position;
			holder.btn_join.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
                      Toast.makeText(mContext, "Clicke buttn"+id, Toast.LENGTH_SHORT).show();
				}
			});
			//加载数据到holder里面
			TripItem item = (TripItem) getItem(position);
			holder.releaseUserName.setText(item.getFromName());
			holder.dest.setText(item.getDestName());
			
			return convertView;
		}
   
    } 
	 
    static class ViewHolder{
		TextView dest;
		TextView oriFrom;
		TextView releaseUserName;
		TextView phone;
		TextView joinPeople;
		TextView goDate;
		TextView expectNum;
		TextView joinNum;
		Button btn_join;
	}
}
