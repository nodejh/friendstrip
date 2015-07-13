package com.friendwithme.service;

import java.util.List;

import android.R.bool;
import android.R.integer;
import android.content.Context;

import com.friendwithme.utils.SharedpreferncesUtil;
import com.friendwithme.model.MyCollectList;
import com.friendwithme.model.MyPraiseList;
import com.friendwithme.model.MyShortComList;
import com.friendwithme.model.MyTravelNoteList;
import com.friendwithme.model.ReleaseTripInfo;
import com.friendwithme.model.SpotsComment;
import com.friendwithme.model.TravelNoteComment;
import com.friendwithme.model.User;

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
		if( getUserInfo() != null ){
			return true;
		}
		return false;
	}
	
    
	/**
	 * 获取本地缓存的用户信息 
	 * @return null 表示无用户信息
	 */
	public User getUserInfo(){
		return SharedpreferncesUtil.getUserInfo(mContext);
	}
	
	/**
	 * 从服务器获取用户信息
	 * @param uid
	 * @return null 表示获取失败
	 */
	public User getUserInfo(String uid){
		return null;
		
	}
	/**
	 * 缓存用户信息
	 * @param user
	 */
	public void saveUserInfo(User user){
		SharedpreferncesUtil.saveUserInfo(mContext, user);
	}
	
	/**
	 * 登出
	 */
	public void loginOut(){
		SharedpreferncesUtil.clearByKey(SharedpreferncesUtil.KEY_USER_INFO, mContext);
	}
	/**
	 * 获取本地缓存的设置信息
	 * @return null 标示用户未设置
	 */
	public List<Object> getSettingInfo(){
		return null;
		
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
	/**
	 * 缓存用户设置信息
	 * @param list
	 */
	public void saveSettingInfo(List<Object> list){
		
	}
	/**
	 * 点赞--景点
	 * @param uid
	 * @param SpotsId
	 */
	public void PraiseSpot(int uid,int SpotsId){
		
	}
	/**
	 * 关注--景点
	 * @param uid
	 * @param SpotsId
	 */
	public void FocusSpot(int uid,int SpotsId ){
		
	}
	/**
	 * 分享--景点
	 * @param uid
	 * @param SpotsId
	 * @param type  分享方式（微信，QQ空间，qq好友，微博等等）
	 */
	public void ShareSpot(int uid,int SpotsId,int type){
		
	}
	
	/**
	 * 评论--景点
	 * @param uid
	 * @param SpotsId
	 * @param comment
	 */
	public void CommentSpot(int uid,int SpotsId,SpotsComment comment){
		
	}
	/**
	 * 点赞--游记
	 * @param uid
	 * @param NoteId
	 */
	public Boolean PraiseNote(int uid,int NoteId){
		return null;
		
	}
	
	/**
	 * 关注--游记
	 * @param uid
	 * @param NoteId
	 */
	public Boolean FocusNote(int uid,int NoteId){
		return null;
		
		
	}
	/**
	 * 分享--游记  
	 * @param uid
	 * @param NoteId
	 */
	public Boolean ShareNote(int uid,int NoteId){
		return null;
	}
	
	/**
	 * 点赞--短评
	 * @param uid
	 * @param ShortComId
	 */
	public void PraiseShortCom(int uid,int ShortComId){
		
	}
	
	/**
	 * 分享--短评
	 * @param uid
	 * @param ShortComId
	 */
	public boolean ShareShortCom(int uid, int ShortComId){
		return false;
		
		 
	}
	
	/**
	 * 发布出行
	 * @param uid
	 * @param tripInfo
	 * @return
	 */
	public boolean ReleaseTravel(int uid,ReleaseTripInfo tripInfo){
		return false;
		
	}
	
	/**
	 * 发表游记
	 * @param uid
	 * @param noteComment
	 * @return
	 */
	public boolean ReleaseTraveNote(int uid,TravelNoteComment noteComment){
		return false;
		
	}
	
	/**
	 * 获取收藏列表
	 * @param uid
	 * @return
	 */
	public MyCollectList getCollection(int uid){
		return null;
	}
	/**
	 * 获取我的游记列表
	 * @param uid
	 * @return
	 */
    public MyTravelNoteList getTravelist(int uid){
		return null;
    	
    }
    /**
     * 获取我的短评列表
     * @param uid
     * @return
     */
    public MyShortComList getShortComList(int uid){
		return null;
    	
    }
    
    /**
     * 获取我收到的赞列表
     * @param uid
     * @return
     */
    public MyPraiseList getPraiseList(int uid){
		return null;
    	
    }
}
