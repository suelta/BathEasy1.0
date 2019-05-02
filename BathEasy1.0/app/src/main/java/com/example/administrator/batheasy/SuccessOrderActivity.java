package com.example.administrator.batheasy;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.batheasy.Accessory.LinkHelper;
import com.example.administrator.batheasy.Accessory.LinkServer;
import com.example.administrator.batheasy.InternalWithServer.ServerReturnAString;
import com.example.administrator.batheasy.InternalWithServer.ServerReturnBathRoomState;
import com.example.administrator.batheasy.InternalWithServer.ServerReturnInfo;
import com.example.administrator.batheasy.InternalWithServer.ServerReturnOrderMsg;
import com.example.administrator.batheasy.InternalWithServer.UserGetBathRoomState;
import com.example.administrator.batheasy.InternalWithServer.UserOrder;
import com.example.administrator.batheasy.InternalWithServer.UserOrderAsk;
import com.example.administrator.batheasy.bean1.BathRoom;
import com.example.administrator.batheasy.bean1.UserInfor;
import com.google.gson.Gson;

import org.apache.mina.core.future.ConnectFuture;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SuccessOrderActivity extends AppCompatActivity {
    private BathRoom bathRoom;
    private UserInfor user;
    private  ServerReturnBathRoomState srbrs;
    private ServerReturnOrderMsg srom;

    private int flag;   //1代表前面有人排队，0代表去洗澡路上，2代表正在洗澡
    private TextView tv_tips;
    private TextView tv_roomid;
    private TextView tv_state;
    private TextView tv_roomaddr;
    private Button bt_cancel;   //0代表可以取消预约。1代表

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_successorder);
        Log.w("SuccessOrderAcitivity","进入了SuccessOrderAcitivity！");
        Intent intent = getIntent();
        user = (UserInfor) intent.getSerializableExtra("userinfo");

        tv_roomid = findViewById(R.id.success_tv_roomid);
        tv_state = findViewById(R.id.success_tv_state);
        tv_tips = findViewById(R.id.success_tv_tips);
        bt_cancel = findViewById(R.id.success_bt_cancel);
        tv_roomaddr = findViewById(R.id.success_tv_roomaddr);

        //连接服务器，获取当前房间的状态。（空闲，前面排队还有多少人，正在洗澡）
        getInfoServer();
        init();
        //根据服务器给出的状态就行不同的初始化
        switch (flag){
            case 0:isFree();break;
            case 1:isQueue();break;
            case 2:isBathing();break;
            default:break;
        }



    }

    //初始化基本信息
    private void init(){
//        printLog("initing");
        tv_state.setText(srom.getOrderState()+"");
        tv_roomid.setText(srom.getRNo()+"");
        if(flag == 1 || flag == 0){
            bt_cancel.setTag(0);
        }else{
            bt_cancel.setTag(1);
        }
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((int)(bt_cancel.getTag()) == 0){
                    getInfoServer2();
                    //设置取消预约,反馈给服务器
                    Toast.makeText(SuccessOrderActivity.this,"取消预约成功",Toast.LENGTH_SHORT).show();
                    //关闭当前页面
                    finish();
                }else{
                    //设置取消按钮不可用
                    bt_cancel.setClickable(false);
                }

            }
        });
    }

    //查找房间状态，预约等相关信息
    private void getInfoServer(){
        LinkServer linkServer = new LinkServer();
        ConnectFuture connectFuture= LinkHelper.getConn(linkServer);
        if(connectFuture.isConnected()){
            UserOrderAsk uoa = new UserOrderAsk();
            uoa.setUTel(user.getUTel());
            uoa.setCharacter("user");
            uoa.setCommand("查询预约");
            String jsonUserinfo = new Gson().toJson(uoa);
            connectFuture.getSession().write(jsonUserinfo.toString());//发送json字符串

            String clientInfo = LinkHelper.getClientInfo(linkServer);//获取返回的字符串
            if(clientInfo.equals("")){
                printLog("获取服务端信息失败");
            }else{
                printLog("获取服务端信息成功");
                srom = new Gson().fromJson(clientInfo,ServerReturnOrderMsg.class);
                if(srom.getOrderState().equals("正在使用")){
                    flag = 2;
                }else if(srom.getOrderState().equals("已经预约")){
                    if(srom.getQueueNum() == 0){
                        flag = 0;
                    }else{
                        flag = 1;
                    }
                }else{
                    flag = -1;
                }
                printLog("flag:"+flag);
            }
        }
    }

    private void getInfoServer2(){
        String strinfo = null;
        LinkServer linkServer = new LinkServer();
        ConnectFuture connectFuture= LinkHelper.getConn(linkServer);
        if(connectFuture.isConnected()){
            //查询个人信息
            UserOrderAsk uoa = new UserOrderAsk();
            uoa.setUTel(user.getUTel());
            uoa.setCharacter("user");
            uoa.setCommand("取消预约");
            String jsonUserinfo = new Gson().toJson(uoa);
            connectFuture.getSession().write(jsonUserinfo.toString());//发送json字符串

            String clientInfo = LinkHelper.getClientInfo(linkServer);//获取返回的字符串
            if(clientInfo.equals("")){
                printLog("获取服务端查询用户和卡信息信息失败");
            }else{
                printLog("获取服务端查询用户和卡信息信息成功");
                ServerReturnAString sra = new Gson().fromJson(clientInfo,ServerReturnAString.class);
                strinfo = sra.getStr();
            }
        }

        Toast.makeText(this,strinfo,Toast.LENGTH_SHORT).show();

    }


    private void printLog(String info) {
        Log.w("SuccessOrderActivity",info);
    }

    //空闲状态的初始化
    private void isFree(){
        //这里应获取服务器传来的信息进行初始化
        String timeStr = srom.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
             date = sdf.parse(timeStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long time = date.getTime() ;


        CountDownTimer timer = new CountDownTimer(10*60*1000-(System.currentTimeMillis() - time),1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long minute = millisUntilFinished/(1000*60);
                long sec = (millisUntilFinished/1000)%60;
                String timeStr = minute+":"+sec;
                String tipsStr = "请在"+timeStr+"内到达浴室，逾期自动取消";
                tv_tips.setText(tipsStr);
            }

            @Override
            public void onFinish() {
                //finish();   //关闭本窗口
            }
        };
        timer.start();  //启动倒计时
    }

    private void isQueue(){
        tv_tips.setText("您前面还有"+srom.getQueueNum()+"人");
    }

    private void isBathing(){
        tv_tips.setText("祝您还有一个良好的体验~");
    }
}

