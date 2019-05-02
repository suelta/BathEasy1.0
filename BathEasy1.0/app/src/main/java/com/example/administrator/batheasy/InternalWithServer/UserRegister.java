package com.example.administrator.batheasy.InternalWithServer;

public class UserRegister extends Message {

	private String UTel;
	private String UName;
	private String UPwd;
	private String USex;
	private String USchool;
	private String UPortrait;

	public String getUPortrait() {
		return UPortrait;
	}
	public void setUPortrait(String uPortrait) {
		UPortrait = uPortrait;
	}
	public String getUTel() {
		return UTel;
	}
	public void setUTel(String uTel) {
		UTel = uTel;
	}
	public String getUName() {
		return UName;
	}
	public void setUName(String uName) {
		UName = uName;
	}
	public String getUPwd() {
		return UPwd;
	}
	public void setUPwd(String uPwd) {
		UPwd = uPwd;
	}
	public String getUSex() {
		return USex;
	}
	public void setUSex(String uSex) {
		USex = uSex;
	}
	public String getUSchool() {
		return USchool;
	}
	public void setUSchool(String uSchool) {
		USchool = uSchool;
	}
	
}
