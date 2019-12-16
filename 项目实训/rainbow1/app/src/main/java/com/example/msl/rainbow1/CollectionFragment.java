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


}
