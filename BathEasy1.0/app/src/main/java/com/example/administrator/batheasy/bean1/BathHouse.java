package com.example.administrator.batheasy.bean1;

import java.io.Serializable;
import java.sql.SQLException;


public class BathHouse implements Serializable{

	private int BNo;		//���ñ��
	private String BName;	//������
	private String BSex;	//��������
	
	
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
