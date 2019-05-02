package com.example.administrator.batheasy.InternalWithServer;

public class UserLogin extends Message {

	private String UTel;
	private String UPwd;

	public UserLogin(String UTel, String UPwd) {
		this.UTel = UTel;
		this.UPwd = UPwd;
	}

	public String getUTel() {
		return UTel;
	}
	public void setUTel(String uTel) {
		UTel = uTel;
	}
	public String getUPwd() {
		return UPwd;
	}
	public void setUPwd(String password) {
		this.UPwd = password;
	}
	
}
