package com.example.administrator.batheasy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class LoadActivity extends AppCompatActivity {
    private final int time = 1000;
    private boolean lag = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {//延时time秒后，将运行如下代码
                if(lag){
                    finish();
                    Intent intent = new Intent(LoadActivity.this , LoginActivity.class);
                    startActivity(intent);
                }
            }
        } , time);
    }

}
