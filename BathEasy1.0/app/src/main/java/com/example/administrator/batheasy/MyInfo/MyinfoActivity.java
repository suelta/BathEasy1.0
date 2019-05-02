package com.example.administrator.batheasy.MyInfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.batheasy.R;
import com.example.administrator.batheasy.bean1.UserInfor;

public class MyinfoActivity extends AppCompatActivity {
    private UserInfor userInfo;
    private TextView tv_name;
    private TextView tv_sex;
    private TextView tv_tel;
    private TextView tv_school;
    private ImageView iv_touxiang;
    private ImageView iv_alter1;    //头像的修改
    private ImageView iv_alter2;    //昵称的修改
    private ImageView iv_alter3;    //性别的修改
    private ImageView iv_alter4;    //学校的修改


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_myinfo);
        /*if(NavUtils.getParentActivityName(MyinfoActivity.this)!=null){
            getSupportActionBar().setDisplayShowTitleEnabled(true);     //设置向左的箭头
        }*/
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("个人信息");

        init();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    /* 界面初始化 */
    private void init(){
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("info_bundle");
        userInfo = (UserInfor) bundle.getSerializable("userinfo");

        tv_name = findViewById(R.id.myinfo_name);
        tv_sex = findViewById(R.id.myinfo_sex);
        tv_school = findViewById(R.id.myinfo_school);
        tv_tel = findViewById(R.id.myinfo_tel);
        iv_touxiang = findViewById(R.id.myinfo_touxinag);

        if(userInfo != null){
            tv_name.setText(userInfo.getUName());
            tv_school.setText(userInfo.getUSchool());
            tv_sex.setText(userInfo.getUSex());
            tv_tel.setText(userInfo.getUTel());
        }else{
            Log.w("MyinfoActivity","没有接收到传来的信息");
        }
    }

}
