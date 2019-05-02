package com.example.administrator.batheasy3.InternalWithServer;

public class UserGetBathRoomState extends Message {

	String UTel;
	private int RNo;

	public String getUTel() {
		return UTel;
	}

	public void setUTel(String uTel) {
		UTel = uTel;
	}

	public int getRNo() {
		return RNo;
	}

	public void setRNo(int rNo) {
		RNo = rNo;
	}
}
