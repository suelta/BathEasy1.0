package com.example.administrator.batheasy.Accessory;

import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.batheasy.MainActivity;
import com.google.gson.Gson;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.SocketSessionConfig;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.xml.sax.helpers.DefaultHandler;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/*
用来连接服务器
 */
public class LinkServer extends Thread{

    public LinkServer(){ }

    /**
     * 线程池，避免阻塞主线程，与服务器建立连接使用，创建一个只有单线程的线程池，尽快执行线程的线程池
     */
    private static ExecutorService executorService = Executors.newSingleThreadExecutor();

    /**
     * 连接对象
     */
    private NioSocketConnector mConnection;
    /**
     * session对象
     */
    private IoSession mSession;
    /**
     * 连接服务器的地址
     */
    private InetSocketAddress mAddress;

    private ConnectFuture mConnectFuture;

    public String getClientInfo() {
        return clientInfo;
    }

    private void setClientInfo(String clientInfo) {
        this.clientInfo = clientInfo;
    }

    //用来保存客服端传来的信息
    private String clientInfo;


    public ConnectFuture getmConnectFuture() {
        if(mConnectFuture == null)
            Log.w("LinkServer","ConnectFuture为空！");
        return mConnectFuture;
    }

    /**
     * 初始化Mina配置信息
     */
    public void initConfig() {
        clientInfo = new String();
        mAddress = new InetSocketAddress("10.138.80.238", 9999);//连接地址,此数据可改成自己要连接的IP和端口号
        mConnection = new NioSocketConnector();// 创建连接
        // 设置读取数据的缓存区大小
        SocketSessionConfig socketSessionConfig = mConnection.getSessionConfig();
        socketSessionConfig.setReadBufferSize(2048);
        socketSessionConfig.setIdleTime(IdleStatus.BOTH_IDLE, 4);//设置4秒没有读写操作进入空闲状态
        mConnection.getFilterChain().addLast("logging", new LoggingFilter());//logging过滤器
        mConnection.getFilterChain().addLast("codec", new ProtocolCodecFilter(new ByteArrayCodecFactory(Charset.forName("UTF-8"))));//自定义解编码器
        mConnection.setHandler(new DefaultIOHandler());//设置handler，在这里进行获取学习
        mConnection.setDefaultRemoteAddress(mAddress);//设置地址

    }

    /**
     * 创建连接
     */
    public void connect() {
        Log.w("LinkServer","connect开始1");
        FutureTask<Void> futureTask = new FutureTask<>(new Callable<Void>() {
            @Override
            public Void call() {
                try {
                    Log.w("LinkServer","connect开始");
                    while (true) {
                        Log.w("LinkServer","尝试连接");
                        mConnectFuture = mConnection.connect();
                        mConnectFuture.awaitUninterruptibly();//一直等到他连接为止
                        mSession = mConnectFuture.getSession();//获取session对象
                        if (mSession != null && mSession.isConnected()) {
                            Log.w("LinkServer","连接成功");
                            break;
                        }
//                       Thread.sleep(3000);//每隔三秒循环一次
                    }
                } catch (Exception e) {//连接 异常
                    Log.w("LinkServer","连接异常");
                }
                return null;
            }
        });
        executorService.execute(futureTask);//执行连接线程
    }

    public void connect1(){
        try {
            Log.w("LinkServer","connect开始");
                Log.w("LinkServer","尝试连接");
                mConnectFuture = mConnection.connect();
                mConnectFuture.awaitUninterruptibly();//一直等到他连接为止
                mSession = mConnectFuture.getSession();//获取session对象
                if (mSession != null && mSession.isConnected()) {
                    Log.w("LinkServer","连接成功");
                }else{
                    Log.w("LinkServer","连接失败");
                }
        } catch (Exception e) {//连接 异常
            Log.w("LinkServer","连接异常");
        }
    }

    @Override
    public void run() {
        initConfig();
        connect1();
    }

    /* 关闭连接 */
    public void closeConn(){

    }

    /**
     * Mina处理消息的handler,从服务端返回的消息一般在这里处理
     */
    private class DefaultIOHandler extends IoHandlerAdapter {

        @Override
        public void sessionOpened(IoSession session) throws Exception {
            super.sessionOpened(session);
        }

        /**
         * 接收到服务器端消息
         *
         * @param session
         * @param message
         * @throws Exception
         */
        @Override
        public void messageReceived(IoSession session, Object message) throws Exception {
            Log.e("DefaultIOHandler", "接收到服务器端消息：" + message.toString());
            clientInfo = message.toString();
        }


        @Override
        public void sessionIdle(IoSession session, IdleStatus status) throws Exception {//客户端进入空闲状态.
            super.sessionIdle(session, status);

        }
    }
}
