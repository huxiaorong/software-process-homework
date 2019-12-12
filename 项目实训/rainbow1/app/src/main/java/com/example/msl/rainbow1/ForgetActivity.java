package com.example.msl.rainbow1;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class ForgetActivity extends AppCompatActivity {

    private EditText etPhoneNum;
    private EditText etCode;
    private Button btnGet;
    private ImageView ivForward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        findViews();
        bindListener();
    }
    private void findViews(){
        etPhoneNum = findViewById(R.id.et_phone_num);
        etCode = findViewById(R.id.et_code);
        btnGet = findViewById(R.id.btn_get);
        ivForward = findViewById(R.id.iv_forward);
    }

    private void bindListener(){
        MyListener myListener = new MyListener();
        etPhoneNum.setOnClickListener(myListener);
        etCode.setOnClickListener(myListener);
        btnGet.setOnClickListener(myListener);
        ivForward.setOnClickListener(myListener);
    }
    private class MyListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.iv_forward:
                    Intent intent2 = new Intent(ForgetActivity.this,ForgetFindActivity.class);
                    startActivity(intent2);
                    break;
                case R.id.btn_get:
                    CountDownTimer timer = new CountDownTimer(60*1000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            btnGet.setEnabled(false);
                            btnGet.setText("已发送" + millisUntilFinished / 1000 + "秒");

                        }

                        @Override
                        public void onFinish() {
                            btnGet.setEnabled(true);
                            btnGet.setText("重新获取验证码");

                        }
                    }.start();
                    break;

            }
        }
    }
}
