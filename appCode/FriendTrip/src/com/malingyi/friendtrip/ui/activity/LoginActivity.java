package com.malingyi.friendtrip.ui.activity;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlSerializer;

import u.aly.da;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.malingyi.friendtrip.R;
import com.malingyi.friendtrip.model.PersonalCenter;
import com.malingyi.friendtrip.model.User;
import com.malingyi.friendtrip.utils.AsyncHttpUtil;
import com.malingyi.friendtrip.utils.CharCheckUtil;

import android.R.string;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Xml;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends SuperActivity {
    
	String loginStatus="";
	String errorMsg="";
	/**测试数据**/
	PersonalCenter pCenter;
	
	//用户注册后，将注册的信息返回给登录界面，这个作为测试用。
	protected static final int requestSign = 0;
	private static final int Sign_Btn_OK = 10;

	protected static final int requestCode = 2;
	
	private TextView txt_sign;     //注册
	private Button   btn_login;    //用户登录
	
	private TextView edit_act;   //账号
	private TextView edit_pwd;   //密码
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_log_in);
		
		if(pCenter == null)
		   pCenter = new PersonalCenter();
		//默认账号密码
		
		initViews();
		
	}
    
	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
	    if( requestCode == requestSign){
	    	if(resultCode == Sign_Btn_OK){
	    		//将刚刚创建的账号信息存入登录界面，之后存入SharePreferences
	    		//里面作为保存，以后只要用户不点击退出，则会自动登录。
	    		//用户点击退出，则清楚SharePreferences数据
	    		if(pCenter == null)
	    		   pCenter = new PersonalCenter();
	    		
//	    		user.setPhone(data.getStringExtra("act"));
//	    		user.setPwd(data.getStringExtra("pwd"));	    		
	    	}
	    }
	}



	private void initViews() {
		// TODO Auto-generated method stub
		txt_sign = (TextView) findViewById(R.id.txt_sign_login);
	    btn_login = (Button) findViewById(R.id.btn_login);
	    edit_act  = (EditText) findViewById( R.id.edit_account_login);
	    edit_pwd  = (EditText) findViewById(R.id.edit_pwd_login);
	    SetListener();
	}
    
	private void SetListener(){
		
		txt_sign.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(mContext, "注册 Clicked!", Toast.LENGTH_SHORT).show();
			    Intent intent = new Intent(mContext,SignActivity.class);
			    
			    //用户注册账号成功后，通过result将注册的账号和密码返回给登录界面
			    //以便于保存到SharePreferences里面，下次直接登录进入主界面
			    startActivityForResult(intent, requestSign);
			    //注册完了跳转到个人中心，登录活动结束。
			    finish();
			}
		});
		
		btn_login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(mContext, "登录 Clicked!", Toast.LENGTH_SHORT).show();
			    //用户登录
				 String act = edit_act.getText().toString();
				 String pwd = edit_pwd.getText().toString();
				if(LoginCheck()){//检测输入的手机是否合法	
				 if(userLogin(act, pwd)){
					//保存用户信息
					mUserController.savePcenterInfo(pCenter);
					Intent  intent = new Intent(mContext,BasicActivity.class);						
					intent.putExtra("tab", 3);  // 0 首页， 1 周边， 2 出行， 3个人中心						
					//当用户登录之后又退出了，需要通知注册界面，便于下次进入的时候
				    //从登录界面开始进入
				    startActivity(intent);				    
				    finish();
				 }
				else {
					Dialog dialog = new Dialog(mContext);
					dialog.setTitle("您输入的账号或密码有误！");
					dialog.setCancelable(true);
					dialog.show();
				}
			  }
			}
		});
		
	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
    
	/**逻辑功能函数**/
	/**
	 * 发送登录请求
	 * @param act
	 * @param pwd
	 * @return
	 */
	@SuppressLint("CommitPrefEdits")
	private Boolean userLogin(String act,String pwd){
		String url="";
		//发送登录请求到服务器
		JSONObject object = new JSONObject();
		
		try {
			object.put("type", "0301");
			object.put("phone", act);
			object.put("pwd", pwd);
			object.put("token", "");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		RequestParams params = new RequestParams(object);
		AsyncHttpUtil.post(url, params, new JsonHttpResponseHandler("utf-8"){

			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONObject errorResponse) {
				// TODO Auto-generated method stub
				super.onFailure(statusCode, headers, throwable, errorResponse);
			    showToast("请求失败!");
			    try {
					loginStatus = errorResponse.getString("status");
				    errorMsg = errorResponse.getString("message");
			    } catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, headers, response);
			    showToast("请求成功");
			    try {
					loginStatus = response.getString("status");
				    if(pCenter.getUser() == null){
				    	User user = new User();
				    	user.setCompus(response.getString("compus"));
				    	user.setGrade(response.getString("grade"));
				    	user.setName(response.getString("name"));
				    	user.setPhone(response.getString("phone"));
				    	user.setPwd(response.getString("pwd"));
				    	user.setSex(response.getString("sex"));
				    	user.setImgURL(response.getString("imageUrl"));
				    	pCenter.setUser(user);
				    }
				    //获取个人中心的加入数、发布数、评论数、私信数等信息
				   pCenter.setComNum(response.getString("commentNum"));
				   pCenter.setJoinNum(response.getString("joinNum"));
				   pCenter.setReleaseNum(response.getString("releaseNum"));
				   pCenter.setLetterNum(response.getString("letterNum"));
			    } catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    
			}
		        	
		});
		return statusParse(loginStatus);
		*/
		/**本地登录**/
		PersonalCenter pCenter = mUserController.getPCenterInfo();
		if(pCenter != null){
		   if(pCenter.getUser().getPhone().trim().equals(act)&&pCenter.getUser().getPwd().trim().equals(pwd)){
			   return true;
		   }
	    }else {
	    	showToast("未有本地记录");
			return false;
		}
		return false;
		
	}
	/**
	 * 登录状态解析
	 * @param status
	 * @return
	 */
	private boolean statusParse(String status){
		if(loginStatus.trim().equals("0")){
			return true;
		}
		else if (loginStatus.trim().equals("0311")) {
			return false;
		}
		else {
			return false;
		}
	}
	/**
	 * 登录参数校验
	 * 
	 * @param phone
	 * @param pwd
	 * @return false || true
	 */
	public boolean LoginCheck() {
		if (!CharCheckUtil.isPhoneNum(edit_act.getText().toString())) {
			showToast("您输入的手机号码有误，请重新输入!");
			return false;
		}
		return true;
	}

	/** 实现xml信息序列化，便于将对象传入xml中**/
	public String serializeXml(User user){
		//实现xml信息序列号的一个对象
        XmlSerializer serializer=Xml.newSerializer();
        StringWriter writer=new StringWriter();
        
        try {
        	//xml数据经过序列化后保存到String中，然后将字串通过OutputStream保存为xml文件
            serializer.setOutput(writer);
            //文档开始
            serializer.startDocument("utf-8", true);
            //开始一个节点
            serializer.startTag("", "userinfo");
            //内容
            serializer.startTag("", "uid");
            serializer.text(user.getUid());
            serializer.endTag("", "uid");
           
            serializer.startTag("", "act");
            serializer.text(user.getPhone());
            serializer.endTag("", "act");
            
            serializer.startTag("", "pwd");
            serializer.text(user.getPwd());
            serializer.endTag("", "pwd");
            
            serializer.startTag("", "name");
            serializer.text(user.getName());
            serializer.endTag("", "name");
            
            serializer.startTag("", "sex");
            serializer.text(user.getSex());
            serializer.endTag("", "sex");
            
            serializer.startTag("", "school");
            serializer.text(user.getPwd());
            serializer.endTag("", "school");
            
            serializer.startTag("", "compus");
            serializer.text(user.getCompus());
            serializer.endTag("", "compus");
            
            serializer.startTag("", "city");
			serializer.text(user.getCity());
	        serializer.endTag("", "city");
	        
	        serializer.endTag("", "userinfo");
	        //关闭文档
	        serializer.endDocument();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		return writer.toString();
	}
	@SuppressWarnings("deprecation")
	private void saveAccountToXml(String data){
		try {
		  FileOutputStream out = this.openFileOutput("user.xml",Context.MODE_WORLD_READABLE|Context.MODE_WORLD_WRITEABLE);
		  OutputStreamWriter writer = new OutputStreamWriter(out);
		 
		  try {
			writer.write(data);
			writer.flush();
			writer.close();
			out.close();
		  } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		  }	  
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void getAccountFromXml(){
		
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
