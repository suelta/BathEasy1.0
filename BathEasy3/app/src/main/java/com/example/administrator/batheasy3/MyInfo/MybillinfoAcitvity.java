package com.example.administrator.batheasy3.MyInfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.batheasy3.Accessory.HttpUtils;
import com.example.administrator.batheasy3.Adapter.ConsumeAdapter;
import com.example.administrator.batheasy3.Adapter.RechargeAdapter;
import com.example.administrator.batheasy3.InternalWithServer.Message;
import com.example.administrator.batheasy3.InternalWithServer.ServerReturnConsumptionRecord;
import com.example.administrator.batheasy3.InternalWithServer.ServerReturnRechargeRecord;
import com.example.administrator.batheasy3.InternalWithServer.UserGetConsumptionRecord;
import com.example.administrator.batheasy3.InternalWithServer.UserGetRechargeRecord;
import com.example.administrator.batheasy3.R;
import com.example.administrator.batheasy3.bean1.Card;
import com.example.administrator.batheasy3.bean1.ConsumptionRecord;
import com.example.administrator.batheasy3.bean1.RechargeRecord;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MybillinfoAcitvity extends AppCompatActivity{
    private List<ConsumptionRecord> ConsumeList=new ArrayList<ConsumptionRecord>();
    private List<RechargeRecord> ChongzhiList=new ArrayList<RechargeRecord>();

    private Button bt_chongzhi;
    private Button bt_consume;
    private TextView signal;

    private Card card;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_mybillinfo);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("账单详情");

        init();


    }

    /******************************************************************************
     * 功能：初始化
     *******************************************************************************/
    private void init() {
        initView();
        initdata();
        initListener();
    }
    /******************************************************************************
     * 功能：初始化监听器
     *******************************************************************************/
    private void initListener() {
        bt_chongzhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RechargeAdapter adapter=new RechargeAdapter(MybillinfoAcitvity.this, R.layout.consume_item,ChongzhiList);
                ListView listView=(ListView)findViewById(R.id.billinfo_lv_recoder);
                listView.setAdapter(adapter);
            }
        });
        bt_consume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConsumeAdapter adapter=new ConsumeAdapter(MybillinfoAcitvity.this,R.layout.consume_item,ConsumeList);
                ListView listView=(ListView)findViewById(R.id.billinfo_lv_recoder);
                listView.setAdapter(adapter);
            }
        });
    }

    /******************************************************************************
     * 功能：初始化数据
     *******************************************************************************/
    private void initdata() {
        Intent intent = getIntent();
        card = (Card) intent.getSerializableExtra("cardinfo");

        UserGetConsumptionRecord ugcr = new UserGetConsumptionRecord();
        ugcr.setUTel(card.getUTel());
        ugcr.setCharacter("server");
        ugcr.setCommand("查询消费记录");
        HttpUtils hu = new HttpUtils("GetConsumptionRecord",new Gson().toJson(ugcr).toString());
        hu.start();
        String clientInfo ="";
        clientInfo = hu.getContent();
        printLog(clientInfo);
        if(clientInfo == null||clientInfo.equals("")||clientInfo.startsWith("<")){
            printLog("查询消费记录失败");
        }else{
            printLog("查询消费记录成功");
            ServerReturnConsumptionRecord srcr = new Gson().fromJson(clientInfo,ServerReturnConsumptionRecord.class);
            ConsumeList = srcr.getRecords();
        }

        UserGetRechargeRecord ugrr = new UserGetRechargeRecord();
        ugrr.setUTel(card.getUTel());
        ugrr.setCharacter("server");
        ugrr.setCommand("查询充值记录");
        HttpUtils hu1 = new HttpUtils("GetRechargeRecord",new Gson().toJson(ugrr).toString());
        hu1.start();

        clientInfo  = hu1.getContent();
        printLog(clientInfo);
        if(clientInfo == null||clientInfo.equals("")||clientInfo.startsWith("<")){
            printLog("查询充值记录失败");
        }else{
            printLog("查询充值记录成功");
            ServerReturnRechargeRecord srrr = new Gson().fromJson(clientInfo,ServerReturnRechargeRecord.class);
            ChongzhiList = srrr.getRecords();
        }


    }
    /******************************************************************************
     * 功能：初始化组件
     *******************************************************************************/
    private void initView() {
        bt_chongzhi=(Button)findViewById(R.id.chongzhi);
        bt_consume=(Button)findViewById(R.id.consume);
        signal=(TextView)findViewById(R.id.consume_signal);
    }

    /******************************************************************************
     * 功能：打印Log.w信息
     *******************************************************************************/
    private void printLog(String info){
        Log.w("MybillinfoActivity",info);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
