package com.malingyi.friendtrip.model;

/**
 * 周边列表中的每项信息
 * @author David
 *
 */
public class SurroundItem extends SuperBean {

	private static final long serialVersionUID = 1L;
	
	private int errorCode;
	private String errorMsg;
	private String itemid;

	private String name;   //活动名称
	private String Title;  //活动标题
	private String ImageSRC;  //活动图片路径
	private String ImageURL;  //活动图片链接地址
}
