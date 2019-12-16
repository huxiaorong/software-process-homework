package com.example.msl.rainbow1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchMoreMovieActivity extends AppCompatActivity {
    private OkHttpClient okHttpClient;

    private ListView listView;
    private SearchDetailsMovieAdapter adapter;
    private String movies;
    private List<Movie> movieList = new ArrayList<>();
    private Gson gson;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movie_more);
        okHttpClient = new OkHttpClient();
        Intent reIntent = getIntent();
        movies = reIntent.getStringExtra("movies");
        Log.e("接收参数", movies);
        //填充adapter
        findView();
        init();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent = new Intent(SearchMoreMovieActivity.this, MovieDetailsActivity.class);
                intent.putExtra("movieId", movieList.get(position).getMovieId());
                getAsync4MovieTypes(movieList.get(position).getMovieId());
            }
        });
    }

    private void init() {
        gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        movieList = gson.fromJson(movies, new TypeToken<List<Movie>>() {
        }.getType());
        adapter = new SearchDetailsMovieAdapter(movieList, R.layout.item_search_details, this);
        Log.e("movieList", movieList.toString());
        listView.setAdapter(adapter);
    }

    private void findView() {
        listView = findViewById(R.id.lv_item_search_movie);
    }
    private void getAsync4MovieTypes(int id) {
        Log.e("request", "start");
        //2.创建Request对象
        Request request = new Request.Builder()
                .url(Constant.BASE_URL + "movieTheme/searchTypeByMovieId?movieId="+id)//设置网络请求的URL地址
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

                final String reStr = response.body().string();
                Log.e("异步GET请求结果", reStr);
                SearchMoreMovieActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        String[] types = gson.fromJson(reStr, String[].class);
                        intent.putExtra("type",TypeToString(types));
                        startActivity(intent);
                    }
                });
            }
        });
    }
    private String TypeToString(String[] types){
        String str = "";
        for (int i = 0;i<types.length-1;i++){
            str = str+types[i]+"/";
        }
        str=str+types[types.length-1];
        return str;
    }
}
