package com.example.msl.rainbow1;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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


public class NotesFragment extends Fragment {
    private OkHttpClient okHttpClient = new OkHttpClient();
    private List<Dynamic> dynamicList = new ArrayList<>();
    private Map<Integer,List<String>> picMap = new HashMap<Integer , List<String>>();
    private Map<Integer,List<String>> map = new HashMap<Integer , List<String>>();
    private List<Dynamic> list = new ArrayList<Dynamic>();
    private NotesAdapter notesAdapter;
    private PraiseAdapter praiseAdapter;
    private ListView listView;
    private Handler mainHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    try {
                        list = getMyNotes();
                        notesAdapter = new NotesAdapter(list,NotesFragment.this,R.layout.item_dynamic);
                        listView.setAdapter(notesAdapter);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    break;
            }
        }
    };



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes, container, false);
        listView = view.findViewById(R.id.lv_post);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


        getPictures();

    }

    public List<Dynamic> getMyNotes() throws IOException {

        FormBody formBody = new FormBody.Builder()
                .add("userId",Constant.USER_STATUS.getUserId()+"")
                .build();
        Request requestLike = new Request.Builder()
                .url(Constant.DYNAMIC_URL+"getMyNotes")
                .post(formBody)
                .build();
        Response r = okHttpClient.newCall(requestLike).execute();
        String json = r.body().string();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Dynamic dynamic = new Dynamic();
                dynamic.setHasLike(false);
                dynamic.setBlog(jsonObject.optString("blog"));
                dynamic.setDynamicId(jsonObject.optInt("dynamicId"));
                dynamic.setHeadimg(jsonObject.getString("headPicture"));
                dynamic.setLikeCount(jsonObject.getInt("likeCount"));
                dynamic.setUsername(jsonObject.getString("userName"));
                dynamic.setUserId(jsonObject.optInt("userId"));
                if (picMap.containsKey(dynamic.getDynamicId())){
                    dynamic.setImgData(picMap.get(dynamic.getDynamicId()));
                }
                dynamicList.add(dynamic);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return dynamicList;

    }

    public void getPictures(){
        Request requestLike1 = new Request.Builder()
                .url(Constant.SHOWPIC_URL)
                .build();
        Response response = null;
        try {
            response = okHttpClient.newCall(requestLike1).execute();
            String plist = response.body().string();
            JSONArray jsonArray = new JSONArray(plist);
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        Message msg = new Message();
        msg.what=1;
        mainHandler.sendMessage(msg);

    }


}
