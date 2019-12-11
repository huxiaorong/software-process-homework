package com.example.msl.rainbow1;

import android.content.Context;
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

public class PlaceDetailsAdapter extends BaseAdapter {
    private List<Movie> movieList=new ArrayList<>();
    private int itemId;
    private PlaceDetailsMovieFragment context;
    private ViewHolder viewHolder;

    public PlaceDetailsAdapter(List<Movie> movieList, int itemId, PlaceDetailsMovieFragment context) {
        this.movieList = movieList;
        this.itemId = itemId;
        this.context = context;
    }

    @Override
    public int getCount() {
        if(null!=movieList){
            return movieList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if(null!=movieList){
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
        if(convertView==null){
            convertView= LayoutInflater.from(context.getContext()).inflate(itemId,null);
            viewHolder=new ViewHolder();
            viewHolder.tvMovieName=convertView.findViewById(R.id.tv_movie_name);
            viewHolder.tvMovieEngName=convertView.findViewById(R.id.tv_movie_engname);
            viewHolder.imgMoviePicture=convertView.findViewById(R.id.img_movie);
            viewHolder.tvScene=convertView.findViewById(R.id.tv_count);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder) convertView.getTag();
        }
        viewHolder.tvMovieName.setText(movieList.get(position).getName());
        viewHolder.tvMovieEngName.setText(movieList.get(position).getEnName());

        RequestOptions options = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context)
                .load(Constant.BASE_URL + "movies/" + movieList.get(position).getImg())
                .apply(options)
                .into(viewHolder.imgMoviePicture);

        viewHolder.tvScene.setText(movieList.get(position).getScene()+"");
        return convertView;
    }

    private class ViewHolder{
        private TextView tvMovieName;
        private TextView tvMovieEngName;
        private ImageView imgMoviePicture;
        private TextView tvScene;

    }
}
