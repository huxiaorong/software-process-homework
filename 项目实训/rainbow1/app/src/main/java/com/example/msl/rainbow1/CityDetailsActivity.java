package com.example.msl.rainbow1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class CityDetailsActivity extends AppCompatActivity {
    private ImageView ivBack;
    private ImageView ivCity;
    private TextView tvCity;
    private TextView tvCountry;
    private TextView tvDescription;
    private ListView lvCityDetails;
    private LinearLayout llborder;
    private CityDetailsAdapter adapter;
    private List<Place> places = new ArrayList<>();
    private ListView listView;
    private Gson gson;
    private City city;
    private String reStr1;
    private String reStr2;
    private RequestOptions options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_details);
        Intent reIntent = getIntent();
        reStr1 = reIntent.getStringExtra("cityInfo");
        reStr2 = reIntent.getStringExtra("cityPlaces");
        findView();
        init();
        dataFill();
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CityDetailsActivity.this,PlaceDetailsActivity.class);
                intent.putExtra("place",gson.toJson(places.get(position)));
                startActivity(intent);
            }
        });
    }

    private void init() {
        gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        options = new RequestOptions()
                .placeholder(R.drawable.loading)//请求过程中显示
                .error(R.drawable.error)//请求失败显示
                .fallback(R.drawable.defaultimg);
        city = gson.fromJson(reStr1, City.class);
        places = gson.fromJson(reStr2, new TypeToken<List<Place>>() {
        }.getType());
        adapter = new CityDetailsAdapter(places, R.layout.item_citydetail, this);
        listView.setAdapter(adapter);

    }

    private void findView() {
        listView = findViewById(R.id.lv_citydetail);
        ivBack = findViewById(R.id.iv_back);
        ivCity = findViewById(R.id.iv_city);
        tvCity = findViewById(R.id.tv_city);
        tvCountry = findViewById(R.id.tv_country);
        tvDescription = findViewById(R.id.tv_description);
        lvCityDetails = findViewById(R.id.lv_citydetail);

    }

    private void dataFill() {
        Glide.with(CityDetailsActivity.this)
                .load(Constant.BASE_IP + "hotCity/" + city.getImg())
                .apply(options)
                .into(ivCity);
        tvCity.setText(city.getName());
        tvCountry.setText(city.getCountry());
        tvDescription.setText(city.getCityDescription());
    }


    protected void onDraw() {
        llborder.measure(0, 0);
        int width = llborder.getMeasuredWidth();
        int height = llborder.getMeasuredHeight();
        int left = llborder.getLeft();
        int right = llborder.getRight();
        int top = llborder.getTop();
        int bottom = llborder.getBottom();
        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(3);//设置线的粗细
        canvas.drawLine(left, top, left + 20, top, paint);//参数一起始点的x轴位置，参数二起始点的y轴位置，参数三终点的x轴水平位置，参数四y轴垂直位置，最后一个参数为Paint 画刷对象。
        Drawable drawable = new BitmapDrawable(bitmap);
        llborder.setBackground(drawable);
    }

}
