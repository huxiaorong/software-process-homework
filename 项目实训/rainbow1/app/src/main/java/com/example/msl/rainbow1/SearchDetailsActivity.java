package com.example.msl.rainbow1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchDetailsActivity extends AppCompatActivity {
    private OkHttpClient okHttpClient;
    private ArrayList<String> list;
    private ListView movieListView;
    private ListView placeListView;
    private SearchDetailsMovieAdapter movieAdapter;
    private SearchDetailPlaceAdapter placeAdapter;
    private List<Movie> movieList = new ArrayList<>();
    private List<Movie> movieList1 = new ArrayList<>();
    private List<Place> placeList = new ArrayList<>();
    private List<Place> placeList1 = new ArrayList<>();
    private Intent intent;
    private SearchView searchView;
    private TextView tvCancel;
    private String reQuery;
    private Gson gson;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_details);
        okHttpClient = new OkHttpClient();
        sharedPreferences = getSharedPreferences("search",
                MODE_PRIVATE);
        findView();
        gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        if (sharedPreferences.getString("searchHistory", "").equals("")) {
            list = new ArrayList<>();
        }else{
            list = gson.fromJson(sharedPreferences.getString("searchHistory", ""), new TypeToken<ArrayList<String>>() {
            }.getType());
        }
        //接收查询内容并显示
        Intent reIntent = getIntent();
        reQuery = reIntent.getStringExtra("query");
        searchView.setQuery(reQuery, false);
        getAsync4Save(reQuery);
        getAsync4Movie();
        tvCancel.setOnClickListener(new View.OnClickListener() {
            //监听取消按钮点击事件
            @Override
            public void onClick(View v) {
                //返回上一界面
                Intent intent = new Intent();
                setResult(1, intent);
                //关闭当前界面
                SearchDetailsActivity.this.finish();
            }
        });
        movieListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent = new Intent(SearchDetailsActivity.this,MovieDetailsActivity.class);
                intent.putExtra("movieId",movieList.get(position).getMovieId());
                getAsync4MovieTypes(movieList.get(position).getMovieId());
            }
        });
        placeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent = new Intent(SearchDetailsActivity.this,PlaceDetailsActivity.class);
                intent.putExtra("place",gson.toJson(placeList.get(position)));
                startActivity(intent);
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!list.contains(query)&&list.size()<10){
                    Collections.reverse(list);
                    list.add(query);
                    Collections.reverse(list);
                }else if(!list.contains(query)){
                    Collections.reverse(list);
                    list.add(query);
                    Collections.reverse(list);
                    list.remove(list.size()-1);
                }else{
                    for (int i=0;i<list.size();i++){
                        if (list.get(i).equals(query)){
                            String curStr = list.get(i);
                            for (int j=i;j>0;j--){
                                list.set(j,list.get(j-1));
                            }
                            list.set(0,curStr);
                            break;
                        }
                    }
                }
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("searchHistory", gson.toJson(list));
                editor.commit();
                getAsync4Save(query);
                reQuery = query;
                getAsync4Movie();
                return false;
            }

            // 当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


    }

    private void init() {
        movieList1 = new ArrayList<>();
        placeList1 = new ArrayList<>();
        if (null != movieList && movieList.size() >= 2) {
            movieList1.add(movieList.get(0));
            movieList1.add(movieList.get(1));
        } else if (null != movieList && movieList.size() == 1) {
            movieList1.add(movieList.get(0));
        }

        if (null != placeList && placeList.size() >= 2) {
            placeList1.add(placeList.get(0));
            placeList1.add(placeList.get(1));
        } else if (null != placeList && placeList.size() == 1) {
            placeList1.add(placeList.get(0));
        }
        movieAdapter = new SearchDetailsMovieAdapter(movieList1, R.layout.item_search_details, this);
        placeAdapter = new SearchDetailPlaceAdapter(placeList1, R.layout.item_seach_place, this);
        movieListView.setAdapter(movieAdapter);
        placeListView.setAdapter(placeAdapter);
        placeAdapter.notifyDataSetChanged();
        setListViewHeightBasedOnChildren(placeListView);
        movieAdapter.notifyDataSetChanged();
        setListViewHeightBasedOnChildren1(movieListView);
    }

    private void findView() {
        movieListView = findViewById(R.id.lv_item_search_movie);
        placeListView = findViewById(R.id.lv_item_search_place);
        searchView = findViewById(R.id.search_content);
        tvCancel = findViewById(R.id.tv_cancle);
    }

    public void onClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_more_movie:
                intent = new Intent(this, SearchMoreMovieActivity.class);
//                Log.e("传参reMovieStr",reMovieStr);
                intent.putExtra("movies", gson.toJson(movieList));
                startActivity(intent);
                break;
            case R.id.btn_more_place:
                intent = new Intent(this, SearchMorePlaceActivity.class);
//                Log.e("传参rePlaceStr",rePlaceStr);
                intent.putExtra("places", gson.toJson(placeList));
                startActivity(intent);
                break;

        }
    }

    private void getAsync4Movie() {
        Log.e("request", "start");
        //2.创建Request对象
        Request request = new Request.Builder()
                .url(Constant.BASE_URL + "search/searchMovie?text=" + reQuery)//设置网络请求的URL地址
                .build();
        //3.创建Call对象
        Call call = okHttpClient.newCall(request);
        //4.发送请求
        //异步请求，不需要创建线程
        call.enqueue(new Callback() {
            @Override
            //请求失败时回调
            public void onFailure(Call call, IOException e) {
                Log.e("requestMovie", "error");
                e.printStackTrace();//打印异常信息
            }

            @Override
            //请求成功之后回调
            public void onResponse(Call call, Response response) throws IOException {

                String reStr = response.body().string();
                Log.e("异步GET请求结果", reStr);
                if (reStr.equals("{}")){
                    movieList=new ArrayList<>();
                }else {
                    movieList = gson.fromJson(reStr, new TypeToken<List<Movie>>() {
                    }.getType());
                }

                getAsync4Place();
            }
        });
    }

    private void getAsync4Place() {
        Log.e("request", "start");
        //2.创建Request对象
        Request request = new Request.Builder()
                .url(Constant.BASE_URL + "search/searchPlace?text=" + reQuery)//设置网络请求的URL地址
                .build();
        //3.创建Call对象
        Call call = okHttpClient.newCall(request);
        //4.发送请求
        //异步请求，不需要创建线程
        call.enqueue(new Callback() {
            @Override
            //请求失败时回调
            public void onFailure(Call call, IOException e) {
                Log.e("requestPlace", "error");
                e.printStackTrace();//打印异常信息
            }

            @Override
            //请求成功之后回调
            public void onResponse(Call call, Response response) throws IOException {

                String reStr = response.body().string();
                Log.e("异步GET请求结果", reStr);

//                intent.putExtra("places", reStr);
                if (reStr.equals("{}")){
                    placeList = new ArrayList<>();
                }else {
                    placeList = gson.fromJson(reStr, new TypeToken<List<Place>>() {
                    }.getType());
                }


                SearchDetailsActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        init();
                    }
                });

            }
        });
    }

    /*
    请求首个电影类型
    */
    private void getAsync4MovieTypes(int id) {
        Log.e("request", "start");
        //2.创建Request对象
        Request request = new Request.Builder()
                .url(Constant.BASE_URL + "movieTheme/searchTypeByMovieId?movieId="+id)//设置网络请求的URL地址
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
                SearchDetailsActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        String[] types = gson.fromJson(reStr, String[].class);
                        intent.putExtra("type",TypeToString(types));
                        startActivity(intent);
                    }
                });
            }
        });
    }
    private String TypeToString(String[] types){
        String str = "";
        for (int i = 0;i<types.length-1;i++){
            str = str+types[i]+"/";
        }
        str=str+types[types.length-1];
        return str;
    }

    //根据当前的ListView的列表项计算列表的尺寸,
    // 解决ScrollView嵌套ListView时，会无法正确的计算ListView的大小
    public void setListViewHeightBasedOnChildren(ListView listView) {
        /**
         * by zsx
         * 获取ListView对应的Adapter
         * 这里的adapter是你为listview所添加的adapter，可以是原始adapter，
         * 也可以是自己定义的adapter，本人这里的articleadpter是自己定义的adapter
         */
        SearchDetailPlaceAdapter listAdapter = (SearchDetailPlaceAdapter) listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

    //根据当前的ListView的列表项计算列表的尺寸,
    // 解决ScrollView嵌套ListView时，会无法正确的计算ListView的大小
    public void setListViewHeightBasedOnChildren1(ListView listView) {
        /**
         * by zsx
         * 获取ListView对应的Adapter
         * 这里的adapter是你为listview所添加的adapter，可以是原始adapter，
         * 也可以是自己定义的adapter，本人这里的articleadpter是自己定义的adapter
         */
        SearchDetailsMovieAdapter listAdapter = (SearchDetailsMovieAdapter) listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            SearchDetailsActivity.this.finish();
            return true;
        }
        return false;

    }

    private void getAsync4Save(String str) {
        Log.e("request", "start");
        //2.创建Request对象
        Request request = new Request.Builder()
                .url(Constant.BASE_URL + "search/insertSearchHistory?query=" + str)//设置网络请求的URL地址
                .build();
        //3.创建Call对象
        Call call = okHttpClient.newCall(request);
        //4.发送请求
        //异步请求，不需要创建线程
        call.enqueue(new Callback() {
            @Override
            //请求失败时回调
            public void onFailure(Call call, IOException e) {
                Log.e("requestPlace", "error");
                e.printStackTrace();//打印异常信息
            }

            @Override
            //请求成功之后回调
            public void onResponse(Call call, Response response) throws IOException {

                String reStr = response.body().string();

                Log.e("异步GET请求结果", reStr);


            }
        });
    }


}
