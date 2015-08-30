package com.malingyi.friendtrip.ui.activity;

import com.malingyi.friendtrip.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;

public class SettingActivity extends SuperActivity {
    
	/**控件信息**/
	private RelativeLayout mPersonInfo;
	private RelativeLayout mSafeCenter;
	private RelativeLayout mUsualFuction;
	private RelativeLayout mAboutUs;
	private RelativeLayout mVersionInfo;
	private Button         mExit;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		initViews();
	}

	private void initViews() {
		// TODO Auto-generated method stub
		mPersonInfo = (RelativeLayout) findViewById(R.id.layout_personInfo_Setting);
		mSafeCenter = (RelativeLayout) findViewById(R.id.layout_safeCenter_Setting);
		mUsualFuction = (RelativeLayout) findViewById(R.id.layout_usualFuc_Setting);
		mAboutUs     = (RelativeLayout) findViewById(R.id.layout_about_Setting);
		mVersionInfo = (RelativeLayout) findViewById(R.id.layout_version_Setting);
		mExit       =  (Button) findViewById(R.id.btn_exit_Setting);
	    
		setListener();
	}

	private void setListener() {
		// TODO Auto-generated method stub
		mPersonInfo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showToast("我的资料 Clicked！");
			}
		});
		mSafeCenter.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showToast("安全中心 Clicked!");
			}
		});
		mUsualFuction.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showToast("通用 Clicked!");
			}
		});
		mAboutUs.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showToast("关于 Clicked!");
			}
		});
		mVersionInfo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showToast("版本 Clicked!");
			}
		});
		mExit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showToast("退出当前账户");
				mUserController.loginOut();
				
				Intent intent = new Intent(mContext,LoginActivity.class);
				startActivity(intent);
				finish();
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

}
