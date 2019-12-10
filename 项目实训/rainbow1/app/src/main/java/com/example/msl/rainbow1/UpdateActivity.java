package com.example.msl.rainbow1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class UpdateActivity extends AppCompatActivity {
    private ListView listView;
    private TextView tvMovieName;
    private TextView tvMovieEngName;
    private TextView tvMoviePlace;
    private TextView tvMovieBrief;
    private ImageView imgMoviePicture;
    private UpdateAdapter updateAdapter;
    private List<Movie> movieList = new ArrayList<>();
    private Intent intent;
    private String[] movieTypeList;
    private String strMovieType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);


        //填充adapter
        findView();
        //listview的点击事件
        listClicked();
    }

    private void listClicked() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent=new Intent(UpdateActivity.this,MovieDetailsActivity.class);
                intent.putExtra("movieId",movieList.get(position).getMovieId());
                intent.putExtra("type",movieTypeList[(int) id]);
                //intent.putExtra("position",id);
                startActivity(intent);
            }
        });
    }

    private void findView() {

        strMovieType=getIntent().getStringExtra("type");
        movieTypeList= strMovieType.split(",");

        String str = getIntent().getStringExtra("movie");
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        movieList=gson.fromJson(str, new TypeToken<List<Movie>>() {}.getType());

        listView = findViewById(R.id.lv_item_movie);
        updateAdapter = new UpdateAdapter(movieList, R.layout.item_movie, this,movieTypeList);
        listView.setAdapter(updateAdapter);
    }

    public void onClicked(View view) {
        switch (view.getId()){
            case R.id.img_left:
                intent=new Intent(UpdateActivity.this,MainActivity.class);
                startActivity(intent);
                break;
        }
    }
}
