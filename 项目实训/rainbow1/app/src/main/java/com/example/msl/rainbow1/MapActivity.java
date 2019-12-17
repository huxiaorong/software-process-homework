package com.example.msl.rainbow1;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ZoomControls;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.Dot;
import com.baidu.mapapi.map.DotOptions;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity {
    private MapView mapView = null;
    private BaiduMap baiduMap;
    private int locationThemeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 在使用SDK各组件之前初始化context信息，传入ApplicationContext
        // 注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_map);
        Intent intent  = getIntent();
        locationThemeId = intent.getIntExtra("locationThemeId",1);
        // 获取地图控件引用
        mapView = findViewById(R.id.map_view);

        //隐藏logo
        View child = mapView.getChildAt(1);
        if (child != null && (child instanceof ImageView ||
                child instanceof ZoomControls)) {
            child.setVisibility(View.INVISIBLE);//GONE（不占据任何空间）、INVISIBLE（看不见、仍然占据空间）隐藏//visible显示
        }

        baiduMap = mapView.getMap();
//        addPolylineOptions();
        LatLng point;
        point = new LatLng(19.701268,110.35565);
        addDotOptions(point);
        MapStatusUpdate move = MapStatusUpdateFactory.newLatLng(point);
        baiduMap.animateMapStatus(move);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mapView.onPause();
    }

    //折线覆盖物
    private void addPolylineOptions() {
        List<LatLng> points = new ArrayList<>();
        LatLng point;
        point = new LatLng(39.965, 116.404);
        addDotOptions(point);
        point = new LatLng(39.925, 116.454);
        addDotOptions(point);
        point = new LatLng(39.955, 116.494);
        addDotOptions(point);
        point = new LatLng(39.905, 116.554);
        addDotOptions(point);
        point = new LatLng(39.965, 116.604);
        addDotOptions(point);
        points.add( new LatLng(39.965, 116.404));
        points.add(new LatLng(39.925, 116.454));
        points.add(new LatLng(39.955, 116.494));
        points.add(new LatLng(39.905, 116.554));
        points.add(new LatLng(39.965, 116.604));


        PolylineOptions polylineOptions = new PolylineOptions()
                .points(points)
                .color(0xAA69c1bf)
                .width(10);

        baiduMap.addOverlay(polylineOptions);
    }
    private void addDotOptions(LatLng point){

        // 构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.flag);
        // 构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)// 设置marker的位置
                .icon(bitmap);  // 必须设置marker图标
        //在地图上添加Marker，并显示
        Marker marker = (Marker)baiduMap.addOverlay(option);

    }

}
