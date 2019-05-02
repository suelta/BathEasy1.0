package com.example.administrator.batheasy3.InternalWithServer;

import java.io.Serializable;
import java.util.Date;

public class ServerReturnBathMsg extends Message implements Serializable{

	private Date BTime;
	private Date ETime;
	private double Bmoney;
	public Date getBTime() {
		return BTime;
	}
	public void setBTime(Date bTime) {
		BTime = bTime;
	}
	public Date getETime() {
		return ETime;
	}
	public void setETime(Date eTime) {
		ETime = eTime;
	}
	public double getBmoney() {
		return Bmoney;
	}
	public void setBmoney(double bmoney) {
		Bmoney = bmoney;
	}	
}
