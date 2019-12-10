package com.example.msl.rainbow1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SearchMoreActivity extends AppCompatActivity {

    private ListView listView;
    private TextView tvMovieName;
    private TextView tvMovieEngName;
    private TextView tvMoviePlace;
    private TextView tvMovieBrief;
    private ImageView imgMoviePicture;
    private SearchDetailsMovieAdapter adapter;
    private List<Movie> movieList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_more);

        //填充adapter
        findView();
        init();

    }

    private void init() {
//        Date date=new Date();
//        Movie movie=new Movie(1,"急速备战","John Wick:Chapter3",date,"","摩洛哥 纽约",18,"2019/美国/动作/犯罪/惊悚",date);
//        Movie movie1=new Movie(1,"急速备战","John Wick:Chapter3",date,"","摩洛哥 纽约",18,"2019/美国/动作/犯罪/惊悚",date);
//        Movie movie2=new Movie(1,"急速备战","John Wick:Chapter3",date,"","摩洛哥 纽约",18,"2019/美国/动作/犯罪/惊悚",date);
//        Movie movie3=new Movie(1,"急速备战","John Wick:Chapter3",date,"","摩洛哥 纽约",18,"2019/美国/动作/犯罪/惊悚",date);
//        movieList.add(movie);
//        movieList.add(movie1);
//        movieList.add(movie2);
//        movieList.add(movie3);
    }

    private void findView() {
        listView=findViewById(R.id.lv_item_search_movie);
        adapter=new SearchDetailsMovieAdapter(movieList,R.layout.item_search_details,this);
        listView.setAdapter(adapter);
    }

}
