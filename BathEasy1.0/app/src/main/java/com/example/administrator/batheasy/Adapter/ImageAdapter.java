package com.example.administrator.batheasy.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class  ImageAdapter extends BaseAdapter {
    private Context mContext;
    private int[] picture;
    public ImageAdapter(Context c,int[] photo){
        this.mContext = c;
        picture = photo;
    }
    @Override
    public int getCount() {
        return picture.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView ;
        if(convertView == null){
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(100,100));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);//设置缩放方式(保持纵横比缩放)
        }else{
            imageView = (ImageView)convertView;
        }
        imageView.setImageResource(picture[position]);
        return imageView;
    }
}