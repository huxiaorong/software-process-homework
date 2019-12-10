package com.example.msl.rainbow1;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lljjcoder.citypickerview.widget.CityPicker;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class EditActivity extends AppCompatActivity {
    private LinearLayout llDate;
    private TextView dateDisplay;
    private LinearLayout llAddress;
    private TextView tvAddress;
    private EditText etUsername;
    private Spinner spSex;
    private TextView tvDate;
    private Calendar calendar= Calendar.getInstance(Locale.CHINA);
    private ImageView ivAvatar;
    private String rsp;
    private ImageView ivLeft;
    private Button btnSave;
    private RadioButton btnMan;
    private RadioButton btnWoman;
    private Handler mainHandle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (rsp.equals("修改成功")){
                        Toast.makeText(EditActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                    }
                    break;

            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        findViews();
        bindListener();

        /**
         * 页面跳转
         */
        if (Constant.USER_STATUS.getAddress()!=null){
            tvAddress.setText(Constant.USER_STATUS.getAddress());
        }
        if (Constant.USER_STATUS.getBirth()!=null){
            tvDate.setText(Constant.USER_STATUS.getBirth()+"");
        }
        if (Constant.USER_STATUS.getUserName()!=null){
            etUsername.setText(Constant.USER_STATUS.getUserName());
        }
        if (Constant.USER_STATUS.getSex() !=null) {
            if (Constant.USER_STATUS.getSex().equals("男")) {
                btnMan.setChecked(true);
            } else if (Constant.USER_STATUS.getSex().equals("女")) {
                btnWoman.setChecked(true);
            }
        }

    }
    private void findViews(){
        llDate = findViewById(R.id.ll_date);
        llAddress = findViewById(R.id.ll_address);
        dateDisplay = findViewById(R.id.tv_date);
        tvAddress = findViewById(R.id.tv_address);
        etUsername = findViewById(R.id.et_username);
        tvAddress = findViewById(R.id.tv_address);
        tvDate = findViewById(R.id.tv_date);
        ivAvatar = findViewById(R.id.iv_avatar);
        ivLeft = findViewById(R.id.iv_left);
        btnSave = findViewById(R.id.btn_save);
        btnMan = findViewById(R.id.btn_man);
        btnWoman = findViewById(R.id.btn_woman);
    }

    private void bindListener() {
        MyListener myListener = new MyListener();
        llDate.setOnClickListener(myListener);
        llAddress.setOnClickListener(myListener);
        btnSave.setOnClickListener(myListener);
        ivLeft.setOnClickListener(myListener);

    }

    private class MyListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.ll_date:
                    showDatePickerDialog(EditActivity.this,  3, dateDisplay, calendar);
                    break;
                case R.id.ll_address:
                    selectAddress();
                    break;
                case R.id.btn_save:
                    edit();
                    break;
                case R.id.iv_left:
                    finish();
                    break;
            }
        }
    }


    /**
     * 日期选择
     * @param activity
     * @param themeResId
     * @param tv
     * @param calendar
     */
    public static void showDatePickerDialog(Activity activity, int themeResId, final TextView tv, Calendar calendar) {
        // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
        new DatePickerDialog(activity, themeResId, new DatePickerDialog.OnDateSetListener() {
            // 绑定监听器(How the parent is notified that the date is set.)
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // 此处得到选择的时间，可以进行你想要的操作
                tv.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth );
            }
        }
                // 设置初始日期
                , calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH)
                , calendar.get(Calendar.DAY_OF_MONTH)).show();
    }


    /**
     * 地址选择
     */
    private void selectAddress() {
        CityPicker cityPicker = new CityPicker.Builder(EditActivity.this)
                .textSize(14)
                .title("地址选择")
                .titleBackgroundColor("#FFFFFF")
                .titleTextColor("#696969")
                .confirTextColor("#696969")
                .cancelTextColor("#696969")
                .province("河北省")
                .city("石家庄市")
                .district("裕华区")
                .textColor(Color.parseColor("#000000"))
                .provinceCyclic(true)
                .cityCyclic(false)
                .districtCyclic(false)
                .visibleItemsCount(7)
                .itemPadding(10)
                .onlyShowProvinceAndCity(false)
                .build();
        cityPicker.show();
        //监听方法，获取选择结果
        cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
            @Override
            public void onSelected(String... strings) {
                //省份
                String province = strings[0];
                //城市
                String city = strings[1];
                //区县（如果设定了两级联动，那么该项返回空）
                String district = strings[2];
                //为TextView赋值
                tvAddress.setText(province.trim() + "-" + city.trim() + "-" + district.trim());
            }

            @Override
            public void onCancel() {

            }
        });
    }

    /**
     * 向服务器端发送修改的内容
     */
    private void edit() {
        String sex = new String();
        if (btnWoman.isChecked()){
            sex = "女";
        }else if(btnMan.isChecked()){
            sex = "男";
        }
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("username", etUsername.getText().toString())
                .add("sex", sex)
                .add("address",tvAddress.getText().toString())
                .add("birth",tvDate.getText().toString())
                .add("userId",Constant.USER_STATUS.getUserId()+"")
                .build();
        Constant.USER_STATUS.setAddress(tvAddress.getText().toString());
        Constant.USER_STATUS.setBirth(tvDate.getText().toString());
        Constant.USER_STATUS.setSex(sex);
        Constant.USER_STATUS.setUserName(etUsername.getText().toString());
        Request request = new Request.Builder()
                .post(body)
                .url(Constant.BASE_URL + "center/edit")//设置网络请求的地址
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                rsp = response.body().string();
                Log.e("rsp", rsp);
                Message msg = new Message();
                msg.what = 1;
                mainHandle.sendMessage(msg);

            }
        });

    }
}
