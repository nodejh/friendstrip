/**
 * 
 */
package com.malingyi.friendtrip.model;

import java.util.ArrayList;

//出行记录项
/**
 * @author David
 *
 */
public class TripItem extends SuperBean {
	private static final long serialVersionUID = 1L;
	
	private String fromId;
	private String fromName;
	private int type;
	private String destName;
	private String oriSchool;
	private String oriCity;
	private String content;
	private String goDate;
	private String releaseDate;
	private String phone;
	private ArrayList<joinPeople> joinlList = new ArrayList<TripItem.joinPeople>();
	private String btn_string;  //当过时的时候要改变btn的字，{加入，已过期}
	private int wantNum;
	private int joinNum;
	class joinPeople{
		String id;
		String name;
	}
	public void setDestName(String destName){
		this.destName=destName;
	}
	public void setFromId(String id){
		fromId=id;
	}
	public void setFromName(String name){
		fromName = name;
	}
	public void setType(int type){
		this.type=type;
	}
	public void setOriSchool(String school){
		oriSchool = school;
	}
	public void setOriCity(String city){
		oriCity = city;
	}
	public void setContent(String content){
		this.content = content;
	}
	public void setGoDate(String godate){
		goDate=godate;
	}
	public void setReleaseDate(String date){
		releaseDate = date;
	}
	public void setPhone(String phone){
		this.phone = phone;
	}
	public void setjoinList(ArrayList<joinPeople> joinList){
		this.joinlList = joinList;
	}
	public void setBtnString(String string){
		btn_string = string;
	}
	public void setWantNum(int num){
		this.wantNum = num;
	}
	public void setJoinNum(int num){
		this.joinNum = num;
	}
	
	
	public String getDestName(){
		return destName;
	}
	public String getFromId(){
		return fromId;
	}
	public String getFromName(){
		return fromName ;
	}
	public int  getType(){
		return type;
	}
	public String getOriSchool( ){
		return oriSchool;
	}
	public String getOriCity( ){
		return oriCity ;
	}
	public String getContent(){
		return content ;
	}
	public String getGoDate( ){
		return goDate;
	}
	public String getReleaseDate(){
		return releaseDate;
	}
	public String getPhone( ){
		return phone;
	}
	public ArrayList<joinPeople> getjoinList( ){
		return joinlList;
	}
	public String getBtnString( ){
		return btn_string;
	}
	public int getWantNum(){
		return wantNum;
	}
	public int getJoinNum( ){
		return joinNum;
	}
}
