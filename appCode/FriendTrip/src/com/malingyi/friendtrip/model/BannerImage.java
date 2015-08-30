package com.malingyi.friendtrip.model;

public class BannerImage extends SuperBean {
     
	private static final long serialVersionUID = 1L;
	private String id;
	private String title;
	private String date;
	private String picUrl;
	private String type;
	
	public void setId(String id){
		this.id=id;
	}
	public void setTitle(String title){
		this.title=title;
	}
	public void setDate(String date){
		this.date=date;
	}
	public void setType(String type){
		this.type=type;
	}
	public void setPicUrl(String url){
		this.picUrl=url;
	}
	public String getId(){
		return id;
	}
	public String getType(){
		return type;
	}
	public String getTitle(){
		return title;
	}
	public String getPicUrl(){
		return picUrl;
	}
	public String getDate(){
		return date;
	}
}
