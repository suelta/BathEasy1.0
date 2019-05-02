package com.example.administrator.batheasy3.bean1;

public enum CardState {

	NORMAL("正常"),
	LOST("挂失"), 
	ARREAR("冻结");
	
	private String desc;
	
	private CardState(String desc) {
		this.desc = desc;
	}
	
	
    public String getDesc() {
        return desc;
    }
}
