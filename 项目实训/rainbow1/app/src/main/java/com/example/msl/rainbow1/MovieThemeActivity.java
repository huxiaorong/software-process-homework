package com.example.msl.rainbow1;

import android.content.Intent;
import android.media.tv.TvContentRating;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
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

public class MovieThemeActivity extends AppCompatActivity {
    private ImageView imgBack;
    private ImageView imgTheme;
    private TextView tvThemeName;
    private TextView tvThemeBrief;
    private TextView tvThemeIntroduce;
    private ListView lvMovieList;
    private MovieThemeAdapter movieThemeAdapter;
    private Gson gson;
    private OkHttpClient okHttpClient;
    private String reStr1;
    private String reStr2;
    private String reStr3;
    private String reStr4;
    private List<Movie> movieList = new ArrayList<>();
    private List<String> movieTypes;
    private List<String> movieCities;
    private MovieTheme movieTheme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_theme);
        Intent intent = getIntent();
        reStr1 = intent.getStringExtra("movies");
        reStr2 = intent.getStringExtra("movieTypes");
        reStr3 = intent.getStringExtra("movieCities");
        reStr4 = intent.getStringExtra("movieTheme");
        okHttpClient = new OkHttpClient();
        findView();
        init();
        dataFill();

    }
    private void init(){
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        movieList = gson.fromJson(reStr1, new TypeToken<List<Movie>>() {
        }.getType());
        movieTypes = gson.fromJson(reStr2,new TypeToken<List<String>>() {
        }.getType());
        movieCities = gson.fromJson(reStr3,new TypeToken<List<String>>() {
        }.getType());
        movieTheme = gson.fromJson(reStr4,MovieTheme.class);
        movieThemeAdapter = new MovieThemeAdapter(movieList,movieTypes,movieCities,R.layout.movie_item,this);
        lvMovieList.setAdapter(movieThemeAdapter);
    }
    private void findView(){
        imgBack = findViewById(R.id.img_back);
        imgTheme = findViewById(R.id.img_theme);
        tvThemeName = findViewById(R.id.tv_theme_name);
        tvThemeBrief = findViewById(R.id.tv_theme_brief);
        tvThemeIntroduce = findViewById(R.id.tv_theme_introduce);
        lvMovieList = findViewById(R.id.lv_movie_list);
    }
    private void dataFill(){
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.loading)//请求过程中显示
                .error(R.drawable.error)//请求失败显示
                .fallback(R.drawable.defaultimg);//请求的URL为null时显示
        Glide.with(MovieThemeActivity.this)
                .load(Constant.BASE_IP+"movieTheme/"+movieTheme.getImg())
                .apply(options)
                .into(imgTheme);
        tvThemeName.setText(movieTheme.getMovieThemeName());
        tvThemeBrief.setText(movieTheme.getBrief());
        tvThemeIntroduce.setText(movieTheme.getIntroduce());
    }

}
