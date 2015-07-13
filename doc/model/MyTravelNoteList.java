package com.friendwithme.model;

import java.util.ArrayList;

/**
 * 游记列表
 * @author David
 *
 */
public class MyTravelNoteList extends SuperBean {
	private static final long serialVersionUID = 1L;
	
	private String uid;
	private ArrayList<MyTravelNoteItem> list;
	private int errorCode;
	private String errorMsg;
	
}
