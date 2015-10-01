package com.malingyi.friendtrip.model;

import java.util.ArrayList;

import android.R.integer;

/**
 * 个人中心
 * @author David
 *
 */
public class PersonalCenter extends SuperBean {
	private static final long serialVersionUID = 1L;
	
	private int errorCode;
	private String errorMsg;
	private User info = new User();
	private String joinNum;
	private String comNum;
	private String releaseNum;
	private String commentNum;
	private String letterNum;
	
	public void setUser(User user){
		info = user;
	}
	public void setJoinNum(String num){
		joinNum = num;
	}
	public void setComNum(String num){
		comNum = num;
	}
	public void setReleaseNum(String num){
		releaseNum = num;
	}
	public void setLetterNum(String num){
		letterNum = num;
	}
	
	public User getUser(){
		return info;
	}
	public String getJoinNum(){
		return joinNum;
	}
	public String getReleaseNum(){
		return releaseNum;
	}
	public String getComNum(){
		return comNum;
	}
	public String getLetterNum(){
		return letterNum;
	}
}
