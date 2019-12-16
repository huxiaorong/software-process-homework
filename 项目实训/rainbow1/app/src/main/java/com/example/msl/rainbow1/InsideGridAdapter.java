package com.example.msl.rainbow1;

import android.app.Dialog;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class InsideGridAdapter extends BaseAdapter {

    private List<String> dataSource = new ArrayList<>();
    private Context context = null;
    private int item_layout_id;

    public void deleteItem(int pos) {
        dataSource.remove(pos);
        notifyDataSetChanged();
    }

    public InsideGridAdapter(List<String> dataSource,Context context,int item_layout_id){
        this.context = context;
        this.dataSource = dataSource;
        this.item_layout_id = item_layout_id;
    }

    @Override
    public int getCount() {
        return dataSource.size();
    }

    @Override
    public Object getItem(int position) {
        return dataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(null == convertView) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(item_layout_id, null);
        }
        ImageView img = convertView.findViewById(R.id.iv_image);

        Glide.with(context)
                .load(Constant.HEADIMAGE_URL+dataSource.get(position))
                .into(img);

        final Dialog dialog = new Dialog(context, R.style.edit_AlertDialog_style);
        dialog.setContentView(R.layout.image_dialog);
        final ImageView imageView = (ImageView) dialog.findViewById(R.id.my_image);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide.with(context)
                        .load(Constant.HEADIMAGE_URL+dataSource.get(position))
                        .into(imageView);
                dialog.show();
            }
        });
        return convertView;
    }
}
