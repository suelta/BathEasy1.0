package com.example.administrator.batheasy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.batheasy.Adapter.ConsumeAdapter;
import com.example.administrator.batheasy.bean.Consume;

import java.util.ArrayList;
import java.util.List;

public class MybillinfoAcitvity extends AppCompatActivity{
    private List<Consume> ConsumeList=new ArrayList<Consume>();
    private List<Consume> ChongzhiList=new ArrayList<Consume>();
    private Button chongzhi;
    private Button consume;
    private TextView signal;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_mybillinfo);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("账单详情");

        chongzhi=(Button)findViewById(R.id.chongzhi);
        consume=(Button)findViewById(R.id.consume);
        signal=(TextView)findViewById(R.id.consume_signal);
        initChongzhi();
        initConsume();
        chongzhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //signal.setText("+");
                ConsumeAdapter adapter=new ConsumeAdapter(MybillinfoAcitvity.this,R.layout.consume_item,ChongzhiList);
                ListView listView=(ListView)findViewById(R.id.list_view);
                listView.setAdapter(adapter);
            }
        });
        consume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // signal.setText("-");
                ConsumeAdapter adapter=new ConsumeAdapter(MybillinfoAcitvity.this,R.layout.consume_item,ConsumeList);
                ListView listView=(ListView)findViewById(R.id.list_view);
                listView.setAdapter(adapter);
            }
        });


    }

    public void initConsume()
    {
        Consume c1=new Consume("澡堂2","20","2015-01-02/19:20","成功");
        ConsumeList.add(c1);
        Consume c2=new Consume("澡堂3","80","2016-04-02/19:20","成功");
        ConsumeList.add(c2);
        Consume c3=new Consume("澡堂9","10","2017-01-07/19:20","成功");
        ConsumeList.add(c3);
        Consume c4=new Consume("澡堂6","40","2018-03-02/19:20","成功");
        ConsumeList.add(c4);
        Consume c5=new Consume("澡堂5","20","2018-05-02/19:20","成功");
        ConsumeList.add(c5);
        Consume c6=new Consume("澡堂3","30","2018-08-02/19:20","成功");
        ConsumeList.add(c6);
        Consume c7=new Consume("澡堂3","25","2018-01-02/19:20","成功");
        ConsumeList.add(c7);
        Consume c8=new Consume("澡堂2","15","2019-08-02/19:20","成功");
        ConsumeList.add(c8);
        Consume c9=new Consume("澡堂7","29","2019-03-02/19:20","成功");
        ConsumeList.add(c9);
        Consume c10=new Consume("澡堂9","40","2019-03-03/19:20","成功");
        ConsumeList.add(c10);

    }


    public void initChongzhi()
    {
        Consume c1=new Consume("澡堂1","20","2015-01-02/19:20","成功");
        ChongzhiList.add(c1);
        Consume c2=new Consume("澡堂3","80","2016-04-02/19:20","成功");
        ChongzhiList.add(c2);
        Consume c3=new Consume("澡堂9","10","2017-01-07/19:20","成功");
        ChongzhiList.add(c3);
        Consume c4=new Consume("澡堂6","40","2018-03-02/19:20","成功");
        ChongzhiList.add(c4);
        Consume c5=new Consume("澡堂5","20","2018-05-02/19:20","成功");
        ChongzhiList.add(c5);
        Consume c6=new Consume("澡堂3","30","2018-08-02/19:20","成功");
        ChongzhiList.add(c6);
        Consume c7=new Consume("澡堂3","25","2018-01-02/19:20","成功");
        ChongzhiList.add(c7);
        Consume c8=new Consume("澡堂2","15","2019-08-02/19:20","成功");
        ChongzhiList.add(c8);
        Consume c9=new Consume("澡堂7","29","2019-03-02/19:20","成功");
        ChongzhiList.add(c9);
        Consume c10=new Consume("澡堂9","40","2019-03-03/19:20","成功");
        ChongzhiList.add(c10);

    }







    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
