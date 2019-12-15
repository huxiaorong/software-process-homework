package com.example.msl.rainbow1;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

public class SharePopwindow extends PopupWindow {

    private View mview;

    public SharePopwindow(Activity context, View.OnClickListener itemsonclick) {
        super(context);
        initview(context,itemsonclick);
    }

    private void initview(final Activity context, View.OnClickListener itemsonclick) {
        LayoutInflater minflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        mview = minflater.inflate(R.layout.sharepopupwindow, null);
        LinearLayout weixfriend = (LinearLayout) mview.findViewById(R.id.weixinghaoyou);
        LinearLayout friendster = (LinearLayout) mview.findViewById(R.id.pengyouquan);
        LinearLayout qqfriend = (LinearLayout) mview.findViewById(R.id.qqhaoyou);
        // LinearLayout qqzone = (LinearLayout) mview.findViewById(R.id.qqkongjian);
        TextView canaletv = (TextView) mview.findViewById(R.id.share_cancle);
        canaletv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                backgroundalpha(context, 1f);
            }
        });
        //设置按钮监听
        weixfriend.setOnClickListener(itemsonclick);
        friendster.setOnClickListener(itemsonclick);
        qqfriend.setOnClickListener(itemsonclick);
        // qqzone.setOnClickListener(itemsonclick);
        //设置selectpicpopupwindow的view
        this.setContentView(mview);
        //设置selectpicpopupwindow弹出窗体的宽
        this.setWidth(WindowManager.LayoutParams.FILL_PARENT);
        //设置selectpicpopupwindow弹出窗体的高
        this.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        //设置selectpicpopupwindow弹出窗体可点击
        this.setFocusable(true);
        //设置popupwindow可触摸
        this.setTouchable(true);
        //设置非popupwindow区域是否可触摸
//    this.setoutsidetouchable(false);
        //设置selectpicpopupwindow弹出窗体动画效果
//    this.setanimationstyle(r.style.select_anim);
        //实例化一个colordrawable颜色为半透明
        this.setOnDismissListener(new OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        backgroundalpha(context, 1f);
            }
        });
    }


    /**
     * 设置添加屏幕的背景透明度
     */
    public void backgroundalpha(Activity context, float bgalpha) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgalpha;
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);
    }
}
