package com.example.msl.rainbow1;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class CenterFragment extends Fragment {
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private TextView tvPraiseFragment;
    private TextView tvCollectionFragment;
    private TextView tvNotesFragment;
    private ImageView ivSettings;
    private Button btnEdit;
    private ImageView ivHeader;
    private TextView tvPraise;
    private TextView tvCollection;
    private TextView tvNotes;
    private String rsp;
    private Handler mainHandle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Count count = new Gson().fromJson(rsp,Count.class);
                    tvCollection.setText(count.getCollectionCount()+"");
                    tvNotes.setText(count.getDynamicCount()+"");
                    tvPraise.setText(count.getPraiseCount()+"");
                    break;

            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_personal_center, container, false);
        findViews(view);
        bindListener();
        tvCollectionFragment.setTextColor(Color.RED);
        fragmentManager = getActivity().getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame, new CollectionFragment());
        transaction.commit();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getCount();

    }

    private void findViews(View view){
        tvPraiseFragment = view.findViewById(R.id.tv_praise_fragment);
        tvCollectionFragment = view.findViewById(R.id.tv_collection_fragment);
        tvNotesFragment = view.findViewById(R.id.tv_notes_fragment);
        ivSettings = view.findViewById(R.id.iv_settings);
        btnEdit = view.findViewById(R.id.btn_edit);
        ivHeader = view.findViewById(R.id.iv_avatar);
        tvNotes = view.findViewById(R.id.tv_notes);
        tvCollection = view.findViewById(R.id.tv_collection);
        tvPraise = view.findViewById(R.id.tv_praise);
    }
    private void bindListener(){
        MyListener myListener = new MyListener();
        tvNotesFragment.setOnClickListener(myListener);
        tvCollectionFragment.setOnClickListener(myListener);
        tvPraiseFragment.setOnClickListener(myListener);
        ivSettings.setOnClickListener(myListener);
        btnEdit.setOnClickListener(myListener);
    }
    private class MyListener implements View.OnClickListener {


        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_collection_fragment:
                    tvCollectionFragment.setTextColor(Color.RED);
                    tvPraiseFragment.setTextColor(Color.BLACK);
                    tvNotesFragment.setTextColor(Color.BLACK);
                    fragmentManager = getActivity().getSupportFragmentManager();
                    transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.frame, new CollectionFragment());
                    transaction.commit();
                    break;
                case R.id.tv_notes_fragment:
                    tvNotesFragment.setTextColor(Color.RED);
                    tvPraiseFragment.setTextColor(Color.BLACK);
                    tvCollectionFragment.setTextColor(Color.BLACK);
                    fragmentManager = getActivity().getSupportFragmentManager();
                    transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.frame, new NotesFragment());
                    transaction.commit();
                    break;
                case R.id.tv_praise_fragment:
                    tvPraiseFragment.setTextColor(Color.RED);
                    tvCollectionFragment.setTextColor(Color.BLACK);
                    tvNotesFragment.setTextColor(Color.BLACK);
                    fragmentManager = getActivity().getSupportFragmentManager();
                    transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.frame, new PraiseFragment());
                    transaction.commit();
                    break;
                case R.id.iv_settings:
                    Intent intent = new Intent(getActivity(), SettingsActivity.class);
                    startActivity(intent);
                    break;
                case R.id.btn_edit:
                    Intent intent1 = new Intent(getActivity(), EditActivity.class);
                    startActivity(intent1);
                    break;
            }
        }
    }
    private void getCount(){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Constant.BASE_URL + "center/getCount?userId="+Constant.USER_STATUS.getUserId())//设置网络请求的地址
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                rsp = response.body().string();
                Log.e("rrr", rsp);
                Message msg = new Message();
                msg.what = 1;
                mainHandle.sendMessage(msg);

            }
        });
    }
}
