package com.example.administrator.batheasy3.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.batheasy3.R;
import com.example.administrator.batheasy3.bean1.BathRoom;

public class DialogRoomFree extends Dialog {
    private BathRoom bathRoom;
    private Button bt_order;
    private Button bt_return;
    private TextView tv_roomid;

    public Boolean getOrder() {
        return isOrder;
    }

    private Boolean isOrder;

    public DialogRoomFree(@NonNull Context context) {
        super(context);
        isOrder = false;
    }

    public DialogRoomFree(@NonNull Context context, BathRoom bathRoom) {
        super(context);
        this.bathRoom = bathRoom;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        setContentView(R.layout.dialog_room_free);
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
            String id = bathRoom.getRNo()+"";
            tv_roomid.setText(id);
        }

        //给返回键设置监听
        bt_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();  //关闭
            }
        });

        //给预定键设置监听
        bt_order.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"预约成功了？",Toast.LENGTH_SHORT).show();
                isOrder = true;
                dismiss();
            }
        });
    }
}
