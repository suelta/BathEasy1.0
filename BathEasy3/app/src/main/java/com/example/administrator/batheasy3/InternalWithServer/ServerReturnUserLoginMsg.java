package com.example.administrator.batheasy3.InternalWithServer;

import com.example.administrator.batheasy3.bean1.BathHouse;
import com.example.administrator.batheasy3.bean1.UserInfor;

import java.util.List;


public class ServerReturnUserLoginMsg extends Message {

	private String loginState;
	private UserInfor user;
	private String userPortrait;	//图像
	private List<BathHouse> usableBathHouse;
	
	
	public String getUserPortrait() {
		return userPortrait;
	}
	public void setUserPortrait(String userPortrait) {
		this.userPortrait = userPortrait;
	}
	public UserInfor getUser() {
		return user;
	}
	public void setUser(UserInfor user) {
		this.user = user;
	}
	public String getLoginState() {
		return loginState;
	}
	public void setLoginState(String loginState) {
		loginState = loginState;
	}
	public List<BathHouse> getUsableBathHouse() {
		return usableBathHouse;
	}
	public void setUsableBathHouse(List<BathHouse> usableBathHouse) {
		this.usableBathHouse = usableBathHouse;
	}
}
