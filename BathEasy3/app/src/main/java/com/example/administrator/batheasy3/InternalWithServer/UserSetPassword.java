package com.example.administrator.batheasy3.InternalWithServer;

public class UserSetPassword extends Message {

	private String UTel;
	private String newPassword;
	public String getUTel() {
		return UTel;
	}
	public void setUTel(String uTel) {
		UTel = uTel;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
}
