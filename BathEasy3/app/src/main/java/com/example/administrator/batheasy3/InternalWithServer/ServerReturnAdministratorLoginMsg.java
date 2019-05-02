package com.example.administrator.batheasy3.InternalWithServer;

import com.example.administrator.batheasy3.bean1.BathRoomState;

import java.util.Map;


public class ServerReturnAdministratorLoginMsg extends Message {

	private String loginState;
	private Map<String, BathRoomState> bRoomStates;
	
	public String getLoginState() {
		return loginState;
	}
	public void setLoginState(String loginState) {
		this.loginState = loginState;
	}
	public Map<String, BathRoomState> getbRoomStates() {
		return bRoomStates;
	}
	public void setbRoomStates(Map<String, BathRoomState> bRoomStates) {
		this.bRoomStates = bRoomStates;
	}
	
	
}
