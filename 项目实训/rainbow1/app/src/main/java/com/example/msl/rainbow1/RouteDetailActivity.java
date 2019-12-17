package com.example.msl.rainbow1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RouteDetailActivity extends AppCompatActivity implements View.OnTouchListener,GestureDetector.OnGestureListener{
    private TextView tvNum;
    private TextView tvPlaceName;
    private TextView tvPlaceEnName;
    private TextView tvAddress;
    private ImageView ivMovie;
    private TextView tvMovieName;
    private ImageView ivMoviePlace;
    private TextView tvMovieInstruction;
    private ImageView ivPlace;
    private TextView tvPlaceName1;
    private TextView tvDes;


    private LinearLayout ll;
    private GestureDetector gd;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_detail);
        findViews();

        ll=findViewById(R.id.ll);
        ll.setOnTouchListener(this);
        ll.setLongClickable(true);          //非常重要
        gd=new GestureDetector((GestureDetector.OnGestureListener)this);


    }
    private  void findViews(){
        tvAddress = findViewById(R.id.tv_address);
        tvDes = findViewById(R.id.tv_des);
        tvMovieInstruction = findViewById(R.id.tv_movie_instruction);
        tvMovieName = findViewById(R.id.tv_movie_name);
        tvNum = findViewById(R.id.tv_num);
        tvPlaceName1 = findViewById(R.id.tv_place_name1);
        tvPlaceName = findViewById(R.id.tv_place_name);
        tvPlaceEnName = findViewById(R.id.tv_place_ename);
        ivMovie = findViewById(R.id.iv_movie);
        ivMoviePlace = findViewById(R.id.iv_movie_place);
        ivPlace = findViewById(R.id.iv_place);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gd.onTouchEvent(event);         //touch事件交给GestureDetector处理
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        //手势识别
        //e1:起点信息
        //e2:终点信息
        //velocityX:x方向移动速度
        //velocityY:y方向移动速度
        final int FLING_MIN_DISTANCE=100;
        final int FLING_MIN_VELOCITY=200;

        if(e1.getX() - e2.getX() > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY){
            Intent intent = new Intent(RouteDetailActivity.this, MainActivity.class);
            startActivity(intent);
        }

        return false;
    }
}
