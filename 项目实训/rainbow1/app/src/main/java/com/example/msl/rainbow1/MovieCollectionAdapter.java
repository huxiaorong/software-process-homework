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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class MovieCollectionAdapter extends BaseAdapter {
    private List<Place> placeList=new ArrayList<>();
    private int itemId;
    private FragmentActivity  context;
    private ViewHolder viewHolder;

    public MovieCollectionAdapter(List<Place> placeList, int itemId, FragmentActivity context) {
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
            viewHolder.tvPlaceEngName=convertView.findViewById(R.id.tv_place_ename);
            viewHolder.imgPlacePicture=convertView.findViewById(R.id.img_place);
            viewHolder.tvPlaceCountry=convertView.findViewById(R.id.tv_country);
            viewHolder.tvPlaceProvinceCity=convertView.findViewById(R.id.tv_province_city);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder) convertView.getTag();
        }
        viewHolder.tvPlaceName.setText(placeList.get(position).getName());
        viewHolder.tvPlaceEngName.setText(placeList.get(position).getEnName());
        viewHolder.tvPlaceCountry.setText(placeList.get(position).getCountry()+"  "+placeList.get(position).getProvince());
        viewHolder.tvPlaceProvinceCity.setText(placeList.get(position).getCity());
        RequestOptions options=new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(convertView)
                .load(Constant.BASE_URL+"places/"+placeList.get(position).getImg())
                .apply(options)
                .into(viewHolder.imgPlacePicture);
        return convertView;
    }

    private class ViewHolder{
        private TextView tvPlaceName;
        private TextView tvPlaceEngName;
        private TextView tvPlaceCountry;
        private TextView tvPlaceProvinceCity;
        private ImageView imgPlacePicture;

    }
}

