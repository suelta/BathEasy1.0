package com.example.administrator.batheasy3.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.batheasy3.Accessory.HttpUtils;
import com.example.administrator.batheasy3.Accessory.LinkHelper;
import com.example.administrator.batheasy3.Accessory.LinkServer;
import com.example.administrator.batheasy3.InternalWithServer.ServerReturnBathMsg;
import com.example.administrator.batheasy3.InternalWithServer.ServerReturnBathRooms;
import com.example.administrator.batheasy3.InternalWithServer.ServerReturnInfo;
import com.example.administrator.batheasy3.InternalWithServer.ServerReturnOrderMsg;
import com.example.administrator.batheasy3.InternalWithServer.UserAutoOrder;
import com.example.administrator.batheasy3.InternalWithServer.UserGetBathRooms;
import com.example.administrator.batheasy3.InternalWithServer.UserOrderAsk;
import com.example.administrator.batheasy3.InternalWithServer.UserSendQRCode;
import com.example.administrator.batheasy3.MainActivity;
import com.example.administrator.batheasy3.R;
import com.example.administrator.batheasy3.SuccessOrderActivity;
import com.example.administrator.batheasy3.SuccessPayActivity;
import com.example.administrator.batheasy3.bean1.BathHouse;
import com.example.administrator.batheasy3.bean1.BathRoom;
import com.example.administrator.batheasy3.bean1.UserInfor;
import com.example.administrator.batheasy3.dialogs.RoomBusyInfoActivity;
import com.example.administrator.batheasy3.dialogs.RoomFaultInfoActivity;
import com.example.administrator.batheasy3.dialogs.RoomFreeInfoActivity;
import com.google.gson.Gson;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.bean.ZxingConfig;
import com.yzq.zxinglibrary.common.Constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;
import static com.example.administrator.batheasy3.R.id.bath_spinner;

/* 显示澡堂的Fragment */
public class Fragment_bath extends Fragment {
    private GridView gridView;
    private Button buttonorder1;    private Spinner spinner;
    private Fragment fragmentflag;
    private ImageView iv_ewm;   //二维码


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
    public static final int REQUEST_CODE_SCAN = 0x0003;


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

        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                String content = data.getStringExtra(Constant.CODED_CONTENT);
                //将获取到的码上传到服务器
                printLog(content);
                getInfoServerEWM(content);
            }
        }
    }

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
//        activityMain = getActivity();

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 0x0011:{
                        printLog("进入handler 0x0011");
                        Fragment current = null;
                        if(getFragmentManager() == null){
                            printLog("getFragmentManager()为空");
                            break;
                        }
                        current = getFragmentManager().findFragmentById(R.id.main_fragment);

                        if (current != null && !(current instanceof Fragment_bath)){
                            printLog("thread退出");
                            break;
                        }
                        if(getInfoServerBathRoom()) {   //判断是否从服务器得到数据

                            //更新界面
                            initdatalist();
                            adapter = new SimpleAdapter(getActivity(), dataList, R.layout.gridview_item, from, to);
                            gridView.setAdapter(adapter);
                        }
                        break;
                    }
                };
            }
        };

        initAdapterData();      //初始化Adapter数据
        InitLinstenr();
        refreshOneMinute(view);
    }

    /******************************************************************************
     * 功能：初始化组件
     *******************************************************************************/
    private void initView(View view) {
        gridView= view.findViewById(R.id.bath_gridview);
        buttonorder1 = view.findViewById(R.id.button_order);
        spinner = view.findViewById(bath_spinner);
        fragmentflag = getFragmentManager().findFragmentById(R.id.main_fragment);
        iv_ewm = view.findViewById(R.id.bath_iv_ewm);

    }

    /******************************************************************************
     * 功能：刷新
     *******************************************************************************/
    public void InitLinstenr(){
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
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
                    int roomnum = getInfoServer();
                    Intent intent = new Intent(getActivity(), SuccessOrderActivity.class);
                    intent.putExtra("userinfo",userInfor);
                    intent.putExtra("roomid",roomnum);
                    startActivity(intent);
                }
            }
        });


        //扫二维码
        iv_ewm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CaptureActivity.class);
                /*ZxingConfig是配置类
                 *可以设置是否显示底部布局，闪光灯，相册，
                 * 是否播放提示音  震动
                 * 设置扫描框颜色等
                 * 也可以不传这个参数
                 * */
                ZxingConfig config = new ZxingConfig();
                config.setPlayBeep(true);//是否播放扫描声音 默认为true
                config.setShake(true);//是否震动  默认为true
                config.setDecodeBarCode(true);//是否扫描条形码 默认为true
                config.setReactColor(R.color.colorAccent);//设置扫描框四个角的颜色 默认为白色
                config.setFrameLineColor(R.color.colorAccent);//设置扫描框边框颜色 默认无色
                config.setScanLineColor(R.color.colorAccent);//设置扫描线的颜色 默认白色
                config.setFullScreenScan(false);//是否全屏扫描  默认为true  设为false则只会在扫描框中扫描
                config.setShowbottomLayout(false);
                intent.putExtra(Constant.INTENT_ZXING_CONFIG, config);
                startActivityForResult(intent, REQUEST_CODE_SCAN);
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //根据所选的item，访问服务器获取相应的浴室信息
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
            roomName[i] = "浴室-"+(i+1);
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
        UserAutoOrder uao = new UserAutoOrder();
        uao.setBNo(alHouse.get(spinner.getSelectedItemPosition()).getBNo());
        uao.setUTel(userInfor.getUTel());
        uao.setCharacter("user");
        uao.setCommand("自动预约");
        HttpUtils hu = new HttpUtils("AutoOrder",new Gson().toJson(uao).toString());
        hu.start();
        String clientInfo  = hu.getContent();
        if(clientInfo == null||clientInfo.equals("")||clientInfo.startsWith("<")){
            printLog("自动预约失败");
            return;
        }
        printLog("自动预约成功");
        ServerReturnOrderMsg srom = new Gson().fromJson(clientInfo,ServerReturnOrderMsg.class);
        int num = srom.getRNo();
        for (int i = 0; i < alRoom.size(); i++) {
            if(alRoom.get(i).getRNo() == num){
                num = i+1;
            }
        }
        if(srom.getOrderState().equals("自动预约成功")){
            //打开一个预约成功的界面
            Intent intent = new Intent(getActivity(), SuccessOrderActivity.class);
            buttonorder1.setText("我的预约");
            buttonorder1.setTag(true);
            intent.putExtra("userinfo",userInfor);
            intent.putExtra("roomid",num);
            startActivity(intent);
        }else{
            Toast.makeText(getActivity(),srom.getOrderState()+"",Toast.LENGTH_SHORT).show();
        }
    }

    /******************************************************************************
     * 功能：查询某澡堂所有澡间
     *******************************************************************************/
    private boolean getInfoServerBathRoom(){
        //查询某澡堂所有澡间
        BathHouse bathHouse= alHouse.get(spinner.getSelectedItemPosition());
        UserGetBathRooms ugbr = new UserGetBathRooms();
        ugbr.setBNo(bathHouse.getBNo());
        ugbr.setUTel(userInfor.getUTel());
        ugbr.setCharacter("user");
        ugbr.setCommand("查询某澡堂所有澡间");
        HttpUtils hu = new HttpUtils("GetBathRooms",new Gson().toJson(ugbr).toString());
        hu.start();

        String clientInfo  = hu.getContent();
        if(clientInfo == null||clientInfo.equals("")||clientInfo.startsWith("<")){
            printLog("查询某澡堂所有澡间失败");
            return false;
        }
        printLog("查询某澡堂所有澡间成功");
        ServerReturnBathRooms srbr = new Gson().fromJson(clientInfo,ServerReturnBathRooms.class);
        alRoom = srbr.getbRoomList();

        return true;
    }

    /******************************************************************************
     * 功能：获取用户卡信息
     *******************************************************************************/
    private void getInfoServerPersonInfo(){
        //查询个人信息
        UserOrderAsk uoa = new UserOrderAsk();
        uoa.setUTel(userInfor.getUTel());
        uoa.setCharacter("user");
        uoa.setCommand("查询用户和卡信息");

        HttpUtils hu = new HttpUtils("GetInfo",new Gson().toJson(uoa).toString());
        hu.start();

        String clientInfo  = hu.getContent();
        if(clientInfo == null||clientInfo.equals("")||clientInfo.startsWith("<")){
            printLog("获取服务端查询用户和卡信息信息失败");
            return;
        }
        printLog("获取服务端查询用户和卡信息信息成功");
        ServerReturnInfo sri = new Gson().fromJson(clientInfo,ServerReturnInfo.class);
        userInfor = sri.getUser();
    }

    /******************************************************************************
     * 功能：定时刷新一次,请求服务器，获得alroom
     *******************************************************************************/
    public void refreshOneMinute (View view) {
        printLog("新建thread");
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try{
                        Thread.sleep(10000);//每隔60s执行一次
                        Fragment current = null;
                        if(getFragmentManager() == null){
                            printLog("getFragmentManager()为空");
                            break;
                        }
                        current = getFragmentManager().findFragmentById(R.id.main_fragment);

                        if (current != null && !(current instanceof Fragment_bath)){
                            printLog("thread退出");
                            break;
                        }

                        Log.w("LoginActivity", "*"+getActivity().toString()+"*" );
                        Message msg = new Message();
                        msg.what = 0x0011;
                        handler.sendMessage(msg);
                        if (current != null && !(current instanceof Fragment_bath)){
                            printLog("thread退出");
                            break;
                        }
                        /*if(getFragmentManager().findFragmentById(R.id.main_fragment)!=fragmentflag){//!getActivity().toString().equals(activityMain.toString())
                            printLog("thread退出2");
                            break;
                        }*/
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    /******************************************************************************
     * 功能：将获取到的二维码
     *******************************************************************************/
    private void getInfoServerEWM(String rNo) {
        printLog("==========="+rNo);
        int Rno = Integer.valueOf(rNo);
        UserSendQRCode usorc = new UserSendQRCode();
        usorc.setUTel(userInfor.getUTel());
        usorc.setRNo(Rno);

        HttpUtils hu = new HttpUtils("Bath",new Gson().toJson(usorc).toString());
        hu.start();

        String clientInfo  = hu.getContent();
        if(clientInfo == null||clientInfo.equals("")){
            printLog("反馈二维码给服务器失败");
            return;
        }
        printLog("反馈二维码给服务器成功");
        ServerReturnBathMsg sri = new Gson().fromJson(clientInfo,ServerReturnBathMsg.class);

        int realroomid = -1;
        BathRoom bathRoom = null;
        for (int i = 0; i < alRoom.size(); i++) {
            if (alRoom.get(i).getRNo() == Rno) {
                realroomid = i + 1;
                bathRoom = alRoom.get(i);
                break;
            }
        }

        if(sri.getCommand().equals("所选浴室与预约浴室不符")){
            //弹出提示框
            AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
            builder.setIcon(R.drawable.icon_tips_black);
            builder.setTitle("温馨提示");
            builder.setMessage("所选浴室与预约浴室不符！");
            builder.setPositiveButton("我知道了",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
            AlertDialog dialog=builder.create();
            dialog.show();
        }else if(sri.getCommand().equals("浴室正在被使用")||sri.getCommand().equals("前方有人在排队")){
            //弹出提示框
            AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
            builder.setIcon(R.drawable.icon_tips_black);
            builder.setTitle("温馨提示");
            builder.setMessage("浴室正在被使用，请您等候！");
            builder.setPositiveButton("我知道了",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
            AlertDialog dialog=builder.create();
            dialog.show();
        }else if(sri.getCommand().equals("开始使用")){
            //打开使用界面
            Intent intent = new Intent(getActivity(), SuccessOrderActivity.class);
            intent.putExtra("userinfo",userInfor);
            intent.putExtra("roominfo",bathRoom);
            startActivity(intent);
        }else if(sri.getCommand().equals("结束使用")){

            Intent intent = new Intent(getActivity(), SuccessPayActivity.class);
            intent.putExtra("ServerReturnBathMsg",sri);
            intent.putExtra("userinfo",userInfor);
            intent.putExtra("roomid",realroomid);
            startActivity(intent);
        }else{
            AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
            builder.setIcon(R.drawable.icon_tips_black);
            builder.setTitle("温馨提示");
            builder.setMessage("您貌似扫错码了！");
            builder.setPositiveButton("我知道了",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
            AlertDialog dialog=builder.create();
            dialog.show();
        }
    }


    /******************************************************************************
     * 功能：查找房间状态，预约等相关信息
     *******************************************************************************/
    private int getInfoServer(){
        ServerReturnOrderMsg srom = null;
        int num;
        UserOrderAsk uoa = new UserOrderAsk();
        uoa.setUTel(userInfor.getUTel());
        uoa.setCharacter("user");
        uoa.setCommand("查询预约");
        HttpUtils hu = new HttpUtils("OrderAsk",new Gson().toJson(uoa).toString());
        hu.start();

        String clientInfo  = hu.getContent();
        if(clientInfo == null||clientInfo.equals("")||clientInfo.startsWith("<!doctype html>")){
            printLog("查询预约失败");
            return -1;
        }else{
            printLog("查询预约成功");
            printLog(clientInfo);
            srom= new Gson().fromJson(clientInfo,ServerReturnOrderMsg.class);
            if(srom == null)    return -1;
            num = srom.getRNo();
            for (int i = 0; i < alRoom.size(); i++) {
                if(alRoom.get(i).getRNo() == num){
                    num = i+1;
                }
            }
        }
        return num;
    }
}
