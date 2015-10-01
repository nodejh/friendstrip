package com.malingyi.friendtrip.ui.activity;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.JsonObject;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.malingyi.friendtrip.R;
import com.malingyi.friendtrip.model.PersonalCenter;
import com.malingyi.friendtrip.model.User;
import com.malingyi.friendtrip.sevice.UserController;
import com.malingyi.friendtrip.ui.activity.SignActivity.CompleteFragment.CompleteHolder;
import com.malingyi.friendtrip.ui.activity.SignActivity.SignFragment.SignHolder;
import com.malingyi.friendtrip.ui.fragment.SuperFragment;
import com.malingyi.friendtrip.utils.AsyncHttpUtil;
import com.malingyi.friendtrip.utils.CharCheckUtil;
import com.tencent.connect.UserInfo;

import android.R.integer;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignActivity extends SuperFragmentActivity {
    
	String signStatus ;
	String errorMsg;
	String testCode="1111";
    PersonalCenter personalCenter;
    UserController mUserController;
	//用于测试时作为返回给源活动的Action标记
	private static final int Sign_Btn_OK = 10;
	
	private SignFragment signFm;
	private CompleteFragment completeFm;
	
	private SignHolder signHolder;         //用户注册账号密码
	private CompleteHolder completeHolder; //用户个人信息

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_in);
		mUserController = UserController.getInstance(mContext);
		initViews();
		if(signFm == null){
			signFm = new SignFragment();			
		}
		changeFragment(signFm);
	}
    
	private void initViews(){
		signFm = new SignFragment();
		completeFm = new CompleteFragment();
	}
	
	private void changeFragment(Fragment targetFragment){
		FragmentManager fManager = getSupportFragmentManager();
	    FragmentTransaction fTransaction = fManager.beginTransaction();
	    
	    fTransaction.replace(R.id.sign_container, targetFragment, "fragment");
	    fTransaction.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
	    fTransaction.commit();
	}
	
	private boolean statusParse(String status){
		if(signStatus.equals("0")){
			return true;
		}
		else if (signStatus.equals("0211")) {
			//注册失败
			return false;
		}
		else if(signStatus.equals("0111")){
			//获取验证码失败
			return false;
		}
		else {
			//其他失败
			return false;
		}
	}
	/**
	 * 注册参数校验
	 * 
	 * @param phone
	 * @param pwd
	 * @return false || true
	 */
	public boolean SignCheck() {
		if (!CharCheckUtil.isPhoneNum(signHolder.edit_phone.getText().toString())) {
			showToast("您输入的手机号码有误，请重新输入!");
			return false;
		}
		if (!CharCheckUtil.isAllDigit(signHolder.edit_testCode.getText().toString())) {
			showToast("您输入的验证码有误，请重新输入!");
			return false;
		}
		if(!signHolder.edit_pwd.getText().toString().
				equals(signHolder.edit_rePwd.getText().toString())){
			showToast("两次输入密码不一致，请重新输入！");
			return false;
		}
		if(signHolder.edit_pwd.getText().toString().trim().equals("")
				||signHolder.edit_rePwd.getText().toString().trim().equals("")){
			showToast("密码不能为空");
		}
		if(signHolder.edit_testCode.getText().toString().trim().equals("")){
			showToast("验证码不能为空！");
		}
		return true;
	}
	@Override
	public void retry() {
		// TODO Auto-generated method stub

	}

	@Override
	public void netError() {
		// TODO Auto-generated method stub

	}

	/**注册的第一个步骤的碎片**/
	class SignFragment extends SuperFragment{

		 private Button btn_sigin; //注册按钮
		 
		 class SignHolder{
			 EditText edit_phone;
			 EditText edit_testCode;
			 EditText edit_pwd;
			 EditText edit_rePwd;
			 Button btn_getTestCode;
			}
		 
		 private View view;
		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onActivityCreated(savedInstanceState);
			this.initViews();
		}

		@Override
		public void onAttach(Activity activity) {
			// TODO Auto-generated method stub
			super.onAttach(activity);
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			view = inflater.inflate(R.layout.fm_sign_phone, container,false);
			return view;
		}
	    
		public void initViews(){
			btn_sigin = (Button) view.findViewById(R.id.btn_sign);
			if(signHolder == null)
			   signHolder = new SignHolder();
			signHolder.edit_phone = (EditText) view.findViewById(R.id.edit_phone_signfm);
			signHolder.edit_pwd = (EditText) view.findViewById(R.id.edit_pwd_signfm);
			signHolder.edit_rePwd =(EditText) view.findViewById(R.id.edit_rePwd_signfm);
			signHolder.edit_testCode = (EditText) view.findViewById(R.id.edit_testCode_signfm);
		    signHolder.btn_getTestCode = (Button) view.findViewById(R.id.btn_getTestCode_signfm);
		    
		    setListener();
		}
		private void setListener() {
			// TODO Auto-generated method stub
			btn_sigin.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				    Toast.makeText(mContext, "点击注册", 1500).show();
				    if((SignCheck())){//注册成功
				    		    
  		        	    if(completeFm == null ){
				    	   completeFm = new CompleteFragment();	
  		        	    }
					    //
//					    sendSignRequest();			    
					    changeFragment(completeFm);
				    }
				    else{//注册失败,根据失败类型确定反馈信息
				    	showToast("注册失败！");
				    	
				    }
				}
			});
		    signHolder.btn_getTestCode.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					/**发送验证码请求**/
					if(signHolder.edit_phone.getText().toString().trim().equals("")){
					    showToast("手机号不能为空！");
					}
					else{
					  if(getTestCode()){
						//自动填充testCode文本框
						signHolder.edit_testCode.setText(testCode);		
					  }
					}
				}
			});
		}
       
		
		
		/** 获取验证码 
         * 
         * @return
         */
		protected Boolean getTestCode() {
			// TODO Auto-generated method stub
			String url="";
			
			JSONObject object = new JSONObject();			
			try {
				object.put("type", "0110");
				object.put("phone", signHolder.edit_phone.getText().toString());
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
				    try {
						signStatus = errorResponse.getString("status");
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
				    try {
						signStatus = response.getString("status");
					    testCode = response.getString("code");
				    } catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				    
				}
			    	
			});
			return statusParse(signStatus);
			*/
			/**本地测试**/
			testCode = Integer.toString((int)(Math.random()*10000));
			return true;
		}

		/**
	     * 向服务器发送注册请求，成功返回true，错误返回false。
	     * 根据具体错误信息向用户发送提醒
	     * @return
	     */
		public boolean sendSignRequest() {
			// TODO Auto-generated method stub
			//获取Edit控件里面填写的注册内容。
			String signUrl="";
			
			JSONObject object = new JSONObject();
			try {
				object.put("phone", personalCenter.getUser().getPhone());
				object.put("pwd", personalCenter.getUser().getPwd());
				object.put("code", signHolder.edit_testCode.getText().toString());
				object.put("token", "token");
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/*
			RequestParams params = new RequestParams(object);
			AsyncHttpUtil.post(signUrl, 
					params,
					new JsonHttpResponseHandler("utf-8"){
	                    Boolean state;
						
						@Override
						public void onFailure(int statusCode, Header[] headers,
								Throwable throwable, JSONObject errorResponse) {
							// TODO Auto-generated method stub
							super.onFailure(statusCode, headers, throwable, errorResponse);
							showToast("失败3");
	                        if(errorResponse != null){
	                        	try {
									signStatus = errorResponse.getString("status");
								    errorMsg   = errorResponse.getString("message");
	                        	} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
	                        	
	                        }
						}						

						@Override
						public void onSuccess(int statusCode, Header[] headers,
								JSONObject response) {
							// TODO Auto-generated method stub
							super.onSuccess(statusCode, headers, response);
							if(response!=null){
								try {
									signStatus = response.getString("status");
									if(user == null){
										user = new User();			
									}
									user.setUid(response.getString("uid"));
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
				    
			});
	        
			return statusParse(signStatus);
			*/
			return true;
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
		 
	 }
	/**注册的第二个步骤的碎片**/
	public class CompleteFragment extends SuperFragment{
	    
		 private View view;
		 private Button btn_finished; //完成按钮
		 
		 class CompleteHolder{
			 TextView txt_name;
			 TextView txt_school;
			 TextView txt_compus;
			 TextView txt_sex;
			 TextView txt_grade;
		 }
		 
		 
		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onActivityCreated(savedInstanceState);
			this.initViews();
		}

		@Override
		public void onAttach(Activity activity) {
			// TODO Auto-generated method stub
			super.onAttach(activity);
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			view = inflater.inflate(R.layout.fm_sign_complete_info, container,false);
			return view;
		}
        public void initViews(){
        	if(completeHolder == null)
        	   completeHolder = new CompleteHolder();
        	
        	btn_finished = (Button) view.findViewById(R.id.btn_finished_signfm);
        	completeHolder.txt_compus = (EditText) view.findViewById(R.id.edit_compus_complete);
        	completeHolder.txt_grade = (EditText) view.findViewById(R.id.edit_grade_complete);
        	completeHolder.txt_name = (EditText)  view.findViewById(R.id.edit_name_complete);
            completeHolder.txt_school = (EditText) view.findViewById(R.id.edit_school_complete);
            completeHolder.txt_sex = (EditText)  view.findViewById(R.id.edit_sex_complete);            
            //设置监听器
            setListener();
        }
        public void setListener(){
	       btn_finished.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Toast.makeText(mContext, "用户点击注册完成，进入用户中心", Toast.LENGTH_SHORT).show();
					/*
					// 该项作为本地注册测试用
				    //返回本次注册信息给登录界面
				    Intent data = new Intent();
				    Bundle bundle = new Bundle();
				    String act = signHolder.edit_phone.getText().toString();
				    String pwd = signHolder.edit_pwd.getText().toString();
				     
				    bundle.putString("act", act);
				    bundle.putString("pwd", pwd);	    
				    data.putExtras(bundle);
				    setResult(Sign_Btn_OK,data);
		      		*/
					
				    if(sendUserInfoRequest()){
				    	setPCenterInfo();
				    	mUserController.savePcenterInfo(personalCenter);
				    }
				    
					//注册完成向登录界面发送消息
					Intent intent = new Intent(mContext,BasicActivity.class);
					intent.putExtra("tab", 3);  //0表示首页，1表示周边，2，表示出行，3表示个人中心
					startActivity(intent);
					finish();
				}
			});
        }

		protected boolean sendUserInfoRequest() {
			// TODO Auto-generated method stub
			JSONObject infoObject = new JSONObject();
			try {
				infoObject.put("name", personalCenter.getUser().getName());
				infoObject.put("school", personalCenter.getUser().getSchool());
				infoObject.put("compus", personalCenter.getUser().getCompus());
				infoObject.put("grade", personalCenter.getUser().getGrade());
				infoObject.put("sex", personalCenter.getUser().getSex());
				infoObject.put("city",personalCenter.getUser().getCity() );
				infoObject.put("province", personalCenter.getUser().getProvince());
				infoObject.put("address", personalCenter.getUser().getAddress());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
			/*
			RequestParams params = new RequestParams(object);
			AsyncHttpUtil.post(signUrl, 
					params,
					new JsonHttpResponseHandler("utf-8"){
	                    Boolean state;
						
						@Override
						public void onFailure(int statusCode, Header[] headers,
								Throwable throwable, JSONObject errorResponse) {
							// TODO Auto-generated method stub
							super.onFailure(statusCode, headers, throwable, errorResponse);
							showToast("失败3");
	                        if(errorResponse != null){
	                        	try {
									signStatus = errorResponse.getString("status");
								    errorMsg   = errorResponse.getString("message");
	                        	} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
	                        	
	                        }
						}						

						@Override
						public void onSuccess(int statusCode, Header[] headers,
								JSONObject response) {
							// TODO Auto-generated method stub
							super.onSuccess(statusCode, headers, response);
							if(response!=null){
								try {
									signStatus = response.getString("status");
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
				    
			});
	        
			return statusParse(signStatus);
			*/
			return true;
		}

		protected void setPCenterInfo() {
			// TODO Auto-generated method stub
			if(personalCenter == null)
		        personalCenter = new PersonalCenter();
							
			personalCenter.getUser().setCity("成都");
			personalCenter.getUser().setCompus(completeHolder.txt_compus.getText().toString());
			personalCenter.getUser().setName(completeHolder.txt_name.getText().toString());
			personalCenter.getUser().setPhone(signHolder.edit_phone.getText().toString());
			personalCenter.getUser().setPwd(signHolder.edit_rePwd.getText().toString());
			personalCenter.getUser().setSchool(completeHolder.txt_school.getText().toString());
			personalCenter.getUser().setSex(completeHolder.txt_sex.getText().toString());
			personalCenter.getUser().setGrade(completeHolder.txt_grade.getText().toString());
			personalCenter.getUser().setProvince("四川省");
			personalCenter.getUser().setAddress("四川省成都市双流县");				 
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
		 
	 }
}
 
 