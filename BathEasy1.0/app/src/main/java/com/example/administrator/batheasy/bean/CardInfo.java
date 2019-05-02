package com.example.administrator.batheasy.bean;

import java.io.Serializable;

/*
保存卡信息
 */
public class CardInfo implements Serializable{
    private String CTel;        //卡对应的电话
    private String CCard;       //卡号
    private String CState;       //卡状态
    private double CBalance;    //卡余额

    public CardInfo(String CTel, String CCard, String CState, double CBalance) {
        this.CTel = CTel;
        this.CCard = CCard;
        this.CState = CState;
        this.CBalance = CBalance;
    }

    public String getCTel() {
        return CTel;
    }

    public void setCTel(String CTel) {
        this.CTel = CTel;
    }

    public String getCCard() {
        return CCard;
    }

    public void setCCard(String CCard) {
        this.CCard = CCard;
    }

    public String getCState() {
        return CState;
    }

    public void setCState(String CState) {
        this.CState = CState;
    }

    public double getCBalance() {
        return CBalance;
    }

    public void setCBalance(double CBalance) {
        this.CBalance = CBalance;
    }
}
