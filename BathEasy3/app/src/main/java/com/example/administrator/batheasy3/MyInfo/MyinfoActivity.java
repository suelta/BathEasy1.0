package com.example.administrator.batheasy3.MyInfo;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.administrator.batheasy3.Accessory.HttpUtils;
import com.example.administrator.batheasy3.InternalWithServer.ServerReturnInfo;
import com.example.administrator.batheasy3.InternalWithServer.UserOrderAsk;
import com.example.administrator.batheasy3.R;
import com.example.administrator.batheasy3.bean1.UserInfor;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MyinfoActivity extends AppCompatActivity {
    private TextView tv_name;
    private TextView tv_sex;
    private TextView tv_tel;
    private TextView tv_school;
    private ImageView iv_touxiang;
    private LinearLayout ll_tx;
    private LinearLayout ll_name;
    private LinearLayout ll_sex;
    private LinearLayout ll_school;
    private Button bt_changinfo;

    boolean isChange;               //标识是否被改变
    private UserInfor userInfo;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_myinfo);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("个人信息");

        init();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0x0030 && resultCode == 0x0031){
            String isInfoChange = data.getStringExtra("isInfoChange");
            if(isInfoChange.equals("true")){
                Intent intent = getIntent();
                intent.putExtra("isInfoChange","true");
                setResult(0x0023,intent);
                getInfoServerPersonInfo();
                initdata();
            }
        }


    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    /******************************************************************************
     * 功能：初始化
     *******************************************************************************/
    private void init(){
        isChange = false;

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("info_bundle");
        userInfo = (UserInfor) bundle.getSerializable("userinfo");

        initView();
        initListenr();
    }

    private void initView() {
        tv_name = findViewById(R.id.myinfo_name);
        tv_sex = findViewById(R.id.myinfo_sex);
        tv_school = findViewById(R.id.myinfo_school);
        tv_tel = findViewById(R.id.myinfo_tel);
        iv_touxiang = findViewById(R.id.myinfo_touxinag);
        ll_sex = findViewById(R.id.myinfo_ll_sex);
        ll_school = findViewById(R.id.myinfo_ll_schhool);
        ll_name = findViewById(R.id.myinfo_ll_name);
        bt_changinfo = findViewById(R.id.myinfo_bt_changinfo);
        ll_tx = findViewById(R.id.myinfo_ll_tx);

        initdata();
    }
    /******************************************************************************
     * 功能：初始化相关数据
     *******************************************************************************/
    private void initdata() {
        if(userInfo != null){
            tv_name.setText(userInfo.getUName());
            tv_school.setText(userInfo.getUSchool());
            tv_sex.setText(userInfo.getUSex());
            tv_tel.setText(userInfo.getUTel());
        }else{
            Log.w("MyinfoActivity","没有接收到传来的信息");
        }

        SharedPreferences pref = getSharedPreferences("BathEasyData",MODE_PRIVATE);
        Boolean isFirst = pref.getBoolean("haveportrait",false);
        if(isFirst){
            String newPath = pref.getString("portraitpath","");
            Bitmap bitmap = BitmapFactory.decodeFile(newPath);
            iv_touxiang.setImageBitmap(bitmap);
        }else{
            iv_touxiang.setImageResource(R.drawable.avatar_1);
        }
    }

    /******************************************************************************
     * 功能：初始化监听器
     *******************************************************************************/
    private void initListenr(){
        bt_changinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyinfoActivity.this,MyAlterInfoActivity.class);
                intent.putExtra("userinfo",userInfo);
                startActivityForResult(intent,0x0030);
            }
        });

        ll_tx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyinfoActivity.this,MyAlterInfoActivity.class);
                intent.putExtra("userinfo",userInfo);
                startActivityForResult(intent,0x0030);
            }
        });

        ll_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyinfoActivity.this,MyAlterInfoActivity.class);
                intent.putExtra("userinfo",userInfo);
                startActivityForResult(intent,0x0030);
            }
        });


        ll_sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyinfoActivity.this,MyAlterInfoActivity.class);
                intent.putExtra("userinfo",userInfo);
                startActivityForResult(intent,0x0030);
            }
        });


        ll_school.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyinfoActivity.this,MyAlterInfoActivity.class);
                intent.putExtra("userinfo",userInfo);
                startActivityForResult(intent,0x0030);
            }
        });
    }

    /******************************************************************************
     * 功能：从服务器获取个人信息
     *******************************************************************************/
    private void getInfoServerPersonInfo(){
        //查询个人信息
        UserOrderAsk uoa = new UserOrderAsk();
        uoa.setUTel(userInfo.getUTel());
        uoa.setCharacter("user");
        uoa.setCommand("查询用户和卡信息");
        String jsonUserinfo = new Gson().toJson(uoa);
        HttpUtils hu = new HttpUtils("GetInfo",new Gson().toJson(uoa).toString());
        hu.start();
        String clientInfo  = hu.getContent();
        if(clientInfo == null||clientInfo.equals("")||clientInfo.startsWith("<")){
            printLog("获取服务端查询用户和卡信息信息失败");
        }else{
            printLog("获取服务端查询用户和卡信息信息成功");
            ServerReturnInfo sri = new Gson().fromJson(clientInfo,ServerReturnInfo.class);
            userInfo = sri.getUser();
        }
    }

    /******************************************************************************
     * 功能：打印Log.w信息
     *******************************************************************************/
    private void printLog(String info){
        Log.w("MyinfoActivity",info);
    }
}
