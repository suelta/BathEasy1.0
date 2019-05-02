package com.example.administrator.batheasy.bean1;

import java.io.Serializable;

public class Card  implements Serializable{

	private String CNo;
	private String UTel;
	private double CBalance;
	private CardState CState;
	
	public String getCNo() {
		return CNo;
	}
	public void setCNo(String cNo) {
		CNo = cNo;
	}
	public String getUTel() {
		return UTel;
	}
	public void setUTel(String uTel) {
		UTel = uTel;
	}
	public double getCBalance() {
		return CBalance;
	}
	public void setCBalance(double cBalance) {
		CBalance = cBalance;
	}
	public CardState getCState() {
		return CState;
	}
	public void setCState(CardState cState) {
		CState = cState;
	}
	
	
}
