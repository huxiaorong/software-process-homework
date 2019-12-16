package com.example.msl.rainbow1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.library.AutoFlowLayout;
import com.example.library.FlowAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchActivity extends AppCompatActivity {
    private SearchView searchView;
    private TextView tvCancel;
    private OkHttpClient okHttpClient;
    private Intent intent;
    private AutoFlowLayout flHistory;
    private AutoFlowLayout flHot;
    private ArrayList<String> list;
    private ArrayList<String> hotList;
    private SharedPreferences sharedPreferences;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        findView();
        sharedPreferences = getSharedPreferences("search",
                MODE_PRIVATE);
        gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        if (sharedPreferences.getString("searchHistory", "").equals("")) {
            list = new ArrayList<>();
        }else{
            list = gson.fromJson(sharedPreferences.getString("searchHistory", ""), new TypeToken<ArrayList<String>>() {
            }.getType());
            addData4History(list);
        }

        okHttpClient = new OkHttpClient();

        getAsync4Hot();
        tvCancel.setOnClickListener(new View.OnClickListener() {
            //监听取消按钮点击事件
            @Override
            public void onClick(View v) {
                //返回上一界面
                SearchActivity.this.finish();
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                //跳转到搜索详情页

                flHistory.removeAllViews();
                jumpSearchPage(query);
                return false;
            }

            // 当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });



        //创建OkHttpClient对象
        Intent reIntent = getIntent();
        if (null!=reIntent){
            String reQuery = reIntent.getStringExtra("reQuery");
            if(null!=reQuery&&!reQuery.equals("")){
                jumpSearchPage(reQuery);
            }
        }



    }

    public void onClicked(View view) {
        switch (view.getId()) {
            case R.id.img_del:
                //点击清除历史记录
                list.clear();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("searchHistory",gson.toJson(list));
                editor.commit();
                flHistory.removeAllViews();
        }
    }

    private void findView() {
        searchView = findViewById(R.id.search_content);
        tvCancel = findViewById(R.id.tv_cancle);
        flHistory = findViewById(R.id.fl_history);
        flHot = findViewById(R.id.fl_hot);
    }

    //跳转搜索详情页面
    private void jumpSearchPage(String query) {
        if (!list.contains(query)&&list.size()<10){
            Collections.reverse(list);
            list.add(query);
            Collections.reverse(list);
        }else if(!list.contains(query)){
            Collections.reverse(list);
            list.add(query);
            Collections.reverse(list);
            list.remove(list.size()-1);
        }else{
            for (int i=0;i<list.size();i++){
                if (list.get(i).equals(query)){
                    String curStr = list.get(i);
                    for (int j=i;j>0;j--){
                        list.set(j,list.get(j-1));
                    }
                    list.set(0,curStr);
                    break;
                }
            }
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("searchHistory",gson.toJson(list));
        editor.commit();
        addData4History(list);

        intent = new Intent(SearchActivity.this, SearchDetailsActivity.class);
        intent.putExtra("query", query);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==0&&resultCode==1){
            hotList.clear();
            flHot.removeAllViews();
            list.clear();
            flHistory.removeAllViews();
            list = gson.fromJson(sharedPreferences.getString("searchHistory",""),new TypeToken<ArrayList<String>>(){}.getType());
            addData4History(list);
            getAsync4Hot();
        }
    }

    private void addData4History(final ArrayList<String> list) {
        //流式布局适配器
        flHistory.setAdapter(new FlowAdapter(list) {
            @Override
            public View getView(final int i) {
                //引入视图
                View inflate = LayoutInflater.from(SearchActivity.this).inflate(R.layout.item_flowlayout, null, false);
                //获取视图控件
                final TextView auto_tv = inflate.findViewById(R.id.auto_tv);
                auto_tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        flHistory.removeAllViews();
                        jumpSearchPage(list.get(i));
                    }
                });
                //修改值
                if (list.get(i).length()<=5){
                    auto_tv.setText(list.get(i));
                }else{
                    auto_tv.setText(list.get(i).substring(0,5)+"...");
                }
//                auto_tv.setText(list.get(i));
                //清空当前集合
//                list.clear();
                //返回视图
                return inflate;
            }
        });
    }
    private void addData4Hot(final ArrayList<String> list) {
        //流式布局适配器
        flHot.setAdapter(new FlowAdapter(list) {
            @Override
            public View getView(final int i) {
                //引入视图
                View inflate = LayoutInflater.from(SearchActivity.this).inflate(R.layout.item_flowlayout, null, false);
                //获取视图控件
                final TextView auto_tv = inflate.findViewById(R.id.auto_tv);
                auto_tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        flHistory.removeAllViews();
                        jumpSearchPage(list.get(i));
                    }
                });
                //修改值
                if (list.get(i).length()<=5){
                    auto_tv.setText(list.get(i));
                }else{
                    auto_tv.setText(list.get(i).substring(0,5)+"...");
                }
//                auto_tv.setText(list.get(i));
                //清空当前集合
//                list.clear();
                //返回视图
                return inflate;
            }
        });
    }
    private void getAsync4Hot() {
        Log.e("request", "start");
        //2.创建Request对象
        Request request = new Request.Builder()
                .url(Constant.BASE_URL + "search/searchHotSearch")//设置网络请求的URL地址
                .build();
        //3.创建Call对象
        Call call = okHttpClient.newCall(request);
        //4.发送请求
        //异步请求，不需要创建线程
        call.enqueue(new Callback() {
            @Override
            //请求失败时回调
            public void onFailure(Call call, IOException e) {
                Log.e("requestPlace", "error");
                e.printStackTrace();//打印异常信息
            }

            @Override
            //请求成功之后回调
            public void onResponse(Call call, Response response) throws IOException {

                final String reStr = response.body().string();
                hotList = gson.fromJson(reStr, new TypeToken<ArrayList<String>>(){}.getType());

                Log.e("异步GET请求结果", reStr);
                SearchActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        addData4Hot(hotList);

                    }
                });

            }
        });
    }

}
