package com.example.msl.rainbow1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PlaceThemeActivity extends AppCompatActivity {
    private ImageView ivBack;
    private ImageView imgTheme;
    private TextView tvThemeTitle;
    private TextView tvThemeGeneral;
    private TextView tvThemeProduce;
    private LinearLayout llSpot1;
    private LinearLayout llSpotMovie1;
    private LinearLayout llSpotMovie2;
    private LinearLayout llSpotMovie3;
    private LinearLayout llSpotMovie4;
    private LinearLayout llSpot2;
    private LinearLayout llSpot3;
    private LinearLayout llSpot4;
    private TextView tvSpotMovieName1;
    private ImageView ivSpot1;
    private TextView tvSpotName1;
    private TextView tvAddress1;
    private TextView tvSpotMovieName2;
    private ImageView ivSpot2;
    private TextView tvSpotName2;
    private TextView tvAddress2;
    private TextView tvSpotMovieName3;
    private ImageView ivSpot3;
    private TextView tvSpotName3;
    private TextView tvAddress3;
    private TextView tvSpotMovieName4;
    private ImageView ivSpot4;
    private TextView tvSpotName4;
    private TextView tvAddress4;
    private OkHttpClient okHttpClient;
    private int curPlaceId;
    private PlaceTheme placeTheme;
    private List<Place> places;
    private Gson gson;
    private RequestOptions options;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_theme);
        Intent intent = getIntent();
        String strTheme = intent.getStringExtra("themeDetail");
        String strPlaces = intent.getStringExtra("themePlaces");
        findView();
        init();
        placeTheme = gson.fromJson(strTheme,PlaceTheme.class);
        places = gson.fromJson(strPlaces, new TypeToken<List<Place>>() {
        }.getType());
        okHttpClient = new OkHttpClient();
        dataFill();
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void init(){
        options = new RequestOptions()
                .placeholder(R.drawable.loading)//请求过程中显示
                .error(R.drawable.error)//请求失败显示
                .fallback(R.drawable.defaultimg);
        gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
    }
    private void findView(){
        ivBack = findViewById(R.id.iv_back);
        imgTheme = findViewById(R.id.img_theme);
        tvThemeTitle = findViewById(R.id.tv_theme_title);
        tvThemeProduce = findViewById(R.id.tv_theme_produce);
        tvThemeGeneral = findViewById(R.id.tv_theme_general);
        llSpot1 = findViewById(R.id.ll_spot1);
        llSpot2 = findViewById(R.id.ll_spot2);
        llSpot3 = findViewById(R.id.ll_spot3);
        llSpot4 = findViewById(R.id.ll_spot4);
        llSpotMovie1 = findViewById(R.id.ll_spot_movie1);
        llSpotMovie2 = findViewById(R.id.ll_spot_movie2);
        llSpotMovie3 = findViewById(R.id.ll_spot_movie3);
        llSpotMovie4 = findViewById(R.id.ll_spot_movie4);
        tvSpotMovieName1 = findViewById(R.id.tv_spot_movie_name1);
        ivSpot1 = findViewById(R.id.iv_spot1);
        tvSpotName1 = findViewById(R.id.tv_spot_name1);
        tvAddress1 = findViewById(R.id.tv_address1);
        tvSpotMovieName2 = findViewById(R.id.tv_spot_movie_name2);
        ivSpot2 = findViewById(R.id.iv_spot2);
        tvSpotName2 = findViewById(R.id.tv_spot_name2);
        tvAddress2 = findViewById(R.id.tv_address2);
        tvSpotMovieName3 = findViewById(R.id.tv_spot_movie_name3);
        ivSpot3 = findViewById(R.id.iv_spot3);
        tvSpotName3 = findViewById(R.id.tv_spot_name3);
        tvAddress3 = findViewById(R.id.tv_address3);
        tvSpotMovieName4 = findViewById(R.id.tv_spot_movie_name4);
        ivSpot4 = findViewById(R.id.iv_spot4);
        tvSpotName4 = findViewById(R.id.tv_spot_name4);
        tvAddress4 = findViewById(R.id.tv_address4);
    }
    private void dataFill(){
        Glide.with(PlaceThemeActivity.this)
                .load(Constant.BASE_IP + "placeTheme/" + placeTheme.getImg())
                .apply(options)
                .into(imgTheme);
        tvThemeTitle.setText(placeTheme.getPlaceThemeName());
        tvThemeProduce.setText(placeTheme.getIntroduce());
        curPlaceId = places.get(0).getPlaceId();
        getAsync4PlaceFilmed(tvSpotMovieName1);
        curPlaceId = places.get(1).getPlaceId();
        getAsync4PlaceFilmed(tvSpotMovieName2);
        curPlaceId = places.get(2).getPlaceId();
        getAsync4PlaceFilmed(tvSpotMovieName3);
        curPlaceId = places.get(3).getPlaceId();
        getAsync4PlaceFilmed(tvSpotMovieName4);
        Glide.with(PlaceThemeActivity.this)
                .load(Constant.BASE_IP + "place/" + places.get(0).getImg())
                .apply(options)
                .into(ivSpot1);
        Glide.with(PlaceThemeActivity.this)
                .load(Constant.BASE_IP + "place/" + places.get(1).getImg())
                .apply(options)
                .into(ivSpot2);
        Glide.with(PlaceThemeActivity.this)
                .load(Constant.BASE_IP + "place/" + places.get(2).getImg())
                .apply(options)
                .into(ivSpot3);
        Glide.with(PlaceThemeActivity.this)
                .load(Constant.BASE_IP + "place/" + places.get(3).getImg())
                .apply(options)
                .into(ivSpot4);
        tvSpotName1.setText(places.get(0).getName());
        tvSpotName2.setText(places.get(1).getName());
        tvSpotName3.setText(places.get(2).getName());
        tvSpotName4.setText(places.get(3).getName());
        tvAddress1.setText(places.get(0).getCountry()+"/"+places.get(0).getProvince()+"/"+places.get(0).getCity());
        tvAddress2.setText(places.get(1).getCountry()+"/"+places.get(1).getProvince()+"/"+places.get(1).getCity());
        tvAddress3.setText(places.get(2).getCountry()+"/"+places.get(2).getProvince()+"/"+places.get(2).getCity());
        tvAddress4.setText(places.get(3).getCountry()+"/"+places.get(3).getProvince()+"/"+places.get(3).getCity());
    }
    public void onClicked(View view) {
        switch (view.getId()){
            case R.id.ll_spot1:
                intent = new Intent(PlaceThemeActivity.this,PlaceDetailsActivity.class);
                intent.putExtra("place",gson.toJson(places.get(0)));
                startActivity(intent);
                break;
            case R.id.ll_spot2:
                intent = new Intent(PlaceThemeActivity.this,PlaceDetailsActivity.class);
                intent.putExtra("place",gson.toJson(places.get(1)));
                startActivity(intent);
                break;
            case R.id.ll_spot3:
                intent = new Intent(PlaceThemeActivity.this,PlaceDetailsActivity.class);
                intent.putExtra("place",gson.toJson(places.get(2)));
                startActivity(intent);
                break;
            case R.id.ll_spot4:
                intent = new Intent(PlaceThemeActivity.this,PlaceDetailsActivity.class);
                intent.putExtra("place",gson.toJson(places.get(3)));
                startActivity(intent);
                break;
        }
    }
    /*
    请求Filmed
     */
    private void getAsync4PlaceFilmed(final TextView textView) {
        Log.e("request", "start");
        //2.创建Request对象
        Request request = new Request.Builder()
                .url(Constant.BASE_URL + "placeTheme/searchFilmedByPlaceId?placeId="+curPlaceId)//设置网络请求的URL地址
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
                Log.e("failure", e.getMessage());
                e.printStackTrace();//打印异常信息
            }

            @Override
            //请求成功之后回调
            public void onResponse(Call call, Response response) throws IOException {

                final String reStr = response.body().string();
                Log.e("异步GET请求结果", reStr);
                PlaceThemeActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        String filmed = gson.fromJson(reStr, String.class);
                        textView.setText(filmed);
                    }
                });
            }
        });
    }
}
