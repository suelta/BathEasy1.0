package com.example.administrator.batheasy.InternalWithServer;

import com.example.administrator.batheasy.bean1.BathRoom;

import java.util.List;
import java.util.Map;


public class ServerReturnBathRooms extends Message {

	private List<BathRoom> bRoomList;
	
	public List<BathRoom> getbRoomList() {
		return bRoomList;
	}

	public void setbRoomList(List<BathRoom> bRoomList) {
		this.bRoomList = bRoomList;
	}

	
//	private Map<String, BathRoomState> bRoomStates;
//	
//	public ServerReturnBathRooms() {}
//
//	public Map<String, BathRoomState> getbRoom() {
//		return bRoomStates;
//	}
//
//	public void setbRoom(Map<String, BathRoomState> bRoom) {
//		this.bRoomStates = bRoom;
//	}
}
