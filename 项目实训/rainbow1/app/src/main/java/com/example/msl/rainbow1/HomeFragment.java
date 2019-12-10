package com.example.msl.rainbow1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hejunlin.superindicatorlibray.LoopViewPager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class HomeFragment extends Fragment implements CarouselAdapter.OnClickListener{
    private OkHttpClient okHttpClient;
    private Glide glide;
    private String strCarouselMovie;//轮播图信息
    private String strUpdateMovie;//最近更新信息
    private List<Movie> carouselList = new ArrayList<>();
    private List<Movie> updateList = new ArrayList<>();
    private TextView tvUpdateName1;//最近更新电影名字
    private TextView tvUpdateName2;
    private TextView tvUpdateName3;
    private ImageView updateImg1;//最近更新电影图片
    private ImageView updateImg2;
    private ImageView updateImg3;
    private Intent intent;
    private String strMovieType;
    private String[] movieTypeList;
    private CarouselAdapter cursorAdapter;
    private int movieId1;
    private View view;
    private Button btnMore;
    private LinearLayout lvUpdate1;
    private LinearLayout lvUpdate2;
    private LinearLayout lvUpdate3;
    private int movieId;

    private Handler mainHandle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    LoopViewPager viewPager = view.findViewById(R.id.looperviewpager);
                    //CircleIndicator indicator = findViewById(R.id.indicator);

                    cursorAdapter = new CarouselAdapter(HomeFragment.this, carouselList);
                    viewPager.setAdapter(cursorAdapter);
                    viewPager.setLooperPic(true);//5s自动轮播
                    //indicator.setViewPager(viewPager);
                    break;
                case 2:
                    tvUpdateName1.setText(updateList.get(0).getName());
                    tvUpdateName2.setText(updateList.get(1).getName());
                    tvUpdateName3.setText(updateList.get(2).getName());
                    RequestOptions options = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL);
                    Glide.with(getActivity())
                            .load(Constant.BASE_URL + "movies/" + updateList.get(0).getImg())
                            .apply(options)
                            .into(updateImg1);
                    Glide.with(getActivity())
                            .load(Constant.BASE_URL + "movies/" + updateList.get(1).getImg())
                            .apply(options)
                            .into(updateImg2);
                    Glide.with(getActivity())
                            .load(Constant.BASE_URL + "movies/" + updateList.get(2).getImg())
                            .apply(options)
                            .into(updateImg3);
                    break;
                case 3:
                    intent=new Intent(getActivity(),MovieDetailsActivity.class);
                    intent.putExtra("movieId", movieId1);
                    intent.putExtra("type", strMovieType);
                    startActivity(intent);
                    break;

            }
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_home, container, false);
        findView(view);
        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity(), UpdateActivity.class);
                intent.putExtra("movie", strUpdateMovie);
                intent.putExtra("type", strMovieType);
                startActivity(intent);
            }
        });
        lvUpdate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieTypeList = strMovieType.split(",");
                intent = new Intent(getActivity(), MovieDetailsActivity.class);
                //intent.putExtra("position", new Long(0));
                intent.putExtra("movieId", updateList.get(0).getMovieId());
                intent.putExtra("type", movieTypeList[0]);
                startActivity(intent);
            }
        });
        lvUpdate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieTypeList = strMovieType.split(",");
                intent = new Intent(getActivity(), MovieDetailsActivity.class);
                //intent.putExtra("position", new Long(1));
                intent.putExtra("movieId", updateList.get(1).getMovieId());
                intent.putExtra("type", movieTypeList[1]);
                startActivity(intent);
            }
        });
        lvUpdate3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieTypeList = strMovieType.split(",");
                intent = new Intent(getActivity(), MovieDetailsActivity.class);
                //intent.putExtra("position", new Long(2));
                intent.putExtra("movieId", updateList.get(2).getMovieId());
                intent.putExtra("type", movieTypeList[2]);
                startActivity(intent);
            }
        });
        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        okHttpClient = new OkHttpClient();

        setCarousel();//设置轮播图
        recentChanges();//最近更新
        recentChangesGetType();
        if (getArguments() != null){
            movieId = Integer.parseInt(getArguments().getString("movieId"));
            Log.e("5555",movieId+"");
            findMovieTypeById(movieId);
        }


    }
    private void recentChangesGetType() {
        Request request = new Request.Builder()
                .url(Constant.INDEX_URL + "recentChangesGetType")
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
            }
        });
    }

    private void findView(View view) {
        tvUpdateName1 = view.findViewById(R.id.tv_update_1);
        tvUpdateName2 = view.findViewById(R.id.tv_update_2);
        tvUpdateName3 = view.findViewById(R.id.tv_update_3);
        updateImg1 = view.findViewById(R.id.img_pic1);
        updateImg2 = view.findViewById(R.id.img_pic2);
        updateImg3 = view.findViewById(R.id.img_pic3);
        btnMore = view.findViewById(R.id.btn_more);
        lvUpdate1 = view.findViewById(R.id.lv_update1);
        lvUpdate2 = view.findViewById(R.id.lv_update2);
        lvUpdate3 = view.findViewById(R.id.lv_update3);

    }
    private void setCarousel() {
        Request request = new Request.Builder()
                .url(Constant.INDEX_URL + "carousel")
                .build();
        final Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                strCarouselMovie = response.body().string();
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

                carouselList = gson.fromJson(strCarouselMovie, new TypeToken<List<Movie>>() {
                }.getType());
                Message msg = new Message();
                msg.what = 1;
                mainHandle.sendMessage(msg);
            }
        });
    }

    public void recentChanges() {
        Request request = new Request.Builder()
                .url(Constant.INDEX_URL + "recentChanges")
                .build();
        final Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                strUpdateMovie = response.body().string();
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

                updateList = gson.fromJson(strUpdateMovie, new TypeToken<List<Movie>>() {
                }.getType());

                Message msg = new Message();
                msg.what = 2;
                mainHandle.sendMessage(msg);
            }
        });
    }




    private void findMovieTypeById(int movieId) {
        movieId1=movieId;
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
                strMovieType=strMovieType.substring(1,strMovieType.length()-1);
                Message msg = new Message();
                msg.what = 3;
                mainHandle.sendMessage(msg);

            }
        });
    }

    @Override
    public int sendMovieId(int movieId) {
        findMovieTypeById(movieId);
        return 0;
    }
}
