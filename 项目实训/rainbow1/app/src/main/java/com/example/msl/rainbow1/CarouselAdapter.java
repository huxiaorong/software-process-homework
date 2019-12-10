package com.example.msl.rainbow1;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class CarouselAdapter extends PagerAdapter {
    private Fragment mContext;
    private List<Movie> list = new ArrayList<>();
    private int newPosition;
    private OkHttpClient okHttpClient = new OkHttpClient();
    private String strMovie;
    private List<Movie> movieList = new ArrayList<>();
    private int id;
    private OnClickListener onClickListener;

    private Handler mainHandle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    onClickListener.sendMovieId(id);
                    break;
            }
        }

        ;
    };

    public CarouselAdapter(Fragment activity, List<Movie> list) {
        mContext = activity;
        this.list = list;
        onClickListener = (OnClickListener) activity;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = LayoutInflater.from(mContext.getContext()).inflate(R.layout.carousel, null);
        final ImageView imageView = view.findViewById(R.id.iv_img);
        VideoView videoView=view.findViewById(R.id.video);
        TextView textView = view.findViewById(R.id.tv_des);
        RequestOptions options = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL);


        if(list.get(position).getVideo()==null){
            Glide.with(mContext)
                    .load(Constant.BASE_URL + "movies/" + list.get(position).getImg())
                    .apply(options)
                    .into(imageView);
        }else{
            videoView.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.GONE);
            Uri uri=Uri.parse(Constant.BASE_URL + "moviesvideo/" + list.get(position).getVideo());
            videoView.setVideoURI(uri);
            videoView.start();

            videoView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for(int i=0;i<list.size();i++){
                        if(imageView.getTag().equals(list.get(i).getImg())){
                            newPosition=i;
                        }
                    }
                    findCarouselMovieByPosition();
                }
            });

        }

        textView.setText(list.get(position).getDescription());


        imageView.setTag(list.get(position).getImg());


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0;i<list.size();i++){
                    if(imageView.getTag().equals(list.get(i).getImg())){
                        newPosition=i;
                    }
                }
                findCarouselMovieByPosition();
            }
        });

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    private void findCarouselMovieByPosition() {

        FormBody body = new FormBody.Builder().add("position", String.valueOf(newPosition)).build();
        Request request = new Request.Builder()
                .url(Constant.INDEX_URL + "findCarouselMovieByPosition")
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
                id = Integer.parseInt(response.body().string());
                Message msg = new Message();
                msg.what = 1;
                mainHandle.sendMessage(msg);
            }
        });
    }

    public interface OnClickListener {
        public int sendMovieId(int movieId);
    }
}
