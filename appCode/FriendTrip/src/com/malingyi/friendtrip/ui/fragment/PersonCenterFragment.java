package com.malingyi.friendtrip.ui.fragment;


import com.malingyi.friendtrip.R;
import com.malingyi.friendtrip.model.PersonalCenter;
import com.malingyi.friendtrip.model.User;
import com.malingyi.friendtrip.ui.activity.BasicActivity;
import com.malingyi.friendtrip.ui.activity.LoginActivity;
import com.malingyi.friendtrip.ui.activity.ReleaseActivity;
import com.malingyi.friendtrip.ui.activity.SuperActivity;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PersonCenterFragment extends SuperFragment {
    /**个人中心信息模型，默认为Null,为null说明用户未登录**/
	private PersonalCenter PCenter = null;
	private View view;
	private ImageView personImage;
	private TextView personName;
	private LinearLayout myMsg;
	private LinearLayout myJoin;
	private LinearLayout myRelease;
	private LinearLayout myComment;
	private Button btn_release;
    	

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);	
		initViews();
		loadDate();	
	}




	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		if(((BasicActivity)activity).getPCenter() !=null)
		   PCenter = ((BasicActivity)activity).getPCenter();
	    
	}




	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return view = inflater.inflate(R.layout.fm_person_center, container, false);
	}

    


	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		showToast("进入个人中心 Resume");
	}



    
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		showToast("进入个人中心 Start");
		/**更新界面**/
		Update();
	}




	/**       辅助函数         ********/
	public void Update(){
		/**更新界面**/
		loadDate();
	}
	private void initViews(){
		
		
		personImage = (ImageView) view.findViewById(R.id.img_person_pCneter);
		personName  = (TextView)  view.findViewById(R.id.txt_name_pCenter);
		myMsg       = (LinearLayout) view.findViewById(R.id.linear_myMsg_pCenter);
		myJoin      = (LinearLayout) view.findViewById(R.id.linear_myJoin_pCenter);
		myRelease   = (LinearLayout) view.findViewById(R.id.linear_myRelease_pCenter);
		myComment   = (LinearLayout) view.findViewById(R.id.linear_myComment_pCenter);
		btn_release = (Button)    view.findViewById(R.id.btn_release_pCenter);
		/**设置监听器**/
		setListener();
		
	}
	
	private void setListener() {
		// TODO Auto-generated method stub
        
		personImage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(mContext, "Person Avatar is Clicked!", Toast.LENGTH_SHORT).show();
			    if( PCenter !=null){//说明用户已经登录
			    	//加载用户信息
			    	loadDate();
			    }
			    else {              //获取不到uid说明用户未登录
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
			}
			
		});
		myMsg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(mContext, "我的信息 is Clicked!", Toast.LENGTH_SHORT).show();
			}
		});
		myJoin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(mContext, "我的加入 is Clicked!", Toast.LENGTH_SHORT).show();
			}
		});
		myRelease.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(mContext, "我的发布 is Clicked!", Toast.LENGTH_SHORT).show();
			}
		});
		myComment.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(mContext, "我的评论 is Clicked!", Toast.LENGTH_SHORT).show();
			}
		});
		
		btn_release.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(mContext, "发布出行 is Clicked!", Toast.LENGTH_SHORT).show();
			    Intent  intent = new Intent(mContext,ReleaseActivity.class);
			    
			    startActivity(intent);
			}
		});
	}

	private void loadDate(){
		//加载数据
		//头像
        if (PCenter != null) {
        	if(PCenter.getUser().getImgURL()!=null 
        			&& !PCenter.getUser().getImgURL().trim().equals(""))
        	    Picasso.with(mContext).load(PCenter.getUser().getImgURL()).error(R.drawable.touxiang).into(personImage);
        	else {
		        personImage.setImageResource(R.drawable.touxiang);
			}
        	if(PCenter.getUser().getName().trim().equals(""))
        	    personName.setText("小白");
        	else
        	    personName.setText(PCenter.getUser().getName());
    	    ((TextView)myMsg.findViewById(R.id.txt_MsgArrow_pCenter_)).setText(PCenter.getLetterNum());
    	    ((TextView)myJoin.findViewById(R.id.txt_JoinArrow_pCenter)).setText(PCenter.getJoinNum());
    	    ((TextView)myRelease.findViewById(R.id.txt_ReleaseArrow_pCenter)).setText(PCenter.getReleaseNum());
    	    ((TextView)myComment.findViewById(R.id.txt_CommentArrow_pCenter)).setText(PCenter.getComNum());   	    	    	
		}
        else {
        	personImage.setImageResource(R.drawable.touxiang);
    	    personName.setText("游客");
    	    ((TextView)myMsg.findViewById(R.id.txt_MsgArrow_pCenter_)).setText("0");
    	    ((TextView)myJoin.findViewById(R.id.txt_JoinArrow_pCenter)).setText("0");
    	    ((TextView)myRelease.findViewById(R.id.txt_ReleaseArrow_pCenter)).setText("0");
    	    ((TextView)myComment.findViewById(R.id.txt_CommentArrow_pCenter)).setText("0");   	    	    	
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

	@Override
	public void pwdError() {
		// TODO Auto-generated method stub
		
	}

}
