package com.example.msl.rainbow1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HotCityActivity extends AppCompatActivity {
    private ListView listView;
    private HotCityAdapter adapter;
    private List<City> cities = new ArrayList<>();
    private Gson gson;
    private String reStr;
    private OkHttpClient okHttpClient;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_city);
        Intent reIntent = getIntent();
        reStr = reIntent.getStringExtra("hotCities");
        Log.e("reStr",reStr);
        okHttpClient = new OkHttpClient();
        findView();
        init();


    }
    private void init() {
        gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        cities = gson.fromJson(reStr, new TypeToken<List<City>>() {
                }.getType());
        adapter=new HotCityAdapter(cities,R.layout.item_hotcity,this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent = new Intent(HotCityActivity.this,CityDetailsActivity.class);
                intent.putExtra("cityInfo",gson.toJson(cities.get(position)));
                getAsync4HotCityPlaces(cities.get(position).getCityId());
            }
        });
    }
    private void findView() {
        listView=findViewById(R.id.lv_hotcity);
    }
    private void getAsync4HotCityPlaces(int id) {
        Log.e("request", "start");
        //2.创建Request对象
        Request request = new Request.Builder()
                .url(Constant.BASE_URL + "placeTheme/searchPlaceByCityId?cityId=" + id)//设置网络请求的URL地址
                .build();
        //3.创建Call对象
        Call call = okHttpClient.newCall(request);
        //4.发送请求
        //异步请求，不需要创建线程
        call.enqueue(new Callback() {
            @Override
            //请求失败时回调
            public void onFailure(Call call, IOException e) {
                Log.e("failure", "error");
                e.printStackTrace();//打印异常信息
            }

            @Override
            //请求成功之后回调
            public void onResponse(Call call, Response response) throws IOException {
                String reStr = response.body().string();
                intent.putExtra("cityPlaces",reStr);
                startActivity(intent);

            }
        });
    }
}
