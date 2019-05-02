package com.example.administrator.batheasy.InternalWithServer;

import com.example.administrator.batheasy.bean1.ConsumptionRecord;

import java.util.List;


public class ServerReturnConsumptionRecord extends Message {

	private List<ConsumptionRecord> records;

	public List<ConsumptionRecord> getRecords() {
		return records;
	}

	public void setRecords(List<ConsumptionRecord> records) {
		this.records = records;
	}
}
