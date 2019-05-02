package com.example.administrator.batheasy;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.batheasy.Fragment.Fragment_bath;
import com.example.administrator.batheasy.Fragment.Fragment_email;
import com.example.administrator.batheasy.Fragment.Fragment_info;
import com.example.administrator.batheasy.bean.BathRoom;
import com.example.administrator.batheasy.bean.CardInfo;
import com.example.administrator.batheasy.bean.UserInfo;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    UserInfo userInfo;  //个人信息
    CardInfo cardInfo;  //卡信息
    ArrayList<BathRoom> alRoom; //房间信息
    int[] bottomIcon = {R.id.bottom_icon_bath,R.id.bottom_icon_email,R.id.bottom_icon_info};
    int[] bottomIconBlue = {R.drawable.icon_bottom_bash_blue,R.drawable.icon_bottom_email_blue,R.drawable.icon_bottom_info_blue};
    int[] bottomIconGray = {R.drawable.icon_bottom_bash_gray,R.drawable.icon_bottom_email_gray,R.drawable.icon_bottom_info_gray};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        接收LoginActvity的信息初始化相关信息
         */
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("initbundle");
        userInfo = (UserInfo) bundle.getSerializable("userinfo");
        cardInfo = (CardInfo) bundle.getSerializable("cardinfo");
        alRoom = (ArrayList<BathRoom>) bundle.getSerializable("roominfo");

        Log.w("MainActivity","tel:"+userInfo.getUTel()+"card:"+cardInfo.getCBalance());

        /*
        给底部的组件设置监听器
         */
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment f = new Fragment_bath();
        ft.replace(R.id.main_fragment,f);
        ft.commit();


        ImageView imageViewBath = findViewById(R.id.bottom_icon_bath);
        ImageView imageViewInfo = findViewById(R.id.bottom_icon_info);
        ImageView imageViewEmail = findViewById(R.id.bottom_icon_email);
        imageViewBath.setImageResource(R.drawable.icon_bottom_bash_blue);
        imageViewBath.setOnClickListener(l);
        imageViewInfo.setOnClickListener(l);
        imageViewEmail.setOnClickListener(l);


    }

    View.OnClickListener l = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            Toast toast1=Toast.makeText(getApplicationContext(),"点击了1",Toast.LENGTH_LONG);
            toast1.show();
            Fragment f = null;
            int flag = -1;
            switch (v.getId()){
                case R.id.bottom_icon_bath: {
                    f = new Fragment_bath();
                    flag = 0;
                    break;}
                case R.id.bottom_icon_email: {
                    f = new Fragment_email();
                    flag = 1;
                    break;}
                case R.id.bottom_icon_info: {
                    f = new Fragment_info();
                    flag = 2;
                    break;}
            }
            ft.replace(R.id.main_fragment,f);   //替换fragment
            ft.commit();
            for(int i = 0; i < bottomIcon.length;i++){
                ImageView iv = findViewById(bottomIcon[i]);
                iv.setImageResource(bottomIconGray[i]);
                if(i == flag){
                    iv.setImageResource(bottomIconBlue[i]);
                }
            }
        }
    };

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public CardInfo getCardInfo() {
        return cardInfo;
    }

    public void setCardInfo(CardInfo cardInfo) {
        this.cardInfo = cardInfo;
    }

    public ArrayList<BathRoom> getAlRoom() {
        return alRoom;
    }

    public void setAlRoom(ArrayList<BathRoom> alRoom) {
        this.alRoom = alRoom;
    }
}