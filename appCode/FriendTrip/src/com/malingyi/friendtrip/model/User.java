package com.malingyi.friendtrip.model;

public class User extends SuperBean {

	private static final long serialVersionUID = 1L;
	
	private String uid="";
	private String pwd="";
	private String sex="";
	private String phone="";
	private String grade="";
	private String name="";
	private String city="";
	private String province="";
	private String address="";
	private String school="";   //学校
	private String compus="";  //校区

	private String imgUrl="";
	
	public void setUid(String uid){
		this.uid = uid;
	}
	public void setPwd(String pwd){
		this.pwd = pwd;
	}
	public void setProvince(String province){
		this.province = province;
	}
	public void setAddress(String address){
		this.address = address;
	}
	public void setSex(String sex){
		this.sex = sex;
	}
	public void setPhone(String phone){
		this.phone = phone;
	}
	public void setName(String name){
		this.name = name;
	}
	public void setCity(String city){
		this.city = city;
	}
	public void setSchool(String school){
		this.school = school;
	}
	public void setCompus(String compus){
		this.compus = compus;
	}
	public void setGrade(String grade){
		this.grade = grade;
	}
	public void setImgURL(String url){
		imgUrl = url;
	}
	
	
	
	public String getUid(){
		return uid;
	}
	public String getPwd(){
		return pwd;
	}
			
	public String getImgURL(){
		return imgUrl;
	}
	public String getSex(){
		return sex;
	}
	public String getName(){
		return name;
	}
	public String getAddress(){
		return address;
	}
	public String getProvince(){
		return province;
	}
	public String getPhone(){
		return phone;
	}
	public String getSchool(){
		return school;
	}
	public String getCompus(){
		return compus;
	}
	public String getCity(){
		return city;
	}
	public String getGrade(){
		return grade;
	}
}

