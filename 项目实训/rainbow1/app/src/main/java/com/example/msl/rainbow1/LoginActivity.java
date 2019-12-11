package com.example.msl.rainbow1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText etPhoneNum;
    private EditText etPwd;
    private ImageView imageView;
    private TextView tvNumLogin;
    private TextView tvForget;
    private Button btnLogin;
    private ImageView ivChat;
    private ImageView ivQQ;
    private ImageView ivWeibo;
    private String rsp;
    private String phone;
    private ImageView ivLeft;
    private Handler mainHandle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (!(rsp.equals("登录失败"))) {
                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
                        Constant.USER_STATUS = gson.fromJson(rsp,User.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(LoginActivity.this, rsp, Toast.LENGTH_SHORT).show();
                    }
                    break;

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViews();
        bindListener();
    }

    private void findViews() {
        etPhoneNum = findViewById(R.id.et_phone_num);
        etPwd = findViewById(R.id.et_pwd);
        imageView = findViewById(R.id.imageView);
        tvForget = findViewById(R.id.tv_forget);
        tvNumLogin = findViewById(R.id.tv_num_login);
        btnLogin = findViewById(R.id.btn_login);
        ivChat = findViewById(R.id.iv_chat);
        ivQQ = findViewById(R.id.iv_qq);
        ivWeibo = findViewById(R.id.iv_weibo);
        ivLeft = findViewById(R.id.iv_left);
    }

    private void bindListener() {
        MyListener myListener = new MyListener();
        imageView.setOnClickListener(myListener);
        tvForget.setOnClickListener(myListener);
        tvNumLogin.setOnClickListener(myListener);
        btnLogin.setOnClickListener(myListener);
        ivWeibo.setOnClickListener(myListener);
        ivQQ.setOnClickListener(myListener);
        ivChat.setOnClickListener(myListener);
        ivLeft.setOnClickListener(myListener);
    }

    private class MyListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.imageView:
                    if (etPwd.getInputType() == 129) {//129:不显示密码，128：密码形式
                        etPwd.setInputType(128);
                        Glide.with(LoginActivity.this).load(R.drawable.open).into(imageView);
                    } else {
                        etPwd.setInputType(129);
                        Glide.with(LoginActivity.this).load(R.drawable.close).into(imageView);
                    }
                    break;
                case R.id.iv_qq:
                    Platform plat = ShareSDK.getPlatform(QQ.NAME);
                    //移除授权状态和本地缓存，下次授权会重新授权
                    plat.removeAccount(true);
                    //SSO授权，传false默认是客户端授权,没有客户端授权或者不支持客户端授权会跳web授权；设置成true是关闭SSO授权(关闭客户端授权)
                    plat.SSOSetting(false);
                    //授权回调监听，监听oncomplete，onerror，oncancel三种状态
                    plat.setPlatformActionListener(new PlatformActionListener() {

                        @Override
                        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                            if (i == Platform.ACTION_USER_INFOR) {
                                PlatformDb platDB = platform.getDb();//获取数平台数据DB
                                //通过DB获取各种数据
                                Log.e("token",platDB.getToken());
                                Log.e("userGender",platDB.getUserGender());
                                platDB.getUserIcon();
                                Log.e("userId",platDB.getUserId());
                                Log.e("username",platDB.getUserName());
                            }
                        }

                        @Override
                        public void onError(Platform platform, int i, Throwable throwable) {

                        }

                        @Override
                        public void onCancel(Platform platform, int i) {

                        }
                    });
                    //抖音登录适配安卓9.0
                    ShareSDK.setActivity(LoginActivity.this);
                    //要数据不要功能，主要体现在不会重复出现授权界面
                    plat.showUser("");


                    break;
                case R.id.iv_weibo:
                    Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
                    weibo.SSOSetting(false);  //设置false表示使用SSO授权方式
                    weibo.setPlatformActionListener(new PlatformActionListener() {
                        @Override
                        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                            if (i == Platform.ACTION_USER_INFOR) {
                                PlatformDb platDB = platform.getDb();//获取数平台数据DB
                                //通过DB获取各种数据
                                Log.e("token",platDB.getToken());
                                Log.e("userGender",platDB.getUserGender());
                                platDB.getUserIcon();
                                Log.e("userId",platDB.getUserId());
                                Log.e("username",platDB.getUserName());
                            }
                        }

                        @Override
                        public void onError(Platform platform, int i, Throwable throwable) {

                        }

                        @Override
                        public void onCancel(Platform platform, int i) {

                        }
                    }); // 设置分享事件回调
                    ShareSDK.setActivity(LoginActivity.this);//抖音登录适配安卓9.0
                    weibo.showUser(null);//单独授权
                    break;
                case R.id.iv_chat:
                    //java
                    Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
                    //wechat.setPlatformActionListener(paListener);
                    wechat.showUser(null);
                    break;
                case R.id.tv_num_login:
                    sendCode(LoginActivity.this);
                    break;
                case R.id.tv_forget:
                    sendCode1(LoginActivity.this);
                    break;
                case R.id.btn_login:
                    login();
                    break;
                case  R.id.iv_left:
                    finish();
                    break;
            }
        }
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
                    numLogin();
                } else {
                    Toast.makeText(LoginActivity.this, "您输入的验证码不正确", Toast.LENGTH_SHORT).show();
                }
            }
        });
        page.show(context);
    }

    public void sendCode1(Context context) {
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
                    String phone = (String) phoneMap.get("phone");
                    Intent intent = new Intent(LoginActivity.this, ForgetFindActivity.class);
                    intent.putExtra("phone",phone);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "您输入的验证码不正确", Toast.LENGTH_SHORT).show();
                }
            }
        });
        page.show(context);
    }

    /**
     * 手机号密码登录
     */
    private void login() {
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("phone", etPhoneNum.getText().toString())
                .add("pwd", etPwd.getText().toString())
                .build();
        Request request = new Request.Builder()
                .post(body)
                .url(Constant.BASE_URL + "center/login")//设置网络请求的地址
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
                Log.e("rsp", rsp);
                Message msg = new Message();
                msg.what = 1;
                mainHandle.sendMessage(msg);

            }
        });

    }

    /**
     * 手机号快速登录
     */
    private void numLogin() {
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("phone", phone)
                .build();
        Request request = new Request.Builder()
                .post(body)
                .url(Constant.BASE_URL + "center/numLogin")//设置网络请求的地址
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

    /**
     * qq登录
     */
    private void qqLogin(String qq) {
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("qq", qq)
                .build();
        Request request = new Request.Builder()
                .post(body)
                .url(Constant.BASE_URL + "center/login")//设置网络请求的地址
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
                Log.e("rsp", rsp);
                Message msg = new Message();
                msg.what = 1;
                mainHandle.sendMessage(msg);

            }
        });

    }
}
