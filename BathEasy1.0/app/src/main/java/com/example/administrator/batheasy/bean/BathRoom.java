package com.example.administrator.batheasy.bean;

import java.io.Serializable;
/*
用来描述澡间的状态
 */
public class BathRoom implements Serializable {
    private int RNo;            //澡间编号
    private int BNo;            //所属澡堂编号
    private String RState;      //房间状态，分为UNAVAILABLE,USING,FREE
    private String BTel;        //使用者的电话号码
    private int QueueNum;      //排队人数

    public BathRoom(int RNo, int BNo, String RState, String UTel, int queueNum) {
        this.RNo = RNo;
        this.BNo = BNo;
        this.RState = RState;
        this.BTel = UTel;
        QueueNum = queueNum;
    }

    public int getRNo() {
        return RNo;
    }

    public void setRNo(int RNo) {
        this.RNo = RNo;
    }

    public int getBNo() {
        return BNo;
    }

    public void setBNo(int BNo) {
        this.BNo = BNo;
    }

    public String getRState() {
        return RState;
    }

    public void setRState(String RState) {
        this.RState = RState;
    }

    public String getUTel() {
        return BTel;
    }

    public void setUTel(String UTel) {
        this.BTel = UTel;
    }

    public int getQueueNum() {
        return QueueNum;
    }

    public void setQueueNum(int queueNum) {
        QueueNum = queueNum;
    }
}
