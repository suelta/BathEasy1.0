package com.example.administrator.batheasy3.bean1;

import java.util.Date;

public class ConsumptionRecord {

	private String CNo;
	private double CMoney;
	private Date CTime;
	private String CAddress;
	
	public ConsumptionRecord(String cNo, double cMoney, Date cTime, String cAddress) {
		super();
		CNo = cNo;
		CMoney = cMoney;
		CTime = cTime;
		CAddress = cAddress;
	}

	public String getCNo() {
		return CNo;
	}
	public void setCNo(String cNo) {
		CNo = cNo;
	}
	public double getCMoney() {
		return CMoney;
	}
	public void setCMoney(double cMoney) {
		CMoney = cMoney;
	}
	public Date getCTime() {
		return CTime;
	}
	public void setCTime(Date cTime) {
		CTime = cTime;
	}
	public String getCAddress() {
		return CAddress;
	}
	public void setCAddress(String cAddress) {
		CAddress = cAddress;
	}
	
}
