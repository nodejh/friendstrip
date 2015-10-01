package com.malingyi.friendtrip.model;

import java.util.ArrayList;

/**
 * 景点详细信息
 * @author David
 *
 */
public class SpotsDetailItem extends SuperBean {
	private static final long serialVersionUID = 1L;
	
	private int ErrorCode;
	private String ErrorMsg;  
	private String SpotsId;   //景点id
	private String Title;    //标题
	private ArrayList<String > imageList;   //景点图片集
	private String content;     //景点内容
	private ArrayList<SpotsCommentList>  commentList;  //景点评论
	private int FocusNum;   //关注人数
	private int ShareNum;   //分享人数
	private int CommentNum;   //评论人数
	private int PraiseNum;   //点赞人数
	
}
