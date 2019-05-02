package com.example.administrator.batheasy3.InternalWithServer;

public class UserSetInfo extends Message {

	private String UTel;
	private String newName;
	private String newSex;
	private String newSchool;
	
	public String getNewSex() {
		return newSex;
	}
	public void setNewSex(String newSex) {
		this.newSex = newSex;
	}
	public String getNewSchool() {
		return newSchool;
	}
	public void setNewSchool(String newSchool) {
		this.newSchool = newSchool;
	}
	public String getUTel() {
		return UTel;
	}
	public void setUTel(String uTel) {
		UTel = uTel;
	}
	public String getNewName() {
		return newName;
	}
	public void setNewName(String newName) {
		this.newName = newName;
	}
	
	
}
