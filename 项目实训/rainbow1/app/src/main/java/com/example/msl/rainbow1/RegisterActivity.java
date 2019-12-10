package com.example.msl.rainbow1;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {
    private EditText etPwd;
    private EditText etPwd1;
    private ImageView imageView;
    private ImageView imageView1;
    private Button btnRegister;
    private String phoneNum;
    private String rsp;
    private Handler mainHandle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Toast.makeText(RegisterActivity.this,rsp,Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(intent);
                    break;

            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Intent intent = getIntent();
        phoneNum = intent.getStringExtra("phone");
        findViews();
        bindListener();
    }
    private void findViews(){
        etPwd = findViewById(R.id.et_pwd);
        imageView = findViewById(R.id.imageView);
        etPwd1 = findViewById(R.id.et_pwd1);
        imageView1 = findViewById(R.id.imageView1);
        btnRegister = findViewById(R.id.btn_register);
    }
    private void bindListener(){
        MyListener myListener = new MyListener();
        imageView.setOnClickListener(myListener);
        imageView1.setOnClickListener(myListener);
        btnRegister.setOnClickListener(myListener);
    }
    private  class  MyListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.imageView:
                    if (etPwd.getInputType() == 129){//129:不显示密码，128：密码形式
                        etPwd.setInputType(128);
                        Glide.with(RegisterActivity.this).load(R.drawable.open).into(imageView);
                    }else{
                        etPwd.setInputType(129);
                        Glide.with(RegisterActivity.this).load(R.drawable.close).into(imageView);
                    }
                    break;
                case R.id.imageView1:
                    if (etPwd1.getInputType() == 129){//129:不显示密码，128：密码形式
                        etPwd1.setInputType(128);
                        Glide.with(RegisterActivity.this).load(R.drawable.open).into(imageView1);
                    }else{
                        etPwd1.setInputType(129);
                        Glide.with(RegisterActivity.this).load(R.drawable.close).into(imageView1);
                    }
                    break;
                case R.id.btn_register:
                    if(etPwd.getText().toString().equals(etPwd1.getText().toString())){
                        register();
                    }else{
                        Toast.makeText(RegisterActivity.this,"两次输入的密码不一致",Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    }
    private void register(){
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("phone",phoneNum)
                .add("pwd",etPwd.getText()
                        .toString()).build();
        Request request = new Request.Builder()
                .post(body)
                .url(Constant.BASE_URL + "center/register")//设置网络请求的地址
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
                Log.e("rsp",rsp);
                if (rsp.equals("注册成功")) {
                    Message msg = new Message();
                    msg.what = 1;
                    mainHandle.sendMessage(msg);
                }

            }
        });

    }


}
