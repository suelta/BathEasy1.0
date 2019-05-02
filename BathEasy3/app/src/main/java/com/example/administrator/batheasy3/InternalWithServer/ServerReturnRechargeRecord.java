package com.example.administrator.batheasy3.InternalWithServer;

import com.example.administrator.batheasy3.bean1.RechargeRecord;

import java.util.List;


public class ServerReturnRechargeRecord extends Message {

	private List<RechargeRecord> records;

	public List<RechargeRecord> getRecords() {
		return records;
	}

	public void setRecords(List<RechargeRecord> records) {
		this.records = records;
	}
	
	
}
