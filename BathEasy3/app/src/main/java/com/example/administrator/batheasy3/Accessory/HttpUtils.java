package com.example.administrator.batheasy3.Accessory;

import android.util.Log;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtils extends Thread {
//    String ip = "192.168.1.104";
//    String ip = "10.138.124.191";
    String ip = "47.100.243.90";

     MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public  OkHttpClient client = new OkHttpClient();
    public String PATH = "http://" + ip + ":8080/BathEasy/";
    String path;    //用于接收指定的路径
    String info;    //发送给服务器的信息
    String content;



    /******************************************************************************
     * 功能：构造函数
     *******************************************************************************/
    public HttpUtils(String path, String info) {
        this.path = path;
        this.info = info;
    }

    @Override
    public void run() {
        printLog("向服务器发送消息");
        String url = PATH+path;
        content = post(url, info);
    }

    /******************************************************************************
     * 功能：向服务器发送请求
     *******************************************************************************/
    private String post(String url, String json) {
        printLog(json);
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            content = response.body().string();//tomcat return
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(content == null){
            printLog("未接受到数据");
            return "";
        }else{
            printLog("接受到数据"+content);
            return content;
        }


    }

    /******************************************************************************
     * 功能：打印Log.w信息
     *******************************************************************************/
    private void printLog(String info){
        Log.w("HttpUtils",info);
    }

    /******************************************************************************
     * 功能：获取发送回来的数据
     *******************************************************************************/
    public String getContent() {
        long time1 = System.currentTimeMillis();
        while(content == null){
            if(System.currentTimeMillis()-time1 > 5000){
                break;
            }
        }
        return content;
    }
}
