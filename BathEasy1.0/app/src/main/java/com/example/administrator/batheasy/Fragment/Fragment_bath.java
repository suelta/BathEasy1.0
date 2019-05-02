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
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.administrator.batheasy.Accessory.LinkHelper;
import com.example.administrator.batheasy.Accessory.LinkServer;
import com.example.administrator.batheasy.InternalWithServer.ServerReturnBathRooms;
import com.example.administrator.batheasy.InternalWithServer.ServerReturnInfo;
import com.example.administrator.batheasy.InternalWithServer.ServerReturnOrderMsg;
import com.example.administrator.batheasy.InternalWithServer.UserAutoOrder;
import com.example.administrator.batheasy.InternalWithServer.UserGetBathRooms;
import com.example.administrator.batheasy.InternalWithServer.UserOrderAsk;
import com.example.administrator.batheasy.MainActivity;
import com.example.administrator.batheasy.R;
import com.example.administrator.batheasy.SuccessOrderActivity;
import com.example.administrator.batheasy.bean1.BathRoom;
import com.example.administrator.batheasy.bean1.BathHouse;
import com.example.administrator.batheasy.bean1.UserInfor;
import com.example.administrator.batheasy.dialogs.DialogRoomBusy;
import com.example.administrator.batheasy.dialogs.DialogRoomFault;
import com.example.administrator.batheasy.dialogs.DialogRoomFree;
import com.example.administrator.batheasy.dialogs.RoomBusyInfoActivity;
import com.example.administrator.batheasy.dialogs.RoomFaultInfoActivity;
import com.example.administrator.batheasy.dialogs.RoomFreeInfoActivity;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import org.apache.mina.core.future.ConnectFuture;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.administrator.batheasy.R.id.bath_spinner;

/* 显示澡堂的Fragment */
public class Fragment_bath extends Fragment implements DialogRoomBusy.DialogListener {
    private GridView gridView;
    private Button buttonorder1;    private Spinner spinner;
    private Fragment fragmentflag;


    private SimpleAdapter adapter;
    private String [] from={"img","text"};
    private int[] to={R.id.img,R.id.text};

    private List<Map<String, Object>> dataList;
    private ArrayAdapter<String> myAdaptertospinner; //用来初始化spinner
    private List<BathHouse> alHouse; //澡堂信息
    private List<BathRoom> alRoom; //浴室信息
    private int[] RoomCoin = {R.drawable.icon_bath_free,R.drawable.icon_bath_using,R.drawable.icon_bath_fault}; //三种房间状态
    private int roomNum;    //房间个数
    private Handler handler;
    private String[] roomName;
    private UserInfor userInfor;
    private LinkServer linkServer;
    private ConnectFuture connectFuture;
    private Activity activityMain;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        printLog("初始化");
        View view = inflater.inflate(R.layout.layout_bath,null);
        init(view);

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0x0001 && resultCode == 0x0002){
            String isOrder = data.getStringExtra("isOrder");
            if(isOrder.equals("true")){
                buttonorder1.setText("我的预约");
                buttonorder1.setTag(true);
            }
        }
        if(requestCode == 0x0003 && resultCode == 0x0004){
            String isOrder = data.getStringExtra("isOrder");
            if(isOrder.equals("true")){
                buttonorder1.setText("我的预约");
                buttonorder1.setTag(true);
            }
        }
    }

    @Override
    public void orderChange() {}

    @Override
    public void onStart() {
        super.onStart();
        printLog("在onstart里面");
        initAdapterData();      //初始化Adapter数据
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        alHouse = ((MainActivity)context).getAlRoom();
        userInfor = ((MainActivity)context).getUserInfo();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /******************************************************************************
     * 功能：初始化
     *******************************************************************************/
    private void init(View view) {
        initView(view);

//        refresh(view);
        activityMain = getActivity();

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 0x0011:{
                        printLog("进入handler 0x0011");
                        getInfoServerBathRoom();

                        //更新界面
                        initdatalist();
                        adapter=new SimpleAdapter(getActivity(), dataList, R.layout.gridview_item, from, to);
                        gridView.setAdapter(adapter);
                        break;
                    }
                };
            }
        };

        initAdapterData();      //初始化Adapter数据
        InitLinstenr();
        refreshOneMinute();
    }

    /******************************************************************************
     * 功能：刷新
     *******************************************************************************/
    private void refresh(View view) {
        RefreshLayout refreshLayout = view.findViewById(R.id.refreshLayout);
        refreshLayout.setRefreshHeader(new BezierRadarHeader(getContext()).setEnableHorizontalDrag(true));
        //设置 Footer 为球脉冲样式
        refreshLayout.setRefreshFooter(new BallPulseFooter(getContext()).setSpinnerStyle(SpinnerStyle.Scale));
       /* final RefreshLayout refreshLayout1 = refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                Message message = new Message();
                message.what = 0x0011;
                handler.sendMessage(message);
                Toast.makeText(getContext(), "更新成功", Toast.LENGTH_LONG).show();
                refreshlayout.finishRefresh(2000*//*,false*//*);//传入false表示刷新失败
            }

            @Override
            public void onRefresh() {
                Message message = new Message();
                message.what = 0x0011;
                handler.sendMessage(message);
                Toast.makeText(getContext(), "更新成功", Toast.LENGTH_LONG).show();
            }
        });*/
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            }
        });
    }

    /******************************************************************************
     * 功能：初始化组件
     *******************************************************************************/
    private void initView(View view) {
        gridView= view.findViewById(R.id.bath_gridview);
        buttonorder1 = view.findViewById(R.id.button_order);
        spinner = view.findViewById(bath_spinner);
        fragmentflag = getFragmentManager().findFragmentById(R.id.main_fragment);
    }

    /******************************************************************************
     * 功能：刷新
     *******************************************************************************/
    public void InitLinstenr(){
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
               /* builder=new AlertDialog.Builder(getActivity());
                builder.setTitle("提示").setMessage(dataList.get(arg2).get("text").toString()).create().show();*/
               //通过获取房间的状态来查看该房间的消息，并显示不同的Dialog
                switch (alRoom.get(arg2).getRState().getDesc()){
                    case"空闲":
                        Intent intent0 = new Intent(getActivity(), RoomFreeInfoActivity.class);
                        intent0.putExtra("bath_roominfo",alRoom.get(arg2));
                        intent0.putExtra("roomid",arg2);
                        intent0.putExtra("userinfo",userInfor);
                        startActivityForResult(intent0,0x0003);
                        break;
                    case "已被预约":
                    case"使用中":
                        /*DialogRoomBusy drb = new DialogRoomBusy(getContext(), alRoom.get(arg2), new DialogRoomBusy.DialogListener() {
                            @Override
                            public void orderChange() {
                                orderChange1();
                            }
                        });
                        drb.setCanceledOnTouchOutside(false);   //设置点击其他地方不不返回
                        drb.show();*/
                        Intent intent1 = new Intent(getActivity(), RoomBusyInfoActivity.class);
                        intent1.putExtra("roomid",arg2);
                        intent1.putExtra("bath_roominfo",alRoom.get(arg2));
                        intent1.putExtra("userinfo",userInfor);
                        startActivityForResult(intent1,0x0001);
                        break;
                    case"不可用":
                        Intent intent2 = new Intent(getActivity(), RoomFaultInfoActivity.class);
                        intent2.putExtra("bath_roominfo",alRoom.get(arg2));
                        intent2.putExtra("roomid",arg2);
                        startActivity(intent2);
                        break;
                }
            }
        });

        //“预约”按钮的事件监听
        buttonorder1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(Boolean)(buttonorder1.getTag())){
                    Log.w("Fragment_bath","这里是自动预约");
                    //这里进行自动预约
                    getInfoServerAutoOrder();
                }else{
                    Log.w("Fragment_bath","这里是我的预约");
                    //打开一个预约成功的界面
                    Intent intent = new Intent(getActivity(), SuccessOrderActivity.class);
                    intent.putExtra("userinfo",userInfor);
                    startActivity(intent);
                }
            }
        });


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //根据所选的item，访问服务器获取相应的浴室信息
                printLog("测试：spinner里面有吗");
                Message message = new Message();
                message.what = 0x0011;
                handler.sendMessage(message);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    /******************************************************************************
     * 功能：初始化Adapter数据
     *******************************************************************************/
    void initAdapterData(){
        //初始化spinner
        List<String> myList = new ArrayList<String>();
        for (int i = 0; i < alHouse.size(); i++) {
            myList.add(alHouse.get(i).getBName());
        }
        myAdaptertospinner = new ArrayAdapter<String>(getActivity(), R.layout.bathspinner_item,R.id.bathspinner_tv_item,myList);
        spinner.setAdapter(myAdaptertospinner);

//        getInfoServerBathRoom();

        getInfoServerPersonInfo();

        if(userInfor.getUState().equals("空闲")){
            buttonorder1.setText("预约");
            buttonorder1.setTag(false);
        }else{
            buttonorder1.setText("我的预约");
            buttonorder1.setTag(true);
        }

        //初始化datalist相关数据
        if(alRoom != null){
            Log.w("Fragment_bath","已接收到房间信息！");
            initdatalist();
        }else{
            Log.w("Fragment_bath","没有接收到房间信息");
        }

        if(dataList == null){
            printLog("dataList为空");
        }else{
            //初始化GridView显示内容
            adapter=new SimpleAdapter(getActivity(), dataList, R.layout.gridview_item, from, to);
            gridView.setAdapter(adapter);
        }
    }

    /******************************************************************************
     * 功能：打印Log.w信息
     *******************************************************************************/
    private void printLog(String info){
        Log.w("Fragment_bath",info);
    }

    /******************************************************************************
     * 功能：打初始化datalist
     *******************************************************************************/
    private void initdatalist(){
        roomNum = alRoom.size();
        //图标下的文字
        roomName=new String[roomNum];
        //初始化roomName
        for (int i = 0; i < roomNum; i++) {
            roomName[i] = "浴室-"+i;
        }

        dataList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < roomNum; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            int tempIcon = 0;   //保存房间的状态

            switch (alRoom.get(i).getRState().getDesc()){
                case"空闲":tempIcon = 0;break;
                case"使用中":tempIcon = 1;break;
                case"已被预约":tempIcon = 1;break;
                case"不可用":tempIcon = 2;break;
            }
            map.put("img", RoomCoin[tempIcon]);
            map.put("text", roomName[i]);
            dataList.add(map);
        }
    }

    /******************************************************************************
     * 功能：自动预约功能
     *******************************************************************************/
    private void getInfoServerAutoOrder(){
        ServerReturnOrderMsg srom = null;
        LinkServer linkServer = new LinkServer();
        ConnectFuture connectFuture= LinkHelper.getConn(linkServer);
        if(connectFuture.isConnected()){
            UserAutoOrder uao = new UserAutoOrder();
            uao.setBNo(alHouse.get(spinner.getSelectedItemPosition()).getBNo());
            uao.setUTel(userInfor.getUTel());
            uao.setCharacter("user");
            uao.setCommand("自动预约");
            String jsonUserinfo = new Gson().toJson(uao);
            connectFuture.getSession().write(jsonUserinfo.toString());//发送json字符串

            String clientInfo = LinkHelper.getClientInfo(linkServer);//获取返回的字符串
            if(clientInfo.equals("")){
                printLog("获取服务端信息失败");
            }else{
                printLog("获取服务端信息成功");
                srom = new Gson().fromJson(clientInfo,ServerReturnOrderMsg.class);
            }
        }
        if(srom.getOrderState().equals("自动预约成功")){
            //打开一个预约成功的界面
            Intent intent = new Intent(getActivity(), SuccessOrderActivity.class);
            buttonorder1.setText("我的预约");
            buttonorder1.setTag(true);
            intent.putExtra("userinfo",userInfor);
            startActivity(intent);
        }else{
            Toast.makeText(getActivity(),srom.getOrderState()+"",Toast.LENGTH_SHORT).show();
        }
    }

    /******************************************************************************
     * 功能：查询某澡堂所有澡间
     *******************************************************************************/
    private void getInfoServerBathRoom(){
        linkServer = new LinkServer();
        connectFuture= LinkHelper.getConn(linkServer);
        if(connectFuture.isConnected()){
            //查询某澡堂所有澡间
            BathHouse bathHouse= alHouse.get(spinner.getSelectedItemPosition());
            UserGetBathRooms ugbr = new UserGetBathRooms();
            ugbr.setBNo(bathHouse.getBNo());
            ugbr.setUTel(userInfor.getUTel());
            ugbr.setCharacter("user");
            ugbr.setCommand("查询某澡堂所有澡间");
            String jsonUserinfo = new Gson().toJson(ugbr);
            connectFuture.getSession().write(jsonUserinfo.toString());//发送json字符串
            printLog("测试：这里是发送了一次！！！！");
            String clientInfo = LinkHelper.getClientInfo(linkServer);//获取返回的字符串
            if(clientInfo.equals("")){
                printLog("获取服务端查询某澡堂所有澡间信息失败");
            }else{
                printLog("获取服务端查询某澡堂所有澡间信息成功");
                ServerReturnBathRooms srbr = new Gson().fromJson(clientInfo,ServerReturnBathRooms.class);
                alRoom = srbr.getbRoomList();
            }
        }
    }

    /******************************************************************************
     * 功能：获取用户卡信息
     *******************************************************************************/
    private void getInfoServerPersonInfo(){
        linkServer = new LinkServer();
        connectFuture= LinkHelper.getConn(linkServer);
        if(connectFuture.isConnected()){
            //查询个人信息
            UserOrderAsk uoa = new UserOrderAsk();
            uoa.setUTel(userInfor.getUTel());
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
                userInfor = sri.getUser();
            }
        }
    }

    /******************************************************************************
     * 功能：定时刷新一次,请求服务器，获得alroom
     *******************************************************************************/
    public void refreshOneMinute () {
        new Thread(new Runnable() {
            @Override
            public void run() {

                while(true){
                    try{
                        if(getFragmentManager().findFragmentById(R.id.main_fragment)!=fragmentflag){//!getActivity().toString().equals(activityMain.toString())
                            break;
                        }
                        Log.w("LoginActivity", "*"+getActivity().toString()+"*" );
                        Message msg = new Message();
                        msg.what = 0x0011;
                        handler.sendMessage(msg);
                        Thread.sleep(10000);//每隔60s执行一次
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

}
