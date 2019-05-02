package com.example.administrator.batheasy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.administrator.batheasy.Accessory.LinkServer;
import com.example.administrator.batheasy.Accessory.SharedHelper;
import com.example.administrator.batheasy.InternalWithServer.ServerReturnRegisterMsg;
import com.example.administrator.batheasy.InternalWithServer.UserRegister;
import com.example.administrator.batheasy.bean1.UserInfor;
import com.google.gson.Gson;

import org.apache.mina.core.future.ConnectFuture;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
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

    private LinkServer linkServer;
    private ConnectFuture connectFuture;
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
        linkServer = new LinkServer();
        linkServer.start();

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
                /*telephone=phoneText2.getText().toString();
                password=pwdText2.getText().toString();
                password2=surepdText.getText().toString();
                message=yanzhengText.getText().toString();
                if(radioButton0.isChecked())    sex = "男";
                else                             sex = "女";
                school = spinner.getSelectedItem().toString();
                name  = nameText.getText().toString();

                if(telephone.equals("")||password.equals("")||password2.equals("")){
                    Toast.makeText(getApplicationContext(), "请填写完整信息", LENGTH_SHORT).show();
                } else{
                    if(!password.equals(password2)){
                        pwdText2.setText(" ");
                        surepdText.setText(" ");
                        Toast.makeText(getApplicationContext(), "两次密码输入不一致，请重新输入密码!", LENGTH_SHORT).show();
                    }
                    else{
                        UserRegister ur = new UserRegister();
                        ur.setUName(name);
                        ur.setUPwd(password);
                        ur.setUTel(telephone);
                        ur.setUSex(sex);
                        ur.setUSchool(school);
                        ur.setCharacter("user");
                        ur.setCommand("注册");

                        connectFuture = linkServer.getmConnectFuture();
                        if (connectFuture != null && connectFuture.isConnected()) {//与服务器连接上
                            Log.w("RegisterActivity","服务器连接成功");
                            Gson gson = new Gson();
                            String jsonUserinfo = gson.toJson(ur);
                            connectFuture.getSession().write(jsonUserinfo.toString());//发送json字符串
                            String clientInfo;
                            long time1 = System.currentTimeMillis();
                            while(true){
                                clientInfo = linkServer.getClientInfo();//获取返回的字符串
                                if(!clientInfo.equals("")){
                                    break;
                                }
                                if(System.currentTimeMillis()-time1 > 10000){
                                    break;
                                }
                            }

                            Log.w("RegisterActivity","+++++++"+clientInfo);
//{"character":"server","command":"注册结果","registerState":"注册成功"}
                            ServerReturnRegisterMsg srrm= gson.fromJson(clientInfo,ServerReturnRegisterMsg.class);
                            if(srrm.getRegisterState().equals("注册成功")){
                                Log.w("RegisterActivity","注册成功！");
                                Toast.makeText(RegisterActivity.this, "注册成功!", LENGTH_SHORT).show();
                                finish();
                            }else if(srrm.getRegisterState().equals("账号已存在")){
                                Toast.makeText(RegisterActivity.this, "账号已存在!", LENGTH_SHORT).show();
                            }else if(srrm.getRegisterState().equals("注册失败")){
                                Toast.makeText(RegisterActivity.this, "注册失败!", LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(RegisterActivity.this, "服务器未响应", LENGTH_SHORT).show();
                            }

                        }*/



                        BmobSMS.verifySmsCode(phoneText2.getText().toString(), yanzhengText.getText().toString(), new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e==null){
                                    telephone=phoneText2.getText().toString();
                                    password=pwdText2.getText().toString();
                                    password2=surepdText.getText().toString();
                                    message=yanzhengText.getText().toString();
                                    if(radioButton0.isChecked())    sex = "男";
                                    else                             sex = "女";
                                    school = spinner.getSelectedItem().toString();
                                    name  = nameText.getText().toString();

                                    if(telephone.equals("")||password.equals("")||password2.equals("")){
                                        Toast.makeText(getApplicationContext(), "请填写完整信息", LENGTH_SHORT).show();
                                    } else{
                                        if(!password.equals(password2)){
                                            pwdText2.setText(" ");
                                            surepdText.setText(" ");
                                            Toast.makeText(getApplicationContext(), "两次密码输入不一致，请重新输入密码!", LENGTH_SHORT).show();
                                        }
                                        else {
                                            UserRegister ur = new UserRegister();
                                            ur.setUName(name);
                                            ur.setUPwd(password);
                                            ur.setUTel(telephone);
                                            ur.setUSex(sex);
                                            ur.setUSchool(school);
                                            ur.setCharacter("user");
                                            ur.setCommand("注册");

                                            connectFuture = linkServer.getmConnectFuture();
                                            if (connectFuture != null && connectFuture.isConnected()) {//与服务器连接上
                                                Log.w("RegisterActivity", "服务器连接成功");
                                                Gson gson = new Gson();
                                                String jsonUserinfo = gson.toJson(ur);
                                                connectFuture.getSession().write(jsonUserinfo.toString());//发送json字符串
                                                String clientInfo;
                                                long time1 = System.currentTimeMillis();
                                                while (true) {
                                                    clientInfo = linkServer.getClientInfo();//获取返回的字符串
                                                    if (!clientInfo.equals("")) {
                                                        break;
                                                    }
                                                    if (System.currentTimeMillis() - time1 > 10000) {
                                                        break;
                                                    }
                                                }

                                                Log.w("RegisterActivity", "+++++++" + clientInfo);
//{"character":"server","command":"注册结果","registerState":"注册成功"}
                                                ServerReturnRegisterMsg srrm = gson.fromJson(clientInfo, ServerReturnRegisterMsg.class);
                                                if (srrm.getRegisterState().equals("注册成功")) {
                                                    Log.w("RegisterActivity", "注册成功！");
                                                    Toast.makeText(RegisterActivity.this, "注册成功!", LENGTH_SHORT).show();
                                                    finish();
                                                } else if (srrm.getRegisterState().equals("账号已存在")) {
                                                    Toast.makeText(RegisterActivity.this, "账号已存在!", LENGTH_SHORT).show();
                                                } else if (srrm.getRegisterState().equals("注册失败")) {
                                                    Toast.makeText(RegisterActivity.this, "注册失败!", LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(RegisterActivity.this, "服务器未响应", LENGTH_SHORT).show();
                                                }

                                            }

                                            Toast.makeText(getApplicationContext(), "短信注册或登录成功：", LENGTH_SHORT).show();
                                        }}
                                }else{
                                    Toast.makeText(getApplicationContext(), "验证码输入错误：" , LENGTH_SHORT).show();
                                }
                            }
                        });


            }
        });
    }
}
