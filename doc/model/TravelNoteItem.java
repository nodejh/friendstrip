package com.friendwithme.model;

public class TravelNoteItem extends SuperBean {
	private static final long serialVersionUID = 1L;
	
	private int errorCode;
	private String errorMsg;
	private String uid ;    //用户id
	private String travelnoteId;   //游记id
	private String name;    //用户名
	private String title;   //游记标题
	private String date;     //发表时间
	private String BriefContent;   //游记简介
	private String userimageSrc;   //用户头像路径
	private String userimegeUrl;   //用户头像链接路径
	private String school;        //学校名
	private String noteImagesrc;   //游记图片
	private String noteImageurl;   //游记图片链接路径
	private int focusNum;
	private int PraiseNum;
	private int shareNum;
}
