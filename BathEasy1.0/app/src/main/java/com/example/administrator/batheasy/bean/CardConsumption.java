package com.example.administrator.batheasy.bean;

import java.io.Serializable;
import java.util.Date;

/*
保存卡的消费和充值记录
 */
public class CardConsumption implements Serializable{
    private String CCNo;          //卡号
    private Date CCTime;         //消费时间
    private double CCMoney;      //消费金额
    private String CCAdress;     //消费地点

    public CardConsumption(String CCNo, Date CCTime, double CCMoney, String CCAdress) {
        this.CCNo = CCNo;
        this.CCTime = CCTime;
        this.CCMoney = CCMoney;
        this.CCAdress = CCAdress;
    }

    public String getCCNo() {
        return CCNo;
    }

    public void setCCNo(String CCNo) {
        this.CCNo = CCNo;
    }

    public Date getCCTime() {
        return CCTime;
    }

    public void setCCTime(Date CCTime) {
        this.CCTime = CCTime;
    }

    public double getCCMoney() {
        return CCMoney;
    }

    public void setCCMoney(double CCMoney) {
        this.CCMoney = CCMoney;
    }

    public String getCCAdress() {
        return CCAdress;
    }

    public void setCCAdress(String CCAdress) {
        this.CCAdress = CCAdress;
    }
}
