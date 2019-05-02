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
                    Toast.makeText(LoadActivity.this , "wait 5s!" , Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LoadActivity.this , LoginActivity.class);
                    startActivity(intent);
                }
            }
        } , time);
        /*Toast.makeText(LoadActivity.this , "hello!" , Toast.LENGTH_LONG).show();
        Log.w("loadactivity","开始了loadactivity");
        Thread myThread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(3000);
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    Log.w("loadactivity","进入了loadactivity");
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();*/
    }

}
