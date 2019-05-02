package com.example.administrator.batheasy3.InternalWithServer;

public class UserAutoOrder extends Message {

	private int BNo;
	private String UTel;

	public String getUTel() {
		return UTel;
	}

	public void setUTel(String UTel) {
		this.UTel = UTel;
	}

	public int getBNo() {
		return BNo;
	}

	public void setBNo(int bNo) {
		BNo = bNo;
	}
	
}
