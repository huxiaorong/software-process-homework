package com.example.msl.rainbow1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class DynamicFragment extends Fragment {

    private Button button;
    private ListView listView;
    private OkHttpClient okHttpClient = new OkHttpClient();
    private SmartRefreshLayout refreshLayout;
    private DynamicAdapter dynamicAdapter;
    private List<Dynamic> dynamicList = new ArrayList<>();;
    private Map<Integer,List<String>> picMap = new HashMap<Integer , List<String>>();
    private Map<Integer,List<String>> map = new HashMap<Integer , List<String>>();
    private List<Dynamic> list = new ArrayList<Dynamic>();
    private String dynamicJson;
    private static final int REFRESH_FINISH = 1;

    private Handler mainHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case REFRESH_FINISH:
                    try {
                        dynamicList.clear();
                        picMap.clear();
                        list = getDynamicData();
                        map = getPictures();
                        for (int i = 0;i<list.size();i++){
                            if (map.containsKey(list.get(i).getDynamicId())){
                                map.get(list.get(i).getDynamicId()).removeAll(Collections.singleton(null));
                                list.get(i).setImgData(map.get(list.get(i).getDynamicId()));
                            }
                        }
                        dynamicAdapter = new DynamicAdapter(list,DynamicFragment.this,R.layout.item_dynamic);
                        listView.setAdapter(dynamicAdapter);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //更新Adapter
                    dynamicAdapter.notifyDataSetChanged();
                    //结束刷新动画
                    refreshLayout.finishRefresh();
                    break;
            }
        }
    };

    public DynamicFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_dynamic, container, false);
        findViews(view);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        try {
            list = getDynamicData();
            map = getPictures();
            Log.e("aaaa",list.get(1).toString());
            Log.e("bbbb",map.toString());
            for (int i = 0;i<list.size();i++){
                if (map.containsKey(list.get(i).getDynamicId())){
                    map.get(list.get(i).getDynamicId()).removeAll(Collections.singleton(null));
                    list.get(i).setImgData(map.get(list.get(i).getDynamicId()));
                }
            }
            Log.e("cccc",list.get(1).getImgData()+"");
            dynamicAdapter = new DynamicAdapter(list,DynamicFragment.this,R.layout.item_dynamic);
            listView.setAdapter(dynamicAdapter);
        } catch (IOException e) {
            e.printStackTrace();
        }




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),PublishedActivity.class) ;
                startActivity(intent) ;
            }
        });

        setListeners();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void findViews(View view){
        refreshLayout = view.findViewById(R.id.smart_layout);
        listView = view.findViewById(R.id.lv_post);
        button = view.findViewById(R.id.btn_new);
    }

    private List<Dynamic> getDynamicData() throws IOException {
        FormBody formBody = new FormBody.Builder()
                .add("userId",Constant.USERID+"")
                .build();
        Request requestLike = new Request.Builder()
                .url(Constant.GETLIKE_URL)
                .post(formBody)
                .build();
        Response r = okHttpClient.newCall(requestLike).execute();
        String jsonLike = r.body().string();
        List<Integer> likeList = new ArrayList<>();
        Gson gson = new Gson();
        likeList = gson.fromJson(jsonLike, new TypeToken<List<Integer>>(){}.getType());
        Log.e("1111",likeList.toString());

        Request requestBlog = new Request.Builder()
                .url(Constant.SHOW_URL)
                .build();
        Response response = okHttpClient.newCall(requestBlog).execute();
        String json = response.body().string();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Dynamic dynamic = new Dynamic();
                dynamic.setDynamicId(jsonObject.getInt("dynamicId"));
                for (int j=0;j<likeList.size();j++){
                    if (dynamic.getDynamicId()==likeList.get(j)){
                        dynamic.setHasLike(true);
                    }else{
                        dynamic.setHasLike(false);
                    }
                }
                dynamic.setHeadimg(jsonObject.getString("headPicture"));
                dynamic.setUsername(jsonObject.getString("userName"));
                dynamic.setLikeCount(jsonObject.getInt("likeCount"));
                dynamic.setBlog(jsonObject.getString("blog"));
                dynamic.setUserId(jsonObject.getInt("id"));
                dynamicList.add(dynamic);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return dynamicList;
    }

    private Map<Integer,List<String>> getPictures() throws IOException {
        Request requestBlog = new Request.Builder()
                .url(Constant.SHOWPIC_URL)
                .build();
        Response response = okHttpClient.newCall(requestBlog).execute();
        String json = response.body().string();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0;i<jsonArray.length();i++){
                List<String> picList = new ArrayList<>();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt("id");
                for (int j=1;j<10;j++){
                    String test = (jsonObject.optString("img"+j));
                    if (!test.equals("null") && test!=null && test!=""){
                        picList.add(test);
                        picList.removeAll(Collections.singleton(null));
                    }
                }
                picMap.put(id,picList);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return picMap;
    }

    private void setListeners(){
        //监听下拉刷新
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //不能执行网络操作，需要使用多线程
                new Thread(){
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //向主线程发送消息，更新视图
                        Message msg = new Message();
                        msg.what = REFRESH_FINISH;
                        mainHandler.sendMessage(msg);
                    }
                }.start();

            }
        });
    }


}
