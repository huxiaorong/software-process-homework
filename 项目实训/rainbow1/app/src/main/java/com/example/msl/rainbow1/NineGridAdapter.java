package com.example.msl.rainbow1;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;

import java.util.List;

public class NineGridAdapter extends BaseAdapter {

    private List<String> dataSource = null;
    private Context context = null;
    private int item_layout_id;

    private View view;
    private Button btnSend;

    public void deleteItem(int pos) {
        dataSource.remove(pos);
        if (dataSource.size()==1){
            btnSend.setEnabled(false);
            btnSend.setBackgroundResource(R.drawable.sendgray);
            btnSend.setTextColor(0xffffffff);
        }
        notifyDataSetChanged();
    }

    public NineGridAdapter(List<String> dataSource,Context context,int item_layout_id){
        this.context = context;
        this.dataSource = dataSource;
        this.item_layout_id = item_layout_id;
    }

    @Override
    public int getCount() {
        if (dataSource.size()==9){
            return dataSource.size();
        }else {
            return dataSource.size() + 1;
        }
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
            LayoutInflater inflater2 = LayoutInflater.from(context);
            view = inflater2.inflate(R.layout.activity_published, null);
            btnSend = view.findViewById(R.id.btn_sendnews);

            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(item_layout_id, null);
        }
        ImageView img = convertView.findViewById(R.id.iv_image);
        ImageView btnDelete = convertView.findViewById(R.id.btn_del);


        if (dataSource.size()>9){
            for (int i = 9;i<dataSource.size();i++){
                dataSource.remove(i);
            }
        }

        if (position<dataSource.size() && position<9){
            String string = dataSource.get(position).toString();
            img.setImageBitmap(BitmapFactory.decodeFile(string));
        }else{
            if (dataSource.size()!=9){
                img.setImageResource(R.drawable.addphoto);
                btnDelete.setVisibility(View.GONE);
            }
        }

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItem(position);
            }
        });
        return convertView;
    }
}
