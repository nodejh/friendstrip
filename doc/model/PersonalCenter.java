package com.friendwithme.model;

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
	private String uid;
	private String name;
	private String imageURL;
	private String imagesrc;
	private ArrayList<PersonalCenterList> list;
	private ArrayList<?> list2;
	private Setting setting;
}
