package com.example.administrator.batheasy3.InternalWithServer;

import java.util.Date;

public class ServerReturnOrderMsg extends Message {
	//不可重复预约，澡间不存在，澡间不可用，预约成功，信用积分不足无法预约，预约失败
	//已经预约，正在使用，空闲(查看预约信息的)
	private String orderState;
	private int RNo;
	private Date time;	//显示什么时候预约和什么时候开始洗澡
	private int queueNum;

	public int getRNo() {
		return RNo;
	}

	public void setRNo(int rNo) {
		this.RNo = rNo;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public int getQueueNum() {
		return queueNum;
	}

	public void setQueueNum(int queueNum) {
		this.queueNum = queueNum;
	}

	public String getOrderState() {
		return orderState;
	}

	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}
	
	
}
