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

public class CityDetailsAdapter extends BaseAdapter {
    private List<Place> placeList=new ArrayList<>();
    private int itemId;
    private Context context;
    private ViewHolder viewHolder;

    public CityDetailsAdapter(List<Place> placeList, int itemId, Context context) {
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
            viewHolder.tvCityDetailName=convertView.findViewById(R.id.tv_citydetail_name);
            viewHolder.tvCityDetailProduce=convertView.findViewById(R.id.tv_citydetail_produce);
            viewHolder.ivCityDetailImage=convertView.findViewById(R.id.iv_citydetail_image);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder) convertView.getTag();
        }
        viewHolder.tvCityDetailName.setText(placeList.get(position).getName());
        if (placeList.get(position).getDescription().length()<=15){
            viewHolder.tvCityDetailProduce.setText(placeList.get(position).getDescription());
        }else{
            viewHolder.tvCityDetailProduce.setText(placeList.get(position).getDescription().substring(0,15)+"...");
        }

        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.loading)//请求过程中显示
                .error(R.drawable.error)//请求失败显示
                .fallback(R.drawable.defaultimg);//请求的URL为null时显示
        Glide.with(context)
                .load(Constant.BASE_IP+"place/"+placeList.get(position).getImg())
                .apply(options)
                .into(viewHolder.ivCityDetailImage);
        return convertView;
    }

    private class ViewHolder{
        private TextView tvCityDetailName;
        private TextView tvCityDetailProduce;
        private ImageView ivCityDetailImage;
    }
}
