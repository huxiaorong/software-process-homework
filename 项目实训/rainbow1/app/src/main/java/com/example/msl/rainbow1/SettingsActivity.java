package com.example.msl.rainbow1;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;

public class SettingsActivity extends AppCompatActivity {
    private RelativeLayout rlEdit;
    private RelativeLayout rlSafety;
    private RelativeLayout rlAgreement;
    private RelativeLayout rlPrivacy;
    private RelativeLayout rlFeedback;
    private RelativeLayout rlAbout;
    private RelativeLayout rlExit;
    private ImageView ivLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        findViews();
        MyListener myListener = new MyListener();
        rlSafety.setOnClickListener(myListener);
        rlPrivacy.setOnClickListener(myListener);
        rlFeedback.setOnClickListener(myListener);
        rlExit.setOnClickListener(myListener);
        rlAgreement.setOnClickListener(myListener);
        rlAbout.setOnClickListener(myListener);
        rlEdit.setOnClickListener(myListener);
        ivLeft.setOnClickListener(myListener);
    }
    private void findViews(){
        rlEdit = findViewById(R.id.rl_edit);
        rlAbout = findViewById(R.id.rl_about);
        rlAgreement = findViewById(R.id.rl_agreement);
        rlExit = findViewById(R.id.rl_exit);
        rlFeedback = findViewById(R.id.rl_feedback);
        rlPrivacy = findViewById(R.id.rl_privacy);
        rlSafety = findViewById(R.id.rl_safety);
        ivLeft = findViewById(R.id.iv_left);
    }
    private class MyListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.rl_about:
                    Intent intent = new Intent(SettingsActivity.this,AboutActivity.class);
                    startActivity(intent);
                    break;
                case R.id.rl_agreement:
                    Intent intent1 = new Intent(SettingsActivity.this,AgreementActivity.class);
                    startActivity(intent1);
                    break;
                case R.id.rl_edit:
                    Intent intent2 = new Intent(SettingsActivity.this,EditActivity.class);
                    startActivity(intent2);
                    break;
                case R.id.rl_exit:
                    new AlertDialog.Builder(SettingsActivity.this)
                            .setMessage("确定要退出么?")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    Intent intent3 = new Intent(SettingsActivity.this,MainActivity.class);
                                    Constant.USER_STATUS = null;
                                    startActivity(intent3);
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                }
                            }).create().show();
                    break;
                case R.id.rl_privacy:
                    Intent intent3 = new Intent(SettingsActivity.this,PrivacyActivity.class);
                    startActivity(intent3);
                    break;
                case R.id.rl_safety:
                    Intent intent4 = new Intent(SettingsActivity.this,AccountAndSafetyActivity.class);
                    startActivity(intent4);
                    break;
                case R.id.rl_feedback:
                    Intent intent5 = new Intent(SettingsActivity.this,FeedbackActivity.class);
                    startActivity(intent5);
                    break;
                case R.id.iv_left:
                    finish();
                    break;
            }
        }
    }
}
