package com.example.msl.rainbow1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.HashMap;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ForgetFindActivity extends AppCompatActivity {

    private EditText etPwd;
    private EditText etPwd1;
    private ImageView imageView;
    private ImageView imageView1;
    private ImageView ivForward;
    private String phone;
    private String rsp;
    private Handler mainHandle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (!(rsp.equals("手机号输入有误"))) {
                        Toast.makeText(ForgetFindActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ForgetFindActivity.this, LoginActivity.class);
                        intent.putExtra("user",rsp);
                        startActivity(intent);
                    }else{
                        sendCode(ForgetFindActivity.this);
                        Toast.makeText(ForgetFindActivity.this, rsp, Toast.LENGTH_SHORT).show();
                    }
                    break;

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_find);
        findViews();
        bindListener();
        Intent intent = getIntent();
        phone = intent.getStringExtra("phone");
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
        ivForward.setOnClickListener(myListener);
        imageView.setOnClickListener(myListener);
        imageView1.setOnClickListener(myListener);
    }
    private class MyListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_forward:
                    if (etPwd.getText().toString().equals(etPwd1.getText().toString())){
                        forget();
                        Intent intent2 = new Intent(ForgetFindActivity.this, MainActivity.class);
                        startActivity(intent2);
                    }else{
                        Toast.makeText(ForgetFindActivity.this,"两次输入密码不一致",Toast.LENGTH_SHORT).show();
                    }

                    break;
                case R.id.imageView:
                    if (etPwd.getInputType() == 129){//129:不显示密码，128：密码形式
                        etPwd.setInputType(128);
                        Glide.with(ForgetFindActivity.this).load(R.drawable.open).into(imageView);
                    }else{
                        etPwd.setInputType(129);
                        Glide.with(ForgetFindActivity.this).load(R.drawable.close).into(imageView);
                    }
                    break;
                case R.id.imageView1:
                    if (etPwd1.getInputType() == 129){//129:不显示密码，128：密码形式
                        etPwd1.setInputType(128);
                        Glide.with(ForgetFindActivity.this).load(R.drawable.open).into(imageView1);
                    }else{
                        etPwd1.setInputType(129);
                        Glide.with(ForgetFindActivity.this).load(R.drawable.close).into(imageView1);
                    }
                    break;

            }
        }
    }
    /**
     * 忘记密码
     */
    private void forget(){
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("phone", phone)
                .add("pwd",etPwd.getText().toString())
                .build();
        Request request = new Request.Builder()
                .post(body)
                .url(Constant.BASE_URL + "center/forget")//设置网络请求的地址
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
                Log.e("rspNum", rsp);
                Message msg = new Message();
                msg.what = 1;
                mainHandle.sendMessage(msg);

            }
        });
    }
    public void sendCode(Context context) {
        RegisterPage page = new RegisterPage();
        //如果使用我们的ui，没有申请模板编号的情况下需传null
        page.setTempCode(null);
        page.setRegisterCallback(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // 处理成功的结果
                    HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
                    // 国家代码，如“86”
                    String country = (String) phoneMap.get("country");
                    // 手机号码，如“13800138000”
                    phone = (String) phoneMap.get("phone");
                    Intent intent = new Intent(ForgetFindActivity.this, ForgetFindActivity.class);
                    intent.putExtra("phone",phone);
                    startActivity(intent);
                } else {

                }
            }
        });
        page.show(context);
    }

}
