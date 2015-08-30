package com.malingyi.friendtrip.model;

import java.util.ArrayList;

/**
 * 个人中心选项列表
 * @author David
 *
 */
public class PersonalCenterList extends SuperBean {
     
	private static final long serialVersionUID = 1L;
	
	private int ErrorCode;
	private String ErrorMsg;
	private ArrayList<PersonalCenterItem> list;   //选项列表
	
	
}
