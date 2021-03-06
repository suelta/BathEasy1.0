package com.example.administrator.batheasy.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.administrator.batheasy.Accessory.LinkHelper;
import com.example.administrator.batheasy.Accessory.LinkServer;
import com.example.administrator.batheasy.InternalWithServer.ServerReturnInfo;
import com.example.administrator.batheasy.InternalWithServer.UserOrderAsk;
import com.example.administrator.batheasy.MainActivity;
import com.example.administrator.batheasy.MyInfo.MyCreditActivity;
import com.example.administrator.batheasy.MybillinfoAcitvity;
import com.example.administrator.batheasy.MyInfo.MycardActivity;
import com.example.administrator.batheasy.MyInfo.MyinfoActivity;
import com.example.administrator.batheasy.MyInfo.MyoptionActivity;
import com.example.administrator.batheasy.MyInfo.MyquestionActivity;
import com.example.administrator.batheasy.MyInfo.MysettingActivity;
import com.example.administrator.batheasy.R;
import com.example.administrator.batheasy.bean1.Card;
import com.example.administrator.batheasy.bean1.UserInfor;
import com.google.gson.Gson;

import org.apache.mina.core.future.ConnectFuture;

/* 显示个人信息的Fragment */
public class Fragment_info extends Fragment {
    UserInfor userInfo;
    Card cardInfo;
    ImageView iv_touxiang;
    TextView tv_name;
    TextView tv_money;
    TextView tv_score;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_info,null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();
    }

    /* 获取MainActivity中的数据 */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        userInfo = ((MainActivity)context).getUserInfo();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0x0021 && resultCode == 0x0020){
            String isOrder = data.getStringExtra("isOrder");
            if(isOrder.equals("true")){
                getInfoServerPersonInfo();
                tv_name.setText(userInfo.getUName());
            }
        }
    }

    /******************************************************************************
     * 功能：初始化
     *******************************************************************************/
    private void init() {
        getInfoServerPersonInfo();
        initView();
        initListener();
    }

    /******************************************************************************
     * 功能：从服务器获取个人信息
     *******************************************************************************/
    private void getInfoServerPersonInfo(){
        LinkServer linkServer = new LinkServer();

        ConnectFuture connectFuture = LinkHelper.getConn(linkServer);
        if(connectFuture.isConnected()){
            //查询个人信息
            UserOrderAsk uoa = new UserOrderAsk();
            uoa.setUTel(userInfo.getUTel());
            uoa.setCharacter("user");
            uoa.setCommand("查询用户和卡信息");
            String jsonUserinfo = new Gson().toJson(uoa);
            connectFuture.getSession().write(jsonUserinfo.toString());//发送json字符串

            String clientInfo = LinkHelper.getClientInfo(linkServer);//获取返回的字符串
            if(clientInfo.equals("")){
                printLog("获取服务端查询用户和卡信息信息失败");
            }else{
                printLog("获取服务端查询用户和卡信息信息成功");
                ServerReturnInfo sri = new Gson().fromJson(clientInfo,ServerReturnInfo.class);
                userInfo = sri.getUser();
                cardInfo = sri.getCard();
            }
        }
    }

    /******************************************************************************
     * 功能：初始化组件
     *******************************************************************************/
    private void initView() {
        iv_touxiang = getActivity().findViewById(R.id.info_touxiang);
        tv_name = getActivity().findViewById(R.id.info_name);
        tv_score = getActivity().findViewById(R.id.info_tv_score);
        tv_money = getActivity().findViewById(R.id.info_tv_money);

        if(userInfo !=null){
//            Log.w("Fragment_info","uesrinfo不为空");
            tv_name.setText(userInfo.getUName());
            tv_score.setText(userInfo.getUScore()+"分");
        }
        if(cardInfo != null){
            tv_money.setText(cardInfo.getCBalance()+"元");
        }
    }

    /******************************************************************************
     * 功能：初始化监听器
     *******************************************************************************/
    public void initListener(){
        LinearLayout ll_info = getActivity().findViewById(R.id.main_ll_info);
        ll_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),MyinfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("userinfo",userInfo);
                intent.putExtra("info_bundle",bundle);
                startActivity(intent);
            }
        });

//        TableRow tr_cardinfo = getActivity().findViewById(R.id.tablerow_cardinfo);
        LinearLayout ll_cardinfo = getActivity().findViewById(R.id.main_ll_cardinfo);
        ll_cardinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),MycardActivity.class);
                intent.putExtra("cardinfo",cardInfo);
                startActivity(intent);
            }
        });

//        TableRow tr_bilinfo = getActivity().findViewById(R.id.tablerow_billinfo);
        LinearLayout ll_billinfo = getActivity().findViewById(R.id.main_ll_billinfo);
        ll_billinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),MybillinfoAcitvity.class);
                startActivity(intent);
            }
        });

//        TableRow tr_creditinfo = getActivity().findViewById(R.id.tablerow_credit);
        LinearLayout ll_creditinfo = getActivity().findViewById(R.id.main_ll_cridetinfo);
        ll_creditinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),MyCreditActivity.class);
                intent.putExtra("userinfo",userInfo);
                startActivity(intent);
            }
        });

//        TableRow tr_question = getActivity().findViewById(R.id.tablerow_question);
        LinearLayout ll_question = getActivity().findViewById(R.id.main_ll_question);
        ll_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),MyquestionActivity.class);
                startActivity(intent);
            }
        });

//        TableRow tr_setting = getActivity().findViewById(R.id.tablerow_setting);
        LinearLayout ll_setting = getActivity().findViewById(R.id.main_ll_setting);
        ll_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),MysettingActivity.class);
                intent.putExtra("userinfo",userInfo);
                startActivityForResult(intent,0x0020);
            }
        });

//        TableRow tr_option = getActivity().findViewById(R.id.tablerow_option);
        LinearLayout ll_option = getActivity().findViewById(R.id.main_ll_option);
        ll_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),MyoptionActivity.class);
                startActivity(intent);
            }
        });
    }

    /******************************************************************************
     * 功能：打印Log.w信息
     *******************************************************************************/
    private void printLog(String info) {
        Log.w("Fragment_info",info);
    }
}
