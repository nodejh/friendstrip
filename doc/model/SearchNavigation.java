package com.friendwithme.model;

import java.util.ArrayList;

/**
 *  搜索导航界面
 * @author David
 *
 */
public class SearchNavigation extends SuperBean {
	private static final long serialVersionUID = 1L;
	
	private String errorCode;
	private String errorMsg;
	private ArrayList<String > HotTag;  //热门搜索标签
	private SpotsList list;     //结果景点列表
}
