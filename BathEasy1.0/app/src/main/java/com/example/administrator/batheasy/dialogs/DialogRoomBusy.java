package com.example.administrator.batheasy.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.batheasy.R;
import com.example.administrator.batheasy.bean.BathRoom;

public class DialogRoomBusy extends Dialog{
    private Button bt_return;
    private TextView tv_roomid;
    private TextView tv_ordernum;
    private Button bt_order;
    BathRoom bathRoom;
    DialogListener listener;

    public DialogRoomBusy(@NonNull Context context, BathRoom bathRoom,DialogListener listener) {
        super(context);
        this.bathRoom = bathRoom;
        this.listener = listener;
    }

    /*
    自定义监听器
     */
    public interface DialogListener{
        void orderChange();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        setContentView(R.layout.dialog_room_busy);
        // 设置窗口大小
        WindowManager windowManager = getWindow().getWindowManager();
        int screenWidth = windowManager.getDefaultDisplay().getWidth();
        int screenHeight = windowManager.getDefaultDisplay().getHeight();
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        // 设置窗口的宽高（为了更好地适配，请别直接写死）
        attributes.width = screenWidth*4/5;
        attributes.height = screenHeight*3/5;
        getWindow().setAttributes(attributes);

        //初始化控件
        bt_return = findViewById(R.id.roombusy_bt_return);
        tv_roomid = findViewById(R.id.roombusy_roomid);
        bt_order = findViewById(R.id.roombusy_bt_order);
        tv_ordernum = findViewById(R.id.roombusy_ordernum);

        //设置数据
        if(bathRoom != null){
            tv_roomid.setText(bathRoom.getRNo()+"");
            tv_ordernum.setText(bathRoom.getQueueNum()+"");
        }



        //给返回键设置监听
        bt_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"dialog-ing",Toast.LENGTH_LONG).show();
                dismiss();  //关闭
            }
        });

        //给预定键设置监听
        bt_order.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"预约成功了？",Toast.LENGTH_SHORT).show();

                listener.orderChange();
            }
        });
    }


}
