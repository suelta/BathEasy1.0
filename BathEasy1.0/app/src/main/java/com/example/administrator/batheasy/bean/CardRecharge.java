package com.example.administrator.batheasy.bean;

import java.io.Serializable;
import java.util.Date;

/*
保存卡的充值记录
 */
public class CardRecharge implements Serializable{
    private String CRNo;          //卡号
    private Date CRTime;         //充值时间
    private double CRMoney;      //充值金额
    private String CRAdress;     //充值

    public CardRecharge(String CRNo, Date CRTime, double CRMoney, String CRAdress) {
        this.CRNo = CRNo;
        this.CRTime = CRTime;
        this.CRMoney = CRMoney;
        this.CRAdress = CRAdress;
    }

    public String getCRNo() {
        return CRNo;
    }

    public void setCRNo(String CRNo) {
        this.CRNo = CRNo;
    }

    public Date getCRTime() {
        return CRTime;
    }

    public void setCRTime(Date CRTime) {
        this.CRTime = CRTime;
    }

    public double getCRMoney() {
        return CRMoney;
    }

    public void setCRMoney(double CRMoney) {
        this.CRMoney = CRMoney;
    }

    public String getCRAdress() {
        return CRAdress;
    }

    public void setCRAdress(String CRAdress) {
        this.CRAdress = CRAdress;
    }
}
