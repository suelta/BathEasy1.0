package com.example.administrator.batheasy.dialogs;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.batheasy.R;
import com.example.administrator.batheasy.bean1.BathRoom;

/* 浴室不能用界面 */
public class RoomFaultInfoActivity extends AppCompatActivity{
    private BathRoom bathRoom;
    private Button bt_return;
    private TextView tv_roomid;

    int roomid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    }

    /******************************************************************************
     * 功能：初始化组件
     *******************************************************************************/
    private void initView() {
        setContentView(R.layout.dialog_room_fault);
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
        tv_roomid = findViewById(R.id.roomfault_roomid);
        bt_return = findViewById(R.id.roomfault_bt_return);

        if(bathRoom != null){
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
                finish();  //关闭
            }
        });
    }
}
