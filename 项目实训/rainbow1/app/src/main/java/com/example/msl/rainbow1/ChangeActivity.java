package com.example.msl.rainbow1;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.IOException;

import cn.smssdk.SMSSDK;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ChangeActivity extends AppCompatActivity {

    private EditText etPwd;
    private EditText etPwd1;
    private ImageView imageView;
    private ImageView imageView1;
    private ImageView ivForward;
    private String rsp;
    private Handler mainHandle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (rsp.equals("修改成功")){
                        Constant.USER_STATUS = null;
                        Intent intent = new Intent(ChangeActivity.this,LoginActivity.class);
                        startActivity(intent);
                    }
                    break;

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);
        findViews();
        bindListener();
    }
    private void findViews(){
        etPwd = findViewById(R.id.et_pwd);
        imageView = findViewById(R.id.imageView);
        imageView1 = findViewById(R.id.imageView1);
        etPwd1 = findViewById(R.id.et_pwd1);
        ivForward = findViewById(R.id.iv_forward);
    }

    private void bindListener(){
        MyListener myListener = new MyListener();
        imageView.setOnClickListener(myListener);
        imageView1.setOnClickListener(myListener);
        ivForward.setOnClickListener(myListener);
    }
    private class MyListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.iv_forward:
                    changePwd();
                    break;
                case R.id.imageView:
                    if (etPwd.getInputType() == 129){//129:不显示密码，128：密码形式
                        etPwd.setInputType(128);
                        Glide.with(ChangeActivity.this).load(R.drawable.open).into(imageView);
                    }else{
                        etPwd.setInputType(129);
                        Glide.with(ChangeActivity.this).load(R.drawable.close).into(imageView);
                    }
                    break;
                case R.id.imageView1:
                    if (etPwd1.getInputType() == 129){//129:不显示密码，128：密码形式
                        etPwd1.setInputType(128);
                        Glide.with(ChangeActivity.this).load(R.drawable.open).into(imageView1);
                    }else{
                        etPwd1.setInputType(129);
                        Glide.with(ChangeActivity.this).load(R.drawable.close).into(imageView1);
                    }
                    break;


            }
        }
    }
    private void changePwd(){
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("pwd", etPwd.getText().toString())
                .add("newPwd", etPwd1.getText().toString())
                .add("phone",Constant.USER_STATUS.getTel())
                .build();
        Request request = new Request.Builder()
                .post(body)
                .url(Constant.BASE_URL + "center/changePwd")//设置网络请求的地址
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                rsp = response.body().string();
                Log.e("rrr", rsp);
                Message msg = new Message();
                msg.what = 1;
                mainHandle.sendMessage(msg);

            }
        });
    }
}
