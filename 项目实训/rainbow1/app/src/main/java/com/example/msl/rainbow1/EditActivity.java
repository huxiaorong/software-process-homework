package com.example.msl.rainbow1;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lljjcoder.citypickerview.widget.CityPicker;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class EditActivity extends AppCompatActivity {
    private LinearLayout llDate;
    private TextView dateDisplay;
    private LinearLayout llAddress;
    private TextView tvAddress;
    private EditText etUsername;
    private TextView tvDate;
    private Calendar calendar = Calendar.getInstance(Locale.CHINA);
    private ImageView ivAvatar;
    private String rsp1;
    private String rsp2;
    private ImageView ivLeft;
    private Button btnSave;
    private RadioButton btnMan;
    private RadioButton btnWoman;
    private LinearLayout llChange;
    private String imgPath;


    private View popBtn;

    private PopupWindow popupWindow;

    private Button pop_img;

    private Button pop_file;

    private Button pop_cancle;

    //相册请求码
    private static final int ALBUM_REQUEST_CODE = 1;
    //相机请求码
    private static final int CAMERA_REQUEST_CODE = 2;
    private SharedPreferences sharedPreferences;



    private Handler mainHandle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (rsp1.equals("修改成功")) {
                        Toast.makeText(EditActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 2:
                    Constant.USER_STATUS.setHeadPicture(rsp2);
                    if(sharedPreferences.getString("user","")!=null){
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
                        editor.putString("user",gson.toJson(Constant.USER_STATUS));
                        editor.commit();
                    }
                    break;

            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        sharedPreferences = getSharedPreferences("loginInfo",MODE_PRIVATE);

        popBtn = getLayoutInflater().inflate(R.layout.pop_btn, null);
        popupWindow = new PopupWindow(popBtn, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        pop_img = (Button) popBtn.findViewById(R.id.pop_img);
        pop_file = (Button) popBtn.findViewById(R.id.pop_file);
        pop_cancle = (Button) popBtn.findViewById(R.id.pop_cancel);



        findViews();
        bindListener();


        /**
         * 页面跳转
         */
        if (Constant.USER_STATUS.getAddress() != null) {
            tvAddress.setText(Constant.USER_STATUS.getAddress());
        }
        if (Constant.USER_STATUS.getBirth() != null) {
            tvDate.setText(Constant.USER_STATUS.getBirth() + "");
        }
        if (Constant.USER_STATUS.getUserName() != null) {
            etUsername.setText(Constant.USER_STATUS.getUserName());
        }
        if (Constant.USER_STATUS.getSex() != null) {
            if (Constant.USER_STATUS.getSex().equals("男")) {
                btnMan.setChecked(true);
            } else if (Constant.USER_STATUS.getSex().equals("女")) {
                btnWoman.setChecked(true);
            }
        }
        if (Constant.USER_STATUS.getHeadPicture() != null) {
            RequestOptions options = new RequestOptions().circleCrop();
            Glide.with(EditActivity.this)
                    .load(Constant.BASE_URL + "headPicture/" + Constant.USER_STATUS.getHeadPicture())
                    .apply(options)
                    .into(ivAvatar);
        }

    }

    private void findViews() {
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
        llChange = findViewById(R.id.ll_change);
    }

    private void bindListener() {
        MyListener myListener = new MyListener();
        llDate.setOnClickListener(myListener);
        llAddress.setOnClickListener(myListener);
        btnSave.setOnClickListener(myListener);
        ivLeft.setOnClickListener(myListener);
        llChange.setOnClickListener(myListener);

    }

    private class MyListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ll_date:
                    showDatePickerDialog(EditActivity.this, 3, dateDisplay, calendar);
                    break;
                case R.id.ll_address:
                    selectAddress();
                    break;
                case R.id.btn_save:
                    upload();
                    edit();
                    break;
                case R.id.iv_left:
                    finish();
                    break;
                case R.id.ll_change:
                    //第一个参数是要将PopupWindow放到的View，第二个参数是位置，第三第四是偏移值
                    popupWindow.setOutsideTouchable(true);
                    popupWindow.showAtLocation(popBtn, Gravity.BOTTOM, 0, 0);


                    pop_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //关闭popupWindow
                            popupWindow.dismiss();
                            getPicFromCamera();//拍照
                        }
                    });
                    pop_file.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popupWindow.dismiss();
                            ActivityCompat.requestPermissions(EditActivity.this, new String[]{
                                    Manifest.permission.READ_EXTERNAL_STORAGE}, 100);

                        }
                    });
                    pop_cancle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popupWindow.dismiss();
                        }
                    });

                    break;
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) { //调用相机后返回
            Bitmap photo = (Bitmap) intent.getExtras().get("data");
            imgPath = "/storage/emulated/0/DCIM/Camera/"+new SimpleDateFormat("yyyyMMddkkmmss").format(new Date())+".jpg";
            File file = new File(imgPath);
            if (!file.exists()){
                try {
                    file.createNewFile();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            BufferedOutputStream bos = null;
            try {
                bos = new BufferedOutputStream(new FileOutputStream(file));
                photo.compress(Bitmap.CompressFormat.JPEG, 80, bos);
                bos.flush();
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


            Log.e("path",imgPath);
            ivAvatar.setImageBitmap(photo);
        }
        if (requestCode == ALBUM_REQUEST_CODE && resultCode == RESULT_OK) {  //调用相册后返回
            Uri uri = intent.getData();
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor.moveToFirst())

            {
                imgPath = cursor.getString(cursor.getColumnIndex("_data"));
                Log.e("path",imgPath);
                RequestOptions requestOptions = new RequestOptions().circleCrop();
                Glide.with(this).load(imgPath).apply(requestOptions).into(ivAvatar);

            }
        }
    }

    /**
     * 从相机获取图片
     */
    private void getPicFromCamera() {
        Intent cameraIntent = new Intent();
        cameraIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
    }


    //用户允许权限
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, ALBUM_REQUEST_CODE);
        }
    }


    /**
     * 上传头像
     */
    private void upload() {
        OkHttpClient okHttpClient = new OkHttpClient();
        //上传头像到服务器端
        File file = new File(imgPath);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", "1", RequestBody.create(MediaType.parse("image/jpg"), file))
                .build();

        Request request = new Request.Builder().url(Constant.BASE_URL + "center/upload?userId=" + Constant.USER_STATUS.getUserId()).post(requestBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                rsp2 = response.body().string();
                Message msg = new Message();
                msg.what = 2;
                mainHandle.sendMessage(msg);
            }
        });
    }


    /**
     * 日期选择
     *
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
                tv.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
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
        if (btnWoman.isChecked()) {
            sex = "女";
        } else if (btnMan.isChecked()) {
            sex = "男";
        }
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("username", etUsername.getText().toString())
                .add("sex", sex)
                .add("address", tvAddress.getText().toString())
                .add("birth", tvDate.getText().toString())
                .add("userId", Constant.USER_STATUS.getUserId() + "")
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
                rsp1 = response.body().string();
                Log.e("rsp", rsp1);
                Message msg = new Message();
                msg.what = 1;
                mainHandle.sendMessage(msg);

            }
        });

    }
}
