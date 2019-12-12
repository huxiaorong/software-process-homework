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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PlaceDetailsMovieFragment extends Fragment {
    private ListView listView;
    private PlaceDetailsAdapter placeDetailsAdapter;
    private View view;
    private Intent intent;
    private OkHttpClient okHttpClient = new OkHttpClient();
    private String strMovieType;
    private TextView tvCount;

    private Handler mainHandle1 = new Handler() {
        @Override
        public void handleMessage(final Message msg) {

            switch (msg.what) {
                case 1:
                    intent.putExtra("type", strMovieType);
                    startActivity(intent);
            }

        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_place_details_movie, container, false);
        findView();
        return view;
    }

    private void findView() {

        listView = view.findViewById(R.id.lv_item_movie1);
        placeDetailsAdapter = new PlaceDetailsAdapter(Constant.MOVIE_LIST, R.layout.item_place_details, this);
        listView.setAdapter(placeDetailsAdapter);

        setListViewHeightBasedOnChildren(listView);
        tvCount=view.findViewById(R.id.tv_count);
        tvCount.setText("共"+Constant.MOVIE_LIST.size()+"部");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                findMovieTypeById(Constant.MOVIE_LIST.get((int) id).getMovieId());
                intent = new Intent(getActivity(), MovieDetailsActivity.class);
                intent.putExtra("movieId", Constant.MOVIE_LIST.get((int) id).getMovieId());
            }
        });
    }

    private void findMovieTypeById(int movieId) {
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
                strMovieType = strMovieType.substring(1, strMovieType.length() - 1);

                Message msg = new Message();
                msg.what = 1;
                mainHandle1.sendMessage(msg);
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
        PlaceDetailsAdapter listAdapter = (PlaceDetailsAdapter) listView.getAdapter();
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
