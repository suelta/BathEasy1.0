package com.example.administrator.batheasy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.administrator.batheasy.Accessory.LinkServer;
import com.example.administrator.batheasy.Accessory.SharedHelper;
import com.example.administrator.batheasy.InternalWithServer.UserLogin;
import com.example.administrator.batheasy.bean.BathRoom;
import com.example.administrator.batheasy.bean.CardInfo;
import com.example.administrator.batheasy.bean.UserInfo;
import com.google.gson.Gson;

import org.apache.mina.core.future.ConnectFuture;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import static android.widget.Toast.LENGTH_SHORT;

public class LoginActivity extends AppCompatActivity {
    private Button login;
    private Button register;
    private EditText phoneText;
    private EditText pwdText;
    private RadioButton radioButton;
    private String telephone;
    private String password;
    private boolean symbol=false;
    private int index;
    boolean firstConn;
    private UserLogin ul;   //保存用户名和密码
    private ConnectFuture connectFuture;
    private LinkServer linkServer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);
        //
        Context mContext = getApplicationContext();
        final SharedHelper sh = new SharedHelper(mContext);

        //获取服务器端的连接
//        linkServer = new LinkServer();
//        linkServer.initConfig();
//        linkServer.connect();
        linkServer = new LinkServer();
        linkServer.start();
        firstConn = true;

        Bmob.initialize(this, "82450cd6b2b2f6c72793f60b884c524c");
        login=(Button)this.findViewById(R.id.login_bt_login);
        register = (Button) this.findViewById(R.id.login_bt_regitser);
        phoneText=(EditText)this.findViewById(R.id.login_et_phone) ;
        pwdText=(EditText)this.findViewById(R.id.login_et_psw);
        if(sh.ReadMessage()){
            Map<String,String> data = sh.read();
            phoneText.setText(data.get("telephone"));
            pwdText.setText(data.get("passwd"));

        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                symbol=false;
                index=0;
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
               // Intent intent = new Intent(LoginActivity.this , MainActivity.class);
                //startActivity(intent);
            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                //finish();
            }
        });
    }

    /** 登录信息检查 */
    private void loginCheck() {

        if(firstConn){
            linkServer = new LinkServer();
            linkServer.start();

            try {
                Thread.sleep(100);
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
            Log.w("LoginActivity",jsonUserinfo);

            String clientInfo = linkServer.getClientInfo();

            if(clientInfo.equals("不存在此账户")){
                Toast.makeText(LoginActivity.this,"不存在此账号",Toast.LENGTH_SHORT).show();
                phoneText.setText("");
                pwdText.setText("");
                return;
            }
            if(clientInfo.equals("密码错误")){
                Toast.makeText(LoginActivity.this,"密码错误",Toast.LENGTH_SHORT).show();
                phoneText.setText("");
                pwdText.setText("");
                return;
            }
            /*将clientInfo解析成相应的数据*/
            //初始化登录用户信息
            String UTel = telephone;
            String UName = "rose";
            String UPassword = password;
            String USex = "女";
            String USChool = "武汉理工大学余家头校区";
            String Ucard = "260001";
            int UScore = 100;
            UserInfo userInfo = new UserInfo(UTel,UName,UPassword,USex,USChool,Ucard,UScore);

            //初始化卡信息
            String CState = "NORMAL";
            double CBalance = 46.66;
            CardInfo cardInfo = new CardInfo(UTel,Ucard,CState,CBalance);

            //采用ArrayList来保存澡间信息
            ArrayList<BathRoom> alRoom = new ArrayList<BathRoom>();
            int roomNum = 10;
            BathRoom bathRoom;
            for (int i = 0; i < roomNum; i++) {
                int RNo = i;
                int BNo = 1;
                String RState = "";
                int k = (int)(Math.random()*3);
                Log.w("LoginActivity",k+"");
                switch (k){
//                                            UNAVAILABLE,USING,FREE
                    case 0:RState = "FREE";break;
                    case 1:RState = "UNAVAILABLE";break;
                    case 2:RState = "USING";break;
                    case 3:RState = "FREE";break;
                }
                String BTel = "";
                int queueNum = 0;
                bathRoom = new BathRoom(RNo,BNo,RState,BTel,queueNum);
                alRoom.add(bathRoom);
            }

            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
            //使用Bundle将userInfo，cardInfo，alRoom传入Bundle中，最后将bundle传入intent中
            Bundle bundle = new Bundle();
            bundle.putSerializable("userinfo",userInfo);
            bundle.putSerializable("cardinfo",cardInfo);
            bundle.putSerializable("roominfo",alRoom);
            intent.putExtra("initbundle",bundle);
            startActivity(intent);
            finish();
        }else{
            Log.w("LoginActivity","服务器连接失败");
            Toast.makeText(LoginActivity.this,"未予服务器连接",Toast.LENGTH_SHORT).show();
        }
    }

    void demo(){
        BmobQuery<UserInfo> bmobQuery = new BmobQuery<UserInfo>();
        bmobQuery.findObjects(new FindListener<UserInfo>() {  //按行查询，查到的数据放到List<Goods>的集合
            @Override
            public void done(List<UserInfo> list, BmobException e) {
                if (e == null){
                    for(int i=0;i<list.size();i++){
                        if(telephone.equals(list.get(i).getUTel().toString())&&password.equals(list.get(i).getUPassword().toString())){
                            // Toast.makeText(LoginActivity.this,"查询到",LENGTH_SHORT);
                            symbol=true;
                            index=i;
                            break;
                        }
                    }
                    if(symbol){
                        Toast.makeText(LoginActivity.this,"查询成功！"+list.get(index).getUTel()
                                +list.get(index).getUPassword(), LENGTH_SHORT).show();
                        /*//保存密码和账号
                        if(radioButton.isChecked()){
                            sh.save( telephone, password);
                        }*/
                        phoneText.setText("");
                        pwdText.setText("");

                        /*
                        这里应接收服务器返回的信息，初始化
                         */
                        //初始化登录用户信息
                        String UTel = telephone;
                        String UName = "rose";
                        String UPassword = password;
                        String USex = "女";
                        String USChool = "武汉理工大学余家头校区";
                        String Ucard = "260001";
                        int UScore = 100;
                        UserInfo userInfo = new UserInfo(UTel,UName,UPassword,USex,USChool,Ucard,UScore);

                        //初始化卡信息
                        String CState = "NORMAL";
                        double CBalance = 46.66;
                        CardInfo cardInfo = new CardInfo(UTel,Ucard,CState,CBalance);

                        //采用ArrayList来保存澡间信息
                        ArrayList<BathRoom> alRoom = new ArrayList<BathRoom>();
                        int roomNum = 10;
                        BathRoom bathRoom;
                        for (int i = 0; i < roomNum; i++) {
                            int RNo = i;
                            int BNo = 1;
                            String RState = "";
                            int k = (int)(Math.random()*3);
                            Log.w("LoginActivity",k+"");
                            switch (k){
//                                            UNAVAILABLE,USING,FREE
                                case 0:RState = "FREE";break;
                                case 1:RState = "UNAVAILABLE";break;
                                case 2:RState = "USING";break;
                                case 3:RState = "FREE";break;
                            }
                            String BTel = "";
                            int queueNum = 0;
                            bathRoom = new BathRoom(RNo,BNo,RState,BTel,queueNum);
                            alRoom.add(bathRoom);
                        }

                        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                        //使用Bundle将userInfo，cardInfo，alRoom传入Bundle中，最后将bundle传入intent中
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("userinfo",userInfo);
                        bundle.putSerializable("cardinfo",cardInfo);
                        bundle.putSerializable("roominfo",alRoom);
                        intent.putExtra("initbundle",bundle);
                        startActivity(intent);
                        finish();
                    }else if(!symbol){
                        phoneText.setText("");
                        pwdText.setText("");
                        Toast.makeText(LoginActivity.this,"账号或者密码错误，请重新输入！", LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(LoginActivity.this,"表中没有元素", LENGTH_SHORT).show();
                }
            }
        });
    }
}
