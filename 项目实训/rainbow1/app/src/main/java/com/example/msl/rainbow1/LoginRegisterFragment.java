package com.example.msl.rainbow1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.HashMap;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;


public class LoginRegisterFragment extends Fragment {
    private Button btnLogin;
    private Button btnRegister;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.activity_login_register, container, false);
        btnLogin = view.findViewById(R.id.btn_login);
        btnRegister = view.findViewById(R.id.btn_register);
        bindListener();
        return view;
    }
    private void bindListener(){
        MyListener myListener = new MyListener();
        btnRegister.setOnClickListener(myListener);
        btnLogin.setOnClickListener(myListener);
    }
    private class MyListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_login:
                    Intent intent = new Intent(getActivity(),LoginActivity.class);
                    startActivity(intent);
                    break;
                case  R.id.btn_register:
                    sendCode(getActivity());
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
                    HashMap<String,Object> phoneMap = (HashMap<String, Object>) data;
                    // 国家代码，如“86”
                    String country = (String) phoneMap.get("country");
                    // 手机号码，如“13800138000”
                    String phone = (String) phoneMap.get("phone");
                    Intent intent = new Intent(getActivity(),RegisterActivity.class);
                    intent.putExtra("phone",phone);
                    getActivity().startActivity(intent);
                } else{

                }
            }
        });
        page.show(context);
    }

}
