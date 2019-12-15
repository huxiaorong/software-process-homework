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

public class HotCityAdapter extends BaseAdapter {
    private List<City> cityList=new ArrayList<>();
    private int itemId;
    private Context context;
    private ViewHolder viewHolder;

    public HotCityAdapter(List<City> cityList, int itemId, Context context) {
        this.cityList = cityList;
        this.itemId = itemId;
        this.context = context;
    }

    @Override
    public int getCount() {
        if(null!=cityList){
            return cityList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if(null!=cityList){
            return cityList.get(position);
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
            viewHolder.tvCityName=convertView.findViewById(R.id.tv_city_name);
            viewHolder.tvCityDescription=convertView.findViewById(R.id.tv_city_description);
            viewHolder.imgCity=convertView.findViewById(R.id.img_city);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder) convertView.getTag();
        }
        viewHolder.tvCityName.setText(cityList.get(position).getName());
        viewHolder.tvCityDescription.setText(cityList.get(position).getCityDescription());

        RequestOptions options = new RequestOptions()
                        .placeholder(R.drawable.loading)//请求过程中显示
                        .error(R.drawable.error)//请求失败显示
                        .fallback(R.drawable.defaultimg);//请求的URL为null时显示
        Glide.with(context)
                .load(Constant.BASE_IP+"hotCity/"+cityList.get(position).getImg())
                .apply(options)
                .into(viewHolder.imgCity);

//        viewHolder.imgPlacePicture.setImageResource(placeList.get(position).getImg());
        return convertView;
    }

    private class ViewHolder{
        private TextView tvCityName;
        private TextView tvCityDescription;
        private ImageView imgCity;
    }
}
