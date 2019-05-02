package com.example.administrator.batheasy.MyInfo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.example.administrator.batheasy.R;

public class MycardActivity extends AppCompatActivity {




//
//    private ImageView[] img=new ImageView[12];
//    private int[] imagePath=new int[]{
//        R.mipmap.chongzhi,R.mipmap.chongzhi, R.mipmap.chongzhi,
//        R.mipmap.chongzhi, R.mipmap.chongzhi, R.mipmap.chongzhi,
//        R.mipmap.chongzhi, R.mipmap.chongzhi, R.mipmap.chongzhi,
//        R.mipmap.chongzhi, R.mipmap.chongzhi, R.mipmap.chongzhi
//    };
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.layout_mycard);
//        GridLayout layout=(GridLayout)findViewById(R.id.layout);
//        for(int i=0;i<imagePath.length;i++){
//            img[i]=new ImageView(MycardActivity.this);
//            img[i].setImageResource(imagePath[i]);
//            img[i].setPadding(2,2,2,2);
//            ViewGroup.LayoutParams params=new ViewGroup.LayoutParams(100,50);
//            img[i].setLayoutParams(params);
//            layout.addView(img[i]);
//        }
//    }















    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_mycard);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("卡信息");
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
