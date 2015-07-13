package com.friendwithme.model;

import java.util.ArrayList;

/**
 * 我收到的赞列表
 * @author David
 *
 */
public class MyPraiseList extends SuperBean{
      
	private static final long serialVersionUID = 1L;
	
	private String uid;
	private ArrayList<MyPraiseItem>  myPraiseList;
	private int ErrorCode;
	private String ErrorMsg;
	
}
