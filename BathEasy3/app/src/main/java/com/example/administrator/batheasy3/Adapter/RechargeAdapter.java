package com.example.administrator.batheasy3.Adapter;

/* 充值Adapter */

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.administrator.batheasy3.R;
import com.example.administrator.batheasy3.bean1.RechargeRecord;

import java.util.List;

public class RechargeAdapter extends ArrayAdapter {
    private int resouceId;
    public RechargeAdapter(@NonNull Context context, int resource, @NonNull List<RechargeRecord> objects) {
        super(context, resource, objects);
        resouceId=resource;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        RechargeRecord consume= (RechargeRecord) getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(resouceId,null);
        TextView consume_place=(TextView)view.findViewById(R.id.consume_place);
        TextView consume_money=(TextView)view.findViewById(R.id.consume_money);
        TextView consume_time=(TextView)view.findViewById(R.id.consume_time);
        TextView consume_result=(TextView)view.findViewById(R.id.consume_result);
        consume_place.setText(consume.getRWay());
        consume_money.setText(consume.getRMoney()+"");
        consume_time.setText(consume.getRTime()+"");
        consume_result.setText("成功");
        return view;
    }
}
