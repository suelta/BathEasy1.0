package com.example.administrator.batheasy.MyInfo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.administrator.batheasy.R;
import com.example.administrator.batheasy.bean1.UserInfor;

import java.util.ArrayList;
import java.util.List;

public class MyinfoActivity extends AppCompatActivity {
    private UserInfor userInfo;
    private TextView tv_name;
    private TextView tv_sex;
    private TextView tv_tel;
    private TextView tv_school;
    private ImageView iv_touxiang;
    private ImageView iv_alter1;    //头像的修改
    private ImageView iv_alter2;    //昵称的修改
    private ImageView iv_alter3;    //性别的修改
    private ImageView iv_alter4;    //学校的修改

    boolean isChange;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_myinfo);
        /*if(NavUtils.getParentActivityName(MyinfoActivity.this)!=null){
            getSupportActionBar().setDisplayShowTitleEnabled(true);     //设置向左的箭头
        }*/
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("个人信息");

        init();
//        initListenr();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    /* 界面初始化 */
    private void init(){
        isChange = false;

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("info_bundle");
        userInfo = (UserInfor) bundle.getSerializable("userinfo");

        tv_name = findViewById(R.id.myinfo_name);
        tv_sex = findViewById(R.id.myinfo_sex);
        tv_school = findViewById(R.id.myinfo_school);
        tv_tel = findViewById(R.id.myinfo_tel);
        iv_touxiang = findViewById(R.id.myinfo_touxinag);

        if(userInfo != null){
            tv_name.setText(userInfo.getUName());
            tv_school.setText(userInfo.getUSchool());
            tv_sex.setText(userInfo.getUSex());
            tv_tel.setText(userInfo.getUTel());
        }else{
            Log.w("MyinfoActivity","没有接收到传来的信息");
        }
    }

    private void initListenr(){

        final AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        final EditText et = new EditText(this);
        iv_alter1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder1.setTitle("修改姓名").setView(et).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tv_name.setText(et.getText().toString());
                        isChange = true;
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
                Intent intent = getIntent();
                if(isChange == true){

                    intent.putExtra("flag","true");
                }else{
                    intent.putExtra("flag","true");
                }
                setResult(0x0021,intent);   //设置返回的信息给Fragemnt_bath
            }
        });

        final AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
        final EditText et2 = new EditText(this);
        iv_alter1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder1.setTitle("修改性别").setView(et).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(et2.getText().toString().equals("男")||et2.getText().toString().equals("女"))
                        tv_sex.setText(et2.getText().toString());
                        isChange = true;
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();

                Intent intent = getIntent();
                if(isChange == true){
                    intent.putExtra("flag","true");
                }else{
                    intent.putExtra("flag","true");
                }
                setResult(0x0021,intent);   //设置返回的信息给Fragemnt_bath
            }
        });

        final AlertDialog.Builder builder3 = new AlertDialog.Builder(this);
        final Spinner spinner = new Spinner(this);
        List<String> list = new ArrayList<String>();
        list.add("beijing");
        list.add("shanghai");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.adapter_item1,list);
        spinner.setAdapter(adapter);
        iv_alter1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder3.setTitle("修改姓名").setView(spinner).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tv_school.setText(spinner.getSelectedItem()+"");
                        isChange = true;
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();

                Intent intent = getIntent();
                if(isChange == true){
                    intent.putExtra("flag","true");
                }else{
                    intent.putExtra("flag","true");
                }
                setResult(0x0021,intent);   //设置返回的信息给Fragemnt_bath
            }
        });
    }

    private void getInfoServerchangeinfo(){
        userInfo.setUName(tv_name.getText().toString());
        userInfo.setUSchool(tv_school.toString());
        userInfo.setUSex(tv_sex.getText().toString());


    }

}
