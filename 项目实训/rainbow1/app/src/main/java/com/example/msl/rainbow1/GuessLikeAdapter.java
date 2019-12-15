package com.example.msl.rainbow1;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class GuessLikeAdapter extends BaseAdapter {
    private List<Movie> movieList = new ArrayList<>();
    private int itemId;
    private HomeFragment context;

    private ImageView img;
    private TextView tvMovieName;

    public GuessLikeAdapter(List<Movie> movieList, int itemId, HomeFragment context) {
        this.movieList = movieList;
        this.itemId = itemId;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (null != movieList) {
            return movieList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (null != movieList) {
            return movieList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context.getContext()).inflate(itemId, null);

        img = convertView.findViewById(R.id.img);
        tvMovieName = convertView.findViewById(R.id.tv_name);
        tvMovieName.setText(movieList.get(position).getName());
        RequestOptions options = new RequestOptions().placeholder(R.drawable.addphoto).diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context)
                .load(Constant.BASE_URL + "movies/" + movieList.get(position).getImg())
                .apply(options)
                .into(img);

        return convertView;
    }
}

