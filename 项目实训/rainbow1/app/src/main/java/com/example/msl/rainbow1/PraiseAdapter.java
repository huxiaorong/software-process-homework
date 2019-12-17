package com.example.msl.rainbow1;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.zhuang.likeviewlibrary.LikeView;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PraiseAdapter extends BaseAdapter {

    private List<Dynamic> dataSource = null;
    private PraiseFragment context = null;
    private int item_layout_id;
    private SharePopwindow sharePopwindow;
    private PopupWindow popupWindow;
    private Boolean isCollect = false;


    public PraiseAdapter(List<Dynamic> dataSource,PraiseFragment context,int item_layout_id){
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
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(null == convertView) {
            LayoutInflater inflater = LayoutInflater.from(context.getContext());
            convertView = inflater.inflate(item_layout_id, null);
        }
        ImageView imgHead = convertView.findViewById(R.id.iv_headimgae);
        TextView tvName = convertView.findViewById(R.id.tv_username);
        TextView tvContent = convertView.findViewById(R.id.tv_content);
        MyGridView gridView = convertView.findViewById(R.id.gridview);
        LinearLayout llCollection =  convertView.findViewById(R.id.ll_collect);
        final ImageView ivCollect = convertView.findViewById(R.id.iv_collect);
        LinearLayout llShare = convertView.findViewById(R.id.ll_share);

        //填充昵称头像及文字内容
        tvName.setText(dataSource.get(position).getUsername());
        tvContent.setText(dataSource.get(position).getBlog());
        //RoundedCorners roundedCorners = new RoundedCorners(30);
        //RequestOptions options = RequestOptions.bitmapTransform(roundedCorners).override(300, 300);
        Glide.with(context)
                .load(Constant.HEADIMAGE_URL+dataSource.get(position).getHeadimg().toString())
                //.apply(options)
                .into(imgHead);

        final Resources resources = context.getResources();
        llCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCollect){
                    Bitmap bmp = BitmapFactory.decodeResource(resources, R.drawable.shoucang);
                    ivCollect.setImageBitmap(bmp);
                    LayoutInflater li = LayoutInflater.from(context.getContext());
                    View tview = li.inflate(R.layout.toast_qvxiao, null);
                    Toast toast = new Toast(context.getContext());
                    toast.setView(tview);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    isCollect = false;
                }else {
                    Bitmap bmp = BitmapFactory.decodeResource(resources, R.drawable.shoucang2);
                    ivCollect.setImageBitmap(bmp);
                    LayoutInflater li = LayoutInflater.from(context.getContext());
                    View tview = li.inflate(R.layout.toast_duigou, null);
                    Toast toast = new Toast(context.getContext());
                    toast.setView(tview);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    isCollect = true;
                }
            }
        });


        final Dynamic dynamic = dataSource.get(position);
        final LikeView likeView = convertView.findViewById(R.id.likeView);
        likeView.setHasLike(dynamic.isHasLike());
        likeView.setLikeCount(dynamic.getLikeCount());

        likeView.setOnLikeListeners(new LikeView.OnLikeListeners() {
            @Override
            public void like(boolean isCancel) {
                dynamic.setHasLike(!isCancel);
                int id = dataSource.get(position).getDynamicId();
                if(isCancel){
                    dynamic.delLikeCount();
                    //连接数据库减已点赞数
                    plusOrMinus("minus",id);
                }else{
                    dynamic.addLikeCount();
                    plusOrMinus("plus",id);
                }
            }
        });


        //点击弹出分享至第三方软件的弹窗
        llShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(context.getContext());
                View cview = li.inflate(R.layout.sharepopupwindow, null);
                backgroundalpha(context.getActivity(),0.5f);
                popupWindow = new PopupWindow(cview, ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                popupWindow.setBackgroundDrawable(new BitmapDrawable());

                LinearLayout weixfriend = (LinearLayout) cview.findViewById(R.id.weixinghaoyou);
                LinearLayout friendster = (LinearLayout) cview.findViewById(R.id.pengyouquan);
                LinearLayout qqfriend = (LinearLayout) cview.findViewById(R.id.qqhaoyou);
                TextView canaletv = (TextView) cview.findViewById(R.id.share_cancle);
                canaletv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                        backgroundalpha(context.getActivity(), 1f);
                    }
                });

                //设置按钮监听
                weixfriend.setOnClickListener(itemsonclick);
                friendster.setOnClickListener(itemsonclick);
                qqfriend.setOnClickListener(itemsonclick);

                popupWindow.setFocusable(true);
                popupWindow.setOutsideTouchable(true);
                popupWindow.setTouchable(true);
                popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        backgroundalpha(context.getActivity(), 1f);
                    }
                });
            }
        });

        List<String> picData = dataSource.get(position).getImgData();
        InsideGridAdapter insideGridAdapter = new InsideGridAdapter(dataSource.get(position).getImgData(),context.getContext(),R.layout.item_inside_grid);
        gridView.setAdapter(insideGridAdapter);

        return convertView;
    }

    //popupwindow中子项的点击事件
    private View.OnClickListener itemsonclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            popupWindow.dismiss();
            switch (v.getId()) {
                case R.id.weixinghaoyou:
                    Toast.makeText(v.getContext(),"这是微信好友",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.pengyouquan:
                    Toast.makeText(v.getContext(),"这是微信朋友圈",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.qqhaoyou:
                    Toast.makeText(v.getContext(),"这是qq好友",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    //设置弹出popupwindow时背景半透明
    public void backgroundalpha(Activity context, float bgalpha) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgalpha;
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);
    }

    public void plusOrMinus(String msg,int id){
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody formBody = new FormBody.Builder()
                .add("what",msg)
                .add("dynamicId",id+"")
                .add("userId",Constant.USER_STATUS.getUserId()+"")
                .build();
        Request requestBlog = new Request.Builder()
                .url(Constant.PULSORMINUS_URL)
                .post(formBody)
                .build();
        Call callBlog = okHttpClient.newCall(requestBlog);
        callBlog.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
            }
        });
    }
}
