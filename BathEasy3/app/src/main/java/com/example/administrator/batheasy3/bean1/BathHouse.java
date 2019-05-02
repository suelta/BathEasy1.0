package com.example.administrator.batheasy3.bean1;

import java.io.Serializable;
import java.sql.SQLException;


public class BathHouse implements Serializable{

	private int BNo;
	private String BName;
	private String BSex;
	private String BSchool;

	public void setBNo(int BNo) {
		this.BNo = BNo;
	}

	public String getBSchool() {
		return BSchool;
	}

	public void setBSchool(String BSchool) {
		this.BSchool = BSchool;
	}

	//���췽��
	public BathHouse(int bNo, String bName, String bSex) {
		BNo = bNo;
		BName = bName;
		BSex = bSex;
	}


	//������ñ��
	public int getBNo() {
		return BNo;
	}


	//�����������
	public String getBName() {
		return BName;
	}


	//������������
	public void setBName(String bName) throws SQLException {
		BName = bName;
	}


	//�����������
	public String getBSex() {
		return BSex;
	}


	//������������
	public void setBSex(String bSex) throws SQLException {
		BSex = bSex;
	}
	
	
	
}
