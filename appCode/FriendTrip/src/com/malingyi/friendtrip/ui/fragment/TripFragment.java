/**
 * 
 */
package com.malingyi.friendtrip.ui.fragment;
//改用PulltoViewPager
import java.util.ArrayList;
import java.util.LinkedList;

import android.R.bool;
import android.R.integer;
import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings.System;
import android.text.format.DateUtils;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.ViewGroup;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.extras.SoundPullEventListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnPullEventListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.extras.SoundPullEventListener;
import com.malingyi.friendtrip.R;
import com.malingyi.friendtrip.model.TripItem;
import com.malingyi.friendtrip.ui.adapter.tirpRecordListAdapter;

/**
 * 出行模块
 */
/**
 * @author David
 *
 */
public class TripFragment extends SuperFragment {
    private View view;
	PullToRefreshListView mListView;
    /**是否正在加载数据**/
	private boolean isRefreshing=false;
	/**加载数据是否成功**/
	private boolean isDataLoad=false;
	/**pullToRefreshListView里面的实际 ListView对象**/
	ListView actuaListView; //pulltorefreshListview中实际的listview
	/**改变量装填的是总共从服务器那边获得的记录数量*/
	LinkedList<TripItem> tripItems = new LinkedList<TripItem>();
	/**该适配器是针对当前页的适配器*/
	TripRecordAdapter tripRecordAdapter;
	/**当前页的记录**/
	LinkedList<TripItem> showList = new LinkedList<TripItem>();//每一页能够显示的数据。
	private TextView txt_where;
	
	private int totalNum=0;  //所有出行项总数
	private static final int SHOW_NUM=14;  //每一页能够显示的数量
	private static final int UP_OR_LOAD_NUM=7; //每一次加载或者更新的数量
	
	
	private int PAGE_START_ID=0;      //当前页最上面的项标号,默认为0
	private int PAGE_LAST_ID=0;         //当前页最下面的项标号
	//标记用户是上拉还是下拉
	private static final int UP=0;     
	private static final int DOWN=1;
	GetDataTask Datatask;
	
	/* (non-Javadoc)
	 * @see com.malingyi.friendtrip.ui.api.RetryNetwork#retry()
	 */
	
	
	
	

	

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fm_trip, container, false);
		return view;
	
	}
    
	 
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		for(int i=0;i<25;i++){
			TripItem tripItem = new TripItem();
			tripItem.setDestName("七舍2单元101B");
			tripItem.setFromName("麻伶毅"+i);
			tripItems.addFirst(tripItem);//最近的项放在最前面
		}
	    for(int i=0;i<SHOW_NUM;i++)
	    	showList.add(tripItems.get(i));
		//
		totalNum=tripItems.size();
		//初始化控件
		initViews();
		
//		mListView.setRefreshing(true);
		mListView.onRefreshComplete();
	}
	
	@Override
	public void retry() {
		// TODO Auto-generated method stub
    
	}
	
	private void initViews(){
		
	    
		txt_where = (TextView) view.findViewById(R.id.txt_where);
	    	
		initTripItemList();
		initListViewAdapter();
		
	}
	
	private void initTripItemList(){
		mListView = (PullToRefreshListView) view.findViewById(R.id.trip_list);
		//设置上下都可以拉动
	    mListView.setMode(Mode.BOTH);
	    //刷新日期和时间
	    String label = DateUtils.formatDateTime(mContext, java.lang.System.currentTimeMillis(), 
				DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
	    mListView.getLoadingLayoutProxy(true,true).setLastUpdatedLabel(label);
	    //设置了这个，而且上拉下拉都要设置为true，那么就会在mListView里面增加Header和Footer，监听器中调用
	    //isHeaderShown/isFooterShown才能起作用
	    mListView.getLoadingLayoutProxy(true, true).setRefreshingLabel("正在刷新。。。。");
		mListView.getLoadingLayoutProxy(true, true).setReleaseLabel("释放拉动!");
	    
		
		//设置拉动监听器
	    mListView.setOnRefreshListener(new OnRefreshListener<ListView>() {

			

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				//如果没有正在获取数据，则获取数据
				if(!isRefreshing){
					isRefreshing=true;
					if(mListView.isHeaderShown()){
						Toast.makeText(mContext, "下拉", Toast.LENGTH_SHORT).show();
						if(PAGE_START_ID == 0){
						  refreshOnlineDatas(true);
						  // 通知Adapter数据改变了
					      tripRecordAdapter.notifyDataSetChanged();
					      // 加载完成后停止刷新
					      mListView.onRefreshComplete();  
						}
						else if(PAGE_START_ID >0){
							refreshOnlineDatas(false);
							// 通知Adapter数据改变了
					        tripRecordAdapter.notifyDataSetChanged();
					        // 加载完成后停止刷新
					        mListView.onRefreshComplete();
						}
						
					}
					else if (mListView.isFooterShown()) {
						Toast.makeText(mContext, "上拉", Toast.LENGTH_SHORT).show();
						//上拉刷新，加载本地已有数据
						refreshShowList(UP);
						//通知刷新成功
						tripRecordAdapter.notifyDataSetChanged();
						mListView.onRefreshComplete();
					}
					//刷新完毕
			        isRefreshing = false;
				}
				//否则直接刷新（加载成功刷新，失败提示）
				else if(isRefreshing == true){
					if( isDataLoad == true){
						//数据加载成功，则刷新
					   
						tripRecordAdapter.notifyDataSetChanged();
						refreshView.onRefreshComplete();
					}
					else {
						//加载失败
					  Toast.makeText(mContext, "加载失败。。。", Toast.LENGTH_SHORT).show();
					}
					isRefreshing = false;
				}
			}
		});
	   /*
		mListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {
            
			//下拉加载
			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				
				//设置显示的日期和时间
				String label = DateUtils.formatDateTime(mContext, java.lang.System.currentTimeMillis(), 
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
			    //上拉加载异步任务，发出网络请求，请求最新数据
				//更新显示的label
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				
				//下拉加载
						GetDataTask task = new GetDataTask(mListView, tripRecordAdapter, tripItems);
						//执行网络加载任务
						task.execute();
				
			}
            //上拉刷新  
			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
               //设定刷新等待动画
				
				
				//加载刷新数据到ShowList中
		        refreshShowList(UP);
		        //通知数据改变
		        tripRecordAdapter.notifyDataSetChanged();
			}
			
		});
	*/
		//监听Drag事件
		
		
		//监听滑动
		mListView.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
//				Toast.makeText(mContext, "scroll state change", Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
//				
			}
		});
	    //设置滑动到底部的监听器
	    mListView.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

			@Override
			public void onLastItemVisible() {
				// TODO Auto-generated method stub
//				Toast.makeText(mContext, "已经到底了", Toast.LENGTH_SHORT).show();
			}
		});
	    //设置允许滑动
	    mListView.setScrollingWhileRefreshingEnabled(true);
	    
	    
	    /**
         * 设置反馈音效
         */
        SoundPullEventListener<ListView> soundListener = new SoundPullEventListener<ListView>(mContext);
//        soundListener.addSoundEvent(State.PULL_TO_REFRESH, R.raw.pull_event);
//        soundListener.addSoundEvent(State.RESET, R.raw.reset_sound);
//        soundListener.addSoundEvent(State.REFRESHING, R.raw.refreshing_sound);
        mListView.setOnPullEventListener(soundListener);

	}
	
	
	public void initListViewAdapter(){
		//获取一个listView对象
		actuaListView = mListView.getRefreshableView();
		actuaListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Toast.makeText(mContext, "User click item （actualListView）", Toast.LENGTH_SHORT).show();
			}
		});
		
		
		//初始化适配器适配器
		tripRecordAdapter = new TripRecordAdapter();
		actuaListView.setAdapter(tripRecordAdapter);
	   
	}
	
	private void refreshOnlineDatas(boolean state){
		if(state == true){
			//已经拉倒顶部，本地已经没有数据了，所以此时要向网络中加载最新数据
			loadingDate();			
		}
		
		//更新ShowList
        refreshShowList(DOWN);
        
	}
	/**
	 * 从服务器请求出行记录的数据
	 */
	private void loadingDate(){
		//从服务器加载数据，然后装填到TripItem里面
		
		//失败，记录失败信息，通知主界面，显示加载失败界面（未联网，断网，无数据...）
		
		//成功
		
		//下拉加载
		GetDataTask task = new GetDataTask();
		//执行网络加载任务
		task.execute();
	}
	/**
	 * * 刷新ShowList
	 * @param state  UP 是上拉，DOWN是下拉
	 */
	private void refreshShowList(int state){
		if(state == UP){
		    upRefreshShowList();	
		}
		else if(state == DOWN){
			downRefreshShowList();
		}
		else {
			Toast.makeText(mContext, "refreshShowList 处传参错误", Toast.LENGTH_SHORT).show();
		}
		
	}
	
	
	/**
	 * 下拉加载时，刷新ShowList
	 */
	private void downRefreshShowList() {
		   
		    
		    showList.clear();
		    if(tripItems.size()<SHOW_NUM){
		    	//加载后数量仍然低于一页
		    	for(int i=0; i<tripItems.size();i++){
		    		showList.add(tripItems.get(i));
		    	}
		    	PAGE_START_ID=0;
		    	PAGE_LAST_ID=tripItems.size()-1;
		    }
		    else {
		    	//当前页的第一个项ID大于每次加载的数目,此时当前页显示的是SHOW_NUM的项数
		    	if(PAGE_START_ID>=UP_OR_LOAD_NUM){
		    		PAGE_START_ID -=UP_OR_LOAD_NUM;
		    		PAGE_LAST_ID -=UP_OR_LOAD_NUM;
		    		for (int i = 0; i < SHOW_NUM; i++) {
						showList.add(tripItems.get(i+PAGE_START_ID));
					}
		    	}
		    	//小于每次加载的数目，说明上面剩下的项数不足
		    	else if (PAGE_START_ID<UP_OR_LOAD_NUM) {
		    		
		    		for (int i = 0; i < SHOW_NUM; i++) {
						showList.add(tripItems.get(i));
					}
					PAGE_START_ID=0;
			    	PAGE_LAST_ID=SHOW_NUM-1;
				}
			}
	
	}
	/**
	 * 上拉刷新ShowList
	 */
	private void upRefreshShowList() {
		// TODO Auto-generated method stub
        
		if((tripItems.size()-1-PAGE_LAST_ID)>=UP_OR_LOAD_NUM){
        	/**剩余的项数足够重新刷新一次*/
        	//清空ShowList 的前UP_OR_LOAD_NUM个	
			 
        	for(int i=1;i<=UP_OR_LOAD_NUM;i++){
        	   	showList.removeFirst();		        		
        	}
        	//向ShowList 后面增加UP_OR_LOAD_NUM个
        	for(int i=1;i<=UP_OR_LOAD_NUM;i++){
        		showList.addLast(tripItems.get(PAGE_LAST_ID+i));
        	}
        	//更新PAGE_START_ID 与 PAGE_LAST_ID
        	PAGE_LAST_ID+=UP_OR_LOAD_NUM;
        	PAGE_START_ID+=UP_OR_LOAD_NUM;
        }	
        else if((tripItems.size()-1-PAGE_LAST_ID)<UP_OR_LOAD_NUM){
        	/**没有了**/
        	if((tripItems.size()-1-PAGE_LAST_ID)==0){
        		Toast.makeText(mContext, "已经没有数据了", Toast.LENGTH_LONG).show();
        	}
        	else if((tripItems.size()-1-PAGE_LAST_ID)<0){
        		Toast.makeText(mContext, "出现错误，最后一项标记超出总数", Toast.LENGTH_LONG).show();
        	}
        	/**剩余的项数不够重新刷新*/
        	//清空ShowList 的前 (tripItems.size()-PAGE_LAST_ID)个
        	for(int i=1;i<=(tripItems.size()-1-PAGE_LAST_ID);i++){
        		showList.removeFirst();
        	}
        	//向ShowList 后面增加(tripItems.size()-PAGE_LAST_ID)个
        	for(int i=1;i<=(tripItems.size()-1-PAGE_LAST_ID);i++){
        		showList.addLast(tripItems.get(PAGE_LAST_ID+i));
        	}
        	//更新PAGE_START_ID 与 PAGE_LAST_ID
        	PAGE_LAST_ID+=(tripItems.size()-1-PAGE_LAST_ID);
        	PAGE_START_ID+=(tripItems.size()-1-PAGE_LAST_ID);
        }
	}
	/* (non-Javadoc)
	 * @see com.malingyi.friendtrip.ui.api.RetryNetwork#netError()
	 */
	@Override
	public void netError() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.malingyi.friendtrip.ui.api.PwdErrorListener#pwdError()
	 */
	@Override
	public void pwdError() {
		// TODO Auto-generated method stub

	}
    class TripRecordAdapter extends BaseAdapter{

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
	  class GetDataTask extends AsyncTask<Void, Void, Void>{
		    
		    LinkedList<TripItem>  new_items = new LinkedList<TripItem>();	    
			@Override
		    protected Void doInBackground(Void... params) {
		        //模拟请求
		        try {
		        	
		            Thread.sleep(2000);
		             //请求数据；
		             
		             //请求失败（断网，未联网，没有最新数据）,handler发送失败消息
		            
		             //请求成功,handler 发送成功消息
		            isDataLoad=true;
		             //将加载到的数据装入View里面
		            for(int i=0;i<3;i++){
		            	totalNum++;
		               TripItem item = new TripItem();
		               item.setFromName("加载"+(totalNum-1));
		               item.setOriSchool("晴川");
		               item.setDestName("天师堂");
		               new_items.addFirst(item);//最新的在最前面
		            }
		        } catch (InterruptedException e) {
		        }
		        return null;
		    }
		    
		    @Override
		    protected void onPostExecute(Void result) {
		        // TODO 自动生成的方法存根
		        super.onPostExecute(result);
		        //得到当前的模式
		        
		        if(isDataLoad==true) {
		            for(int i=new_items.size()-1;i>=0;i--){
		        	   tripItems.addFirst(new_items.get(i));
		            }
		        }
		        //从网上加载数据之后，PAGE_START_ID以及PAGE_LAST_ID可能会改变
		        if(new_items.size()>0){
		        	PAGE_START_ID+=new_items.size();
		        	PAGE_LAST_ID+=new_items.size();
		        }
		        else {
					//当前已经是最新数据了，网络上并没有得到新数据
				}
		        
		    }
		}
}


