package com.example.administrator.batheasy3.Accessory;

import android.util.Log;

import org.apache.mina.core.future.ConnectFuture;

public class LinkHelper {
    /******************************************************************************
     * 功能：反复获取返回的字符串
     *******************************************************************************/
    static public String getClientInfo(LinkServer linkServer){
        String clientInfo = "";
        long time1 = System.currentTimeMillis();
        while(true){
            clientInfo = linkServer.getClientInfo();//获取返回的字符串
            if(!clientInfo.equals("")){
                break;
            }
            if(System.currentTimeMillis()-time1 > 10000){
                break;
            }
        }
        return clientInfo;
    }

    /******************************************************************************
     * 功能：连接到服务器并获取ConnectFuture
     *******************************************************************************/
    static public ConnectFuture getConn(LinkServer linkServer){
        if(linkServer == null){
            linkServer = new LinkServer();
        }

        linkServer.start();
        ConnectFuture connectFuture;
        long time1 = System.currentTimeMillis();

        while(true){
            connectFuture = linkServer.getmConnectFuture();
            if(connectFuture != null && connectFuture.isConnected()){
                break;
            }
            if(System.currentTimeMillis()-time1 > 10000){
                Log.w("LinkHelper","获取ConnectFuture失败");
                break;
            }
        }
        return connectFuture;
    }
}
