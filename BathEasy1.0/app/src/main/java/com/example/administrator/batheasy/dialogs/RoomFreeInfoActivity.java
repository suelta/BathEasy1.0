package com.example.administrator.batheasy.dialogs;

import android.appwidget.AppWidgetProvider;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.batheasy.Accessory.LinkHelper;
import com.example.administrator.batheasy.Accessory.LinkServer;
import com.example.administrator.batheasy.InternalWithServer.ServerReturnOrderMsg;
import com.example.administrator.batheasy.InternalWithServer.UserOrder;
import com.example.administrator.batheasy.R;
import com.example.administrator.batheasy.SuccessOrderActivity;
import com.example.administrator.batheasy.bean1.BathRoom;
import com.example.administrator.batheasy.bean1.UserInfor;
import com.google.gson.Gson;

import org.apache.mina.core.future.ConnectFuture;

/* 浴室Free界面 */
public class RoomFreeInfoActivity extends AppCompatActivity{
    private BathRoom bathRoom;
    private Button bt_order;
    private Button bt_return;
    private TextView tv_roomid;

    boolean isOrder;    //设置预约成功的标志
    int roomid;
    private ServerReturnOrderMsg srom;
    private UserInfor userInfor;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_room_free);
        init();
    }

    /******************************************************************************
     * 功能：初始化
     *******************************************************************************/
    private void init() {
        initData();
        initView();
    }

    /******************************************************************************
     * 功能：初始化数据
     *******************************************************************************/
    private void initData(){
        Intent intent = getIntent();
        roomid = intent.getIntExtra("roomid",-1);
        bathRoom = (BathRoom) intent.getSerializableExtra("bath_roominfo");
        userInfor = (UserInfor) intent.getSerializableExtra("userinfo");
    }

    /******************************************************************************
     * 功能：初始化组件
     *******************************************************************************/
    private void initView() {
        // 设置窗口大小
        WindowManager windowManager = getWindow().getWindowManager();
        int screenWidth = windowManager.getDefaultDisplay().getWidth();
        int screenHeight = windowManager.getDefaultDisplay().getHeight();
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        // 设置窗口的宽高（为了更好地适配，请别直接写死）
        attributes.width = screenWidth*4/5;
        attributes.height = screenHeight*3/5;
        getWindow().setAttributes(attributes);

        //初始化组件
        tv_roomid = findViewById(R.id.roomfree_roomid);
        bt_order = findViewById(R.id.roomfree_bt_order);
        bt_return = findViewById(R.id.roomfree_bt_return);

        if(bathRoom != null){
            Log.w("DialogRoomFree",bathRoom.getRNo()+"");
            tv_roomid.setText(roomid+"");
        }

        initListener();
    }

    /******************************************************************************
     * 功能：初始化监听器
     *******************************************************************************/
    private void initListener() {
        //给返回键设置监听
        bt_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returninfo();
                finish();
            }
        });

        //给预定键设置监听
        bt_order.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(RoomFreeInfoActivity.this,"预约成功了？",Toast.LENGTH_SHORT).show();
                getInfoServer();
                //不可重复预约，澡间不存在，澡间不可用，预约成功，信用积分不足无法预约，预约失败
                if(srom.getOrderState().equals("预约成功")){
                    isOrder = true;     //设置预约成功的标志
                    //打开一个预约成功的界面
                    Intent intent = new Intent(RoomFreeInfoActivity.this, SuccessOrderActivity.class);
                    intent.putExtra("userinfo",userInfor);
                    intent.putExtra("roominfo",bathRoom);
                    startActivity(intent);
                    returninfo();
                    finish();
                }else{
                    isOrder = false;
                }
            }
        });
    }

    /******************************************************************************
     * 功能：给父Activity返回消息
     *******************************************************************************/
    public void returninfo(){
        Intent intent = getIntent();
        if(isOrder){
            intent.putExtra("isOrder","true");
        }else{
            intent.putExtra("isOrder","false");
        }
        setResult(0x0004,intent);   //设置返回的信息给Fragemnt_bath
    }

    /******************************************************************************
     * 功能：获取服务器连接
     *******************************************************************************/
    private void getInfoServer(){
        LinkServer linkServer = new LinkServer();
        ConnectFuture connectFuture= LinkHelper.getConn(linkServer);
        if(connectFuture.isConnected()){
            UserOrder uo = new UserOrder();
            uo.setRNo(bathRoom.getRNo());
            uo.setUTel(userInfor.getUTel());
            uo.setCharacter("user");
            uo.setCommand("预约");
            String jsonUserinfo = new Gson().toJson(uo);
            connectFuture.getSession().write(jsonUserinfo.toString());//发送json字符串

            String clientInfo = LinkHelper.getClientInfo(linkServer);//获取返回的字符串
            if(clientInfo.equals("")){
                printLog("获取服务端信息失败");
            }else{
                printLog("获取服务端信息成功");
                srom = new Gson().fromJson(clientInfo,ServerReturnOrderMsg.class);
                Toast.makeText(this,srom.getOrderState(),Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void printLog(String info) {
        Log.w("RoomFreeInfoActivity",info);
    }
}
