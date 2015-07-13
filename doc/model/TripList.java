package com.friendwithme.model;

import java.util.ArrayList;

/**
 * 出行发布列表
 * @author David
 *
 */
public class TripList extends SuperBean {
     
	private static final long serialVersionUID = 1L;
	
	private int errorCode;
	private String errorMsg;
	private ArrayList<ReleaseTripInfo> list;
	private String str;
}
