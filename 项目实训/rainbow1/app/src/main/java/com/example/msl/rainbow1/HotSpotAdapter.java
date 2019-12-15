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

public class HotSpotAdapter extends BaseAdapter {
    private List<Place> placeList=new ArrayList<>();
    private int itemId;
    private Context context;
    private ViewHolder viewHolder;

    public HotSpotAdapter(List<Place> placeList, int itemId, Context context) {
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
            viewHolder.tvSpotName=convertView.findViewById(R.id.tv_spotname);
            viewHolder.tvSpotProduce=convertView.findViewById(R.id.tv_spotproduce);
            viewHolder.ivSpotImage=convertView.findViewById(R.id.iv_spotimage);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder) convertView.getTag();
        }
        viewHolder.tvSpotName.setText(placeList.get(position).getName());
        viewHolder.tvSpotProduce.setText(placeList.get(position).getDescription());
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.loading)//请求过程中显示
                .error(R.drawable.error)//请求失败显示
                .fallback(R.drawable.defaultimg);//请求的URL为null时显示
        Glide.with(context)
                .load(Constant.BASE_IP+"place/"+placeList.get(position).getImg())
                .apply(options)
                .into(viewHolder.ivSpotImage);
        return convertView;
    }

    private class ViewHolder{
        private TextView tvSpotName;
        private TextView tvSpotProduce;
        private ImageView ivSpotImage;
    }
}
