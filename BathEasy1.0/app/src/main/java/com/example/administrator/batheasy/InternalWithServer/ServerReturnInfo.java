package com.example.administrator.batheasy.InternalWithServer;

import com.example.administrator.batheasy.bean1.Card;
import com.example.administrator.batheasy.bean1.UserInfor;

public class ServerReturnInfo extends Message {

	private Card card;
	private UserInfor user;
	
	public Card getCard() {
		return card;
	}
	public void setCard(Card card) {
		this.card = card;
	}
	public UserInfor getUser() {
		return user;
	}
	public void setUser(UserInfor user) {
		this.user = user;
	}
	
	
}
