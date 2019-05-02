package com.example.administrator.batheasy3.MyInfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.batheasy3.R;
import com.example.administrator.batheasy3.bean1.Card;

public class MycardActivity extends AppCompatActivity {
    TextView tv_money;
    ImageView iv_rechage;
    ImageView iv_recoder;
    ImageView iv_loss;

    Card card;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_mycard);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("卡信息");

        init();
    }
    /******************************************************************************
     * 功能：初始化
     *******************************************************************************/
    private void init() {
        Intent intent = getIntent();
        card = (Card) intent.getSerializableExtra("cardinfo");
        initView();

        initListener();
    }
    /******************************************************************************
     * 功能：初始化监听器
     *******************************************************************************/
    private void initListener() {
        //“卡充值”事件
        iv_rechage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //“账户明细查询”事件
        iv_recoder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //“卡挂失”事件
        iv_loss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    /******************************************************************************
     * 功能：初始化组件
     *******************************************************************************/
    private void initView() {
        tv_money = findViewById(R.id.myc_tv_money);
        iv_loss = findViewById(R.id.myc_im_loss);
        iv_rechage = findViewById(R.id.myc_im_cz);
        iv_recoder = findViewById(R.id.myc_im_recoder);

        tv_money.setText(card.getCBalance()+"");
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
