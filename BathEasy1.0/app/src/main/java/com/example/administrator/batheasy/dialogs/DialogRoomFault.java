package com.example.administrator.batheasy.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.batheasy.R;
import com.example.administrator.batheasy.bean1.BathRoom;

public class DialogRoomFault extends Dialog {
    private BathRoom bathRoom;
    private Button bt_return;
    private TextView tv_roomid;

    public DialogRoomFault(@NonNull Context context) {
        super(context);
    }

    public DialogRoomFault(@NonNull Context context, BathRoom bathRoom) {
        super(context);
        this.bathRoom = bathRoom;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

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
            tv_roomid.setText(bathRoom.getRNo()+"");
        }

        //给返回键设置监听
        bt_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();  //关闭
            }
        });
    }
}
