package com.example.msl.rainbow1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SearchMoreMovieActivity extends AppCompatActivity {

    private ListView listView;
    private TextView tvMovieName;
    private TextView tvMovieEngName;
    private TextView tvMoviePlace;
    private TextView tvMovieBrief;
    private ImageView imgMoviePicture;
    private SearchDetailsMovieAdapter adapter;
    private String movies;
    private List<Movie> movieList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movie_more);
        Intent intent = getIntent();
        movies = intent.getStringExtra("movies");
        Log.e("接收参数",movies);
        //填充adapter
        findView();
        init();

    }

    private void init() {
        Date date=new Date();
//        Movie movie=new Movie(1,"急速备战","John Wick:Chapter3",date,R.drawable.update,"摩洛哥 纽约",18,"2019/美国/动作/犯罪/惊悚");
//        Movie movie1=new Movie(1,"急速备战","John Wick:Chapter3",date,R.drawable.update,"摩洛哥 纽约",18,"2019/美国/动作/犯罪/惊悚");
//        Movie movie2=new Movie(1,"急速备战","John Wick:Chapter3",date,R.drawable.update,"摩洛哥 纽约",18,"2019/美国/动作/犯罪/惊悚");
//        Movie movie3=new Movie(1,"急速备战","John Wick:Chapter3",date,R.drawable.update,"摩洛哥 纽约",18,"2019/美国/动作/犯罪/惊悚");
//        movieList.add(movie);
//        movieList.add(movie1);
//        movieList.add(movie2);
//        movieList.add(movie3);

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        movieList = gson.fromJson(movies,new TypeToken<List<Movie>>(){}.getType());
        adapter=new SearchDetailsMovieAdapter(movieList,R.layout.item_search_details,this);
        Log.e("movieList",movieList.toString());
        listView.setAdapter(adapter);
    }

    private void findView() {
        listView=findViewById(R.id.lv_item_search_movie);
    }

}
