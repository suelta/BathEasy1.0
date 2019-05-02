package com.example.administrator.batheasy3.MyInfo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.administrator.batheasy3.Accessory.HttpUtils;
import com.example.administrator.batheasy3.InternalWithServer.Message;
import com.example.administrator.batheasy3.InternalWithServer.ServerReturnInfo;
import com.example.administrator.batheasy3.InternalWithServer.UserOrderAsk;
import com.example.administrator.batheasy3.InternalWithServer.UserSetPassword;
import com.example.administrator.batheasy3.LoginActivity;
import com.example.administrator.batheasy3.MainActivity;
import com.example.administrator.batheasy3.R;
import com.example.administrator.batheasy3.bean1.UserInfor;
import com.google.gson.Gson;

public class MysettingActivity extends AppCompatActivity {
    LinearLayout ll_alterpsw;
    LinearLayout setting_tuichu;

    UserInfor userInfo;
    Boolean changpsw = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_mysetting);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("设置");


        Intent intent = getIntent();
        userInfo = (UserInfor) intent.getSerializableExtra("userinfo");

       setting_tuichu = findViewById(R.id.setting_tuichu);
        setting_tuichu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MysettingActivity.this, LoginActivity.class);
                startActivity(intent);
                MainActivity.mActivity.finish();    //关闭主界面
                finish();                           //关闭setting页面
            }
        });

        //修改密码
        final AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        final EditText et = new EditText(this);
        ll_alterpsw = findViewById(R.id.setting_alterpsw);
        ll_alterpsw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changpsw = false;
                builder1.setTitle("请输入您的新密码").setView(et).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newPsw = et.getText().toString();
                        if(newPsw.equals("")|| newPsw == null){
                            Toast.makeText(MysettingActivity.this,"密码不能为空！",Toast.LENGTH_SHORT);
                        }else {
                            getInfoServerAlterpsw(newPsw);
                        }
                        printLog("修改密码ing1");
                        if(changpsw){
                            Toast.makeText(getBaseContext(),"修改密码成功",Toast.LENGTH_LONG);
                        }else{
                            Toast.makeText(getApplicationContext(),"修改密码失败",Toast.LENGTH_LONG);
                        }
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
                printLog("修改密码ing");

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    private void getInfoServerAlterpsw(String newpsw){
        UserSetPassword usp = new UserSetPassword();
        usp.setUTel(userInfo.getUTel());
        usp.setNewPassword(newpsw);
        HttpUtils hu = new HttpUtils("SetPassword",new Gson().toJson(usp).toString());
        hu.start();
        String clientInfo  = hu.getContent();
        if(clientInfo == null || clientInfo.equals("")){
            printLog("获取服务端查询修改密码失败");
        }else{
            printLog("获取服务端查询修改密码成功");
            Message message = new Gson().fromJson(clientInfo,Message.class);
            if(message.getCommand().equals("修改密码成功")){
                changpsw = true;
            }
        }
    }

    /******************************************************************************
     * 功能：打印Log.w信息
     *******************************************************************************/
    private void printLog(String info) {
        Log.w("MysettingActivity",info);
    }
}
