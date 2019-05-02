package com.example.administrator.batheasy3;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.administrator.batheasy3.Accessory.HttpUtils;
import com.example.administrator.batheasy3.InternalWithServer.ServerReturnBathMsg;
import com.example.administrator.batheasy3.InternalWithServer.UserSendQRCode;
import com.example.administrator.batheasy3.bean1.UserInfor;
import com.google.gson.Gson;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SuccessPayActivity extends AppCompatActivity {
    TextView tv_money;
    TextView tv_startTime;
    TextView tv_endTime;
    TextView tv_totalTime;
    TextView tv_roomid;

    private ServerReturnBathMsg sri;
    private UserInfor userinfo;
    private int roomid;

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_successpay);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("结算");
        init();
    }

    /******************************************************************************
     * 功能：初始化
     *******************************************************************************/
    private void init() {
        printLog("结算页面");
        Intent intent = getIntent();
        userinfo = (UserInfor) intent.getSerializableExtra("userinfo");
        roomid = intent.getIntExtra("roomid",-1);
        sri = (ServerReturnBathMsg) intent.getSerializableExtra("ServerReturnBathMsg");

        initView();
    }

    /******************************************************************************
     * 功能：初始化组件
     *******************************************************************************/
    private void initView() {
        tv_money = findViewById(R.id.sp_tv_money);
        tv_startTime = findViewById(R.id.sp_tv_begintime1);
        tv_endTime = findViewById(R.id.sp_tv_endtime);
        tv_totalTime = findViewById(R.id.sp_tv_time);
        tv_roomid = findViewById(R.id.sp_tv_roomid);
        if(sri !=  null){
            tv_money.setText(sri.getBmoney()+"元");
            DateFormat bf = new SimpleDateFormat("MM-dd HH:mm");
            Date stime = sri.getBTime();
            String strtime = bf.format(stime);
            tv_startTime.setText(strtime);
            Date etime = sri.getETime();
            strtime = bf.format(etime);
            tv_endTime.setText(strtime);

            long onDutyBack = stime.getTime();
            long offDutyBack = etime.getTime();
            long totaltime = (offDutyBack-onDutyBack)/(1000 * 60);
            tv_totalTime.setText(totaltime+"分钟");

            tv_roomid.setText(roomid+"");
        }

    }

    private void printLog(String info) {
        Log.w("SuccessPayAcitivity",info);
    }
}
