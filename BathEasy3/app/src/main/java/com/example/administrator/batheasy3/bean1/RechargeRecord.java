package com.example.administrator.batheasy3.bean1;

import java.util.Date;

public class RechargeRecord {

	private String CNo;
	private double RMoney;
	private Date RTime;
	private String RWay;
	
	public RechargeRecord(String cNo, double rMoney, Date rTime, String rWay) {
		CNo = cNo;
		RMoney = rMoney;
		RTime = rTime;
		RWay = rWay;
	}
	public String getCNo() {
		return CNo;
	}
	public void setCNo(String cNo) {
		CNo = cNo;
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
	public String getRWay() {
		return RWay;
	}
	public void setRWay(String rWay) {
		RWay = rWay;
	}
	
	
}
