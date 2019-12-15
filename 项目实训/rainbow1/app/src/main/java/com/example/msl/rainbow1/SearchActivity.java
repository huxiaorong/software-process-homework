package com.example.msl.rainbow1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.library.AutoFlowLayout;
import com.example.library.FlowAdapter;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchActivity extends AppCompatActivity {
    private SearchView searchView;
    private TextView tvCancel;
    private TextView tvPopularSearches;
    private OkHttpClient okHttpClient;
    private String queryStr;
    private Intent intent;
    private AutoFlowLayout flowLayout;
    private ArrayList<String> list;
    private ArrayList<String> searchHistory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        list = new ArrayList<>();
        searchHistory = new ArrayList<>();
        findView();

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

                queryStr = query;
                jumpSearchPage(query);
                return false;
            }

            // 当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        tvPopularSearches.setOnClickListener(new View.OnClickListener() {
            //点击热门搜索时触发该方法
            @Override
            public void onClick(View v) {
                jumpSearchPage(tvPopularSearches.getText().toString());
            }
        });
        //创建OkHttpClient对象
        okHttpClient = new OkHttpClient();
        Intent reIntent = getIntent();
        if (null!=reIntent){
            String reQuery = reIntent.getStringExtra("reQuery");
            if(null!=reQuery&&!reQuery.equals("")){
                queryStr = reQuery;
                jumpSearchPage(reQuery);
            }
        }



    }

    public void onClicked(View view) {
        switch (view.getId()) {
            case R.id.img_del:
                //点击清除历史记录
                list.clear();
                flowLayout.removeAllViews();
        }
    }

    private void findView() {
        searchView = findViewById(R.id.search_content);
        tvCancel = findViewById(R.id.tv_cancle);
        tvPopularSearches = findViewById(R.id.tv_popular_searches);
        flowLayout = findViewById(R.id.flowLayout);
    }

    //跳转搜索详情页面
    private void jumpSearchPage(String query) {
        queryStr = query;
        if (!searchHistory.contains(query)){
            list.add(query);
            addData(list);
            searchHistory.add(query);
        }


        intent = new Intent(SearchActivity.this, SearchDetailsActivity.class);
        intent.putExtra("query", query);
        getAsync4Movie();


    }

    private void getAsync4Movie() {
        Log.e("request", "start");
        //2.创建Request对象
        Request request = new Request.Builder()
                .url(Constant.BASE_URL + "search/searchMovie?text=" + queryStr)//设置网络请求的URL地址
                .build();
        //3.创建Call对象
        Call call = okHttpClient.newCall(request);
        //4.发送请求
        //异步请求，不需要创建线程
        call.enqueue(new Callback() {
            @Override
            //请求失败时回调
            public void onFailure(Call call, IOException e) {
                Log.e("requestMovie", "error");
                e.printStackTrace();//打印异常信息
            }

            @Override
            //请求成功之后回调
            public void onResponse(Call call, Response response) throws IOException {

                String reStr = response.body().string();
//                reMovieStr = reStr;
                intent.putExtra("movies", reStr);
                Log.e("异步GET请求结果", reStr);
                getAsync4Place();
//                Log.e("异步GET请求结果reMovieStr",reMovieStr);
//                        .setDateFormat("yyyy-MM-dd HH:mm:ss")
//                        .create();
//                Type listType = new TypeToken<List<Movie>>(){}.getType();
//
//                reMovies = gson.fromJson(reStr,listType);
//                Log.e("reMovies",reMovies.toString());


            }
        });
    }

    private void getAsync4Place() {
        Log.e("request", "start");
        //2.创建Request对象
        Request request = new Request.Builder()
                .url(Constant.BASE_URL + "search/searchPlace?text=" + queryStr)//设置网络请求的URL地址
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

                String reStr = response.body().string();
                intent.putExtra("places", reStr);
//                rePlaceStr = reStr;
                Log.e("异步GET请求结果", reStr);
//                Log.e("异步GET请求结果 rePlaceStr",rePlaceStr);
//                Gson gson = new Gson();
//                Type listType = new TypeToken<List<Place>>(){}.getType();
//
//                if (reStr.equals("")||reStr.equals("[]")){
//                    rePlaces = new ArrayList<>();
//                }else{
//                    rePlaces = gson.fromJson(reStr,listType);
//                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startActivity(intent);
            }
        });
    }

    private void addData(final ArrayList<String> list) {
        //流式布局适配器
        flowLayout.setAdapter(new FlowAdapter(list) {
            @Override
            public View getView(int i) {
                //引入视图
                View inflate = LayoutInflater.from(SearchActivity.this).inflate(R.layout.item_flowlayout, null, false);
                //获取视图控件
                final TextView auto_tv = inflate.findViewById(R.id.auto_tv);
                auto_tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        queryStr = auto_tv.getText().toString();
                        jumpSearchPage(auto_tv.getText().toString());
                    }
                });
                //修改值
                auto_tv.setText(list.get(i));


                //清空当前集合
                list.clear();
                //返回视图
                return inflate;
            }
        });
    }


}
