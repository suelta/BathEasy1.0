package com.example.administrator.batheasy.Fragment;
//这个是用来解析json数据的，暂时先不用，需要同一局域网下，否则无法得到服务器json数据，bath界面加载不出来
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.example.administrator.batheasy.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Fragment_bath2 extends Fragment {
    private GridView gridView;
    private List<Map<String, Object>> dataList;
    private SimpleAdapter adapter;
    String [] from={"img","text"};
    int[] to={R.id.img,R.id.text};
    AlertDialog.Builder builder;
    private String Msge;
    private static final int SHOW_RESPONSE=0;
    Handler handler=new Handler(){
        public void handleMessage(Message msg){
            switch(msg.what){
                case SHOW_RESPONSE:
                    String response=(String)msg.obj;
                    adapter=new SimpleAdapter(getActivity(), dataList, R.layout.gridview_item, from, to);
                    gridView.setAdapter(adapter);
                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                            builder=new AlertDialog.Builder(getActivity());
                            builder.setTitle("提示").setMessage(dataList.get(arg2).get("text").toString()).create().show();
                        }
                    });
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_bath,null);
        gridView= view.findViewById(R.id.bath_gridview);
        sendRequestWidthHtpClient();
     //   initData();//初始化数据
        return view;
    }
    private void sendRequestWidthHtpClient(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpGet httpGet = new HttpGet("http://192.168.1.101:8080/bath.json");
                    HttpResponse httpResponse = httpClient.execute(httpGet);
                    if(httpResponse.getStatusLine().getStatusCode()==200){
                        HttpEntity entity=httpResponse.getEntity();
                        String response= EntityUtils.toString(entity,"utf-8");
                        parseJSONWithJSONObject(response);
                        Message message=new Message();
                        message.what=SHOW_RESPONSE;
                        message.obj=Msge.toString();
                        handler.sendMessage(message);
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void parseJSONWithJSONObject(String jsonData){
        try {
            dataList = new ArrayList<Map<String, Object>>();
            JSONArray jsonArray=new JSONArray(jsonData);
            for(int i=0;i<jsonArray.length();i++){
                Map<String, Object> map = new HashMap<String, Object>();
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                String BNo=jsonObject.getString("BNo");
                String RState=jsonObject.getString("RState");
                if(RState.equals("free")){
                    map.put("img",R.drawable.icon_bath2);
                    map.put("text",BNo);
                }else if(RState.equals("using")){
                    map.put("img",R.drawable.icon_using);
                    map.put("text",BNo);
                }else if(RState.equals("unavailable")){
                    map.put("img",R.drawable.icon_unavailable);
                    map.put("text",BNo);
                }
                Msge+=BNo;//not use
                Msge+=RState;//not use
                dataList.add(map);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    void initData() {
        //图标
        int icno[] = {R.drawable.icon_bath2, R.drawable.icon_bath2, R.drawable.icon_bath2,
                R.drawable.icon_bath2, R.drawable.icon_bath2, R.drawable.icon_bath2, R.drawable.icon_bath2,
                R.drawable.icon_bath2, R.drawable.icon_bath2, R.drawable.icon_bath2, R.drawable.icon_bath2, R.drawable.icon_bath2,
                R.drawable.icon_bath2, R.drawable.icon_bath2, R.drawable.icon_bath2,
                R.drawable.icon_bath2, R.drawable.icon_bath2, R.drawable.icon_bath2, R.drawable.icon_bath2,
                R.drawable.icon_bath2, R.drawable.icon_bath2, R.drawable.icon_bath2, R.drawable.icon_bath2, R.drawable.icon_bath2
        };
        //图标下的文字
        String name[]=new String[40];
        for(int i=1;i<=icno.length;i++){
            name[i-1]=("澡间"+i).toString();
        }
        dataList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < icno.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("img", icno[i]);
            map.put("text", name[i]);
            dataList.add(map);
        }
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
}
