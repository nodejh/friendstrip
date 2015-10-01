package com.malingyi.friendtrip.model;

import java.util.ArrayList;

/**
 * 景点列表
 * @author David
 *
 */
public class SpotsList extends SuperBean {
     
	private static final long serialVersionUID = 1L;
	
	private int errorCode;    //加载错误代码
	private String ErrorMsg;   //加载错误信息
	private ArrayList<SpotsItem> Spotslist;  //景点列表
	private ArrayList<?>   list2;    
	private String str;      
	
	
	
}
