package com.example.administrator.batheasy3.bean1;

public abstract class Character {

	protected String phoneNumber;
	protected String name;
	protected String password;
	
	
	public Character(String phoneNumber, String name, String password) {
		this.phoneNumber = phoneNumber;
		this.name = name;
		this.password = password;
	}
	
	
	public Character(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	

	
	
	public String getPhoneNumber() {
		return phoneNumber;
	}


//	public void setPhoneNumber(String phoneNumber) {
//		this.phoneNumber = phoneNumber;
//	}


	public String getName() {
		return name;
	}



	public String getPassword() {
		return password;
	}

}
