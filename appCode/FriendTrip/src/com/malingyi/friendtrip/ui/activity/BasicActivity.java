package com.malingyi.friendtrip.ui.activity;

import com.malingyi.friendtrip.R;
import com.malingyi.friendtrip.model.PersonalCenter;
import com.malingyi.friendtrip.model.User;
import com.malingyi.friendtrip.sevice.UserController;
import com.malingyi.friendtrip.ui.fragment.HomeFragment;
import com.malingyi.friendtrip.ui.fragment.PersonCenterFragment;
import com.malingyi.friendtrip.ui.fragment.SurroundFragment;
import com.malingyi.friendtrip.ui.fragment.TripFragment;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenu.OnMenuListener;
import com.special.ResideMenu.ResideMenuItem;












import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Event;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

public class BasicActivity extends SuperFragmentActivity implements HomeFragment.OnFragmentLoadCompleteListener{
	
	PersonalCenter personalCenter = null;
	
    private UserController mUserController;
    //以下表示tab的编号
	private static final int TAB_HOME=0;
	private static final int TAB_SURROUND=1;
	private static final int TAB_TRIP=2;
	private static final int TAB_PCENTER=3;
    protected static final int requestCode = 0;
	ResideMenu resideMenu ;
    // 菜单项
    ResideMenuItem UserImageItem;
    ResideMenuItem UserNameItem;
    ResideMenuItem MyReleaseItem;
    ResideMenuItem MyJoinItem;
    ResideMenuItem MyCommentItem;
    ResideMenuItem SettingItem;
    ResideMenuItem ExitItem;
    
    /** Tab项*/
    LinearLayout homeTab;
    LinearLayout tripTab;
    LinearLayout surroundingTab;
    LinearLayout pCenterTab;
    
    /** Tabs Fragment*/
    HomeFragment homeFragment;
    TripFragment tripFragment;
    PersonCenterFragment pCenterFragment;
    SurroundFragment surroundFragment;
    /** menu item activitys*/
    
    /** Head **/
    View head_searchView;    //搜索上栏
    View head_pCenterView;   //个人中心上栏
    FrameLayout head_container ;
    ImageButton btn_open_menu;  //打开左边的菜单
    AutoCompleteTextView txt_search; //搜索栏
    TextView  txt_citys;    // 城市切换
    
    /**个人中心的设置选项**/
    ImageView  head_Setting_pCenter;
	private BasicActivity mContext;
    

    //监控用户点击返回按钮的时间，作为两次点击返回的标记
	private long exitTime = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_basic);
		mContext=this;
		mUserController = UserController.getInstance(this);
		initViews();
		if ( savedInstanceState == null ) {
			setCurrentTabFm(TAB_HOME);
		}
	 }
	 
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
    
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		showToast("返回主界面 onStart");
		
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		showToast("返回主界面 onResume");
		UpDate();
	}
    
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		showToast("进入主界面 Restart");
	}

	/**
	 * 监听用户按下返回键，显示再按一次退出。
	 */
	@SuppressWarnings("deprecation")
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			ExitDialog();
			break;

		default:
			break;
		}
		return true;
	}
	/**监听对话框里面的button点击事件*/  
	    DialogInterface.OnClickListener exitlistener = new DialogInterface.OnClickListener()  
	    {  
	        public void onClick(DialogInterface dialog, int which)  
	        {  
	            switch (which)  
	            {  
	            case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序  
	                mContext.finish();
	            	System.exit(0); 
	                break;  
	            case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框  
	                dialog.dismiss();
	            	break;  
	            default:  
	                break;  
	            }  
	        }  
	 };    

    
	public void ExitDialog(){
		if ((System.currentTimeMillis() - exitTime) > 2000) {
			exitTime = System.currentTimeMillis();
			Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();                                
            exitTime = System.currentTimeMillis();   
		}
		else{
		   // 创建退出对话框  
		   AlertDialog isExit = new AlertDialog.Builder(this).create();  
		   // 设置对话框标题  
		   isExit.setTitle("系统提示");  
		   // 设置对话框消息  
		   isExit.setMessage("确定要退出吗");  
		   // 添加选择按钮并注册监听  
		   isExit.setButton("确定", exitlistener);  
		   isExit.setButton2("取消", exitlistener);  
		   // 显示对话框  
		   isExit.show();  
		}
	}
	/**更新界面**/
	public void UpDate(){
		/**判断是否已经登录，已经登录则加载用户信息，否则显示默认信息**/
	    if(mUserController.isLogin()){
	    	personalCenter = mUserController.getPCenterInfo();
	    	UserImageItem.setIcon(personalCenter.getUser().getImgURL());
			UserNameItem.setTitle(personalCenter.getUser().getName());
	    }
	    else {
	    	UserImageItem.setIcon(R.drawable.touxiang);
			UserNameItem.setTitle("游客");
	    }
	}
	private void initViews() {
		// TODO Auto-generated method stub
		/**初始化左边的菜单 */
		initMenu();	
		/** 初始化下面的TabBar*/
		initTabBar();
		/**初始化上面的HeadBar*/
		initHeadBar();
	}	 
	private void initTabBar() {
		// TODO Auto-generated method stub
		
	    homeTab = (LinearLayout) findViewById(R.id.home_tab);
	    tripTab = (LinearLayout) findViewById(R.id.trip_tab);
	    surroundingTab = (LinearLayout) findViewById(R.id.surrounding_tab);
	    pCenterTab = (LinearLayout) findViewById(R.id.pCenter_tab);
	    
	    //设置监听器
	    
	    homeTab.setOnClickListener(tabBarListener);
	    
	    tripTab.setOnClickListener(tabBarListener);
	    
	    surroundingTab.setOnClickListener(tabBarListener);
	    
	    pCenterTab.setOnClickListener(tabBarListener);
	}
	
	private void initHeadBar(){
		head_container = (FrameLayout) findViewById(R.id.head_container_basic);
		initSearchHead();
		initPCenterHead();
	}
	
	private void initPCenterHead() {
		// TODO Auto-generated method stub
		
		   head_pCenterView = View.inflate(mContext, R.layout.include_head_pcenter, null);
	       head_Setting_pCenter = (ImageView) head_pCenterView.findViewById(R.id.img_setting_pCenter);
		   head_container.addView(head_pCenterView);
		   
		   head_Setting_pCenter.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(mContext,SettingActivity.class);
				startActivity(intent);
			}
		});
	}

	private void initSearchHead() {
		// TODO Auto-generated method stub
		
	    head_searchView = View.inflate(mContext, R.layout.include_header_bar, null);
	    head_container.addView(head_searchView);
	    
		btn_open_menu = (ImageButton) head_searchView.findViewById(R.id.btn_menu);
		txt_search    = (AutoCompleteTextView) head_searchView.findViewById(R.id.txt_search_basic);
        txt_citys     = (TextView) head_searchView.findViewById(R.id.txt_Citys);
        
		btn_open_menu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!resideMenu.isOpened()){
				    resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
				}else {
					resideMenu.closeMenu();
				}
			}
		});
	    
		txt_search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(mContext, "搜索栏被点击！", Toast.LENGTH_SHORT).show();
			    Intent intent = new Intent(mContext,SearchActivity.class);
			    startActivity(intent);
			}
		});
		
		txt_citys.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(mContext, "城市标签被点击！", Toast.LENGTH_SHORT).show();
			}
		});
	}

	public void initMenu(){
		//loadding user info
		
		//attach to current activity
	    resideMenu = new ResideMenu(mContext);
		resideMenu.setBackground(R.drawable.bg);
		resideMenu.attachToActivity(mContext);
	    resideMenu.setScaleValue(0.6f);
	    UserNameItem = new ResideMenuItem(this);
	    UserNameItem.setTitle("游客");
	    UserImageItem = new ResideMenuItem(this,R.drawable.touxiang,true );
		MyReleaseItem = new ResideMenuItem(this, R.drawable.icon_home, "我的发布");			MyJoinItem = new ResideMenuItem(this, R.drawable.icon_profile, "我的加入");
		MyCommentItem = new ResideMenuItem(this, R.drawable.icon_calendar, "我的评论");
		SettingItem = new ResideMenuItem(this, R.drawable.icon_settings, "设置");
		ExitItem = new ResideMenuItem(this, R.drawable.icon_news, "退出");
				
		//add item listener
		UserImageItem.setOnClickListener(menuItemListener);
		UserNameItem.setOnClickListener(menuItemListener);
		MyReleaseItem.setOnClickListener(menuItemListener);
		MyJoinItem.setOnClickListener(menuItemListener);
		MyCommentItem.setOnClickListener(menuItemListener);
		SettingItem.setOnClickListener(menuItemListener);
		ExitItem.setOnClickListener(menuItemListener);
		
		//add menu listener
		resideMenu.setMenuListener(menuListener);
		
		//add item to menu
		resideMenu.addMenuItem(UserImageItem, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(UserNameItem, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(MyReleaseItem, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(MyJoinItem, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(MyCommentItem, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(SettingItem, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(ExitItem, ResideMenu.DIRECTION_LEFT);
		
		resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);
	
				
	}
	
	protected void SetTabSelect(int index) {
		// TODO Auto-generated method stub
		
	}
	protected void CleanTabImage() {
		// TODO Auto-generated method stub
		
	}
	
	private ResideMenu.OnMenuListener menuListener=new OnMenuListener() {
		
		@Override
		public void openMenu() {
			// TODO Auto-generated method stub
			Toast.makeText(mContext, "Menu is opened!", Toast.LENGTH_SHORT).show();
		}
		
		@Override
		public void closeMenu() {
			// TODO Auto-generated method stub
			Toast.makeText(mContext, "Menu is close!", Toast.LENGTH_SHORT).show();
		}
	};
	
	/**
	 * 设置下边TabBar的监听事件
	 */
	private OnClickListener tabBarListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v == homeTab){
				Toast.makeText(mContext, "Trip tab clicked!", Toast.LENGTH_SHORT).show();
		        //消除下面Tab的图片状态
		        int index = 1;
		        CleanTabImage();
		        SetTabSelect(index);
		        setCurrentTabFm(TAB_HOME);
				
			}else if (v == tripTab) {
				Toast.makeText(mContext, "Trip tab clicked!", Toast.LENGTH_SHORT).show();
			    
				int index = 1;
		        CleanTabImage();
		        SetTabSelect(index);
		        setCurrentTabFm(TAB_TRIP);
		        
			}else if (v == surroundingTab) {
                Toast.makeText(mContext, "Surrounding tab clicked!", Toast.LENGTH_SHORT).show();
			    
				
				int index = 1;
		        CleanTabImage();
		        SetTabSelect(index);
		        setCurrentTabFm(TAB_SURROUND);
			}
			else if (v == pCenterTab) {
				Toast.makeText(mContext, "pCenter tab clicked!", Toast.LENGTH_SHORT).show();
			    
				int index = 3;
				CleanTabImage();
				SetTabSelect(index);
				setCurrentTabFm(TAB_PCENTER);
			}
		}
	};
	/**
	 * 设置左边的菜单项监听事件
	 */
	private View.OnClickListener menuItemListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if( v == UserImageItem ){
				Toast.makeText(mContext, "UserImage Clicked!", Toast.LENGTH_SHORT).show();
                if(personalCenter == null){
                	//说明用户未登录
                	Builder dialog = new AlertDialog.Builder(mContext);
    			    dialog.setCancelable(true);
    			    dialog.setTitle("请先登录");
    			    
    				dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
    					
    					@Override
    					public void onClick(DialogInterface dialog, int which) {
    						// TODO Auto-generated method stub
    						Intent intent = new Intent(mContext,LoginActivity.class);
    					    startActivity(intent);
    					    dialog.dismiss();
    					}
    				});
    			    dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
    					
    					@Override
    					public void onClick(DialogInterface dialog, int which) {
    						// TODO Auto-generated method stub
    						dialog.dismiss();
    					}
    				});
    			    dialog.show();
                }
                else{
                	if(pCenterFragment == null){
                		pCenterFragment = new PersonCenterFragment();
                	}                	
                	changeFragment(pCenterFragment); 
                }
                
                
			}else if( v == UserNameItem ){
				Toast.makeText(mContext, "UserName Clicked!", Toast.LENGTH_SHORT).show();
//				changeActivity();
			}else if( v == MyReleaseItem ){
				Toast.makeText(mContext, "MyRelease Clicked!", Toast.LENGTH_SHORT).show();
				
//				changeActivity();
			}else if ( v == MyJoinItem ) {
				Toast.makeText(mContext, "MyJoin Clicked!", Toast.LENGTH_SHORT).show();
//				changeActivity();
			}else if ( v == MyCommentItem ) {
				Toast.makeText(mContext, "MyComment Clicked!", Toast.LENGTH_SHORT).show();
//				changeActivity();
			}else if ( v == SettingItem ) {
				Toast.makeText(mContext, "Setting Clicked!", Toast.LENGTH_SHORT).show();
//				changeActivity();
			}else if ( v == ExitItem ) {
				Toast.makeText(mContext, "ExitItem Clicked!", Toast.LENGTH_SHORT).show();
//				changeActivity();
				mContext.finish();
            	System.exit(0);
			}
			resideMenu.closeMenu();
		}
	};
	//open gesture operation
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		//禁止Banner轮播滑动
		FrameLayout frameLayout = (FrameLayout) findViewById(R.id.ignoredView_home);
		resideMenu.addIgnoredView(frameLayout);
		return resideMenu.dispatchTouchEvent(ev);
	}
	protected void changeActivity(Activity targetActivity) {
		//转换界面的时候要清除 之前页面的ignoredView
		resideMenu.clearIgnoredViewList();
		Intent intent = new Intent(this,targetActivity.getClass());
//		startActivityForResult(intent, requestCode);
		startActivity(intent);
	}
	public void changeFragment(Fragment targetFragment) {
		// TODO Auto-generated method stub
        resideMenu.clearIgnoredViewList();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(targetFragment instanceof PersonCenterFragment){
        	//如果是PersonCenterFragment,就没有了searchBar
        	//那么替换布局就不一样了
        	//将searchBar隐藏,替换成pCenterBar
        	if(head_searchView != null)
        	   head_searchView.setVisibility(View.INVISIBLE);
        	if(head_pCenterView == null){
        	   head_pCenterView = View.inflate(mContext, R.layout.include_head_pcenter, null);
        	   initPCenterHead();
        	}
        	head_pCenterView.setVisibility(View.VISIBLE);      
        }
        else {
        	
        	if(head_pCenterView != null){
        		head_pCenterView.setVisibility(View.INVISIBLE);
        	}
        	if(head_searchView == null){
          	    head_searchView = View.inflate(mContext, R.layout.include_header_bar, null);
        	    initSearchHead();
        	}
        	head_searchView.setVisibility(View.VISIBLE);
    	}
        fragmentTransaction.replace(R.id.fragment_container_basic, targetFragment);
        fragmentTransaction.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();     
	}
	public ResideMenu getResideMenu(){
		return resideMenu;
	}
	
	public void setCurrentTabFm(int index){
		switch (index) {
		case TAB_HOME:
			if(homeFragment == null)
				homeFragment = new HomeFragment();
			  changeFragment(homeFragment);
			break;
		case TAB_SURROUND:
			if(surroundFragment == null)
				surroundFragment = new SurroundFragment();
			  changeFragment(surroundFragment);
			break;
		case TAB_TRIP:
			if(tripFragment == null)
				tripFragment = new TripFragment();
			  changeFragment(tripFragment);
			 break;
		case TAB_PCENTER:
			if(pCenterFragment == null)
				pCenterFragment = new PersonCenterFragment();
			changeFragment(pCenterFragment);
		default:
			break;
		}
	}
	
	public User getUser(){
		if(personalCenter != null)
			return personalCenter.getUser();
		else {
			return null;
		}
	}
	public PersonalCenter getPCenter(){
		if (personalCenter != null) {
			return personalCenter;
		}
		else {
			return null;
		}
	}
	@Override
	public void onLoadComplete() {
		// TODO Auto-generated method stub
		//说明加载成功，可以重新刷新Fragment
		Toast.makeText(mContext, "回调onLoadComplete函数", Toast.LENGTH_SHORT).show();
		
		//changeFragment(homeFragment);
	}


	@Override
	public void retry() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void netError() {
		// TODO Auto-generated method stub
		
	}
}
