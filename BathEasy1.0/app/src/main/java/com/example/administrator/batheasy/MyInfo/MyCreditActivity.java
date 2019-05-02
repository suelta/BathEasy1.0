package com.example.administrator.batheasy.MyInfo;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
    // 定义的背景颜色
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

        init();
    }

    /******************************************************************************
     * 功能：初始化
     *******************************************************************************/
    private void init(){
        Intent intent = getIntent();
        userInfo = (UserInfor) intent.getSerializableExtra("userinfo");

        initView();
    }

    private void initView() {
        newCreditSesameView = findViewById(R.id.sesame_view);
        bt_CreditInfo = findViewById(R.id.mycredit_bt_creditinfo);
        relativeLayout = findViewById(R.id.mycredit_relayout);
        relativeLayout.setBackgroundColor(mColors[0]);
        newCreditSesameView.setSesameValues(100);
        startColorChangeAnim();

        if(userInfo == null){
            printLog("未获取到intent中的数据");
            newCreditSesameView.setSesameValues(100);
            startColorChangeAnim();
        }else{
            newCreditSesameView.setSesameValues(userInfo.getUScore());
            startColorChangeAnim();
        }
    }

    /******************************************************************************
     * 功能：初始化监听器
     *******************************************************************************/
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

    /******************************************************************************
     * 功能：设置背景的变化
     *******************************************************************************/
    public void startColorChangeAnim()
    {
        ObjectAnimator animator = ObjectAnimator.ofInt(relativeLayout, "backgroundColor", mColors);
        animator.setDuration(3000);
        animator.setEvaluator(new ArgbEvaluator());
        animator.start();
    }
    /******************************************************************************
     * 功能：打印Log.w信息
     *******************************************************************************/
    private void printLog(String info) {
        Log.w("MyCreditActivity",info);
    }
}
