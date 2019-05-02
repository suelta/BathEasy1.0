package com.example.administrator.batheasy3.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.administrator.batheasy3.MainActivity;
import com.example.administrator.batheasy3.R;
import com.example.administrator.batheasy3.bean1.UserInfor;
import com.paradigm.botkit.BotKitClient;
import com.paradigm.botkit.ChatActivity;
import com.paradigm.botlib.VisitorInfo;

/* 显示邮件的Fragment */
public class Fragment_email extends Fragment {
    RelativeLayout rl;

    UserInfor userinfo;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_email,null);
        init(view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        userinfo = ((MainActivity)context).getUserInfo();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void init(View view) {
        rl = view.findViewById(R.id.email_rl_chatkufu);
        initListener(view);
    }

    private void initListener(View view) {
        rl.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String accessKey="NTg3OCM0ZjViODUwOS01MTJmLTQyYWEtOWFhMy1mYTIwN2NmOGM1NjYjMDlkZDA3YjktODYyYS00YWU0LThmNGItYTgxNmY1MmY4OGIyI2Y0ZTkwMmRkZDNjZTdiNWE2MGVkYWFhOTI3MDIyYzQz";
                BotKitClient.getInstance().init(getContext(),accessKey);
                VisitorInfo visitorInfo = new VisitorInfo();
                visitorInfo.userName = userinfo.getUTel();
                visitorInfo.nickName = userinfo.getUName();
                visitorInfo.mail = "";
                visitorInfo.phone = userinfo.getUTel();
                BotKitClient.getInstance().setVisitor(visitorInfo);
                // 调出客服页面
                Intent intent = new Intent();
                intent.setClass(getActivity(), ChatActivity.class);
                startActivity(intent);
            }
        });
    }
}
