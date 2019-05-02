package com.example.administrator.batheasy3.InternalWithServer;

public class UserGetBathRooms extends Message {

	private int BNo;
	private String UTel;
	

	public String getUTel() {
		return UTel;
	}

	public void setUTel(String uTel) {
		UTel = uTel;
	}

	public int getBNo() {
		return BNo;
	}

	public void setBNo(int bNo) {
		BNo = bNo;
	}
}
