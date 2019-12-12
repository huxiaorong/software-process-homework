package com.example.msl.rainbow1;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ModifyPhoneNumActivity extends AppCompatActivity {
    private CheckBox cbCheck;
    private EditText etPhoneNum;
    private EditText etCode;
    private Button  btnGet;
    private TextView tvAgreement;
    private TextView tvPrivacy;
    private ImageView ivForward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_phone_num);
        findViews();
        bindListener();
    }


    private void findViews(){
        cbCheck = findViewById(R.id.cb_check);
        etPhoneNum = findViewById(R.id.et_phone_num);
        etCode = findViewById(R.id.et_code);
        btnGet = findViewById(R.id.btn_get);
        tvAgreement = findViewById(R.id.tv_agreement);
        tvPrivacy = findViewById(R.id.tv_privacy);
        ivForward = findViewById(R.id.iv_forward);
    }

    private void bindListener(){
        MyListener myListener = new MyListener();
        cbCheck.setOnClickListener(myListener);
        etPhoneNum.setOnClickListener(myListener);
        etCode.setOnClickListener(myListener);
        btnGet.setOnClickListener(myListener);
        tvPrivacy.setOnClickListener(myListener);
        tvAgreement.setOnClickListener(myListener);
        ivForward.setOnClickListener(myListener);
    }


    private class MyListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.tv_agreement:
                    Intent intent = new Intent(ModifyPhoneNumActivity.this,AgreementActivity.class);
                    startActivity(intent);
                    break;
                case R.id.tv_privacy:
                    Intent intent1 = new Intent(ModifyPhoneNumActivity.this,PrivacyActivity.class);
                    startActivity(intent1);
                    break;
                case R.id.iv_forward:
                    if (!(cbCheck.isChecked())){
                        Toast.makeText(ModifyPhoneNumActivity.this,"请同意",Toast.LENGTH_SHORT).show();
                    }else{
                        Intent intent2 = new Intent(ModifyPhoneNumActivity.this,AccountAndSafetyActivity.class);
                        startActivity(intent2);
                    }
                    break;
                case R.id.btn_get:
                    CountDownTimer timer = new CountDownTimer(60*1000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            btnGet.setEnabled(false);
                            btnGet.setText("已发送(" + millisUntilFinished / 1000 + ")");

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
