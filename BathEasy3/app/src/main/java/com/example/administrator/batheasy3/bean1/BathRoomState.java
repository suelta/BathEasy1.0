package com.example.administrator.batheasy3.bean1;

public enum BathRoomState {

	FREE("空闲"),
	USING("使用中"),
	ORDERED("已被预约"),
	UNAVAILABLE("不可用");
	
	private String desc;

	public String getDesc() {
		return desc;
	}

	private BathRoomState(String desc) {
		this.desc = desc;
	}
	


}
