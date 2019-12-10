package com.example.msl.rainbow1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class FindBackPwdActivity extends AppCompatActivity {
    private EditText etPwd;
    private EditText etPwd1;
    private ImageView imageView;
    private ImageView imageView1;
    private ImageView ivForward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_back_pwd);
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
        ivForward.setOnClickListener(myListener);
        imageView.setOnClickListener(myListener);
        imageView1.setOnClickListener(myListener);
    }
    private class MyListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_forward:
                    if (etPwd.getText().equals(etPwd1.getText())){
                        Intent intent2 = new Intent(FindBackPwdActivity.this, MainActivity.class);
                        startActivity(intent2);
                    }else{
                        Toast.makeText(FindBackPwdActivity.this,"两次输入密码不一致",Toast.LENGTH_SHORT).show();
                    }

                    break;
                case R.id.imageView:
                    if (etPwd.getInputType() == 129){//129:不显示密码，128：密码形式
                        etPwd.setInputType(128);
                        Glide.with(FindBackPwdActivity.this).load(R.drawable.open).into(imageView);
                    }else{
                        etPwd.setInputType(129);
                        Glide.with(FindBackPwdActivity.this).load(R.drawable.close).into(imageView);
                    }
                    break;
                case R.id.imageView1:
                    if (etPwd1.getInputType() == 129){//129:不显示密码，128：密码形式
                        etPwd1.setInputType(128);
                        Glide.with(FindBackPwdActivity.this).load(R.drawable.open).into(imageView1);
                    }else{
                        etPwd1.setInputType(129);
                        Glide.with(FindBackPwdActivity.this).load(R.drawable.close).into(imageView1);
                    }
                    break;

            }
        }
    }
}
