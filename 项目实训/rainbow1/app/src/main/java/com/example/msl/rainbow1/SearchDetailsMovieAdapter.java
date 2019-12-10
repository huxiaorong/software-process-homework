package com.example.msl.rainbow1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SearchDetailsMovieAdapter extends BaseAdapter {
    private List<Movie> movieList = new ArrayList<>();
    private int itemId;
    private Context context;
    private ViewHolder viewHolder;

    public SearchDetailsMovieAdapter(List<Movie> movieList, int itemId, Context context) {
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

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(itemId, null);
            viewHolder = new ViewHolder();
            viewHolder.tvMovieName = convertView.findViewById(R.id.tv_movie_name);
            viewHolder.tvMovieBrief = convertView.findViewById(R.id.tv_movie_brief);
            viewHolder.imgMoviePicture = convertView.findViewById(R.id.img_movie);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvMovieName.setText(movieList.get(position).getName());
        viewHolder.tvMovieBrief.setText(movieList.get(position).getDescription());
        //viewHolder.imgMoviePicture.setImageResource(movieList.get(position).getImg());
        return convertView;
    }

    private class ViewHolder {
        private TextView tvMovieName;
        private TextView tvMovieBrief;
        private ImageView imgMoviePicture;
    }
}