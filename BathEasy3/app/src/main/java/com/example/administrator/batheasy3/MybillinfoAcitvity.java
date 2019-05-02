package com.example.administrator.batheasy3;

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
import com.example.administrator.batheasy3.InternalWithServer.ServerReturnConsumptionRecord;
import com.example.administrator.batheasy3.InternalWithServer.ServerReturnRechargeRecord;
import com.example.administrator.batheasy3.InternalWithServer.UserGetConsumptionRecord;
import com.example.administrator.batheasy3.InternalWithServer.UserGetRechargeRecord;
import com.example.administrator.batheasy3.bean1.Card;
import com.example.administrator.batheasy3.bean1.Consume;
import com.example.administrator.batheasy3.bean1.ConsumptionRecord;
import com.example.administrator.batheasy3.bean1.RechargeRecord;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MybillinfoAcitvity extends AppCompatActivity{
/*    private List<Consume> ConsumeList=new ArrayList<Consume>();
    private List<Consume> ChongzhiList=new ArrayList<Consume>();*/
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

        String info ="";
        info = hu.getContent();System.out.println("=============="+info);printLog(info);

        ServerReturnConsumptionRecord srcr = new Gson().fromJson(info,ServerReturnConsumptionRecord.class);
        ConsumeList = srcr.getRecords();


        UserGetRechargeRecord ugrr = new UserGetRechargeRecord();
        ugrr.setUTel(card.getUTel());
        ugrr.setCharacter("server");
        ugrr.setCommand("查询充值记录");
        HttpUtils hu1 = new HttpUtils("GetRechargeRecord",new Gson().toJson(ugrr).toString());
        hu1.start();

        info  = hu1.getContent();printLog(info);
        ServerReturnRechargeRecord srrr = new Gson().fromJson(info,ServerReturnRechargeRecord.class);
        ChongzhiList = srrr.getRecords();

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
    /*public void initConsume()
    {
        Consume c1=new Consume("澡堂2","20","2015-01-02/19:20","成功");
        ConsumeList.add(c1);
        Consume c2=new Consume("澡堂3","80","2016-04-02/19:20","成功");
        ConsumeList.add(c2);
        Consume c3=new Consume("澡堂9","10","2017-01-07/19:20","成功");
        ConsumeList.add(c3);
        Consume c4=new Consume("澡堂6","40","2018-03-02/19:20","成功");
        ConsumeList.add(c4);
        Consume c5=new Consume("澡堂5","20","2018-05-02/19:20","成功");
        ConsumeList.add(c5);
        Consume c6=new Consume("澡堂3","30","2018-08-02/19:20","成功");
        ConsumeList.add(c6);
        Consume c7=new Consume("澡堂3","25","2018-01-02/19:20","成功");
        ConsumeList.add(c7);
        Consume c8=new Consume("澡堂2","15","2019-08-02/19:20","成功");
        ConsumeList.add(c8);
        Consume c9=new Consume("澡堂7","29","2019-03-02/19:20","成功");
        ConsumeList.add(c9);
        Consume c10=new Consume("澡堂9","40","2019-03-03/19:20","成功");
        ConsumeList.add(c10);

    }


    public void initChongzhi()
    {
        Consume c1=new Consume("澡堂1","20","2015-01-02/19:20","成功");
        ChongzhiList.add(c1);
        Consume c2=new Consume("澡堂3","80","2016-04-02/19:20","成功");
        ChongzhiList.add(c2);
        Consume c3=new Consume("澡堂9","10","2017-01-07/19:20","成功");
        ChongzhiList.add(c3);
        Consume c4=new Consume("澡堂6","40","2018-03-02/19:20","成功");
        ChongzhiList.add(c4);
        Consume c5=new Consume("澡堂5","20","2018-05-02/19:20","成功");
        ChongzhiList.add(c5);
        Consume c6=new Consume("澡堂3","30","2018-08-02/19:20","成功");
        ChongzhiList.add(c6);
        Consume c7=new Consume("澡堂3","25","2018-01-02/19:20","成功");
        ChongzhiList.add(c7);
        Consume c8=new Consume("澡堂2","15","2019-08-02/19:20","成功");
        ChongzhiList.add(c8);
        Consume c9=new Consume("澡堂7","29","2019-03-02/19:20","成功");
        ChongzhiList.add(c9);
        Consume c10=new Consume("澡堂9","40","2019-03-03/19:20","成功");
        ChongzhiList.add(c10);

    }*/







    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
