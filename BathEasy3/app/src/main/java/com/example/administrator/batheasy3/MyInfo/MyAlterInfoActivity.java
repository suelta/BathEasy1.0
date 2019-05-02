package com.example.administrator.batheasy3.MyInfo;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.administrator.batheasy3.Accessory.HttpUtils;
import com.example.administrator.batheasy3.Accessory.ImgIOJsonOutputUtils;
import com.example.administrator.batheasy3.Accessory.WriteToFile;
import com.example.administrator.batheasy3.InternalWithServer.Message;
import com.example.administrator.batheasy3.InternalWithServer.UserSetInfo;
import com.example.administrator.batheasy3.InternalWithServer.UserSetPortrait;
import com.example.administrator.batheasy3.R;
import com.example.administrator.batheasy3.bean1.UserInfor;
import com.google.gson.Gson;

import java.io.IOException;

import static com.example.administrator.batheasy3.Accessory.ImgIOJsonOutputUtils.encodeImage;

public class MyAlterInfoActivity extends AppCompatActivity {
    EditText et_name;
    RadioButton rb_sex_male;
    RadioButton rb_sex_fmale;
    Spinner sp_school;
    EditText et_tel;
    Button bt_alterinfo;
    Button bt_changeimage;
    ImageView iv_iamge;

    String filename;
    String photoFormat;
    UserInfor userinfo;
    String str = null;
    public static final int SELECT_PHOTO = 2;
    Boolean photoflag = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_altermyinfo);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("修改个人信息");
        init();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SELECT_PHOTO :
                Log.w("qqq","onActivityResult"+"----"+data.getData());
                if (resultCode == RESULT_OK) {
                    //判断手机系统版本号
                    if (Build.VERSION.SDK_INT > 19) {
                        //4.4及以上系统使用这个方法处理图片
                        handleImgeOnKitKat(data);
                    }else {
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }

    /******************************************************************************
     * 功能：初始化
     *******************************************************************************/
    private void init() {
        Intent intent = getIntent();
        userinfo = (UserInfor) intent.getSerializableExtra("userinfo");
        printLog(userinfo.getUName()+userinfo.getUSchool());
        initView();
        initListener();
    }

    /******************************************************************************
     * 功能：初始化监听器
     *******************************************************************************/
    private void initListener() {
        //更新消息到服务器
        bt_alterinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et_name.getText().toString();
                String school = sp_school.getSelectedItem().toString();
                String sex = null;
                if(rb_sex_male.isChecked()){
                    sex = "男";
                }else{
                    sex = "女";
                }

                userinfo.setUName(name);
                userinfo.setUSchool(school);
                userinfo.setUSex(sex);


                if(getServerInfo()){   //更换了头像，就将手机中缓存的头像删除，缓存新的头像
                    if(!photoflag){
                        printLog("头像不存在");
                    }else{
                        filename ="portrait."+photoFormat;
                        WriteToFile.init(getApplicationContext(),"/data");//初始化，第一句代码
                        byte[] decode = null;
                        try {
                            decode = ImgIOJsonOutputUtils.decode(str);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        WriteToFile.writeToFile(decode,filename);//写入到手机内存中

                        SharedPreferences.Editor editor = getSharedPreferences("BathEasyData",MODE_PRIVATE).edit();
                        editor.putBoolean("haveportrait",true);
                        editor.putString("portraitpath",WriteToFile.getNowPath());  //将存放头像的路径存起来
                        editor.apply();
                    }
                    Toast.makeText(getBaseContext(),"修改消息成功",Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                        Toast.makeText(getBaseContext(),"修改消息失败",Toast.LENGTH_SHORT).show();
                }

            }
        });

        //获取手机中的照片，作为头像
        bt_changeimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //从相册中选取图片
                select_photo();
                photoflag = true;
            }
        });
    }

    /******************************************************************************
     * 功能：初始化组件
     *******************************************************************************/
    private void initView() {
        bt_changeimage = findViewById(R.id.alinfo_bt_changeimage);
        iv_iamge = findViewById(R.id.alinfo_iv_image);
        bt_alterinfo = findViewById(R.id.alinfo_bt_alterinfo);
        et_name = findViewById(R.id.alinfo_et_name);
        rb_sex_male = findViewById(R.id.alinfo_rb_male);
        rb_sex_fmale = findViewById(R.id.alinfo_rb_female);
        et_tel = findViewById(R.id.alinfo_et_phone);
        sp_school = findViewById(R.id.alinfo_sp_school);


        et_name.setText(userinfo.getUName());

        if(userinfo.getUSex().equals("男")){
            rb_sex_male.setSelected(true);
        }else{
            rb_sex_fmale.setSelected(true);
        }
        et_tel.setText(userinfo.getUTel());
        et_tel.setFocusable(false); //设置成不可获得焦点

        String[] schools=getResources().getStringArray(R.array.whichschool);
        for (int i = 0; i < schools.length; i++) {
            if(schools[i].equals(userinfo.getUSchool())){
                sp_school.setSelection(i);
            }
        }

        SharedPreferences pref = getSharedPreferences("BathEasyData",MODE_PRIVATE);
        Boolean isFirst = pref.getBoolean("haveportrait",false);
        if(isFirst){
            String newPath = pref.getString("portraitpath","");
            Bitmap bitmap = BitmapFactory.decodeFile(newPath);
            iv_iamge.setImageBitmap(bitmap);
        }
    }

    /******************************************************************************
     * 功能：上传信息到服务器
     *******************************************************************************/
    private Boolean getServerInfo(){
        UserSetInfo usi = new UserSetInfo();
        usi.setNewName(userinfo.getUName());
        usi.setNewSchool(userinfo.getUSchool());
        usi.setNewSex(userinfo.getUSex());
        usi.setUTel(userinfo.getUTel());
        Gson gson = new Gson();
        HttpUtils hu = new HttpUtils("SetInfo",gson.toJson(usi).toString());
        hu.start();

        String clientInfo  = hu.getContent();
        if(clientInfo == null||clientInfo.equals("")||clientInfo.startsWith("<!")){
            printLog("修改信息失败");
            return false;
        }
        Message message = gson.fromJson(clientInfo,Message.class);

        Message message1 = null;
        Boolean isFlag = true;
        if(photoflag){
            UserSetPortrait usp = new UserSetPortrait();
            usp.setNewPortrait(str);
            usp.setUTel(userinfo.getUTel());
            HttpUtils hu1 = new HttpUtils("SetPortrait",gson.toJson(usp).toString());
            hu1.start();
            String clientInfo1  = hu1.getContent();
            if(clientInfo1 == null||clientInfo1.equals("")||clientInfo1.startsWith("<!")){
                printLog("修改头像信息失败");
                return false;
            }
            message1 = gson.fromJson(clientInfo,Message.class);
            if(message1.getCommand().equals("修改头像失败")){
                isFlag = false;
            }
        }

        if(message.getCommand().equals("修改信息成功") &&isFlag ){
            Intent intent = getIntent();
            intent.putExtra("isInfoChange","true");
            setResult(0x0031,intent);
            return true;
        }else{
            return false;
        }
    }

    /******************************************************************************
     * 功能：打印Log.w日志
     *******************************************************************************/
    private void printLog(String info) {
        Log.w("MyAlterInfoActivity",info);
    }

    /******************************************************************************
     * 功能：从相册中选择照片
     *******************************************************************************/
    public void select_photo() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }else {
            openAlbum();
        }
    }
    /******************************************************************************
     * 功能：打开相册的方法
     *******************************************************************************/
    private void openAlbum() {
//      Intent intent = new Intent("android.intent.action.GET_CONTENT");
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,SELECT_PHOTO);
    }

    /******************************************************************************
     * 功能：4.4以下系统处理图片的方法
     *******************************************************************************/
    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri,null);
        displayImage(imagePath);
    }

    /******************************************************************************
     * 功能：4.4及以上系统处理图片的方法
     *******************************************************************************/
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void handleImgeOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this,uri)) {
            //如果是document类型的uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                //解析出数字格式的id
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docId));
                imagePath = getImagePath(contentUri,null);
            }else if ("content".equalsIgnoreCase(uri.getScheme())) {
                //如果是content类型的uri，则使用普通方式处理
                imagePath = getImagePath(uri,null);
            }else if ("file".equalsIgnoreCase(uri.getScheme())) {
                //如果是file类型的uri，直接获取图片路径即可
                imagePath = uri.getPath();
            }
            //根据图片路径显示图片
            displayImage(imagePath);
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {// MediaStore (and general)
            displayImage(getDataColumn(getApplicationContext(), uri, null, null));
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            displayImage(uri.getPath());
        }
    }

    /******************************************************************************
     * 功能：处理uri.getScheme()为content的情况
     * *******************************************************************************/
    public static String getDataColumn(Context context, Uri uri, String selection,String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /******************************************************************************
     * 功能：根据图片路径显示图片的方法
     *******************************************************************************/
    private void displayImage(String imagePath) {
        if (imagePath != null) {
            //对图片进行编码
            try {
                str = encodeImage(imagePath);
                //获取照片的格式
                String[] strarrays = imagePath.split("\\.");
                photoFormat = strarrays[strarrays.length-1];

                printLog(imagePath+"："+photoFormat);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);

            iv_iamge.setImageBitmap(bitmap);
        }else {
            Toast.makeText(MyAlterInfoActivity.this,"failed to get image",Toast.LENGTH_SHORT).show();
        }
    }

    /******************************************************************************
     * 功能：通过uri和selection来获取真实的图片路径
     *******************************************************************************/
    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri,null,selection,null,null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1 :
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                }else {
                    Toast.makeText(MyAlterInfoActivity.this,"failed to get image",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
}
