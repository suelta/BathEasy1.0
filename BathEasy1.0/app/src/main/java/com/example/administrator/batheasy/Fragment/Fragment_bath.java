package com.example.administrator.batheasy.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Switch;
import android.widget.Toast;

import com.example.administrator.batheasy.Adapter.ImageAdapter;
import com.example.administrator.batheasy.MainActivity;
import com.example.administrator.batheasy.R;
import com.example.administrator.batheasy.bean.BathRoom;
import com.example.administrator.batheasy.dialogs.DialogRoomBusy;
import com.example.administrator.batheasy.dialogs.DialogRoomFault;
import com.example.administrator.batheasy.dialogs.DialogRoomFree;

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
import static android.widget.Toast.LENGTH_SHORT;
import static com.example.administrator.batheasy.R.layout.gridview_item;
public class Fragment_bath extends Fragment implements DialogRoomBusy.DialogListener {
    private GridView gridView;
    private List<Map<String, Object>> dataList;
    private SimpleAdapter adapter;
    private String [] from={"img","text"};
    private int[] to={R.id.img,R.id.text};
    private AlertDialog.Builder builder;
    private Button buttonorder1;
    private String Msge;

    private ArrayList<BathRoom> alRoom; //房间信息
    private int[] RoomCoin = {R.drawable.icon_bath2,R.drawable.icon_using,R.drawable.icon_unavailable}; //三种房间状态
    private int roomNum;    //房间个数

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_bath,null);
        gridView= view.findViewById(R.id.bath_gridview);
        buttonorder1 = view.findViewById(R.id.button_order);
        initAdapterData();      //初始化Adapter数据
        bathInit();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        alRoom = ((MainActivity)context).getAlRoom();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /* 设置GridView的点击事件 */
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getContext(),position+"点击了",Toast.LENGTH_LONG).show();
    }



    //初始化相关数据
    public void bathInit(){
        //初始化GridView显示内容
        adapter=new SimpleAdapter(getActivity(), dataList, R.layout.gridview_item, from, to);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
               /* builder=new AlertDialog.Builder(getActivity());
                builder.setTitle("提示").setMessage(dataList.get(arg2).get("text").toString()).create().show();*/
               //通过获取房间的状态来查看该房间的消息，并显示不同的Dialog
                switch (alRoom.get(arg2).getRState()){
                    case"FREE":
                        DialogRoomFree drfree = new DialogRoomFree(getContext(),alRoom.get(arg2));
                        drfree.setCanceledOnTouchOutside(false);   //设置点击其他地方不不返回
                        drfree.show();
                        break;
                    case"USING":
                        DialogRoomBusy drb = new DialogRoomBusy(getContext(), alRoom.get(arg2), new DialogRoomBusy.DialogListener() {
                            @Override
                            public void orderChange() {
                                orderChange1();
                            }
                        });
                        drb.setCanceledOnTouchOutside(false);   //设置点击其他地方不不返回
                        drb.show();
                        break;
                    case"UNAVAILABLE":
                        DialogRoomFault drf = new DialogRoomFault(getContext(),alRoom.get(arg2));
                        drf.setCanceledOnTouchOutside(false);   //设置点击其他地方不不返回
                        drf.show();
                        break;
                }
            }
        });

        //“预约”按钮的事件监听
        buttonorder1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(buttonorder1.getText().toString().equals("预约")){
                    //这里进行自动预约
                    //请求服务器

                    //接收服务器传来的数据

                    //进行预约处理
                }else{

                }
            }
        });
    }

    /* 初始化Adapter数据 */
    void initAdapterData(){
        roomNum = 0;
        if(alRoom != null){
            Log.w("Fragment_bath","已接收到房间信息！");
            roomNum = alRoom.size();
            //图标下的文字
            String[] roomName=new String[roomNum];
            //初始化roomName
            for (int i = 0; i < roomNum; i++) {
                roomName[i] = ("澡间"+alRoom.get(i).getRNo()).toString();
            }

            dataList = new ArrayList<Map<String, Object>>();
            for (int i = 0; i < roomNum; i++) {
                Map<String, Object> map = new HashMap<String, Object>();
                int tempIcon = 0;   //保存房间的状态
                switch (alRoom.get(i).getRState()){
                    case"FREE":tempIcon = 0;break;
                    case"USING":tempIcon = 1;break;
                    case"UNAVAILABLE":tempIcon = 2;break;
                }
                map.put("img", RoomCoin[tempIcon]);
                map.put("text", roomName[i]);
                dataList.add(map);
            }
        }else{
            Log.w("Fragment_bath","没有接收到房间信息");
        }
    }


    @Override
    public void orderChange() {}

    /*
   进行预约了，将“预约”按钮变成“我的预约”，实现另一种功能。
    */
    public void orderChange1() {
        buttonorder1.setText("我的预约");
        //将数据传到服务器端，
        //打开

    }


    /* 将数据传到服务器中 */
    private void synData(){

    }

    /*void initData() {
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
    }*/
}
