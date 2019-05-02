package com.example.administrator.batheasy3.bean1;


import java.io.Serializable;

public class UserInfor implements Serializable{

    private String UTel;
    private String UName;
    private String UPwd;
    private int UScore;
    private String USex;
    private String USchool;
    private String UState;

    public UserInfor(String uTel, String uName, String uPwd, int uScore, String uSex, String uSchool, String uState) {
        UTel = uTel;
        UName = uName;
        UPwd = uPwd;
        UScore = uScore;
        USex = uSex;
        USchool = uSchool;
        UState = uState;
    }

    public String getUState() {
        return UState;
    }

    public void setUState(String uState) {
        UState = uState;
    }

    public String getUTel() {
        return UTel;
    }
    public void setUTel(String uTel) {
        UTel = uTel;
    }
    public String getUName() {
        return UName;
    }
    public void setUName(String uName) {
        UName = uName;
    }
    public String getUPwd() {
        return UPwd;
    }
    public void setUPwd(String uPwd) {
        UPwd = uPwd;
    }
    public int getUScore() {
        return UScore;
    }
    public void setUScore(int uScore) {
        UScore = uScore;
    }
    public String getUSex() {
        return USex;
    }
    public void setUSex(String uSex) {
        USex = uSex;
    }
    public String getUSchool() {
        return USchool;
    }
    public void setUSchool(String uSchool) {
        USchool = uSchool;
    }



}

