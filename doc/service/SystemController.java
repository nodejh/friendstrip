package com.friendwithme.service;

import java.util.ArrayList;

import com.friendwithme.model.SpotsCommentList;
import com.friendwithme.model.SpotsList;
import com.friendwithme.model.SurroundList;
import com.friendwithme.model.TravelNoteList;
import com.friendwithme.model.TravelReleaseList;

import android.content.Context;


/**
 * 系统业务逻辑封装
 * @author David
 *
 */
public class SystemController {
    
	private Context m_context;
	private int errorCode;
	private static SystemController instance;
	private SystemController(Context context) {
		// TODO Auto-generated constructor stub
	}
    
	/**
	 * 获取系统业务实例
	 * @param context
	 * @return
	 */
	public static SystemController getInstance(Context context){
		if(instance==null){
			instance=new SystemController(context);
		}
		return instance;
	}
	/**
	 * 登录
	 * @param Phone
	 * @param pwd
	 * @return uid or null if fail
	 */
	public String Sign(String Phone,String pwd){
		String uid = null;
		return uid;
	}
	/**
	 * 手机号码验证（本地验证）
	 * @param Phone
	 * @return true or false
	 */
	public boolean PhoneIdTest(String Phone){
		return false;
	}
	
	/**
	 * 请求验证码（通过友盟的接口）
	 * @param Phone
	 * @return 验证码 or null 标示请求失败
	 */
	public String RequestTestCode(String Phone){
		return null;
		
	}
	
	/**
	 * 注册请求
	 * @param Phone
	 * @param pwd
	 * @param repeatpwd
	 * @param testCode  验证码
	 * @param province
	 * @param city
	 * @param school
	 * @return 返回uid,Or null if fail
	 */
	public String Login(String Phone,String pwd,String repeatpwd,String testCode,String province,String city,String school){
		
		return null;
		
	}
	
	/**
	 * 初始化用户信息
	 * @param uid
	 * @return
	 */
	public boolean initialUserUi(String uid){
		return false;
		
	}
	
	
	/**
	 * 获取推荐景点列表
	 * @return
	 */
	public SpotsList getPromoteSpotsList(String Cityname){
		return null;
		
	}
	
    /**
     * 定位（由百度API实现）
     * @return  返回位置信息
     */
	public ArrayList<String> Location(){
		return null;
		
	}
	
	/**
	 * 获取搜索景点列表
	 * @param name
	 * @return
	 */
	public SpotsList getSpotsList(String name){
		return null;
		
	}
	
	/**
	 * 获取热门搜索景点列表（此处目前先通过百度API）
	 * @return
	 */
	public SpotsList getHotSearchList(){
		return null;
		
	}
	
	/**
	 * 获取游记列表
	 * @return
	 */
	public TravelNoteList getTravelNoteList(){
		return null;
		
	}
	
	/**
	 * 获取出行发布列表
	 * @return
	 */
	public TravelReleaseList getTravelReleaseList(){
		return null;
		
	}
	
	/**
	 * 获取景点评论列表
	 * @return
	 */
	public SpotsCommentList getSpotsCommentList(){
		return null;
		
	}
	/**
	 * 获取周边列表（该功能由百度API实现，不经过我们的服务器）
	 * @return
	 */
	public SurroundList getSurroundList(){
		return null;
	}
	
	/**
	 * 获取城市列表
	 * @return
	 */
	public ArrayList<String> getCityList(){
		return null;
	}
	
	/**
	 * 获取学校列表
	 * @param CityName
	 * @return
	 */
	public ArrayList<String>  getSchoolList(String CityName){
		return null;
		
	}
}
