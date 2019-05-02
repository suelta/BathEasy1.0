package com.example.administrator.batheasy.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.administrator.batheasy.R;
import com.example.administrator.batheasy.bean.Consume;

import java.util.List;

public class ConsumeAdapter extends ArrayAdapter {
    private int resouceId;
    public ConsumeAdapter(@NonNull Context context, int resource, @NonNull List<Consume> objects) {
        super(context, resource, objects);
        resouceId=resource;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Consume consume= (Consume) getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(resouceId,null);
        TextView consume_place=(TextView)view.findViewById(R.id.consume_place);
        TextView consume_money=(TextView)view.findViewById(R.id.consume_money);
        TextView consume_time=(TextView)view.findViewById(R.id.consume_time);
        TextView consume_result=(TextView)view.findViewById(R.id.consume_result);
        consume_place.setText(consume.getConsume_place());
        consume_money.setText(consume.getConsume_money());
        consume_time.setText(consume.getConsume_time());
        consume_result.setText(consume.getConsume_result());
        return view;
    }
}
