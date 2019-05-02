package com.example.administrator.batheasy3.bean1;

import java.io.Serializable;

public class BathRoom implements Serializable{
	
	private int RNo;
	private int BNo;
	private BathRoomState RState;
	private int queueNum;



	//���췽��
	public BathRoom(int rNo, int bNo, BathRoomState rState) {
		RNo = rNo;
		BNo = bNo;
		RState = rState;
	}
	
	
	public int getQueueNum() {
		return queueNum;
	}


	public void setQueueNum(int queueNum) {
		this.queueNum = queueNum;
	}


	//��������
	public int getRNo() {
		return RNo;
	}


	//������ñ��
	public int getBNo() {
		return BNo;
	}


	//������״̬
	public BathRoomState getRState() {
		return RState;
	}



}
