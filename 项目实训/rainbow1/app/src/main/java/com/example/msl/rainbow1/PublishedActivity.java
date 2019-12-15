package com.example.msl.rainbow1;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.Resource;
import com.scrat.app.selectorlibrary.ImageSelector;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class PublishedActivity extends Activity {

    private OkHttpClient okHttpClient = new OkHttpClient();
    private GridView gridView;
    private NineGridAdapter nineGridAdapter = null;
    private Button btnAdd;
    private Button btnSend;
    private List<String> dataSource = new ArrayList<>();
    private List<String> paths = null;
    private Dialog dialog;
    private String imagePath;
    private EditText editText;
    private String blog;
    private File file;
    private String fileName = "";
    private static final int REQUEST_CODE_SELECT_IMG = 1;
    private static final int MAX_SELECT_COUNT = 9;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_published);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        editText = findViewById(R.id.et_sharenews);
        gridView = findViewById(R.id.gridview);
        ImageView ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnAdd = findViewById(R.id.btn_addphoto);
        btnSend = findViewById(R.id.btn_sendnews);

        dialog = new Dialog(this, R.style.edit_AlertDialog_style);
        dialog.setContentView(R.layout.image_dialog);
        final ImageView imageView = (ImageView) dialog.findViewById(R.id.my_image);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position==dataSource.size()){
                    selectImg(view);
                }else{
                    String url = dataSource.get(position).toString();
                    imageView.setImageBitmap(BitmapFactory.decodeFile(url));
                    dialog.show();
                }
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (dataSource.size()==0){
                    if (TextUtils.isEmpty(editText.getText())){
                        btnSend.setEnabled(false);
                        btnSend.setBackgroundResource(R.drawable.sendgray);
                        btnSend.setTextColor(0xffffffff);
                    }else{
                        btnSend.setEnabled(true);
                        btnSend.setBackgroundResource(R.drawable.sendborder);
                        btnSend.setTextColor(0xff515151);
                    }
                }else{
                    btnSend.setEnabled(true);
                    btnSend.setBackgroundResource(R.drawable.sendborder);
                    btnSend.setTextColor(0xff515151);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadBlog();
                uploadImage();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_SELECT_IMG) {
            showImage(data); //设置图片 跟图片目录
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showImage(Intent data) {
        paths = ImageSelector.getImagePaths(data);

        //再次点击添加图片时两个list
        if (dataSource==null){
            dataSource = paths;
        }else{
            dataSource.addAll(paths);
            dataSource = new ArrayList<String>(new LinkedHashSet<>(dataSource));
        }
        initView(dataSource);

        //一开始的添加图片按钮消失
        btnAdd.setVisibility(View.GONE);
        btnSend.setEnabled(true);
        btnSend.setBackgroundResource(R.drawable.sendborder);
        btnSend.setTextColor(0xff515151);

    }

    public void selectImg(View v) {
        ImageSelector.show(this, REQUEST_CODE_SELECT_IMG, MAX_SELECT_COUNT);
    }

    public void initView(final List<String> dataSource){
        nineGridAdapter = new NineGridAdapter(dataSource,this,R.layout.item_gridview);
        gridView.setAdapter(nineGridAdapter);

    }

    //向服务器端发送文字
    private void uploadBlog(){
        blog = editText.getText().toString();
        FormBody formBody = new FormBody.Builder()
                .add("userId","3")
                .add("blog",blog)
                .build();
        Request requestBlog = new Request.Builder()
                .url(Constant.NEYDYNAMIC_URL)
                .post(formBody)
                .build();
        Call callBlog = okHttpClient.newCall(requestBlog);
        callBlog.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.body().string().equals("success")){
                    Looper.prepare();
                    sendSuccess();
                    Looper.loop();
                }
            }
        });
    }

    public void uploadImage(){
        if (dataSource.size()!=0){
            for (int i = 0;i<dataSource.size();i++){
                file = new File(dataSource.get(i));
                if (file.getName() == null) {
                    //Toast.makeText(PublishedActivity.this,"没名字",Toast.LENGTH_SHORT).show();
                } else {
                    String count = String.valueOf(i+1);
                    Log.e("1111",count+"");
                    fileName = getFileName(dataSource.get(i));
                    RequestBody requestBody = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("count",count)
                            .addFormDataPart("file"+i, fileName, RequestBody.create(MediaType.parse("image/jpg"), file))
                            .build();
                    Request build = new Request.Builder()
                            .url(Constant.NEYDYNAMICPICTURE_URL)
                            .post(requestBody)
                            .build();
                    new OkHttpClient().newCall(build).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                        }
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            if (response.body().string().equals("success")){
                                Looper.prepare();
                                sendSuccess();
                                Looper.loop();
                            }
                        }
                    });
                }
            }
        }else{
            sendSuccess();
        }
    }

    public String getFileName(String pathandname) {
        int start = pathandname.lastIndexOf("/");
        if (start != -1) {
            return pathandname.substring(start + 1);
        } else {
            return null;
        }
    }

    public void sendSuccess(){
        LayoutInflater li = LayoutInflater.from(PublishedActivity.this);
        View tview = li.inflate(R.layout.send_success, null);
        Toast toast = new Toast(PublishedActivity.this);
        toast.setView(tview);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        finish();
    }


}
