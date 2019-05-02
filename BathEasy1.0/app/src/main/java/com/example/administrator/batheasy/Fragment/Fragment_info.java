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
import android.widget.TableRow;
import android.widget.TextView;

import com.example.administrator.batheasy.MainActivity;
import com.example.administrator.batheasy.MyInfo.MyCreditActivity;
import com.example.administrator.batheasy.MybillinfoAcitvity;
import com.example.administrator.batheasy.MyInfo.MycardActivity;
import com.example.administrator.batheasy.MyInfo.MyinfoActivity;
import com.example.administrator.batheasy.MyInfo.MyoptionActivity;
import com.example.administrator.batheasy.MyInfo.MyquestionActivity;
import com.example.administrator.batheasy.MyInfo.MysettingActivity;
import com.example.administrator.batheasy.R;
import com.example.administrator.batheasy.bean.CardInfo;
import com.example.administrator.batheasy.bean.UserInfo;

public class Fragment_info extends Fragment {
    UserInfo userInfo;
    CardInfo cardInfo;
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
        myinfoInit();
        myinfoEventInit();
    }

    /* 初始化界面的相关值，如：余额等 */
    private void myinfoInit() {
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

    //给myinfo页面设置监听器
    public void myinfoEventInit(){
        TableRow tr_info = getActivity().findViewById(R.id.tablerow_info);
        tr_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),MyinfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("userinfo",userInfo);
                intent.putExtra("info_bundle",bundle);
                startActivity(intent);
            }
        });

        TableRow tr_cardinfo = getActivity().findViewById(R.id.tablerow_cardinfo);
        tr_cardinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),MycardActivity.class);
                startActivity(intent);
            }
        });

        TableRow tr_bilinfo = getActivity().findViewById(R.id.tablerow_billinfo);
        tr_bilinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),MybillinfoAcitvity.class);
                startActivity(intent);
            }
        });

        TableRow tr_creditinfo = getActivity().findViewById(R.id.tablerow_credit);
        tr_creditinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),MyCreditActivity.class);
                startActivity(intent);
            }
        });

        TableRow tr_question = getActivity().findViewById(R.id.tablerow_question);
        tr_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),MyquestionActivity.class);
                startActivity(intent);
            }
        });

        TableRow tr_setting = getActivity().findViewById(R.id.tablerow_setting);
        tr_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),MysettingActivity.class);
                startActivity(intent);
            }
        });

        TableRow tr_option = getActivity().findViewById(R.id.tablerow_option);
        tr_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),MyoptionActivity.class);
                startActivity(intent);
            }
        });
    }

    /* 获取MainActivity中的数据 */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        userInfo = ((MainActivity)context).getUserInfo();
        cardInfo =  ((MainActivity)context).getCardInfo();
    }
}
