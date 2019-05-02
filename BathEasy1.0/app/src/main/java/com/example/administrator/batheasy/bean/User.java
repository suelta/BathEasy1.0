package com.example.administrator.batheasy.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class User extends BmobObject {
    private String telephone;
    private String password;
    private BmobFile  picture;
   // private String sex;
    public String getTelephone(){return telephone;}
    public void setTelephone(String telephone){this.telephone=telephone;}
    public String getPassword(){return password;}
    public void setPassword(String password){this.password=password;}
    public BmobFile getFile(){return picture;}
    public void setPicture(BmobFile picture){this.picture=picture;}

}
