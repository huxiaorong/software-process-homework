package com.example.msl.rainbow1;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.ViewTarget;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MainActivity extends AppCompatActivity{
    private LinearLayout llHome;
    private LinearLayout llMovie;
    private LinearLayout llDynamic;
    private LinearLayout llCity;
    private LinearLayout llCenter;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private TextView tvHome;
    private TextView tvMovie;
    private TextView tvCity;
    private TextView tvCenter;
    private ImageView ivDynamic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        //绑定监听器
        bindListener();

        //默认显示首页
        tvHome.setTextColor(Color.BLACK);
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.tab_content, new HomeFragment());
        transaction.commit();




    }
    private void findViews(){
        llCenter = findViewById(R.id.tab_spec_me);
        llCity = findViewById(R.id.tab_spec_city);
        llHome = findViewById(R.id.tab_spec_homepage);
        llMovie = findViewById(R.id.tab_spec_movie);
        llDynamic = findViewById(R.id.tab_spec_post);
        tvCenter = findViewById(R.id.tv_me);
        tvCity = findViewById(R.id.tv_city);
        tvHome = findViewById(R.id.tv_homepage);
        tvMovie = findViewById(R.id.tv_movie);
        ivDynamic = findViewById(R.id.iv_post);
    }

    private void bindListener(){
        MyListener myListener = new MyListener();
        llDynamic.setOnClickListener(myListener);
        llMovie.setOnClickListener(myListener);
        llHome.setOnClickListener(myListener);
        llCity.setOnClickListener(myListener);
        llCenter.setOnClickListener(myListener);
    }

    private class MyListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.tab_spec_homepage:
                    tvHome.setTextColor(Color.BLACK);
                    tvCity.setTextColor(Color.rgb(97,97,97));
                    tvCenter.setTextColor(Color.rgb(97,97,97));
                    tvMovie.setTextColor(Color.rgb(97,97,97));
                    Glide.with(MainActivity.this).load(R.drawable.post).into(ivDynamic);
                    fragmentManager = getSupportFragmentManager();
                    transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.tab_content, new HomeFragment());
                    transaction.commit();
                    break;
                case R.id.tab_spec_city:
                    tvCity.setTextColor(Color.BLACK);
                    tvHome.setTextColor(Color.rgb(97,97,97));
                    tvCenter.setTextColor(Color.rgb(97,97,97));
                    tvMovie.setTextColor(Color.rgb(97,97,97));
                    Glide.with(MainActivity.this).load(R.drawable.post).into(ivDynamic);
                    fragmentManager = getSupportFragmentManager();
                    transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.tab_content, new CityFragment());
                    transaction.commit();
                    break;
                case R.id.tab_spec_me:
                    tvCenter.setTextColor(Color.BLACK);
                    tvCity.setTextColor(Color.rgb(97,97,97));
                    tvHome.setTextColor(Color.rgb(97,97,97));
                    tvMovie.setTextColor(Color.rgb(97,97,97));
                    Glide.with(MainActivity.this).load(R.drawable.post).into(ivDynamic);
                    fragmentManager = getSupportFragmentManager();
                    transaction = fragmentManager.beginTransaction();
                    if (null == Constant.USER_STATUS) {
                        transaction.replace(R.id.tab_content, new LoginRegisterFragment());
                    }else{
                        CenterFragment fragment = new CenterFragment();
                        transaction.replace(R.id.tab_content, fragment);
                    }
                    transaction.commit();
                    break;
                case R.id.tab_spec_movie:
                    tvMovie.setTextColor(Color.BLACK);
                    tvCity.setTextColor(Color.rgb(97,97,97));
                    tvCenter.setTextColor(Color.rgb(97,97,97));
                    tvHome.setTextColor(Color.rgb(97,97,97));
                    Glide.with(MainActivity.this).load(R.drawable.post).into(ivDynamic);
                    fragmentManager = getSupportFragmentManager();
                    transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.tab_content, new MovieFragment());
                    transaction.commit();
                    break;
                case R.id.tab_spec_post:
                    tvHome.setTextColor(Color.rgb(97,97,97));
                    tvCity.setTextColor(Color.rgb(97,97,97));
                    tvCenter.setTextColor(Color.rgb(97,97,97));
                    tvMovie.setTextColor(Color.rgb(97,97,97));
                    Glide.with(MainActivity.this).load(R.drawable.x).into(ivDynamic);
                    fragmentManager = getSupportFragmentManager();
                    transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.tab_content, new DynamicFragment());
                    transaction.commit();
                    break;
            }
        }
    }

}

