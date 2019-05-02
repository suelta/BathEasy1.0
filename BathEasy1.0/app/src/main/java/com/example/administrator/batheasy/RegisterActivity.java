package com.example.administrator.batheasy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.administrator.batheasy.Accessory.SharedHelper;
import com.example.administrator.batheasy.bean.UserInfo;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import static android.widget.Toast.LENGTH_SHORT;

public class RegisterActivity extends AppCompatActivity {
    private String telephone;
    private String password;
    private String password2;
    private String message;
    private String name;
    private String sex;
    private String school;
    private EditText nameText;
    private EditText phoneText2;
    private EditText pwdText2;
    private EditText surepdText;
    private EditText yanzhengText;
    private RadioButton radioButton0;   //代表男
    private RadioButton radioButton1;   //代表女
    private Spinner spinner;            //保存的学校的信息
    private Button sureBtn;
    private Button cancleBtn;
    private Button yanzhengBtn;
    private SharedHelper sh;//
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_register);
       sh = new SharedHelper(getApplicationContext());//
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("注  册");

        Bmob.initialize(this, "82450cd6b2b2f6c72793f60b884c524c");
        Register();
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    void  Register(){
        phoneText2=(EditText)findViewById(R.id.register_et_phone);
        pwdText2=(EditText)findViewById(R.id.register_et_psw);
        surepdText=(EditText)findViewById(R.id.register_et_psw2);
        yanzhengText=(EditText)findViewById(R.id.register_et_verification);
        sureBtn=(Button)findViewById(R.id.regitser_bt_regitser);
        yanzhengBtn=(Button)findViewById(R.id.register_bt_yzm);
        radioButton0 = findViewById(R.id.register_rb_male);
        radioButton1 = findViewById(R.id.register_rb_female);
        spinner = findViewById(R.id.register_sp_school);
        nameText = findViewById(R.id.register_et_name);
        //设置spinner的默认值为第一项
        spinner.setSelection(0);

        /* 设置接收验证码 */
        yanzhengBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * TODO template 如果是自定义短信模板，此处替换为你在控制台设置的自定义短信模板名称；如果没有对应的自定义短信模板，则使用默认短信模板。
                 */
                Toast.makeText(getApplicationContext(), phoneText2.getText().toString() , LENGTH_SHORT).show();
                BmobSMS.requestSMSCode(phoneText2.getText().toString(), "easyBath", new QueryListener<Integer>() {
                    @Override
                    public void done(Integer smsId, BmobException e) {
                        if (e == null) {
                            Toast.makeText(getApplicationContext(), "发送验证码成功，短信ID：" + smsId , LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "发送验证码失败：" + e.getErrorCode() + "-" + e.getMessage(), LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        /* 注册事件 */
        sureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                telephone=phoneText2.getText().toString();
                password=pwdText2.getText().toString();
                password2=surepdText.getText().toString();
                message=yanzhengText.getText().toString();
                if(radioButton0.isChecked())    sex = "男";
                else                             sex = "女";
                school = spinner.getSelectedItem().toString();
                name  = nameText.getText().toString();

                if(telephone.equals("")||password.equals("")||password2.equals("") ||message.equals("")){
                    Toast.makeText(getApplicationContext(), "请填写完整信息", LENGTH_SHORT).show();
                } else{
                    if(!password.equals(password2)){
                        pwdText2.setText(" ");
                        surepdText.setText(" ");
                        Toast.makeText(getApplicationContext(), "两次密码输入不一致，请重新输入密码!", LENGTH_SHORT).show();
                    }
                    else{
                        BmobSMS.verifySmsCode(phoneText2.getText().toString(), yanzhengText.getText().toString(), new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e==null){
                                    Toast.makeText(getApplicationContext(), "短信注册或登录成功：" , LENGTH_SHORT).show();
                                    // 实例化用户表更新或则添加时打开，查询时关闭
                                    //Q:这里的卡号还不知道如何确定？暂时定为空
                                    UserInfo us = new UserInfo(telephone,name,password,sex,school,"",100);
                                    us.save(new SaveListener<String>() {//添加
                                        public void done(String s, BmobException e) {
                                            if (e == null) {
                                                sh.save(telephone,password);//
                                                Toast.makeText(RegisterActivity.this, "注册成功!"+telephone+" "+password, LENGTH_SHORT).show();
                                                finish();
                                            } else {
                                                Toast.makeText(RegisterActivity.this, "注册失败!", LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }else{
                                    Toast.makeText(getApplicationContext(), "验证码输入错误：" , LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            }
        });
    }
}
