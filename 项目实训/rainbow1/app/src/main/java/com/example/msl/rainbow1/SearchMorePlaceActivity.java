package com.example.msl.rainbow1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SearchMorePlaceActivity extends AppCompatActivity {

    private ListView listView;
    private TextView tvMovieName;
    private TextView tvMovieEngName;
    private TextView tvMoviePlace;
    private TextView tvMovieBrief;
    private ImageView imgMoviePicture;
    private SearchDetailPlaceAdapter adapter;
    private List<Place> placeList = new ArrayList<>();
    private String places;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_place_more);
        Intent intent = getIntent();
        places = intent.getStringExtra("places");
        Log.e("接收参数", places);
        //填充adapter
        findView();
        init();

    }

    private void init() {
        Date date = new Date();
//        Movie movie=new Movie(1,"急速备战","John Wick:Chapter3",date,R.drawable.update,"摩洛哥 纽约",18,"2019/美国/动作/犯罪/惊悚");
////        Movie movie1=new Movie(1,"急速备战","John Wick:Chapter3",date,R.drawable.update,"摩洛哥 纽约",18,"2019/美国/动作/犯罪/惊悚");
////        Movie movie2=new Movie(1,"急速备战","John Wick:Chapter3",date,R.drawable.update,"摩洛哥 纽约",18,"2019/美国/动作/犯罪/惊悚");
////        Movie movie3=new Movie(1,"急速备战","John Wick:Chapter3",date,R.drawable.update,"摩洛哥 纽约",18,"2019/美国/动作/犯罪/惊悚");
////        movieList.add(movie);
////        movieList.add(movie1);
////        movieList.add(movie2);
////        movieList.add(movie3);
        Gson gson = new Gson();
        placeList = gson.fromJson(places, new TypeToken<List<Place>>() {}.getType());
        adapter = new SearchDetailPlaceAdapter(placeList, R.layout.item_seach_place, this);
        Log.e("placeList", placeList.toString());
        listView.setAdapter(adapter);

    }

    private void findView() {
        listView = findViewById(R.id.lv_item_search_place);

    }

}
