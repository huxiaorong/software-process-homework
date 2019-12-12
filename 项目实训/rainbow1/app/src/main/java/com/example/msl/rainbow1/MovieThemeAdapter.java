package com.example.msl.rainbow1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class MovieThemeAdapter extends BaseAdapter {
    private List<Movie> movieList=new ArrayList<>();
    private int itemId;
    private Context context;
    private ViewHolder viewHolder;
    private List<String> movieTypes;
    private List<String> movieCities;

    public MovieThemeAdapter(List<Movie> movieList, List<String>  movieTypes,List<String> movieCities, int itemId, Context context) {
        this.movieList = movieList;
        this.itemId = itemId;
        this.context = context;
        this.movieTypes = movieTypes;
        this.movieCities = movieCities;
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
            viewHolder.imgMovie = convertView.findViewById(R.id.img_movie);
            viewHolder.tvMovieName = convertView.findViewById(R.id.tv_movie_name);
            viewHolder.tvMovieEnname = convertView.findViewById(R.id.tv_movie_enname);
            viewHolder.tvYearType = convertView.findViewById(R.id.tv_year_type);
            viewHolder.tvMovieCountry = convertView.findViewById(R.id.tv_movie_country);
            viewHolder.tvMovieScene=convertView.findViewById(R.id.tv_movie_scene);
            viewHolder.tvMovieCity=convertView.findViewById(R.id.tv_movie_city);
            viewHolder.tvMovieDescription=convertView.findViewById(R.id.tv_movie_description);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder) convertView.getTag();
        }
        viewHolder.tvMovieName.setText(movieList.get(position).getName());
        viewHolder.tvMovieEnname.setText(movieList.get(position).getEnName());
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.loading)//请求过程中显示
                .error(R.drawable.error)//请求失败显示
                .fallback(R.drawable.defaultimg);//请求的URL为null时显示
        Glide.with(context)
                .load(Constant.BASE_IP+"movie/"+movieList.get(position).getImg())
                .apply(options)
                .into(viewHolder.imgMovie);
        //电影类型
        viewHolder.tvYearType.setText(movieList.get(position).getReleaseYear().getYear()+" " + movieTypes.get(position));
        viewHolder.tvMovieCountry.setText(movieList.get(position).getCountry());
        viewHolder.tvMovieScene.setText(movieList.get(position).getScene()+"");
        viewHolder.tvMovieDescription.setText(movieList.get(position).getDescription());
        viewHolder.tvMovieCity.setText(movieCities.get(position));
        return convertView;
    }

    private class ViewHolder{
        private ImageView imgMovie;
        private TextView tvMovieName;
        private TextView tvMovieEnname;
        private TextView tvYearType;
        private TextView tvMovieCountry;
        private TextView tvMovieScene;
        private TextView tvMovieCity;
        private TextView tvMovieDescription;
    }
}
