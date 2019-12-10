package com.example.msl.rainbow1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PlaceSurroundingActivity extends AppCompatActivity {

    private ListView listView;
    private TextView tvMovieName;
    private TextView tvMovieEngName;
    private ImageView imgMoviePicture;
    private SurroundingAdapter adapter;
    private List<Place> placeList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_surrounding);

        //填充adapter
        findView();
        //init();

    }

//    private void init() {
//        Place place=new Place(1,"二厂文创公园","Testbed2",R.drawable.place,"中国 重庆市","重庆市","重庆市","FILMED 从你的全世界路过");
//        Place place1=new Place(1,"二厂文创公园","Testbed2",R.drawable.place,"中国 重庆市","重庆市","重庆市","FILMED 从你的全世界路过");
//        Place place2=new Place(1,"二厂文创公园","Testbed2",R.drawable.place,"中国 重庆市","重庆市","重庆市","FILMED 从你的全世界路过");
//        Place place3=new Place(1,"二厂文创公园","Testbed2",R.drawable.place,"中国 重庆市","重庆市","重庆市","FILMED 从你的全世界路过");
//        Place place4=new Place(1,"二厂文创公园","Testbed2",R.drawable.place,"中国 重庆市","重庆市","重庆市","FILMED 从你的全世界路过");
//        Place place5=new Place(1,"二厂文创公园","Testbed2",R.drawable.place,"中国 重庆市","重庆市","重庆市","FILMED 从你的全世界路过");
//        Place place6=new Place(1,"二厂文创公园","Testbed2",R.drawable.place,"中国 重庆市","重庆市","重庆市","FILMED 从你的全世界路过");
//        Place place7=new Place(1,"二厂文创公园","Testbed2",R.drawable.place,"中国 重庆市","重庆市","重庆市","FILMED 从你的全世界路过");
//
//        placeList.add(place);
//        placeList.add(place1);
//        placeList.add(place2);
//        placeList.add(place3);
//        placeList.add(place4);
//        placeList.add(place5);
//        placeList.add(place6);
//        placeList.add(place7);
//
//        adapter.notifyDataSetChanged();
//        setListViewHeightBasedOnChildren(listView);
//    }

    private void findView() {
        listView=findViewById(R.id.lv_item_surrounding);
        adapter=new SurroundingAdapter(placeList,R.layout.item_surrounding,this);
        listView.setAdapter(adapter);
        setListViewHeightBasedOnChildren(listView);
    }


    //根据当前的ListView的列表项计算列表的尺寸,
    // 解决ScrollView嵌套ListView时，会无法正确的计算ListView的大小
    public void setListViewHeightBasedOnChildren(ListView listView) {
        /**
         * by zsx
         * 获取ListView对应的Adapter
         * 这里的adapter是你为listview所添加的adapter，可以是原始adapter，
         * 也可以是自己定义的adapter，本人这里的articleadpter是自己定义的adapter
         */
        SurroundingAdapter listAdapter = (SurroundingAdapter) listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }
}
