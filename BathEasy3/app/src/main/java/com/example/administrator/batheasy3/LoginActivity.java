package com.example.administrator.batheasy3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.batheasy3.Accessory.HttpUtils;
import com.example.administrator.batheasy3.Accessory.LinkHelper;
import com.example.administrator.batheasy3.Accessory.LinkServer;
import com.example.administrator.batheasy3.InternalWithServer.ServerReturnUserLoginMsg;
import com.example.administrator.batheasy3.InternalWithServer.UserLogin;
import com.example.administrator.batheasy3.bean1.BathHouse;
import com.example.administrator.batheasy3.bean1.UserInfor;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.List;

//import cn.bmob.v3.Bmob;

import static android.widget.Toast.LENGTH_SHORT;

/* 登录界面 */
public class LoginActivity extends AppCompatActivity {

    private Button login;
    private Button register;
    private EditText phoneText;
    private EditText pwdText;
    private String telephone;
    private String password;

    private boolean symbol=false;
    private int index;
    boolean firstConn;
    private UserLogin ul;
    private LinkServer linkServer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);

        init();
    }

    /******************************************************************************
     * 功能：初始化
    *******************************************************************************/
    private void init() {
        initView();
        initListener();
    }

    /******************************************************************************
     * 功能：初始化监听器
     *******************************************************************************/
    private void initListener() {
        //“登录”按键监听
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                telephone=phoneText.getText().toString();
                password=pwdText.getText().toString();
               /* Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);*/
                // radioButton=(RadioButton)findViewById(R.id.radioButton);
                if(telephone.equals("")||password.equals("")){
                    Toast.makeText(LoginActivity.this,"请输入完整信息", LENGTH_SHORT).show();
                }else{
                    //初始化ul
                    ul = new UserLogin(telephone,password);
                    ul.setCharacter("user");
                    ul.setCommand("登录");
                    //登录检查
                    loginCheck();
                }
            }
        });

        //“注册”按钮事件
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开注册界面
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    /******************************************************************************
     * 功能：初始化组件
     *******************************************************************************/
    private void initView() {
        login=(Button)this.findViewById(R.id.login_bt_login);
        register = (Button) this.findViewById(R.id.login_bt_regitser);
        phoneText=(EditText)this.findViewById(R.id.login_et_phone) ;
        pwdText=(EditText)this.findViewById(R.id.login_et_psw);
    }

    /******************************************************************************
     * 功能：登录信息检查
     *******************************************************************************/
    private void loginCheck(){
        HttpUtils hu = new HttpUtils("Login",new Gson().toJson(ul).toString());
        hu.start();

        String clientInfo  = hu.getContent();
        System.out.println("=================================="+clientInfo);
        if(clientInfo == null||clientInfo.equals("")||clientInfo.startsWith("<")){
            printLog("Error,连接服务器失败");
            Toast.makeText(LoginActivity.this,"连接服务器失败",Toast.LENGTH_SHORT).show();
            return;
        }
        ServerReturnUserLoginMsg srulm = new Gson().fromJson(clientInfo,ServerReturnUserLoginMsg.class);
        String loginState = srulm.getLoginState();
        if(loginState.equals("用户名或密码错误")){
            Toast.makeText(LoginActivity.this,"用户名或密码错误",Toast.LENGTH_SHORT).show();
            phoneText.setText("");
            pwdText.setText("");
            return;
        }
        if(loginState.equals("不可重复登录")){
            Toast.makeText(LoginActivity.this,"不可重复登录",Toast.LENGTH_SHORT).show();
            phoneText.setText("");
            pwdText.setText("");
            return;
        }
        UserInfor user = srulm.getUser();
        List<BathHouse> usableBathHouse = srulm.getUsableBathHouse();

        //跳转到主页面
        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
        //使用Bundle将userInfo，cardInfo，alRoom传入Bundle中，最后将bundle传入intent中
        Bundle bundle = new Bundle();
        bundle.putSerializable("userinfo",user);
        bundle.putSerializable("bathhouse", (Serializable) usableBathHouse);
        intent.putExtra("initbundle",bundle);
        startActivity(intent);
        finish();
    }

    /******************************************************************************
     * 功能：打印Log.w信息
     *******************************************************************************/
    private void printLog(String info){
        Log.w("LoginActivity",info);
    }
}
