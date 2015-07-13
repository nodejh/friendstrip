package com.friendwithme.model;

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
	private String SpotsId;
	private String Title;
	private ArrayList<String > imageList;
	private String content;
	private ArrayList<SpotsCommentList>  commentList;
	private int FocusNum;
	private int ShareNum;
	private int CommentNum;
	private int PraiseNum;
	
}
