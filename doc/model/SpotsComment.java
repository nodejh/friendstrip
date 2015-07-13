package com.friendwithme.model;

/**
 *   对景点的评论
 * @author David
 *
 */

public class SpotsComment extends SuperComment {
	private static final long serialVersionUID = 1L;
	
	private String SpotsId;  //景点Id
	private String uid;       //用户Id
	private String name;     //用户名
	private String date;     //评论时间
	private String content;   //评论内容
	private String src;      //用户头像路径
	private String LinkUrl;  //头像链接地址
 
}
