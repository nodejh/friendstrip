package com.malingyi.friendtrip.sevice;

import java.util.List;

import com.malingyi.friendtrip.model.PersonalCenter;
import com.malingyi.friendtrip.model.User;
import com.malingyi.friendtrip.utils.SharedpreferncesUtil;

import android.content.Context;



/**
 * 封装用户 业务逻辑
 * 
 * @author longtao.li
 *
 */
public class UserController {
	
	private static UserController instance;
	private Context mContext;

	private UserController(Context context) {
		mContext = context;
	}

	public static UserController getInstance(Context context) {
		if (instance == null) {
			instance = new UserController(context);
		}
		return instance;
	}
	
	/**
	 * 是否已经登录
	 * @return
	 */
	public boolean isLogin(){
		if( getPCenterInfo() != null ){
			return true;
		}
		return false;
	}
	

	/**
	 * 获取本地缓存的用户基本信息 
	 * @return null 表示无用户信息
	 */
	public User getUserInfo(){
		return SharedpreferncesUtil.getUserInfo(mContext);
	}
	/**
	 * 获取用户中心全部信息
	 * @return 
	 */
	public PersonalCenter getPCenterInfo(){
		return SharedpreferncesUtil.getPcenterInfo(mContext);
	}
	/**
	 * 缓存用户中心信息
	 * @param pCenterinfo
	 */
	public void savePcenterInfo(PersonalCenter pCenterinfo){
		SharedpreferncesUtil.savePcenterInfo(mContext,pCenterinfo);
	}
	/**
	 * 缓存用户基本信息
	 * @param user
	 */
	public void saveUserInfo(User user){
		SharedpreferncesUtil.saveUserInfo(mContext, user);
	}
	
	/**
	 * 登出
	 */
	public void loginOut(){
		SharedpreferncesUtil.clearByKey(SharedpreferncesUtil.KEY_PCENTER_INFO, mContext);
	}
	/**
	 * 获取本地缓存的收藏信息 
	 * 
	 * @return null 表示无用户信息
	 */
	public List<Object> getCollectionInfo(){
		return SharedpreferncesUtil.getCollectionInfo(mContext);
	}
	
	/**
	 * 缓存用户收藏信息
	 * @param user
	 */
	public void saveCollectionInfo(List<Object> list){
		SharedpreferncesUtil.saveCollectionInfo(mContext, list);
	}
	
	
}
