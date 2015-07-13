package com.friendwithme.model;

import java.util.ArrayList;

public class TravelNoteList extends SuperBean {
     
	private static final long serialVersionUID = 1L;
	
	private String uid;
	private ArrayList<MyTravelNoteItem>  myTravelNoteList;
	private int ErrorCode;
	private String ErrorMsg;
}
