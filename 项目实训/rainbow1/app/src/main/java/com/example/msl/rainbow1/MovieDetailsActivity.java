package com.example.msl.rainbow1;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MovieDetailsActivity extends AppCompatActivity{
    private OkHttpClient okHttpClient;
    private String strMovie;
    private List<Movie> movieList = new ArrayList<>();
    private ImageView imgLike;
    private Intent intent;

    private TextView tvMovieName;
    private TextView tvMovieEnName;
    private ImageView imgMovie;
    private TextView tvMovieCount;
    private TextView tvMovieType;
    private TextView tvMovieCountry;
    private TextView tvReleaseYear;
    private TextView tvMovieDescription;

    private String movieType;
    private long position = 0;//点击listview的位置

    private int movieId;
    private int userId;
    private User user;

    private List<Place> placeList = new ArrayList<>();
    private String strPlace;
    private MovieDetailsAdapter movieDetailsAdapter;
    private ListView lvMovieDetail;

    private String collecteInfo;

    private Handler mainHandle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Calendar c = Calendar.getInstance();
                    c.setTime(movieList.get(0).getReleaseYear());
                    int year = c.get(Calendar.YEAR);
                    tvMovieName.setText(movieList.get(0).getName());
                    tvMovieEnName.setText(movieList.get(0).getEnName());
                    tvMovieCount.setText(movieList.get(0).getScene() + "");
                    tvMovieCountry.setText(movieList.get(0).getCountry());
                    tvReleaseYear.setText(year + "");
                    tvMovieDescription.setText(movieList.get(0).getDescription());
                    tvMovieType.setText(movieType);
                    RequestOptions options = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL);
                    Glide.with(MovieDetailsActivity.this)
                            .load(Constant.BASE_URL + "movies/" + movieList.get(0).getImg())
                            .apply(options)
                            .into(imgMovie);
                    imgMovie.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            try {
                                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(MovieDetailsActivity.this);
                                builder.setTitle("提示");
                                builder.setMessage("是否保存图片");
                                builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        downImg();
                                    }
                                });
                                builder.setNeutralButton("否", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                });
                                builder.show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return true;
                        }
                    });
                    break;
                case 2:
                    Log.e("wxx", placeList.toString());
                    lvMovieDetail = findViewById(R.id.lv_item_place);
                    movieDetailsAdapter = new MovieDetailsAdapter(placeList, R.layout.item_place, MovieDetailsActivity.this);
                    lvMovieDetail.setAdapter(movieDetailsAdapter);
                    setListViewHeightBasedOnChildren(lvMovieDetail);
                    lvMovieDetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            intent = new Intent(MovieDetailsActivity.this, PlaceDetailsActivity.class);
                            Gson gson = new Gson();
                            Place place = new Place();
                            place.setName(placeList.get((int) id).getName());
                            place.setEnName(placeList.get((int) id).getEnName());
                            place.setCity(placeList.get((int) id).getCity());
                            place.setCountry(placeList.get((int) id).getCountry());
                            place.setDescription(placeList.get((int) id).getDescription());
                            place.setImg(placeList.get((int) id).getImg());
                            place.setPlaceId(placeList.get((int) id).getPlaceId());
                            place.setProvince(placeList.get((int) id).getProvince());
                            String strPlace = gson.toJson(place);
                            intent.putExtra("place", strPlace);
                            startActivity(intent);
                        }
                    });
                    break;
                case 3:
                    if(collecteInfo.equals("yes")){
                        imgLike.setImageResource(R.drawable.collected);
                        imgLike.setTag("collected");
                    }else{
                        imgLike.setImageResource(R.drawable.collect);
                        imgLike.setTag("collect");
                    }
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        findView();

        okHttpClient = new OkHttpClient();
        //根据movieId向服务器请求数据
        findMovieById();

        if(Constant.USER_STATUS!=null){
            judgeCollected();
        }else{
            imgLike.setImageResource(R.drawable.collect);
            imgLike.setTag("collect");
        }
    }

    private void findView() {
        tvMovieName = findViewById(R.id.tv_movie_name);
        tvMovieEnName = findViewById(R.id.tv_movie_engname);
        tvMovieCountry = findViewById(R.id.tv_country);
        tvMovieCount = findViewById(R.id.tv_count);
        tvMovieDescription = findViewById(R.id.tv_description);
        tvMovieType = findViewById(R.id.tv_type);
        imgMovie = findViewById(R.id.img_movie);
        tvReleaseYear = findViewById(R.id.tv_release_year);

        imgLike = findViewById(R.id.img_like);
        imgLike.setTag("collect");

    }

    private void findMovieById() {
        movieId = getIntent().getIntExtra("movieId", 0);
        findPlaceByMovieId(movieId);
        //position=getIntent().getLongExtra("position",0);
        movieType = getIntent().getStringExtra("type");

        FormBody body = new FormBody.Builder().add("movieId", String.valueOf(movieId)).build();
        Request request = new Request.Builder()
                .url(Constant.INDEX_URL + "findMovieById")
                .post(body)
                .build();
        final Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                strMovie = response.body().string();
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

                movieList = gson.fromJson(strMovie, new TypeToken<List<Movie>>() {
                }.getType());

                Message msg = new Message();
                msg.what = 1;
                mainHandle.sendMessage(msg);
            }
        });
    }

    public void onClicked(View view) {
        switch (view.getId()) {
            case R.id.img_like:
                if (Constant.USER_STATUS!=null) {
                    collecte();
                } else{
                    Intent intent = new Intent(MovieDetailsActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.img_left:
                MovieDetailsActivity.this.finish();
                break;

        }
    }

    public void findPlaceByMovieId(int movieId) {
        FormBody body = new FormBody.Builder().add("movieId", String.valueOf(movieId)).build();
        Request request = new Request.Builder()
                .url(Constant.INDEX_URL + "findPlaceByMovieId")
                .post(body)
                .build();
        final Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                strPlace = response.body().string();
                Gson gson = new Gson();

                placeList = gson.fromJson(strPlace, new TypeToken<List<Place>>() {
                }.getType());


                Message msg = new Message();
                msg.what = 2;
                mainHandle.sendMessage(msg);
            }
        });
    }

    ;

    //根据当前的ListView的列表项计算列表的尺寸,
    // 解决ScrollView嵌套ListView时，会无法正确的计算ListView的大小
    public void setListViewHeightBasedOnChildren(ListView listView) {
        /**
         * by zsx
         * 获取ListView对应的Adapter
         * 这里的adapter是你为listview所添加的adapter，可以是原始adapter，
         * 也可以是自己定义的adapter，本人这里的articleadpter是自己定义的adapter
         */
        MovieDetailsAdapter listAdapter = (MovieDetailsAdapter) listView.getAdapter();
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
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

    private void downImg() {
        DownloadTask downloadTask = new DownloadTask();
        downloadTask.execute();
    }

    private class DownloadTask extends AsyncTask {

        //UI线程，在doInBackground之前执行
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        //子线程
        @Override
        protected Object doInBackground(Object[] objects) {
            //下载网络图片
            try {
                URL url = new URL(Constant.BASE_URL + "movies/" + movieList.get(0).getImg());

                //1、
                //InputStream is=url.openStream();//得到输入流对象，只能从对方获取数据

                //2、
                URLConnection connection = url.openConnection();  //建立连接，读取和发送都可以
                InputStream is = connection.getInputStream();

                File file = new File("/cache/files" + "/" + movieList.get(0).getImg());

                Log.e("www", getFilesDir().toString());
//                Log.e("sssvvv",Environment.getExternalStorageDirectory().getAbsolutePath());

                Log.e("aaaaa", file.getAbsolutePath());
                FileOutputStream fileOutputStream = new FileOutputStream(file);

                byte[] buffer = new byte[1024];
                int len;
                while ((len = is.read(buffer)) != -1) {
                    fileOutputStream.write(buffer, 0, len);
                    Log.e("ssss", "s");
                }

                fileOutputStream.close();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
        }
    }

    private void collecte() {
        if (imgLike.getTag() == "collect") {
            imgLike.setImageResource(R.drawable.collected);
            imgLike.setTag("collected");
            addMovieCollecte();
        } else {
            imgLike.setImageResource(R.drawable.collect);
            imgLike.setTag("collect");
            cancelMovieCollecte();
        }
    }

    private void addMovieCollecte() {
        FormBody body = new FormBody.Builder().add("movieId", String.valueOf(movieId))
                .add("userId", String.valueOf(Constant.USER_STATUS.getUserId())).build();
        Request request = new Request.Builder()
                .url(Constant.INDEX_URL + "addMovieCollecte")
                .post(body)
                .build();
        final Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

    private void cancelMovieCollecte() {
        FormBody body = new FormBody.Builder().add("movieId", String.valueOf(movieId))
                .add("userId", String.valueOf(Constant.USER_STATUS.getUserId())).build();
        Request request = new Request.Builder()
                .url(Constant.INDEX_URL + "cancelMovieCollecte")
                .post(body)
                .build();
        final Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

    private void judgeCollected() {
        FormBody body = new FormBody.Builder().add("movieId", String.valueOf(movieId))
                .add("userId", String.valueOf(Constant.USER_STATUS.getUserId())).build();
        Request request = new Request.Builder()
                .url(Constant.INDEX_URL + "judgeMovieCollected")
                .post(body)
                .build();
        final Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                collecteInfo=response.body().string();

                Log.e("ccc",collecteInfo);
                Message msg = new Message();
                msg.what = 3;
                mainHandle.sendMessage(msg);
            }
        });
    }

}