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

public class SurroundingAdapter extends BaseAdapter {
        private List<Place> placeList=new ArrayList<>();
    private int itemId;
    private Context context;
    private ViewHolder viewHolder;

    public SurroundingAdapter(List<Place> placeList, int itemId, Context context) {
        this.placeList = placeList;
        this.itemId = itemId;
        this.context = context;
    }

    @Override
    public int getCount() {
        if(null!=placeList){
            return placeList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if(null!=placeList){
            return placeList.get(position);
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
            viewHolder.tvPlaceName=convertView.findViewById(R.id.tv_place_name);
            viewHolder.tvPlaceEngName=convertView.findViewById(R.id.tv_place_engname);
            viewHolder.tvPlace=convertView.findViewById(R.id.tv_place);
            viewHolder.tv_from_movie=convertView.findViewById(R.id.tv_from_movie);
            viewHolder.imgPlacePicture=convertView.findViewById(R.id.img);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder) convertView.getTag();
        }
        viewHolder.tvPlaceName.setText(placeList.get(position).getName());
        viewHolder.tvPlaceEngName.setText(placeList.get(position).getEnName());
        viewHolder.tvPlace.setText(placeList.get(position).getCountry());
        viewHolder.tv_from_movie.setText(placeList.get(position).getDescription());
        //viewHolder.imgPlacePicture.setImageResource(placeList.get(position).getImg());
        return convertView;
    }

    private class ViewHolder{
        private TextView tvPlaceName;
        private TextView tvPlaceEngName;
        private TextView tvPlace;
        private TextView tv_from_movie;
        private ImageView imgPlacePicture;
    }
}
