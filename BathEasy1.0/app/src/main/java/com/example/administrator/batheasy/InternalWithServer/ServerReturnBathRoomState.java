package com.example.administrator.batheasy.InternalWithServer;


import com.example.administrator.batheasy.bean1.BathRoom;

public class ServerReturnBathRoomState extends Message {

	private BathRoom bRoom;
	private int queueNum;
	public BathRoom getbRoom() {
		return bRoom;
	}
	public void setbRoom(BathRoom bRoom) {
		this.bRoom = bRoom;
	}
	public int getQueueNum() {
		return queueNum;
	}
	public void setQueueNum(int queueNum) {
		this.queueNum = queueNum;
	}
	
	
	
}
