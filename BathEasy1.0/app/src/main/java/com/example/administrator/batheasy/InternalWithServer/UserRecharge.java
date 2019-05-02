package com.example.administrator.batheasy.InternalWithServer;

import java.util.Date;

public class UserRecharge extends Message {

	private String CNo;
	private String RWay;
	private double RMoney;
	private Date RTime;
	
	public String getCNo() {
		return CNo;
	}
	public void setCNo(String cNo) {
		CNo = cNo;
	}
	public String getRWay() {
		return RWay;
	}
	public void setRWay(String rWay) {
		RWay = rWay;
	}
	public double getRMoney() {
		return RMoney;
	}
	public void setRMoney(double rMoney) {
		RMoney = rMoney;
	}
	public Date getRTime() {
		return RTime;
	}
	public void setRTime(Date rTime) {
		RTime = rTime;
	}
	
}
