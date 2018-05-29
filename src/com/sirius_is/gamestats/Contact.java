package com.sirius_is.gamestats;

public class Contact {
	
	//private variables
	int _id;
	String _nickname;
	String _phone_number;
	
	// Empty constructor
	public Contact(){
		
	}
	// constructor
	public Contact(int id, String nickname, String _phone_number){
		this._id = id;
		this._nickname = nickname;
		this._phone_number = _phone_number;
	}
	
	// constructor
	public Contact(String nickname, String _phone_number){
		this._nickname = nickname;
		this._phone_number = _phone_number;
	}
	// getting ID
	public int getID(){
		return this._id;
	}
	
	// setting id
	public void setID(int id){
		this._id = id;
	}
	
	// getting nickname
	public String getNickname(){
		return this._nickname;
	}
	
	// setting nickname
	public void setNickname(String nickname){
		this._nickname = nickname;
	}
	
	// getting phone number
	public String getPhoneNumber(){
		return this._phone_number;
	}
	
	// setting phone number
	public void setPhoneNumber(String phone_number){
		this._phone_number = phone_number;
	}
}
