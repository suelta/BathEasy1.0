package com.example.administrator.batheasy.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/*
保存用户信息
 */
public class UserInfo extends BmobObject implements Serializable{
    private String UTel;        //用户电话
    private String UName;       //用户昵称
    private String UPassword;   //用户密码
    private String USex;        //用户性别
    private String USchool;     //用户所在学校，校区
    private String UCard;       //用户所持洗澡卡
    private int UScore;         //用户信用积分

    public UserInfo(){}

    public UserInfo(String UTel, String UName, String UPassword, String USex, String USchool, String UCard, int UScore) {
        this.UTel = UTel;
        this.UName = UName;
        this.UPassword = UPassword;
        this.USex = USex;
        this.USchool = USchool;
        this.UCard = UCard;
        this.UScore = UScore;
    }

    public String getUTel() {
        return UTel;
    }

    public void setUTel(String UTel) {
        this.UTel = UTel;
    }

    public String getUName() {
        return UName;
    }

    public void setUName(String UName) {
        this.UName = UName;
    }

    public String getUPassword() {
        return UPassword;
    }

    public void setUPassword(String UPassword) {
        this.UPassword = UPassword;
    }

    public String getUSex() {
        return USex;
    }

    public void setUSex(String USex) {
        this.USex = USex;
    }

    public String getUSchool() {
        return USchool;
    }

    public void setUSchool(String USchool) {
        this.USchool = USchool;
    }

    public String getUCard() {
        return UCard;
    }

    public void setUCard(String UCard) {
        this.UCard = UCard;
    }

    public int getUScore() {
        return UScore;
    }

    public void setUScore(int UScore) {
        this.UScore = UScore;
    }
}
