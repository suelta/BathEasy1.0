package com.example.administrator.batheasy.MyInfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.administrator.batheasy.LoginActivity;
import com.example.administrator.batheasy.R;//新加的

public class MysettingActivity extends AppCompatActivity {

    LinearLayout setting_tuichu;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_mysetting);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("设置");


       /* setting_tuichu = findViewById(R.id.main_ll_setting);
        setting_tuichu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MysettingActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });*/
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
