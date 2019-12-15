package com.example.msl.rainbow1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class HotSpotsActivity extends AppCompatActivity {
    private ListView lvHotPlace;
    private HotSpotAdapter hotSpotAdapter;
    private List<Place> placeList = new ArrayList<>();
    private Gson gson;
    private String reStr;
    private ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_spots);
        Intent intent = getIntent();
        reStr = intent.getStringExtra("hotPlaces");
        findView();
        init();
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        lvHotPlace.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(HotSpotsActivity.this, PlaceDetailsActivity.class);
                intent.putExtra("place", gson.toJson(placeList.get(position)));
                startActivity(intent);
            }
        });
    }

    private void init() {
        gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        placeList = gson.fromJson(reStr, new TypeToken<List<Place>>() {
        }.getType());
        hotSpotAdapter = new HotSpotAdapter(placeList, R.layout.item_hotspot, this);
        lvHotPlace.setAdapter(hotSpotAdapter);
    }

    private void findView() {
        lvHotPlace = findViewById(R.id.lv_hotplace);
        ivBack = findViewById(R.id.iv_back);
    }
}
