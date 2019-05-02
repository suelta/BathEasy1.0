package com.example.administrator.batheasy3.MyInfo;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.batheasy3.R;
import com.example.administrator.batheasy3.bean1.Card;

public class MycardActivity extends AppCompatActivity {
    TextView tv_money;
    ImageView iv_rechage;
    ImageView iv_recoder;
    TextView tv_cardid;

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
                new AlertDialog.Builder(MycardActivity.this)
                        .setTitle("提示")
                        .setMessage("app充值正在努力开发中，请到人工处充值")
                        .setPositiveButton("确定", null)
                        .show();
            }
        });

        //“账户明细查询”事件
        iv_recoder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MycardActivity.this,MybillinfoAcitvity.class);
                intent.putExtra("cardinfo",card);
                startActivity(intent);
            }
        });
    }

    /******************************************************************************
     * 功能：初始化组件
     *******************************************************************************/
    private void initView() {
        tv_money = findViewById(R.id.myc_tv_money);
        iv_rechage = findViewById(R.id.myc_im_cz);
        iv_recoder = findViewById(R.id.myc_im_recoder);
        tv_cardid = findViewById(R.id.myc_tv_cardid);

        tv_cardid.setText(card.getCNo());
        tv_money.setText(card.getCBalance()+"");
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
