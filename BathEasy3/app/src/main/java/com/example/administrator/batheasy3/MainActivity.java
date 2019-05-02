package com.example.administrator.batheasy3;

import android.app.Activity;
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

import com.example.administrator.batheasy3.Accessory.HttpUtils;
import com.example.administrator.batheasy3.Fragment.Fragment_bath;
import com.example.administrator.batheasy3.Fragment.Fragment_email;
import com.example.administrator.batheasy3.Fragment.Fragment_info;
import com.example.administrator.batheasy3.InternalWithServer.Message;
import com.example.administrator.batheasy3.InternalWithServer.UserOrderAsk;
import com.example.administrator.batheasy3.MyInfo.MysettingActivity;
import com.example.administrator.batheasy3.bean1.BathHouse;
import com.example.administrator.batheasy3.bean1.UserInfor;
import com.google.gson.Gson;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ImageView imageViewBath;
    ImageView imageViewInfo;
    ImageView imageViewEmail;

    FragmentManager fm;
    FragmentTransaction ft;

    UserInfor userInfo;  //个人信息
    List<BathHouse> alRoom; //房间信息
    public static Activity mActivity;

    //底部图标
    int[] bottomIcon = {R.id.bottom_icon_bath,R.id.bottom_icon_email,R.id.bottom_icon_info};
    int[] bottomIconBlue = {R.drawable.icon_bottom_bash_blue,R.drawable.icon_bottom_email_blue,R.drawable.icon_bottom_info_blue};
    int[] bottomIconGray = {R.drawable.icon_bottom_bash_gray,R.drawable.icon_bottom_email_gray,R.drawable.icon_bottom_info_gray};

    // 功能：设置监听器
    View.OnClickListener l = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            changeFragment(v);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mActivity = this;
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        printLog("==============================================================awsl");
        getInfoServerexit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getInfoServerexit();
    }

    private Boolean getInfoServerexit(){
        UserOrderAsk uoa = new UserOrderAsk();
        uoa.setUTel(userInfo.getUTel());
        uoa.setCommand("退出登录");
        HttpUtils hu = new HttpUtils("Exit",new Gson().toJson(uoa).toString());
        hu.start();
        String clientInfo  = hu.getContent();
        if(clientInfo == null||clientInfo.equals("")||clientInfo.startsWith("<")){
            printLog("退出登录信息同步到服务器失败");
//            Toast.makeText(MainActivity.this,"退出失败", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            printLog("退出登录信息同步到服务器成功");
            Message message = new Gson().fromJson(clientInfo,Message.class);
//            Toast.makeText(MainActivity.this,message.getCommand(), Toast.LENGTH_SHORT).show();
            if(message.getCommand().equals("退出登录成功")){
                return true;
            }else{
                return false;
            }
        }
    }

    /******************************************************************************
     * 功能：初始化
     *******************************************************************************/
    private void init() {
        // 接收LoginActvity的信息初始化相关信息
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("initbundle");
        userInfo = (UserInfor) bundle.getSerializable("userinfo");
        alRoom = (List<BathHouse>) bundle.getSerializable("bathhouse");

        printLog("tel:"+userInfo.getUTel());

        initView();
        initListener();
    }

    /******************************************************************************
     * 功能：初始化监听器
     *******************************************************************************/
    private void initListener() {
        imageViewBath.setOnClickListener(l);
        imageViewInfo.setOnClickListener(l);
        imageViewEmail.setOnClickListener(l);
    }

    /******************************************************************************
     * 功能：初始化组件
     *******************************************************************************/
    private void initView() {
        imageViewBath = findViewById(R.id.bottom_icon_bath);
        imageViewInfo = findViewById(R.id.bottom_icon_info);
        imageViewEmail = findViewById(R.id.bottom_icon_email);
        imageViewBath.setImageResource(R.drawable.icon_bottom_bash_blue);

        // 给底部的组件设置监听器，初始化默认显示的组件
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        Fragment f = new Fragment_bath();
        ft.replace(R.id.main_fragment,f);
        ft.commit();
    }

    /******************************************************************************
     * 功能："点击底部图标"事件
     *******************************************************************************/
    private void changeFragment(View v){
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
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

    /******************************************************************************
     * 功能：打印Log.w信息
     *******************************************************************************/
    private void printLog(String info){
        Log.w("MainActivity",info);
    }

    public UserInfor getUserInfo() {
        return userInfo;
    }

    public void setUserInfor(UserInfor userInfo) {
        this.userInfo = userInfo;
    }

    public List<BathHouse> getAlRoom() {
        return alRoom;
    }

    public void setAlRoom(List<BathHouse> alRoom) {
        this.alRoom = alRoom;
    }
}
