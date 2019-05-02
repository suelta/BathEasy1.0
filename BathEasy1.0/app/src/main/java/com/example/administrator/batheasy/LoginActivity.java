package com.example.administrator.batheasy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.batheasy.Accessory.LinkHelper;
import com.example.administrator.batheasy.Accessory.LinkServer;
import com.example.administrator.batheasy.InternalWithServer.ServerReturnUserLoginMsg;
import com.example.administrator.batheasy.InternalWithServer.UserLogin;
import com.example.administrator.batheasy.bean1.BathHouse;
import com.example.administrator.batheasy.bean1.UserInfor;
import com.google.gson.Gson;

import org.apache.mina.core.future.ConnectFuture;

import java.io.Serializable;
import java.util.List;

import cn.bmob.v3.Bmob;
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
    private ConnectFuture connectFuture;
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

        //获取服务器端的连接
        linkServer = new LinkServer();
        linkServer.start();
        firstConn = true;

        //初始化Bmob，用来实现“验证码”功能
        Bmob.initialize(this, "82450cd6b2b2f6c72793f60b884c524c");

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
     * 功能：
     *******************************************************************************/
    private void initView() {
        login=(Button)this.findViewById(R.id.login_bt_login);
        register = (Button) this.findViewById(R.id.login_bt_regitser);
        phoneText=(EditText)this.findViewById(R.id.login_et_phone) ;
        pwdText=(EditText)this.findViewById(R.id.login_et_psw);
        /*便于调试*/
        phoneText.setText("123");
        pwdText.setText("123");
    }

    /******************************************************************************
     * 功能：登录信息检查
     *******************************************************************************/
    private void loginCheck() {

        connectFuture = linkServer.getmConnectFuture();
        if(connectFuture==null || !connectFuture.isConnected()){
            linkServer = new LinkServer();
            linkServer.start();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        connectFuture = linkServer.getmConnectFuture();
        if (connectFuture != null && connectFuture.isConnected()) {//与服务器连接上
            Log.w("LoginActivity","服务器连接成功");
            Gson gson = new Gson();
            String jsonUserinfo = gson.toJson(ul);
            connectFuture.getSession().write(jsonUserinfo.toString());//发送json字符串

            printLog(jsonUserinfo);
            /*将clientInfo解析成相应的数据*/
            String clientInfo = LinkHelper.getClientInfo(linkServer);
            if(clientInfo.equals("")){
                printLog("获取服务端信息失败");
                return;
            }
            ServerReturnUserLoginMsg srulm = gson.fromJson(clientInfo,ServerReturnUserLoginMsg.class);
            String loginState = srulm.getLoginState();
            if(loginState.equals("用户名或密码错误")){
                Toast.makeText(LoginActivity.this,"用户名或密码错误",Toast.LENGTH_SHORT).show();
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
        }else{
            Log.w("LoginActivity","服务器连接失败");
            Toast.makeText(LoginActivity.this,"未予服务器连接",Toast.LENGTH_SHORT).show();
        }
    }

    /******************************************************************************
     * 功能：打印Log.w信息
     *******************************************************************************/
    private void printLog(String info){
        Log.w("LoginActivity",info);
    }
}
