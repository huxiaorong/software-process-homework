package com.example.msl.rainbow1;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

public class AccountAndSafetyActivity extends AppCompatActivity {
    private RelativeLayout rlModify;
    private RelativeLayout rlChange;
    private RelativeLayout rlCancellation;
    private TextView tvPhoneNum;
    private ImageView ivLeft;
    private String rsp;
    private String phone;
    private Handler mainHandle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (!(rsp.equals("您输入的手机号已存在"))) {
                        Toast.makeText(AccountAndSafetyActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                        Constant.USER_STATUS.setTel(phone);
                        Intent intent = new Intent(AccountAndSafetyActivity.this, AccountAndSafetyActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(AccountAndSafetyActivity.this, rsp, Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 2:
                    if (rsp.equals("注销成功")){
                        Constant.USER_STATUS = null;
                        Intent intent = new Intent(AccountAndSafetyActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                    break;

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_and_safety);
        findViews();
        MyListener myListener = new MyListener();
        rlModify.setOnClickListener(myListener);
        rlChange.setOnClickListener(myListener);
        ivLeft.setOnClickListener(myListener);
        rlCancellation.setOnClickListener(myListener);
        String phoneNum = Constant.USER_STATUS.getTel().substring(0, 3) + "****" + Constant.USER_STATUS.getTel().substring(7, 11);
        tvPhoneNum.setText(phoneNum);
    }

    private void findViews() {
        rlCancellation = findViewById(R.id.rl_cancellation);
        rlChange = findViewById(R.id.rl_change);
        rlModify = findViewById(R.id.rl_modify);
        tvPhoneNum = findViewById(R.id.tv_phone_num);
        ivLeft = findViewById(R.id.iv_left);
    }

    private class MyListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.rl_modify:
                    sendCode(AccountAndSafetyActivity.this);
                    break;
                case R.id.rl_change:
                    Intent intent1 = new Intent(AccountAndSafetyActivity.this, ChangeActivity.class);
                    startActivity(intent1);
                    break;
                case R.id.rl_cancellation:
                    new AlertDialog.Builder(AccountAndSafetyActivity.this)
                            .setMessage("确定要注销么?")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    cancellation();
                                    Intent intent3 = new Intent(AccountAndSafetyActivity.this, MainActivity.class);
                                    Constant.USER_STATUS = null;
                                    startActivity(intent3);
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                }
                            }).create().show();
                    break;
                case R.id.iv_left:
                    finish();
                    break;

            }
        }
    }

    /**
     * 注销账号
     */
    private void cancellation() {
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("userId", Constant.USER_STATUS.getUserId()+"")
                .build();
        Request request = new Request.Builder()
                .post(body)
                .url(Constant.BASE_URL + "center/cancellation")//设置网络请求的地址
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
                msg.what = 2;
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
                    modifyNum();
                } else {
                    Toast.makeText(AccountAndSafetyActivity.this, "您输入的验证码不正确", Toast.LENGTH_SHORT).show();
                }
            }
        });
        page.show(context);
    }

    /**
     * 修改手机号
     */
    private void modifyNum() {
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("phone", Constant.USER_STATUS.getTel())
                .add("newPhone", phone)
                .build();
        Request request = new Request.Builder()
                .post(body)
                .url(Constant.BASE_URL + "center/modifyNum")//设置网络请求的地址
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
