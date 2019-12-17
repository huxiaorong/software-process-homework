package com.example.msl.rainbow1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


public class CollectionFragment extends Fragment {

    private ListView listView;
    private List<Movie> movieList = new ArrayList<>();
    private String[] movieTypeList;
    private String strMovieType;
    private String strMovies;

    private List<Place> placeList = new ArrayList<>();
    private String strPlace;
    private MovieCollectionAdapter movieCollectionAdapter;
    private ListView lvPlace;
    private List<Place> copyPlaceList = new ArrayList<>();


    private Handler mainHandle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    findMovieTypeById();
                    break;
                case 2:
                    CollectionAdapter adapter = new CollectionAdapter(movieList, R.layout.item_movie, getActivity(), movieTypeList);
                    listView.setAdapter(adapter);
                    setListViewHeightBasedOnChildren(listView);
                    getPlace();
                    break;
                case 3:
                    movieCollectionAdapter = new MovieCollectionAdapter(placeList,R.layout.item_place,getActivity());
                    lvPlace.setAdapter(movieCollectionAdapter);
                    copyPlaceList.addAll(placeList);
                    setListViewHeightBasedOnChildren1(lvPlace);
                    break;
            }
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_collection, container, false);
        //填充adapter
        listView = view.findViewById(R.id.lv_item_movie);
        lvPlace = view.findViewById(R.id.lv_item_place);

        getCollection();
        listClicked();
        return view;
    }



    private void listClicked() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),MovieDetailsActivity.class);
                intent.putExtra("movieId",movieList.get(position).getMovieId());
                intent.putExtra("type",movieTypeList[(int) id]);
                //intent.putExtra("position",id);
                startActivity(intent);
            }
        });

        lvPlace.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), PlaceDetailsActivity.class);
                Gson gson = new Gson();
                Place place = new Place();
                place.setName(copyPlaceList.get((int) id).getName());
                place.setEnName(copyPlaceList.get((int) id).getEnName());
                place.setCity(copyPlaceList.get((int) id).getCity());
                place.setCountry(copyPlaceList.get((int) id).getCountry());
                place.setDescription(copyPlaceList.get((int) id).getDescription());
                place.setImg(copyPlaceList.get((int) id).getImg());
                place.setPlaceId(copyPlaceList.get((int) id).getPlaceId());
                place.setProvince(copyPlaceList.get((int) id).getProvince());
                String strPlace = gson.toJson(place);
                intent.putExtra("place", strPlace);
                startActivity(intent);
            }
        });
    }


    public void getCollection() {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Constant.BASE_URL + "center/getCollection?userId=" + Constant.USER_STATUS.getUserId())
                .build();
        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                strMovies = response.body().string();
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

                movieList = gson.fromJson(strMovies, new TypeToken<List<Movie>>() {
                }.getType());
                Log.e("movie",movieList.get(0).toString());
                Message msg = new Message();
                msg.what = 1;
                mainHandle.sendMessage(msg);
            }
        });
    }



    public void getPlace() {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Constant.BASE_URL + "center/getPlace?userId=" + Constant.USER_STATUS.getUserId())
                .build();
        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                strPlace = response.body().string();
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

                placeList = gson.fromJson(strPlace, new TypeToken<List<Place>>() {
                }.getType());
                Log.e("movie",movieList.get(0).toString());
                Message msg = new Message();
                msg.what = 3;
                mainHandle.sendMessage(msg);
            }
        });
    }



    private void findMovieTypeById() {
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody body = new FormBody.Builder().add("strMovies", strMovies).build();
        Request request = new Request.Builder()
                .url(Constant.BASE_URL + "center/findMovieType")
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
                movieTypeList = new Gson().fromJson(strMovieType, String[].class);
                Message msg = new Message();
                msg.what = 2;
                mainHandle.sendMessage(msg);
            }
        });
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
        CollectionAdapter listAdapter = (CollectionAdapter) listView.getAdapter();
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
        MovieCollectionAdapter listAdapter = (MovieCollectionAdapter) listView.getAdapter();
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


}
