package com.example.msl.rainbow1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SearchDetailsActivity extends AppCompatActivity {

    private ListView movieListView;
    private ListView placeListView;
    private TextView tvMovieName;
    private TextView tvMovieEngName;
    private TextView tvMoviePlace;
    private TextView tvMovieBrief;
    private ImageView imgMoviePicture;
    private SearchDetailsMovieAdapter movieAdapter;
    private SearchDetailPlaceAdapter placeAdapter;
    private List<Movie> movieList = new ArrayList<>();
    private List<Movie> movieList1 = new ArrayList<>();
    private List<Place> placeList = new ArrayList<>();
    private List<Place> placeList1 = new ArrayList<>();
    private Intent intent;
    private SearchView searchView;
    private TextView tvCancel;
    private String query;
    private String movies;
    private String places;

//    private List<Movie> reMovies;
//    private List<Place> rePlaces;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_details);


        //接收查询内容并显示
        Intent reIntent = getIntent();
        query = reIntent.getStringExtra("query");
        movies = reIntent.getStringExtra("movies");
        places = reIntent.getStringExtra("places");
//        Log.e("query", query);
//        Log.e("movies", movies);
//        Log.e("places", places);
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        Type listType = new TypeToken<List<Movie>>() {
        }.getType();
        movieList = gson.fromJson(movies, listType);
//        Log.e("reMovies", movieList.toString());
        placeList = gson.fromJson(places, new TypeToken<List<Place>>() {
        }.getType());




        //填充adapter
        findView();
        init();
        tvCancel.setOnClickListener(new View.OnClickListener() {
            //监听取消按钮点击事件
            @Override
            public void onClick(View v) {
                //返回上一界面
                SearchDetailsActivity.this.finish();
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                //跳转到搜索页
                Intent intent = new Intent(SearchDetailsActivity.this, SearchActivity.class);
                intent.putExtra("reQuery", query);
                startActivity(intent);
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
        Date date = new Date();
//        Movie movie = new Movie(1, "急速备战", "John Wick:Chapter3", date, R.drawable.update, "摩洛哥 纽约", 18, "2019/美国/动作/犯罪/惊悚");
//        Movie movie1 = new Movie(1, "急速备战", "John Wick:Chapter3", date, R.drawable.update, "摩洛哥 纽约", 18, "2019/美国/动作/犯罪/惊悚");
//        Movie movie2 = new Movie(1, "急速备战", "John Wick:Chapter3", date, R.drawable.update, "摩洛哥 纽约", 18, "2019/美国/动作/犯罪/惊悚");
//        Movie movie3 = new Movie(1, "急速备战", "John Wick:Chapter3", date, R.drawable.update, "摩洛哥 纽约", 18, "2019/美国/动作/犯罪/惊悚");
//        movieList.add(movie);
//        movieList.add(movie1);
//        movieList.add(movie2);
//        movieList.add(movie3);
//        if (movieList.size() > 2) {
//            movieList1.add(movieList.get(0));
//            movieList1.add(movieList.get(1));
//        }

        if (null != movieList && movieList.size() >= 2) {
            movieList1.add(movieList.get(0));
            movieList1.add(movieList.get(1));
        } else if (null != movieList && movieList.size() == 1) {
            movieList1.add(movieList.get(0));
        }

//        Place place = new Place(1, "二厂文创公园", "Testbed2", R.drawable.place, "中国 重庆市", "重庆市", "重庆市", "FILMED 从你的全世界路过");
//        Place place1 = new Place(1, "二厂文创公园", "Testbed2", R.drawable.place, "中国 重庆市", "重庆市", "重庆市", "FILMED 从你的全世界路过");
//        Place place2 = new Place(1, "二厂文创公园", "Testbed2", R.drawable.place, "中国 重庆市", "重庆市", "重庆市", "FILMED 从你的全世界路过");
//        Place place3 = new Place(1, "二厂文创公园", "Testbed2", R.drawable.place, "中国 重庆市", "重庆市", "重庆市", "FILMED 从你的全世界路过");
//        Place place4 = new Place(1, "二厂文创公园", "Testbed2", R.drawable.place, "中国 重庆市", "重庆市", "重庆市", "FILMED 从你的全世界路过");
//        Place place5 = new Place(1, "二厂文创公园", "Testbed2", R.drawable.place, "中国 重庆市", "重庆市", "重庆市", "FILMED 从你的全世界路过");
//        Place place6 = new Place(1, "二厂文创公园", "Testbed2", R.drawable.place, "中国 重庆市", "重庆市", "重庆市", "FILMED 从你的全世界路过");
//        Place place7 = new Place(1, "二厂文创公园", "Testbed2", R.drawable.place, "中国 重庆市", "重庆市", "重庆市", "FILMED 从你的全世界路过");
//
//        placeList.add(place);
//        placeList.add(place1);
//        placeList.add(place2);
//        placeList.add(place3);
//        placeList.add(place4);
//        placeList.add(place5);
//        placeList.add(place6);
//        placeList.add(place7);
        if (null != placeList && placeList.size() >= 2) {
            placeList1.add(placeList.get(0));
            placeList1.add(placeList.get(1));
        } else if (null != placeList && placeList.size() == 1) {
            placeList1.add(placeList.get(0));
        }

        placeAdapter.notifyDataSetChanged();
        setListViewHeightBasedOnChildren(placeListView);
        movieAdapter.notifyDataSetChanged();
        setListViewHeightBasedOnChildren1(movieListView);
        searchView.setQuery(query, true);
    }

    private void findView() {
        movieListView = findViewById(R.id.lv_item_search_movie);
        placeListView = findViewById(R.id.lv_item_search_place);
        movieAdapter = new SearchDetailsMovieAdapter(movieList1, R.layout.item_search_details, this);
        placeAdapter = new SearchDetailPlaceAdapter(placeList1, R.layout.item_seach_place, this);

        movieListView.setAdapter(movieAdapter);
        placeListView.setAdapter(placeAdapter);

        searchView = findViewById(R.id.search_content);
        tvCancel = findViewById(R.id.tv_cancle);
    }

    public void onClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_more_movie:
                intent = new Intent(this, SearchMoreMovieActivity.class);
//                Log.e("传参reMovieStr",reMovieStr);
                intent.putExtra("movies", movies);
                startActivity(intent);
                break;
            case R.id.btn_more_place:
                intent = new Intent(this, SearchMorePlaceActivity.class);
//                Log.e("传参rePlaceStr",rePlaceStr);
                intent.putExtra("places", places);
                startActivity(intent);
                break;

        }
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


}
