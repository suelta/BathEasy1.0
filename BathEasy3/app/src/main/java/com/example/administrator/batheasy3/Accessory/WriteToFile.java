package com.example.administrator.batheasy3.Accessory;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by Cavell on 2018/4/23.
 */
public class WriteToFile {
    private static String TAG = "WriteToFile";
    private static String filePath = null;
    private static String nowPath = null;

    public static String getNowPath() {
        return nowPath;
    }

    public static void setNowPath(String nowPath) {
        WriteToFile.nowPath = nowPath;
    }

    public static void init(Context context, String fileName){
        filePath = getFilePath(context) + fileName;//此处传入的filename 应该是类似 /BlueTooth/record1
    }

    private static String getFilePath(Context context) {

        if (Environment.MEDIA_MOUNTED.equals(Environment.MEDIA_MOUNTED) ||
                !Environment.isExternalStorageRemovable()) {//如果外部储存可用
            return context.getExternalFilesDir(null).getPath();//获得外部存储路径,默认路径为 /storage/emulated/0/Android/data/com.bm.cavell.batterymeasurement/files/Logs/log_2017-09-14_16-15-09.log
        } else {
            return context.getFilesDir().getPath();//直接存在/data/data里，非root手机是看不到的
        }
    }

    public static void writeToFile(String msg,String filename) {

        if (null == filePath) {
            Log.e(TAG, "filePath == null ，未初始化WriteToFile");
            return;
        }

        String text = msg + "\n";//log日志内容，可以自行定制

        //如果父路径不存在
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();//创建父路径
        }

        FileOutputStream fos = null;//FileOutputStream会自动调用底层的close()方法，不用关闭
        BufferedWriter bw = null;
        try {
            String filepath1 = filePath+"/"+filename;
            Log.w("WriteToFile",filepath1);
            nowPath = filepath1;
            fos = new FileOutputStream(filepath1, false);//这里的第二个参数代表追加还是覆盖，true为追加，flase为覆盖
            bw = new BufferedWriter(new OutputStreamWriter(fos));
            bw.write(text);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null) {
                    bw.close(); //关闭缓冲流
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void writeToFile(byte[] msg,String filename) {

        if (null == filePath) {
            Log.e(TAG, "filePath == null ，未初始化WriteToFile");
            return;
        }

        //如果父路径不存在
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();//创建父路径
        }

        FileOutputStream fos = null;//FileOutputStream会自动调用底层的close()方法，不用关闭
        BufferedWriter bw = null;
        try {
            String filepath1 = filePath+"/"+filename;
            Log.w("WriteToFile",filepath1);
            nowPath = filepath1;
            fos = new FileOutputStream(filepath1, false);//这里的第二个参数代表追加还是覆盖，true为追加，flase为覆盖
            for (int i = 0; i < msg.length; i++) {
                fos.write(msg[i]);
                fos.flush();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close(); //关闭缓冲流
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

