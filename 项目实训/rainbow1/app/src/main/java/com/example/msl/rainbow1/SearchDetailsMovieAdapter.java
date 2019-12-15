package com.example.msl.rainbow1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

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

        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.loading)//请求过程中显示
                .error(R.drawable.error)//请求失败显示
                .fallback(R.drawable.defaultimg);//请求的URL为null时显示
        Glide.with(context)
                .load(Constant.BASE_IP+"movie/"+movieList.get(position).getImg())
                .apply(options)
                .into(viewHolder.imgMoviePicture);

//        viewHolder.imgMoviePicture.setImageResource(movieList.get(position).getImg());
        return convertView;
    }

    private class ViewHolder {
        private TextView tvMovieName;
        private TextView tvMovieBrief;
        private ImageView imgMoviePicture;
    }
}