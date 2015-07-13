package com.friendwithme.model;

import java.util.ArrayList;

import android.media.Image;


/**
 * 我的收藏列表（包括游记收藏、景点收藏、图片收藏）
 * @author David
 *
 */
public class MyCollectList extends SuperBean {
	private static final long serialVersionUID = 1L;
	
	private String uid;    //用户id
	private TravelNoteList travelNoteList;  //游记列表
	private SpotsList spotsList;     //景点列表
	//private ArrayList<Image> imageList;  //图片列表
	private int ErrorCode;         //返回码
	private String errorMsg;    //返回信息
	private String str;
}
