package com.example.administrator.batheasy.bean1;

public enum CardState {

	NORMAL("����"),
	LOST("��ʧ"), 
	ARREAR("����");
	
	private String desc;
	
	private CardState(String desc) {
		this.desc = desc;
	}
	
	
	@Override
    public String toString() {
        return desc;
    }
}
