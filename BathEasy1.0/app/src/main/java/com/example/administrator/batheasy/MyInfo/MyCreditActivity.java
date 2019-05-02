package com.example.administrator.batheasy.MyInfo;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.administrator.batheasy.bean1.UserInfor;
import com.example.administrator.batheasy.myview.CreditSesame;
import com.example.administrator.batheasy.R;

public class MyCreditActivity extends AppCompatActivity {
    RelativeLayout relativeLayout;
    CreditSesame newCreditSesameView;
    Button bt_CreditInfo;

    UserInfor userInfo;
    // The gradient color can define your own
    private final int[] mColors = new int[]{
            0xFF00BFFF,
            0xFF00BFFF,
            0xFF00BFFF
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_mycredit);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("信用积分");

        initView();
    }

    private void initView(){
        newCreditSesameView = findViewById(R.id.sesame_view);
        bt_CreditInfo = findViewById(R.id.mycredit_bt_creditinfo);
        relativeLayout = findViewById(R.id.mycredit_relayout);
        relativeLayout.setBackgroundColor(mColors[0]);
        newCreditSesameView.setSesameValues(100);
        startColorChangeAnim();

        Intent intent = getIntent();
        userInfo = (UserInfor) intent.getSerializableExtra("userinfo");
        if(userInfo == null){
            newCreditSesameView.setSesameValues(100);
            startColorChangeAnim();
        }else{
            newCreditSesameView.setSesameValues(userInfo.getUScore());
            startColorChangeAnim();
        }
    }

    private void initListenr(){
        //设置"查看信用积分明细"
        bt_CreditInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
    // The background color gradient animation Simply illustrates the effect Can customize according to your need
    public void startColorChangeAnim()
    {
        ObjectAnimator animator = ObjectAnimator.ofInt(relativeLayout, "backgroundColor", mColors);
        animator.setDuration(3000);
        animator.setEvaluator(new ArgbEvaluator());
        animator.start();
    }
}
