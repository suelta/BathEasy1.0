package com.example.administrator.batheasy.MyInfo;

import android.os.Handler;
import android.os.Message;
import android.renderscript.ScriptIntrinsicYuvToRGB;
import android.widget.Switch;

import java.util.Timer;
import java.util.TimerTask;

public class TimerDemo {
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:break;
                default:break;
            }
        }
    };;

    public static void main(String[] args) {
        TimerTask timerTask = new TimerTask() {
            long time = System.currentTimeMillis();
            @Override
            public void run() {
                if(System.currentTimeMillis() - time > 10000) cancel();;
                System.out.println("I am Runing:::"+(System.currentTimeMillis()-time));
            }
        };

        Timer timer = new Timer("");   //为TRUE设置为守护线程
//        timer.schedule(timerTask,1000);

        timer.schedule(timerTask,0,1000);


    }

    public void demo(){
        TimerTask timerTask = new TimerTask() {
            long time = System.currentTimeMillis();
            @Override
            public void run() {

                Message message = Message.obtain();
                message.what = 1;
                handler.sendMessage(message);
            }
        };

        Timer timer = new Timer();



    }
}
