package com.example.administrator.batheasy3.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.administrator.batheasy3.R;
import com.example.administrator.batheasy3.bean1.Consume;
import com.example.administrator.batheasy3.bean1.ConsumptionRecord;

import java.util.List;

public class ConsumeAdapter extends ArrayAdapter {
    private int resouceId;
    public ConsumeAdapter(@NonNull Context context, int resource, @NonNull List<ConsumptionRecord> objects) {
        super(context, resource, objects);
        resouceId=resource;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ConsumptionRecord consume= (ConsumptionRecord) getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(resouceId,null);
        TextView consume_place=(TextView)view.findViewById(R.id.consume_place);
        TextView consume_money=(TextView)view.findViewById(R.id.consume_money);
        TextView consume_time=(TextView)view.findViewById(R.id.consume_time);
        TextView consume_result=(TextView)view.findViewById(R.id.consume_result);
        consume_place.setText(consume.getCAddress());
        consume_money.setText(consume.getCMoney()+"");
        consume_time.setText(consume.getCTime()+"");
        consume_result.setText("成功");
        return view;
    }
}
