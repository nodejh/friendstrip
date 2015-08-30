package com.malingyi.friendtrip.model;

import java.util.ArrayList;

/**
 * 景点详细信息
 * @author David
 *
 */
public class SpotsCommentList extends SuperBean {

	private static final long serialVersionUID = 1L;
	
	private int ErrorCode;
	private String ErrorMsg;
	private String spotId;    //景点id
	private ArrayList<SpotsComment>  list;   
	private String str;
}
