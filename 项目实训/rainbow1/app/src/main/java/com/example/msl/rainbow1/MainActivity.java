package com.example.msl.rainbow1;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
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
    private ImageView ivHome;
    private ImageView ivCity;
    private ImageView ivCenter;
    private ImageView ivMovie;
    private TextView tvPost;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("loginInfo",MODE_PRIVATE);
        setContentView(R.layout.activity_main);
        findViews();
        //绑定监听器
        bindListener();

        //默认显示首页
        Glide.with(MainActivity.this).load(R.drawable.home_checked).into(ivHome);
        tvHome.setTextColor(Color.rgb(69,193,191));
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.tab_content, new HomeFragment());
        transaction.commit();



        if(sharedPreferences.getString("user","")!=null){
            String userJson = sharedPreferences.getString("user","");
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
            Constant.USER_STATUS = gson.fromJson(userJson,User.class);
        }





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
        ivCenter = findViewById(R.id.iv_center);
        ivCity = findViewById(R.id.iv_city);
        ivHome = findViewById(R.id.iv_home);
        ivMovie = findViewById(R.id.iv_movie);
        tvPost = findViewById(R.id.tv_post);
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

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.tab_spec_homepage:
                    Glide.with(MainActivity.this).load(R.drawable.home_checked).into(ivHome);
                    tvHome.setTextColor(Color.rgb(69,193,191));
                    Glide.with(MainActivity.this).load(R.drawable.city).into(ivCity);
                    tvCity.setTextColor(Color.rgb(97,97,97));
                    Glide.with(MainActivity.this).load(R.drawable.center).into(ivCenter);
                    tvCenter.setTextColor(Color.rgb(97,97,97));
                    Glide.with(MainActivity.this).load(R.drawable.movie2).into(ivMovie);
                    tvMovie.setTextColor(Color.rgb(97,97,97));
                    Glide.with(MainActivity.this).load(R.drawable.post).into(ivDynamic);
                    fragmentManager = getSupportFragmentManager();
                    transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.tab_content, new HomeFragment());
                    transaction.commit();
                    break;
                case R.id.tab_spec_city:
                    Glide.with(MainActivity.this).load(R.drawable.city_checked).into(ivCity);
                    tvCity.setTextColor(Color.rgb(69,193,191));
                    Glide.with(MainActivity.this).load(R.drawable.home).into(ivHome);
                    tvHome.setTextColor(Color.rgb(97,97,97));
                    Glide.with(MainActivity.this).load(R.drawable.center).into(ivCenter);
                    tvCenter.setTextColor(Color.rgb(97,97,97));
                    Glide.with(MainActivity.this).load(R.drawable.movie2).into(ivMovie);
                    tvMovie.setTextColor(Color.rgb(97,97,97));
                    Glide.with(MainActivity.this).load(R.drawable.post).into(ivDynamic);
                    fragmentManager = getSupportFragmentManager();
                    transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.tab_content, new CityFragment());
                    transaction.commit();
                    break;
                case R.id.tab_spec_me:
                    Glide.with(MainActivity.this).load(R.drawable.center_checked).into(ivCenter);
                    tvCenter.setTextColor(Color.rgb(69,193,191));
                    Glide.with(MainActivity.this).load(R.drawable.city).into(ivCity);
                    tvCity.setTextColor(Color.rgb(97,97,97));
                    Glide.with(MainActivity.this).load(R.drawable.home).into(ivHome);
                    tvHome.setTextColor(Color.rgb(97,97,97));
                    Glide.with(MainActivity.this).load(R.drawable.movie2).into(ivMovie);
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
                    Glide.with(MainActivity.this).load(R.drawable.movie_checked).into(ivMovie);
                    tvMovie.setTextColor(Color.rgb(69,193,191));
                    Glide.with(MainActivity.this).load(R.drawable.city).into(ivCity);
                    tvCity.setTextColor(Color.rgb(97,97,97));
                    Glide.with(MainActivity.this).load(R.drawable.center).into(ivCenter);
                    tvCenter.setTextColor(Color.rgb(97,97,97));
                    Glide.with(MainActivity.this).load(R.drawable.home).into(ivHome);
                    tvHome.setTextColor(Color.rgb(97,97,97));
                    Glide.with(MainActivity.this).load(R.drawable.post).into(ivDynamic);
                    fragmentManager = getSupportFragmentManager();
                    transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.tab_content, new MovieFragment());
                    transaction.commit();
                    break;
                case R.id.tab_spec_post:
                    Glide.with(MainActivity.this).load(R.drawable.home).into(ivHome);
                    tvHome.setTextColor(Color.rgb(97,97,97));
                    Glide.with(MainActivity.this).load(R.drawable.city).into(ivCity);
                    tvCity.setTextColor(Color.rgb(97,97,97));
                    Glide.with(MainActivity.this).load(R.drawable.center).into(ivCenter);
                    tvCenter.setTextColor(Color.rgb(97,97,97));
                    Glide.with(MainActivity.this).load(R.drawable.movie2).into(ivMovie);
                    tvMovie.setTextColor(Color.rgb(97,97,97));
                    Glide.with(MainActivity.this).load(R.drawable.post_checked).into(ivDynamic);
                    fragmentManager = getSupportFragmentManager();
                    transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.tab_content, new DynamicFragment());
                    transaction.commit();
                    break;
            }
        }
    }

}

