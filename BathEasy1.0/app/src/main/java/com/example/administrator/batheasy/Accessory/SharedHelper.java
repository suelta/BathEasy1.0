package com.example.administrator.batheasy.Accessory;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;
import java.util.HashMap;
import java.util.Map;
public class SharedHelper {
    private Context mContext;
    public SharedHelper() {
    }
    public SharedHelper(Context mContext) {
        this.mContext = mContext;
    }
    //定义一个保存数据的方法
    public boolean save(String telephone, String passwd) {
        SharedPreferences sp = mContext.getSharedPreferences("mysp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("telephone", telephone);
        editor.putString("passwd", passwd);
        editor.commit();
        Toast.makeText(mContext, "保存成功", Toast.LENGTH_SHORT).show();
        return true;
    }
    //定义一个读取SP文件的方法
    public Map<String, String> read() {
        Map<String, String> data = new HashMap<String, String>();
        SharedPreferences sp = mContext.getSharedPreferences("mysp", Context.MODE_PRIVATE);
        data.put("telephone", sp.getString("telephone", "admin"));
        data.put("passwd", sp.getString("passwd", "123456"));
        return data;
    }
    public boolean ReadMessage() {
        Map<String, String> data = new HashMap<String, String>();
        SharedPreferences sp = mContext.getSharedPreferences("mysp", Context.MODE_PRIVATE);
        if(sp.getString("telephone", "admin").equals(null)&&sp.getString("passwd", "admin").equals(null)){
            return false;
        }
        return true;
        //data.put("telephone", sp.getString("telephone", "admin"));
        //ata.put("passwd", sp.getString("passwd", "admin"));

    }

}