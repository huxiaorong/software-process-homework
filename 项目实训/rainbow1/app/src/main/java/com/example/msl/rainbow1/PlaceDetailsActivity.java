package com.example.msl.rainbow1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class PlaceDetailsActivity extends AppCompatActivity {
    private ListView listView;
    private Intent intent;

    private ImageView placeImg;
    private TextView tvPlaceName;
    private TextView tvPlace;
    private TextView tvPlaceDes;
    private TextView tvCount;


    private PlaceDetailsAdapter adapter;
    private List<Movie> movieList = new ArrayList<>();

    private String strPlace;
    private Place place;
    private Gson gson = new Gson();
    private OkHttpClient okHttpClient = new OkHttpClient();

    private String strMovie;
    private PlaceDetailsAdapter placeDetailsAdapter;
    private ListView movieListView;
    private String strMovieType;

    private String collecteInfo;
    private ImageView imgLike;
    private User user;

    private Handler mainHandle = new Handler() {
        @Override
        public void handleMessage(final Message msg) {

            switch (msg.what) {
                case 1:
                    placeDetailsAdapter = new PlaceDetailsAdapter(movieList, R.layout.item_place_details, PlaceDetailsActivity.this);
                    movieListView = findViewById(R.id.lv_item_movie1);
                    movieListView.setAdapter(placeDetailsAdapter);
                    tvCount.setText("共" + movieList.size() + "部");
                    movieListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            findMovieTypeById(movieList.get((int) id).getMovieId());
                            intent = new Intent(PlaceDetailsActivity.this, MovieDetailsActivity.class);
                            intent.putExtra("movieId", movieList.get((int) id).getMovieId());


                        }
                    });
                    break;
                case 2:
                    if(collecteInfo.equals("yes")){
                        imgLike.setImageResource(R.drawable.collected);
                        imgLike.setTag("collected");
                    }else{
                        imgLike.setImageResource(R.drawable.collect);
                        imgLike.setTag("collect");
                    }
                    break;
            }
        }
    };

    private Handler mainHandle1 = new Handler() {
        @Override
        public void handleMessage(final Message msg) {

            switch (msg.what) {
                case 1:
                    intent.putExtra("type", strMovieType);
                    startActivity(intent);
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_details);

        //填充adapter
        findView();
        init();
        findMovieByPlaceId(place.getPlaceId());

        if(Constant.USER_STATUS!=null){
            judgeCollected();
        }else{
            imgLike.setImageResource(R.drawable.collect);
            imgLike.setTag("collect");
        }
    }


    private void findMovieByPlaceId(int placeId) {
        FormBody body = new FormBody.Builder().add("placeId", String.valueOf(placeId)).build();
        Request request = new Request.Builder()
                .url(Constant.INDEX_URL + "findMovieByPlaceId")
                .post(body)
                .build();
        final Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                strMovie = response.body().string();
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

                movieList = gson.fromJson(strMovie, new TypeToken<List<Movie>>() {
                }.getType());

                Message msg = new Message();
                msg.what = 1;
                mainHandle.sendMessage(msg);
            }
        });
    }

    private void init() {
        RequestOptions options = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(this)
                .load(Constant.BASE_URL + "places/" + place.getImg())
                .apply(options)
                .into(placeImg);
        tvPlaceName.setText(place.getName());
        tvPlace.setText(place.getCountry() + place.getProvince());
        tvPlaceDes.setText(place.getDescription());
    }

    private void findView() {
        strPlace = getIntent().getStringExtra("place");
        place = gson.fromJson(strPlace, Place.class);

        listView = findViewById(R.id.lv_item_movie1);
        adapter = new PlaceDetailsAdapter(movieList, R.layout.item_place_details, this);
        listView.setAdapter(adapter);

        placeImg = findViewById(R.id.img_place);
        tvPlaceName = findViewById(R.id.tv_place_name);
        tvPlace = findViewById(R.id.tv_place);
        tvPlaceDes = findViewById(R.id.tv_place_descri);
        tvCount = findViewById(R.id.tv_count);

        imgLike = findViewById(R.id.img_like);
        imgLike.setTag("collect");
    }

    public void onClicked(View view) {
        switch (view.getId()) {
            case R.id.img_left:
                finish();
                break;
            case R.id.img_like:
                if (Constant.USER_STATUS!=null) {
                    collecte();
                } else{
                    Intent intent = new Intent(PlaceDetailsActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }

    private void findMovieTypeById(int movieId) {
        FormBody body = new FormBody.Builder().add("movieId", String.valueOf(movieId)).build();
        Request request = new Request.Builder()
                .url(Constant.INDEX_URL + "findMovieTypeById")
                .post(body)
                .build();
        final Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                strMovieType = response.body().string();
                strMovieType = strMovieType.substring(1, strMovieType.length() - 1);

                Message msg = new Message();
                msg.what = 1;
                mainHandle1.sendMessage(msg);
            }
        });
    }

    private void collecte() {
        if (imgLike.getTag() == "collect") {
            imgLike.setImageResource(R.drawable.collected);
            imgLike.setTag("collected");
            addPlaceCollecte();
        } else {
            imgLike.setImageResource(R.drawable.collect);
            imgLike.setTag("collect");
            cancelPlaceCollecte();
        }
    }

    private void addPlaceCollecte() {
        FormBody body = new FormBody.Builder().add("placeId", String.valueOf(place.getPlaceId()))
                .add("userId", String.valueOf(Constant.USER_STATUS.getUserId())).build();
        Request request = new Request.Builder()
                .url(Constant.INDEX_URL + "addPlaceCollecte")
                .post(body)
                .build();
        final Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

    private void cancelPlaceCollecte() {
        FormBody body = new FormBody.Builder().add("placeId", String.valueOf(place.getPlaceId()))
                .add("userId", String.valueOf(Constant.USER_STATUS.getUserId())).build();
        Request request = new Request.Builder()
                .url(Constant.INDEX_URL + "cancelPlaceCollecte")
                .post(body)
                .build();
        final Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }


    private void judgeCollected() {
        FormBody body = new FormBody.Builder().add("placeId", String.valueOf(place.getPlaceId()))
                .add("userId", String.valueOf(Constant.USER_STATUS.getUserId())).build();
        Request request = new Request.Builder()
                .url(Constant.INDEX_URL + "judgePlaceCollected")
                .post(body)
                .build();
        final Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                collecteInfo=response.body().string();

                Log.e("ccc",collecteInfo);
                Message msg = new Message();
                msg.what = 2;
                mainHandle.sendMessage(msg);
            }
        });
    }
}
