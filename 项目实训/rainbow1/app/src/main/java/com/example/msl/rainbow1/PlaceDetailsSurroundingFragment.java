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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PlaceDetailsSurroundingFragment extends Fragment {
    private View view;
    private SurroundingAdapter surroundingAdapter;
    private ListView listView;
    private List<Place> placeList = new ArrayList<>();
    private OkHttpClient okHttpClient = new OkHttpClient();

    private String strPlaceList;
    private Intent intent;
    private Place place;
    private Gson gson=new Gson();
    private String strPlace;

    private Handler mainHandle = new Handler() {
        @Override
        public void handleMessage(final Message msg) {

            switch (msg.what) {
                case 1:
                    findView();
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_place_details_surrounding, container, false);

        findSurroundingByPlaceId();

        return view;
    }

    private void findView() {

        listView = view.findViewById(R.id.lv_item_surrounding);
        surroundingAdapter = new SurroundingAdapter(placeList, R.layout.item_surrounding, getActivity());
        listView.setAdapter(surroundingAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent=new Intent(getActivity(),PlaceDetailsActivity.class);
                place=placeList.get((int) id);
                strPlace=gson.toJson(place);
                intent.putExtra("place",strPlace);
                startActivity(intent);
            }
        });
    }

    private void findSurroundingByPlaceId() {
        FormBody body = new FormBody.Builder().add("placeId", String.valueOf(Constant.PLACE_ID)).build();
        Request request = new Request.Builder()
                .url(Constant.INDEX_URL + "findSurroundingByPlaceId")
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
                strPlaceList = response.body().string();
                Gson gson = new Gson();
                placeList = gson.fromJson(strPlaceList, new TypeToken<List<Place>>() {
                }.getType());

                Message msg = new Message();
                msg.what = 1;
                mainHandle.sendMessage(msg);
            }
        });
    }
}

