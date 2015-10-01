package com.malingyi.friendtrip.model;

/**
 * 景点列表中的一项
 * @author David
 *
 */
public class SpotsItem extends SuperBean {
      
	private static final long serialVersionUID = 1L;
	
	private String SpotsId;
	private String name;     //景点名
	private String brief;    //景点简介
	private double price;    //门票票价
	private int focusNum;    //关注人数
	private String AndroidLinkUrl;    //图片链接路径
	private String src;    //图片本地路径
	private SpotsItemImage image;   //景点图片基本信息 
}
