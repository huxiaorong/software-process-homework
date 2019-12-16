package com.example.msl.rainbow1;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class CollectionAdapter extends BaseAdapter {
    private List<Movie> movieList=new ArrayList<>();
    private String[] type;
    private int itemId;
    private FragmentActivity context;
    private ViewHolder viewHolder;

    public CollectionAdapter(List<Movie> movieList, int itemId, FragmentActivity context,String[] type) {
        this.movieList = movieList;
        this.itemId = itemId;
        this.context = context;
        this.type=type;
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
            convertView= LayoutInflater.from(context).inflate(itemId,null);
            viewHolder=new ViewHolder();
            viewHolder.tvMovieName=convertView.findViewById(R.id.tv_movie_name);
            viewHolder.tvMovieEngName=convertView.findViewById(R.id.tv_movie_engname);
            viewHolder.tvMoviePlace=convertView.findViewById(R.id.tv_movie_place);
            viewHolder.tvMovieType=convertView.findViewById(R.id.tv_movie_brief);
            viewHolder.imgMoviePicture=convertView.findViewById(R.id.img_movie);
            viewHolder.tvScene=convertView.findViewById(R.id.tv_movie_count);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder) convertView.getTag();
        }
        viewHolder.tvMovieName.setText(movieList.get(position).getName());
        viewHolder.tvMovieEngName.setText(movieList.get(position).getEnName());
        viewHolder.tvMoviePlace.setText(movieList.get(position).getCountry());
        viewHolder.tvMovieType.setText(type[position]);
        Glide.with(convertView)
                .load(Constant.BASE_URL+"movies/"+movieList.get(position).getImg())
                .into(viewHolder.imgMoviePicture);
        viewHolder.tvScene.setText(movieList.get(position).getScene()+"");
        return convertView;
    }

    private class ViewHolder{
        private TextView tvMovieName;
        private TextView tvMovieEngName;
        private TextView tvMoviePlace;
        private TextView tvMovieType;
        private ImageView imgMoviePicture;
        private TextView tvScene;

    }
}
