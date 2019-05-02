package com.example.administrator.batheasy3;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.batheasy3.Accessory.HttpUtils;
import com.example.administrator.batheasy3.Accessory.LinkHelper;
import com.example.administrator.batheasy3.Accessory.LinkServer;
import com.example.administrator.batheasy3.InternalWithServer.ServerReturnAString;
import com.example.administrator.batheasy3.InternalWithServer.ServerReturnBathMsg;
import com.example.administrator.batheasy3.InternalWithServer.ServerReturnBathRoomState;
import com.example.administrator.batheasy3.InternalWithServer.ServerReturnOrderMsg;
import com.example.administrator.batheasy3.InternalWithServer.UserOrderAsk;
import com.example.administrator.batheasy3.InternalWithServer.UserSendQRCode;
import com.example.administrator.batheasy3.bean1.BathRoom;
import com.example.administrator.batheasy3.bean1.UserInfor;
import com.google.gson.Gson;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.bean.ZxingConfig;
import com.yzq.zxinglibrary.common.Constant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/* 预约成功界面 */
public class SuccessOrderActivity extends AppCompatActivity {
    private BathRoom bathRoom;
    private UserInfor user;
    private  ServerReturnBathRoomState srbrs;
    private ServerReturnOrderMsg srom;
    private int roomid;
    private int flag;   //1代表前面有人排队，0代表去洗澡路上，2代表正在洗澡
    public static final int REQUEST_CODE_SCAN = 0x0050;

    private TextView tv_tips;
    private TextView tv_roomid;
    private TextView tv_state;
    private TextView tv_roomaddr;
    private Button bt_cancel;   //0代表可以取消预约。1代表
    private ImageView iv_ewm;
    private TextView tv_isservice;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_successorder);

        Log.w("SuccessOrderAcitivity","进入了SuccessOrderAcitivity！");

        init();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                String content = data.getStringExtra(Constant.CODED_CONTENT);
                //将获取到的码上传到服务器
                printLog(content);
                getInfoServerEWM(content);
            }
        }
    }

    /******************************************************************************
     * 功能：初始化组件
     *******************************************************************************/
    private void initView() {
        tv_roomid = findViewById(R.id.success_tv_roomid);
        tv_state = findViewById(R.id.success_tv_state);
        tv_tips = findViewById(R.id.success_tv_tips);
        bt_cancel = findViewById(R.id.success_bt_cancel);
        tv_roomaddr = findViewById(R.id.success_tv_roomaddr);
        iv_ewm = findViewById(R.id.success_iv_ewm);
        tv_isservice = findViewById(R.id.success_tv_isservice);
    }

    /******************************************************************************
     * 功能：将获取到的二维码
     *******************************************************************************/
    private void getInfoServerEWM(String rNo) {
        printLog("==========="+rNo);
        int Rno = Integer.valueOf(rNo);
        UserSendQRCode usorc = new UserSendQRCode();
        usorc.setUTel(user.getUTel());
        usorc.setRNo(Rno);

        HttpUtils hu = new HttpUtils("Bath",new Gson().toJson(usorc).toString());
        hu.start();

        String clientInfo  = hu.getContent();
        if(clientInfo == null||clientInfo.equals("")){
            printLog("反馈二维码给服务器失败");
            return;
        }
        printLog("反馈二维码给服务器成功");
        ServerReturnBathMsg sri = new Gson().fromJson(clientInfo,ServerReturnBathMsg.class);

        if(sri.getCommand().equals("所选浴室与预约浴室不符")){
            //弹出提示框
            AlertDialog.Builder builder=new AlertDialog.Builder(SuccessOrderActivity.this);
            builder.setIcon(R.drawable.icon_tips_black);
            builder.setTitle("温馨提示");
            builder.setMessage("所选浴室与预约浴室不符！");
            builder.setPositiveButton("我知道了",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
            AlertDialog dialog=builder.create();
            dialog.show();
        }else if(sri.getCommand().equals("浴室正在被使用")||sri.getCommand().equals("前方有人在排队")){
            //弹出提示框
            AlertDialog.Builder builder=new AlertDialog.Builder(SuccessOrderActivity.this);
            builder.setIcon(R.drawable.icon_tips_black);
            builder.setTitle("温馨提示");
            builder.setMessage("浴室正在被使用，请您等候！");
            builder.setPositiveButton("我知道了",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
            AlertDialog dialog=builder.create();
            dialog.show();
        }else if(sri.getCommand().equals("开始使用")){
            //打开使用界面
            Intent intent = new Intent(SuccessOrderActivity.this, SuccessOrderActivity.class);
            intent.putExtra("userinfo",user);
            intent.putExtra("roominfo",bathRoom);
            intent.putExtra("roomid",roomid);
            startActivity(intent);
            finish();
        }else if(sri.getCommand().equals("结束使用")){

            Intent intent = new Intent(SuccessOrderActivity.this, SuccessPayActivity.class);
            intent.putExtra("ServerReturnBathMsg",sri);
            intent.putExtra("userinfo",user);
            intent.putExtra("roomid",roomid);
            startActivity(intent);
        }else{
            AlertDialog.Builder builder=new AlertDialog.Builder(SuccessOrderActivity.this);
            builder.setIcon(R.drawable.icon_tips_black);
            builder.setTitle("温馨提示");
            builder.setMessage("您貌似扫错码了！");
            builder.setPositiveButton("我知道了",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
            AlertDialog dialog=builder.create();
            dialog.show();
        }
    }

    /******************************************************************************
     * 功能：初始化
     *******************************************************************************/
    private void init(){
        //通过intent获取数据
        Intent intent = getIntent();
        user = (UserInfor) intent.getSerializableExtra("userinfo");
        roomid = intent.getIntExtra("roomid",-1);
        //连接服务器，获取当前房间的状态。（空闲，前面排队还有多少人，正在洗澡）

        if(roomid == -1){
            roomid = bathRoom.getBNo();
        }
        getInfoServer();

        initView();
        initData();
        initListener();

        //根据服务器给出的状态就行不同的初始化
        switch (flag){
            case 0:isFree();break;
            case 1:isQueue();break;
            case 2:isBathing();break;
            default:break;
        }
    }

    /******************************************************************************
     * 功能：初始化相关数据
     *******************************************************************************/
    private void initData() {
        tv_state.setText(srom.getOrderState()+"");
        tv_roomid.setText(roomid+"");
        tv_roomaddr.setText(user.getUSchool());

        if(flag == 1 || flag == 0){
            bt_cancel.setTag(0);
        }else{
            bt_cancel.setTag(1);
        }
    }

    /******************************************************************************
     * 功能：初始化监听器
     *******************************************************************************/
    private void initListener() {
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((int)(bt_cancel.getTag()) == 0){
                    //设置取消预约,反馈给服务器
                    getInfoServercancel();
                    Toast.makeText(SuccessOrderActivity.this,"取消预约成功",Toast.LENGTH_SHORT).show();
                    //关闭当前页面
                    finish();
                }else{
                    //设置取消按钮不可用
                    bt_cancel.setClickable(false);
                }
            }
        });


        //扫二维码
        iv_ewm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SuccessOrderActivity.this, CaptureActivity.class);
                ZxingConfig config = new ZxingConfig();
                config.setPlayBeep(true);//是否播放扫描声音 默认为true
                config.setShake(true);//是否震动  默认为true
                config.setDecodeBarCode(true);//是否扫描条形码 默认为true
                config.setReactColor(R.color.colorAccent);//设置扫描框四个角的颜色 默认为白色
                config.setFrameLineColor(R.color.colorAccent);//设置扫描框边框颜色 默认无色
                config.setScanLineColor(R.color.colorAccent);//设置扫描线的颜色 默认白色
                config.setFullScreenScan(false);//是否全屏扫描  默认为true  设为false则只会在扫描框中扫描
                config.setShowbottomLayout(false);
                intent.putExtra(Constant.INTENT_ZXING_CONFIG, config);
                startActivityForResult(intent, REQUEST_CODE_SCAN);
            }
        });
    }

    /******************************************************************************
     * 功能：查找房间状态，预约等相关信息
     *******************************************************************************/
    private void getInfoServer(){
        UserOrderAsk uoa = new UserOrderAsk();
        uoa.setUTel(user.getUTel());
        uoa.setCharacter("user");
        uoa.setCommand("查询预约");
        HttpUtils hu = new HttpUtils("OrderAsk",new Gson().toJson(uoa).toString());
        hu.start();

        String clientInfo  = hu.getContent();
        if(clientInfo == null||clientInfo.equals("")||clientInfo.startsWith("<!doctype html>")){
            printLog("查询预约失败");
            return;
        }else{
            printLog("查询预约成功");
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

    /******************************************************************************
     * 功能：取消预约，向服务器发送请求
     *******************************************************************************/
    private void getInfoServercancel() {
        //查询个人信息
        UserOrderAsk uoa = new UserOrderAsk();
        uoa.setUTel(user.getUTel());
        uoa.setCharacter("user");
        uoa.setCommand("取消预约");
        HttpUtils hu = new HttpUtils("CancelOrder", new Gson().toJson(uoa).toString());
        hu.start();
        String strinfo = null;
        String clientInfo = hu.getContent();
        if (clientInfo == null || clientInfo.equals("")) {
            printLog("取消预约失败");
            return;
        }
        printLog("取消预约成功");
        ServerReturnAString sra = new Gson().fromJson(clientInfo, ServerReturnAString.class);
        strinfo = sra.getStr();

        Toast.makeText(this, strinfo, Toast.LENGTH_SHORT).show();
    }

    /******************************************************************************
     * 功能：打印Log.w信息
     *******************************************************************************/
    private void printLog(String info) {
        Log.w("SuccessOrderActivity",info);
    }

    /******************************************************************************
     * 功能：空闲状态页面的初始化
     *******************************************************************************/
    private void isFree(){
        //这里应获取服务器传来的信息进行初始化
//        String timeStr = srom.getTime();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = srom.getTime();
//        try {
//             date = sdf.parse(timeStr);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
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

    /******************************************************************************
     * 功能：排队状态页面的初始化
     *******************************************************************************/
    private void isQueue(){
        tv_tips.setText("您前面还有"+srom.getQueueNum()+"人");
    }

    /******************************************************************************
     * 功能：正在洗浴状态页面的初始化
     *******************************************************************************/
    private void isBathing(){
        tv_isservice.setText("服务中");
        tv_tips.setText("祝您还有一个良好的体验~");
    }
}

