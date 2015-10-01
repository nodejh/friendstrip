package com.malingyi.friendtrip.model;

/**
 * 出行信息
 * @author David
 *
 */
public class ReleaseTripInfo extends SuperBean {
	private static final long serialVersionUID = 1L;
	
	private String uid;      //用户id
	private String Itemid;    //出行Id
	private String name ;    //用户名  
	private String ReleaseDate;   //发布日期
	private String DestName;   //目的地	
	private String Sex;    //发布者性别
	private String GoDate;     //出发日期
	private String PhoneNum;    //联系方式
	private String content;   //附加说明
}
