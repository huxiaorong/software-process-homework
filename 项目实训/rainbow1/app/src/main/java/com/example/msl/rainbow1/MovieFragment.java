package com.example.msl.rainbow1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;



public class MovieFragment extends Fragment {
    private ImageView imgSearch;
    private LinearLayout llHeadTheme;
    private ImageView imgHeadTheme;
    private TextView tvHeadThemeName;
    private TextView tvHeadThemeBrief;
    private LinearLayout llFirstTheme;
    private LinearLayout llSecondTheme;
    private LinearLayout llThirdTheme;
    private ImageView imgFirstTheme;
    private ImageView imgSecondTheme;
    private ImageView imgThirdTheme;
    private TextView tvFirstThemeName;
    private TextView tvSecondThemeName;
    private TextView tvThirdThemeName;
    private TextView tvFirstThemeBrief;
    private TextView tvSecondThemeBrief;
    private TextView tvThirdThemeBrief;
    private ImageView imgShowTheme;
    private LinearLayout llShowMovie;
    private ImageView imgShowMovie;
    private TextView tvShowMovieName;
    private TextView tvShowMovieBrief;
    private TextView tvShowMovieDescription;
    private LinearLayout llThemeMoreMovies;
    private RequestOptions options;
    private Gson gson;
    private OkHttpClient okHttpClient;
    private List<MovieTheme> movieThemes;
    private List<Movie> themeMovies = new ArrayList<>();
    private List<String> movieTypes;
    private List<String> movieCities;
    private Intent intent;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View  view =  inflater.inflate(R.layout.activity_movie, container, false);
        findView(view);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        okHttpClient = new OkHttpClient();
        init();
        getAsync4MovieTheme();
        getAsync4MovieThemeFirstPlaces();
    }

    private void findView(View view){
        imgSearch = view.findViewById(R.id.img_search);
        llHeadTheme = view.findViewById(R.id.ll_head_theme);
        imgHeadTheme = view.findViewById(R.id.img_head_theme);
        tvHeadThemeName = view.findViewById(R.id.tv_head_theme_name);
        tvHeadThemeBrief = view.findViewById(R.id.tv_head_theme_brief);
        llFirstTheme = view.findViewById(R.id.ll_first_theme);
        llSecondTheme = view.findViewById(R.id.ll_second_theme);
        llThirdTheme = view.findViewById(R.id.ll_third_theme);
        imgFirstTheme = view.findViewById(R.id.img_first_theme);
        imgSecondTheme = view.findViewById(R.id.img_second_theme);
        imgThirdTheme = view.findViewById(R.id.img_third_theme);
        tvFirstThemeName = view.findViewById(R.id.tv_first_theme_name);
        tvSecondThemeName = view.findViewById(R.id.tv_second_theme_name);
        tvThirdThemeName = view.findViewById(R.id.tv_third_theme_name);
        tvFirstThemeBrief = view.findViewById(R.id.tv_first_theme_brief);
        tvSecondThemeBrief = view.findViewById(R.id.tv_second_theme_brief);
        tvThirdThemeBrief = view.findViewById(R.id.tv_third_theme_brief);
        imgShowTheme = view.findViewById(R.id.img_show_theme);
        imgShowMovie = view.findViewById(R.id.img_show_movie);
        llShowMovie = view.findViewById(R.id.ll_show_movie);
        tvShowMovieName = view.findViewById(R.id.tv_show_movie_name);
        tvShowMovieBrief = view.findViewById(R.id.tv_show_movie_brief);
        tvShowMovieDescription=view.findViewById(R.id.tv_show_movie_description);
        llThemeMoreMovies=view.findViewById(R.id.ll_theme_more_movies);
    }
    private void init(){
        options = new RequestOptions()
                .placeholder(R.drawable.loading)//请求过程中显示
                .error(R.drawable.error)//请求失败显示
                .fallback(R.drawable.defaultimg);//请求的URL为null时显示
        gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
    }
    public void onClicked(View view) {
        switch (view.getId()){
            case R.id.ll_head_theme:
                intent = new Intent(getActivity(),MovieThemeActivity.class);
                intent.putExtra("movieTheme",gson.toJson(movieThemes.get(0)));
                getAsync4MovieThemePlaces(movieThemes.get(0).getMovieThemeId());
                break;
            case R.id.ll_show_movie:
                //跳转到电影详情页
                break;
            case R.id.ll_theme_more_movies:
                intent = new Intent(getActivity(),MovieThemeActivity.class);
                intent.putExtra("movieTheme",gson.toJson(movieThemes.get(0)));
                getAsync4MovieThemePlaces(movieThemes.get(0).getMovieThemeId());
                break;
            case R.id.ll_first_theme:
                intent = new Intent(getActivity(),MovieThemeActivity.class);
                intent.putExtra("movieTheme",gson.toJson(movieThemes.get(1)));
                getAsync4MovieThemePlaces(movieThemes.get(1).getMovieThemeId());
                break;
            case R.id.ll_second_theme:
                intent = new Intent(getActivity(),MovieThemeActivity.class);
                intent.putExtra("movieTheme",gson.toJson(movieThemes.get(2)));
                getAsync4MovieThemePlaces(movieThemes.get(2).getMovieThemeId());
                break;
            case R.id.ll_third_theme:
                intent = new Intent(getActivity(),MovieThemeActivity.class);
                intent.putExtra("movieTheme",gson.toJson(movieThemes.get(3)));
                getAsync4MovieThemePlaces(movieThemes.get(3).getMovieThemeId());
                break;
        }
    }
    /*
    请求电影主题列表
     */
    private void getAsync4MovieTheme() {
        Log.e("request", "start");
        //2.创建Request对象
        Request request = new Request.Builder()
                .url(Constant.BASE_URL + "movieTheme/searchMovieTheme")//设置网络请求的URL地址
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
                Log.e("error", e.getMessage());
                e.printStackTrace();//打印异常信息
            }

            @Override
            //请求成功之后回调
            public void onResponse(Call call, Response response) throws IOException {

                String reStr = response.body().string();
                movieThemes = gson.fromJson(reStr, new TypeToken<List<MovieTheme>>() {
                }.getType());

                Log.e("异步GET请求结果", reStr);
                Log.e("异步GET请求结果", movieThemes.toString());

                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        if(movieThemes.size()>0){
                            Glide.with(getActivity())
                                    .load(Constant.BASE_IP + "movieTheme/" + movieThemes.get(0).getImg())
                                    .apply(options)
                                    .into(imgHeadTheme);
                            tvHeadThemeName.setText(movieThemes.get(0).getMovieThemeName());
                            tvHeadThemeBrief.setText(movieThemes.get(0).getBrief());
                            Glide.with(getActivity())
                                    .load(Constant.BASE_IP + "movieTheme/" + movieThemes.get(0).getImg())
                                    .apply(options)
                                    .into(imgShowTheme);

                        }
                        if(movieThemes.size()>1){
                            Glide.with(getActivity())
                                    .load(Constant.BASE_IP + "movieTheme/" + movieThemes.get(1).getImg())
                                    .apply(options)
                                    .into(imgFirstTheme);
                            tvFirstThemeName.setText(movieThemes.get(1).getMovieThemeName());
                            tvFirstThemeBrief.setText(movieThemes.get(1).getBrief());

                        }
                        if(movieThemes.size()>2){
                            Glide.with(getActivity())
                                    .load(Constant.BASE_IP + "movieTheme/" + movieThemes.get(2).getImg())
                                    .apply(options)
                                    .into(imgSecondTheme);
                            tvSecondThemeName.setText(movieThemes.get(2).getMovieThemeName());
                            tvSecondThemeBrief.setText(movieThemes.get(2).getBrief());

                        }
                        if(movieThemes.size()>3){
                            Glide.with(getActivity())
                                    .load(Constant.BASE_IP + "movieTheme/" + movieThemes.get(3).getImg())
                                    .apply(options)
                                    .into(imgThirdTheme);
                            tvThirdThemeName.setText(movieThemes.get(3).getMovieThemeName());
                            tvThirdThemeBrief.setText(movieThemes.get(3).getBrief());

                        }
                    }
                });

            }
        });
    }
    /*
    请求首个电影主题相关地电影列表
     */
    private void getAsync4MovieThemeFirstPlaces() {
        Log.e("request", "start");
        //2.创建Request对象
        Request request = new Request.Builder()
                .url(Constant.BASE_URL + "movieTheme/searchMovieThemeById?movieThemeId=1")//设置网络请求的URL地址
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
                e.printStackTrace();//打印异常信息
            }

            @Override
            //请求成功之后回调
            public void onResponse(Call call, Response response) throws IOException {

                final String reStr = response.body().string();
                Log.e("异步GET请求结果", reStr);
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {

                        themeMovies = gson.fromJson(reStr, new TypeToken<List<Movie>>(){}.getType());
                        if (themeMovies.size()> 0){
                            Glide.with(getActivity())
                                    .load(Constant.BASE_IP + "movie/" + themeMovies.get(0).getImg())
                                    .apply(options)
                                    .into(imgShowMovie);
                            tvShowMovieName.setText(themeMovies.get(0).getName());
                            tvShowMovieDescription.setText(themeMovies.get(0).getDescription());
                            getAsync4FirstMovieTypes();
                        }
                    }
                });
            }
        });
    }
    /*
    请求电影主题相关地电影列表
    */
    private void getAsync4MovieThemePlaces(final int id ) {
        Log.e("request", "start");
        //2.创建Request对象
        Request request = new Request.Builder()
                .url(Constant.BASE_URL + "movieTheme/searchMovieThemeById?movieThemeId="+id)//设置网络请求的URL地址
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
                e.printStackTrace();//打印异常信息
            }

            @Override
            //请求成功之后回调
            public void onResponse(Call call, Response response) throws IOException {

                final String reStr = response.body().string();
                Log.e("异步GET请求结果", reStr);
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        themeMovies = gson.fromJson(reStr, new TypeToken<List<Movie>>(){}.getType());
                        getAsync4MovieTypes(id);
                    }
                });
            }
        });
    }
    /*
    请求首个电影类型
     */
    private void getAsync4FirstMovieTypes() {
        Log.e("request", "start");
        //2.创建Request对象
        Request request = new Request.Builder()
                .url(Constant.BASE_URL + "movieTheme/searchTypeByMovieId?movieId=1")//设置网络请求的URL地址
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
                e.printStackTrace();//打印异常信息
            }

            @Override
            //请求成功之后回调
            public void onResponse(Call call, Response response) throws IOException {

                final String reStr = response.body().string();
                Log.e("异步GET请求结果", reStr);
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        String[] types = gson.fromJson(reStr, String[].class);
                        tvShowMovieBrief.setText(themeMovies.get(0).getCountry()+" "+themeMovies.get(0).getReleaseYear().getYear()+TypeToString(types) );

                    }
                });
            }
        });
    }
    /*
    请求电影主题类型列表
    */
    private void getAsync4MovieTypes(final int id) {
        Log.e("request", "start");
        //2.创建Request对象
        Request request = new Request.Builder()
                .url(Constant.BASE_URL + "movieTheme/searchTypeListByMovieThemeId?movieThemeId="+id)//设置网络请求的URL地址
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
                e.printStackTrace();//打印异常信息
            }
            @Override
            //请求成功之后回调
            public void onResponse(Call call, Response response) throws IOException {

                final String reStr = response.body().string();
                Log.e("异步GET请求结果", reStr);
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        movieTypes = gson.fromJson(reStr, new TypeToken<List<String>>(){}.getType());
                        getAsync4Moviecities(id);
                    }
                });
            }
        });
    }
    /*
    请求电影片场类型列表
    */
    private void getAsync4Moviecities(int id) {
        Log.e("request", "start");
        //2.创建Request对象
        Request request = new Request.Builder()
                .url(Constant.BASE_URL + "movieTheme/searchCityListByMovieThemeId?movieThemeId="+id)//设置网络请求的URL地址
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
                e.printStackTrace();//打印异常信息
            }
            @Override
            //请求成功之后回调
            public void onResponse(Call call, Response response) throws IOException {

                final String reStr = response.body().string();
                Log.e("异步GET请求结果", reStr);
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        movieCities = gson.fromJson(reStr, new TypeToken<List<String>>(){}.getType());
                        intent.putExtra("movies",gson.toJson(themeMovies));
                        intent.putExtra("movieTypes",gson.toJson(movieTypes));
                        intent.putExtra("movieCities",gson.toJson(movieCities));
                        startActivity(intent);
                    }
                });
            }
        });
    }
    private String TypeToString(String[] types){
        String str = "";
        for (int i = 0;i<types.length;i++){
            str = str+" "+types[i];
        }

        return str;
    }
    private String cityToString(String[] types){
        String str = "";
        for (int i = 0;i<types.length-1;i++){
            str = str+types[i]+"/";
        }
        str=str+types[types.length-1];
        return str;
    }

}
