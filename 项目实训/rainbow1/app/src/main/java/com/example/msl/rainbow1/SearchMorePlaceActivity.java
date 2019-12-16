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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SearchMorePlaceActivity extends AppCompatActivity {

    private ListView listView;
    private SearchDetailPlaceAdapter adapter;
    private List<Place> placeList = new ArrayList<>();
    private String places;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_place_more);
        Intent reIntent = getIntent();
        places = reIntent.getStringExtra("places");
        Log.e("接收参数", places);
        //填充adapter
        findView();
        init();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchMorePlaceActivity.this, PlaceDetailsActivity.class);
                intent.putExtra("place", gson.toJson(placeList.get(position)));
                startActivity(intent);
            }
        });
    }

    private void init() {
        gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        placeList = gson.fromJson(places, new TypeToken<List<Place>>() {
        }.getType());
        adapter = new SearchDetailPlaceAdapter(placeList, R.layout.item_seach_place, this);
        Log.e("placeList", placeList.toString());
        listView.setAdapter(adapter);

    }

    private void findView() {
        listView = findViewById(R.id.lv_item_search_place);

    }

}
