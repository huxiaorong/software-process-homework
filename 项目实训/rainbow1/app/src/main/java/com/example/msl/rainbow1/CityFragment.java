package com.example.msl.rainbow1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CityFragment extends Fragment {
    private LinearLayout llOneTheme;
    private LinearLayout llTwoTheme;
    private LinearLayout llThreeTheme;
    private LinearLayout llFourTheme;
    private ImageView ivSearch;
    private LinearLayout llHead;
    private ImageView imgHead;
    private TextView tvHeadTitle;
    private TextView tvHeadDescription;
    private LinearLayout llFirstCity;
    private ImageView imgFirstCity;
    private TextView tvFirstCityName;
    private TextView tvFirstCityCountry;
    private LinearLayout llSecondCity;
    private ImageView imgSecondCity;
    private TextView tvSecondCityName;
    private TextView tvSecondCityCountry;
    private LinearLayout llThirdCity;
    private ImageView imgThirdCity;
    private TextView tvThirdCityName;
    private TextView tvThirdCityCountry;
    private LinearLayout llForthCity;
    private ImageView imgForthCity;
    private TextView tvForthCityName;
    private TextView tvForthCityCountry;
    private LinearLayout llMoreCity;
    private LinearLayout llBigPlace;
    private ImageView imgBigPlace;
    private TextView tvBigPlaceName;
    private TextView tvBigPlaceAddress;
    private TextView tvBigPlaceFilmed;
    private LinearLayout llFirstPlace;
    private LinearLayout llSecondPlace;
    private LinearLayout llThirdPlace;
    private LinearLayout llForthPlace;
    private ImageView imgFirstPlace;
    private ImageView imgSecondPlace;
    private ImageView imgThirdPlace;
    private ImageView imgForthPlace;
    private TextView tvFirstPlace;
    private TextView tvSecondPlace;
    private TextView tvThirdPlace;
    private TextView tvForthPlace;
    private LinearLayout llMoreSpots;
    private ImageView imgFirstTheme;
    private TextView tvFirstTheme;
    private LinearLayout llOneBig;
    private ImageView imgThemeFirstBig;
    private TextView tvThemeFirstBigName;
    private TextView tvThemeFirstBigAddress;
    private TextView tvThemeFirstBigDescription;
    private LinearLayout llOneSmall;
    private LinearLayout llTwoSmall;
    private LinearLayout llThreeSmall;
    private LinearLayout llFourSmall;
    private ImageView imgOneSmall;
    private ImageView imgTwoSmall;
    private ImageView imgThreeSmall;
    private ImageView imgFourSmall;
    private LinearLayout llMoreSicily;
    private TextView tvOneThemeName;
    private ImageView imgOneTheme;
    private TextView tvOneThemeBrief;
    private TextView tvTwoThemeName;
    private ImageView imgTwoTheme;
    private TextView tvTwoThemeBrief;
    private ImageView imgThreeTheme;
    private TextView tvThreeThemeBrief;
    private TextView tvThreeThemeName;
    private TextView tvFourThemeName;
    private ImageView imgFourTheme;
    private TextView tvFourThemeBrief;
    private OkHttpClient okHttpClient;
    private RequestOptions options;
    private Gson gson;
    private List<PlaceTheme> placeThemes;
    private List<City> cities;
    private List<Place> places;
    private int curPlaceThemeId;
    private int curPlaceId;
    private int curCityId;
    private Intent intent;
    private List<Place> themePlaces;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_city, container, false);
        findView(view);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        okHttpClient = new OkHttpClient();
        init();
    }

    private void findView(View view) {
        llOneTheme = view.findViewById(R.id.ll_one_theme);
        llTwoTheme = view.findViewById(R.id.ll_two_theme);
        llThreeTheme = view.findViewById(R.id.ll_three_theme);
        llFourTheme = view.findViewById(R.id.ll_four_theme);
        llHead = view.findViewById(R.id.ll_head);
        ivSearch = view.findViewById(R.id.iv_search);
        imgHead = view.findViewById(R.id.img_head);
        tvHeadTitle = view.findViewById(R.id.tv_head_title);
        tvHeadDescription = view.findViewById(R.id.tv_head_description);
        llFirstCity = view.findViewById(R.id.ll_first_city);
        llSecondCity = view.findViewById(R.id.ll_second_city);
        llThirdCity = view.findViewById(R.id.ll_third_city);
        llForthCity = view.findViewById(R.id.ll_forth_city);
        imgFirstCity = view.findViewById(R.id.img_first_city);
        imgSecondCity = view.findViewById(R.id.img_second_city);
        imgThirdCity = view.findViewById(R.id.img_third_city);
        imgForthCity = view.findViewById(R.id.img_forth_city);
        tvFirstCityName = view.findViewById(R.id.tv_first_city_name);
        tvSecondCityName = view.findViewById(R.id.tv_second_city_name);
        tvThirdCityName = view.findViewById(R.id.tv_third_city_name);
        tvForthCityName = view.findViewById(R.id.tv_forth_city_name);
        tvFirstCityCountry = view.findViewById(R.id.tv_first_city_country);
        tvSecondCityCountry = view.findViewById(R.id.tv_second_city_country);
        tvThirdCityCountry = view.findViewById(R.id.tv_third_city_country);
        tvForthCityCountry = view.findViewById(R.id.tv_forth_city_country);
        llMoreCity = view.findViewById(R.id.ll_more_city);
        llBigPlace = view.findViewById(R.id.ll_big_place);
        imgBigPlace = view.findViewById(R.id.img_big_place);
        tvBigPlaceName = view.findViewById(R.id.tv_big_place_name);
        tvBigPlaceAddress = view.findViewById(R.id.tv_big_place_address);
        tvBigPlaceFilmed = view.findViewById(R.id.tv_big_place_filmed);
        llFirstPlace = view.findViewById(R.id.ll_first_place);
        llSecondPlace = view.findViewById(R.id.ll_second_place);
        llThirdPlace = view.findViewById(R.id.ll_third_place);
        llForthPlace = view.findViewById(R.id.ll_forth_place);
        imgFirstPlace = view.findViewById(R.id.img_first_place);
        imgSecondPlace = view.findViewById(R.id.img_second_place);
        imgThirdPlace = view.findViewById(R.id.img_third_place);
        imgForthPlace = view.findViewById(R.id.img_forth_place);
        tvFirstPlace = view.findViewById(R.id.tv_first_place);
        tvSecondPlace = view.findViewById(R.id.tv_second_place);
        tvThirdPlace = view.findViewById(R.id.tv_third_place);
        tvForthPlace = view.findViewById(R.id.tv_forth_place);
        llMoreSpots = view.findViewById(R.id.ll_more_spots);
        imgFirstTheme = view.findViewById(R.id.img_first_theme);
        tvFirstTheme = view.findViewById(R.id.tv_first_theme);
        llOneBig = view.findViewById(R.id.ll_one_big);
        imgThemeFirstBig = view.findViewById(R.id.img_theme_first_big);
        tvThemeFirstBigName = view.findViewById(R.id.tv_theme_first_big_name);
        tvThemeFirstBigAddress = view.findViewById(R.id.tv_theme_first_big_address);
        tvThemeFirstBigDescription = view.findViewById(R.id.tv_theme_first_big_description);
        llOneSmall = view.findViewById(R.id.ll_one_small);
        llTwoSmall = view.findViewById(R.id.ll_two_small);
        llThreeSmall = view.findViewById(R.id.ll_three_small);
        llFourSmall = view.findViewById(R.id.ll_four_small);
        imgOneSmall = view.findViewById(R.id.img_one_small);
        imgTwoSmall = view.findViewById(R.id.img_two_small);
        imgThreeSmall = view.findViewById(R.id.img_three_small);
        imgFourSmall = view.findViewById(R.id.img_four_small);
        llMoreSicily = view.findViewById(R.id.ll_more_sicily);
        tvOneThemeName = view.findViewById(R.id.tv_one_theme_name);
        imgOneTheme = view.findViewById(R.id.img_one_theme);
        tvOneThemeBrief = view.findViewById(R.id.tv_one_theme_brief);
        tvTwoThemeName = view.findViewById(R.id.tv_two_theme_name);
        imgTwoTheme = view.findViewById(R.id.img_two_theme);
        tvTwoThemeBrief = view.findViewById(R.id.tv_two_theme_brief);
        tvThreeThemeName = view.findViewById(R.id.tv_three_theme_name);
        imgThreeTheme = view.findViewById(R.id.img_three_theme);
        tvThreeThemeBrief = view.findViewById(R.id.tv_three_theme_brief);
        tvFourThemeName = view.findViewById(R.id.tv_four_theme_name);
        imgFourTheme = view.findViewById(R.id.img_four_theme);
        tvFourThemeBrief = view.findViewById(R.id.tv_four_theme_brief);
    }

    /*
    点击事件
     */

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //搜索
        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });
        //顶部主题
        llHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity(), PlaceThemeActivity.class);
                intent.putExtra("themeDetail", gson.toJson(placeThemes.get(0)));
                curPlaceThemeId = placeThemes.get(0).getPlaceThemeId();
                getAsync4CityThemePlaces();
            }
        });
        //热门城市
        //1
        llFirstCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity(),CityDetailsActivity.class);
                intent.putExtra("cityInfo",gson.toJson(cities.get(0)));
                curCityId  = cities.get(0).getCityId();
                getAsync4HotCityPlaces();
            }
        });
        //2
        llSecondCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity(),CityDetailsActivity.class);
                intent.putExtra("cityInfo",gson.toJson(cities.get(1)));
                curCityId  = cities.get(1).getCityId();
                getAsync4HotCityPlaces();
            }
        });
        //3
        llThirdCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity(),CityDetailsActivity.class);
                intent.putExtra("cityInfo",gson.toJson(cities.get(2)));
                curCityId  = cities.get(2).getCityId();
                getAsync4HotCityPlaces();
            }
        });
        //4
        llForthCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity(),CityDetailsActivity.class);
                intent.putExtra("cityInfo",gson.toJson(cities.get(3)));
                curCityId  = cities.get(3).getCityId();
                getAsync4HotCityPlaces();
            }
        });
        //更多
        llMoreCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity(),HotCityActivity.class);
                intent.putExtra("hotCities",gson.toJson(cities));
                startActivity(intent);
            }
        });
        //热门景点
        //大图
        llBigPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到地点详情页
                intent = new Intent(getActivity(),PlaceDetailsActivity.class);
                intent.putExtra("place",gson.toJson(places.get(0)));
                startActivity(intent);
            }
        });
        //1
        llFirstPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到地点详情页
                intent = new Intent(getActivity(),PlaceDetailsActivity.class);
                intent.putExtra("place",gson.toJson(places.get(1)));
                startActivity(intent);
            }
        });
        //2
        llSecondPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到地点详情页
                intent = new Intent(getActivity(),PlaceDetailsActivity.class);
                intent.putExtra("place",gson.toJson(places.get(2)));
                startActivity(intent);
            }
        });
        //3
        llThirdPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到地点详情页
                intent = new Intent(getActivity(),PlaceDetailsActivity.class);
                intent.putExtra("place",gson.toJson(places.get(3)));
                startActivity(intent);
            }
        });
        //4
        llForthPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到地点详情页
                intent = new Intent(getActivity(),PlaceDetailsActivity.class);
                intent.putExtra("place",gson.toJson(places.get(4)));
                startActivity(intent);
            }
        });

        //更多
        llMoreSpots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity(),HotSpotsActivity.class);
                intent.putExtra("hotPlaces",gson.toJson(places));
                startActivity(intent);
            }
        });
        //第一个主题
        //大图
        llOneBig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到地点详情页
                intent = new Intent(getActivity(),PlaceDetailsActivity.class);
                intent.putExtra("place",gson.toJson(themePlaces.get(0)));
                startActivity(intent);
            }
        });
        //1
        llOneSmall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到地点详情页
                intent = new Intent(getActivity(),PlaceDetailsActivity.class);
                intent.putExtra("place",gson.toJson(themePlaces.get(1)));
                startActivity(intent);
            }
        });
        //2
        llTwoSmall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到地点详情页
                intent = new Intent(getActivity(),PlaceDetailsActivity.class);
                intent.putExtra("place",gson.toJson(themePlaces.get(2)));
                startActivity(intent);
            }
        });
        //3
        llThreeSmall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到地点详情页
                intent = new Intent(getActivity(),PlaceDetailsActivity.class);
                intent.putExtra("place",gson.toJson(themePlaces.get(3)));
                startActivity(intent);
            }
        });
        //4
        llFourSmall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到地点详情页
                intent = new Intent(getActivity(),PlaceDetailsActivity.class);
                intent.putExtra("place",gson.toJson(themePlaces.get(4)));
                startActivity(intent);
            }
        });
        //更多
        llMoreSicily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity(),PlaceThemeActivity.class);
                intent.putExtra("themeDetail",gson.toJson(placeThemes.get(0)));
                curPlaceThemeId = placeThemes.get(0).getPlaceThemeId();
                getAsync4CityThemePlaces();
            }
        });
        //主题列表
        //1
        llOneTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity(), PlaceThemeActivity.class);
                intent.putExtra("themeDetail", gson.toJson(placeThemes.get(1)));
                curPlaceThemeId = placeThemes.get(1).getPlaceThemeId();
                getAsync4CityThemePlaces();
            }
        });
        //2
        llTwoTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity(),PlaceThemeActivity.class);
                intent.putExtra("themeDetail",gson.toJson(placeThemes.get(2)));
                curPlaceThemeId = placeThemes.get(2).getPlaceThemeId();
                getAsync4CityThemePlaces();
            }
        });
        //3
        llThreeTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity(),PlaceThemeActivity.class);
                intent.putExtra("themeDetail",gson.toJson(placeThemes.get(3)));
                curPlaceThemeId = placeThemes.get(3).getPlaceThemeId();
                getAsync4CityThemePlaces();
            }
        });
        //4
        llFourTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity(),PlaceThemeActivity.class);
                intent.putExtra("themeDetail",gson.toJson(placeThemes.get(4)));
                curPlaceThemeId = placeThemes.get(4).getPlaceThemeId();
                getAsync4CityThemePlaces();
            }
        });

    }



    private void init() {
        Log.e("init", "start");

        placeThemes = new ArrayList<>();
        cities = new ArrayList<>();
        places = new ArrayList<>();

        options = new RequestOptions()
                .placeholder(R.drawable.loading)//请求过程中显示
                .error(R.drawable.error)//请求失败显示
                .fallback(R.drawable.defaultimg);//请求的URL为null时显示
        gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        //查询城市主题
        getAsync4CityTheme();
        //查询热门城市
        getAsync4HotCity();
        //查询热门景点
        getAsync4HotPlace();

    }

    /*
    请求城市主题列表
     */
    private void getAsync4CityTheme() {
        Log.e("request", "start");
        //2.创建Request对象
        Request request = new Request.Builder()
                .url(Constant.BASE_URL + "placeTheme/searchPlaceTheme")//设置网络请求的URL地址
                .build();
        //3.创建Call对象
        Call call = okHttpClient.newCall(request);
        //4.发送请求
        //异步请求，不需要创建线程
        call.enqueue(new Callback() {
            @Override
            //请求失败时回调
            public void onFailure(Call call, IOException e) {
                Log.e("failure", "error");
                Log.e("error", e.getMessage());
                e.printStackTrace();//打印异常信息
            }

            @Override
            //请求成功之后回调
            public void onResponse(Call call, Response response) throws IOException {

                String reStr = response.body().string();
                placeThemes = gson.fromJson(reStr, new TypeToken<List<PlaceTheme>>() {
                }.getType());

                Log.e("异步GET请求结果", reStr);
                Log.e("异步GET请求结果", placeThemes.toString());

                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        //顶部主题和底部首个主题数据初始化
                        if (placeThemes.size() >= 1) {
                            Glide.with(getActivity())
                                    .load(Constant.BASE_IP + "placeTheme/" + placeThemes.get(0).getImg())
                                    .apply(options)
                                    .into(imgHead);
                            tvHeadTitle.setText(placeThemes.get(0).getPlaceThemeName());
                            tvHeadDescription.setText(placeThemes.get(0).getBrief());


                            Glide.with(getActivity())
                                    .load(Constant.BASE_IP + "placeTheme/" + placeThemes.get(0).getImg())
                                    .apply(options)
                                    .into(imgFirstTheme);
                            tvFirstTheme.setText(placeThemes.get(0).getBrief());

                        }

                        //底层主题列表数据初始化
                        if (placeThemes.size() > 1) {
                            Glide.with(getActivity())
                                    .load(Constant.BASE_IP + "placeTheme/" + placeThemes.get(1).getImg())
                                    .apply(options)
                                    .into(imgOneTheme);
                            tvOneThemeName.setText(placeThemes.get(1).getPlaceThemeName());
                            tvOneThemeBrief.setText(placeThemes.get(1).getBrief());
                        }
                        if (placeThemes.size() > 2) {
                            Glide.with(getActivity())
                                    .load(Constant.BASE_IP + "placeTheme/" + placeThemes.get(2).getImg())
                                    .apply(options)
                                    .into(imgTwoTheme);
                            tvTwoThemeName.setText(placeThemes.get(2).getPlaceThemeName());
                            tvTwoThemeBrief.setText(placeThemes.get(2).getBrief());
                        }
                        if (placeThemes.size() > 3) {
                            Glide.with(getActivity())
                                    .load(Constant.BASE_IP + "placeTheme/" + placeThemes.get(3).getImg())
                                    .apply(options)
                                    .into(imgThreeTheme);
                            tvThreeThemeName.setText(placeThemes.get(3).getPlaceThemeName());
                            tvThreeThemeBrief.setText(placeThemes.get(3).getBrief());
                        }
                        if (placeThemes.size() > 4) {
                            Glide.with(getActivity())
                                    .load(Constant.BASE_IP + "placeTheme/" + placeThemes.get(4).getImg())
                                    .apply(options)
                                    .into(imgFourTheme);
                            tvFourThemeName.setText(placeThemes.get(4).getPlaceThemeName());
                            tvFourThemeBrief.setText(placeThemes.get(4).getBrief());
                        }
                    }
                });
                getAsync4CityThemeFirstPlaces();
            }
        });
    }

    /*
    请求首个城市主题相关地点列表
     */
    private void getAsync4CityThemeFirstPlaces() {
        Log.e("request", "start");
        //2.创建Request对象
        Request request = new Request.Builder()
                .url(Constant.BASE_URL + "placeTheme/searchPlaceThemeById?placeThemeId=1")//设置网络请求的URL地址
                .build();
        //3.创建Call对象
        Call call = okHttpClient.newCall(request);
        //4.发送请求
        //异步请求，不需要创建线程
        call.enqueue(new Callback() {
            @Override
            //请求失败时回调
            public void onFailure(Call call, IOException e) {
                Log.e("failure", "error");
                e.printStackTrace();//打印异常信息
            }

            @Override
            //请求成功之后回调
            public void onResponse(Call call, Response response) throws IOException {

                final String reStr = response.body().string();
                Log.e("异步GET请求结果", reStr);
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {

                        themePlaces = gson.fromJson(reStr, new TypeToken<List<Place>>() {
                        }.getType());
                        if (themePlaces.size() > 0) {
                            Glide.with(getActivity())
                                    .load(Constant.BASE_IP + "place/" + themePlaces.get(0).getImg())
                                    .apply(options)
                                    .into(imgThemeFirstBig);
                            tvThemeFirstBigName.setText(themePlaces.get(0).getName());
                            tvThemeFirstBigAddress.setText(themePlaces.get(0).getCountry() + " " + themePlaces.get(0).getCity());
                            tvThemeFirstBigDescription.setText(themePlaces.get(0).getDescription());
                        }
                        if (themePlaces.size() > 1) {
                            Glide.with(getActivity())
                                    .load(Constant.BASE_IP + "place/" + themePlaces.get(1).getImg())
                                    .apply(options)
                                    .into(imgOneSmall);
                        }
                        if (themePlaces.size() > 2) {
                            Glide.with(getActivity())
                                    .load(Constant.BASE_IP + "place/" + themePlaces.get(2).getImg())
                                    .apply(options)
                                    .into(imgOneSmall);
                        }
                        if (themePlaces.size() > 3) {
                            Glide.with(getActivity())
                                    .load(Constant.BASE_IP + "place/" + themePlaces.get(3).getImg())
                                    .apply(options)
                                    .into(imgOneSmall);
                        }
                        if (themePlaces.size() > 4) {
                            Glide.with(getActivity())
                                    .load(Constant.BASE_IP + "place/" + themePlaces.get(4).getImg())
                                    .apply(options)
                                    .into(imgOneSmall);
                        }
                    }
                });
            }
        });
    }

    /*
    请求城市主题相关地点列表
     */
    private void getAsync4CityThemePlaces() {
        Log.e("request", "start");
        //2.创建Request对象
        Request request = new Request.Builder()
                .url(Constant.BASE_URL + "placeTheme/searchPlaceThemeById?placeThemeId=" + curPlaceThemeId)//设置网络请求的URL地址
                .build();
        //3.创建Call对象
        Call call = okHttpClient.newCall(request);
        //4.发送请求
        //异步请求，不需要创建线程
        call.enqueue(new Callback() {
            @Override
            //请求失败时回调
            public void onFailure(Call call, IOException e) {
                Log.e("failure", "error");
                e.printStackTrace();//打印异常信息
            }

            @Override
            //请求成功之后回调
            public void onResponse(Call call, Response response) throws IOException {

                final String reStr = response.body().string();
                Log.e("异步GET请求结果", reStr);
//                curThemePlaces = gson.fromJson(reStr, new TypeToken<List<Place>>() {
//                }.getType());

                intent.putExtra("themePlaces", reStr);
                startActivity(intent);
            }
        });
    }

    /*
    请求Filmed
     */
    private void getAsync4PlaceFilmed() {
        Log.e("request", "start");
        //2.创建Request对象
        Request request = new Request.Builder()
                .url(Constant.BASE_URL + "placeTheme/searchFilmedByPlaceId?placeId=" + curPlaceId)//设置网络请求的URL地址
                .build();
        //3.创建Call对象
        Call call = okHttpClient.newCall(request);
        //4.发送请求
        //异步请求，不需要创建线程
        call.enqueue(new Callback() {
            @Override
            //请求失败时回调
            public void onFailure(Call call, IOException e) {
                Log.e("failure", "error");
                Log.e("failure", e.getMessage());
                e.printStackTrace();//打印异常信息
            }

            @Override
            //请求成功之后回调
            public void onResponse(Call call, Response response) throws IOException {

                final String reStr = response.body().string();
                Log.e("异步GET请求结果", reStr);
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        String filmed = gson.fromJson(reStr, String.class);
                        tvBigPlaceFilmed.setText("FILMED " + filmed);

                    }
                });
            }
        });
    }

    /*
    请求热门城市
     */
    private void getAsync4HotCity() {
        Log.e("request", "start");
        //2.创建Request对象
        Request request = new Request.Builder()
                .url(Constant.BASE_URL + "placeTheme/searchHotCity")//设置网络请求的URL地址
                .build();
        //3.创建Call对象
        Call call = okHttpClient.newCall(request);
        //4.发送请求
        //异步请求，不需要创建线程
        call.enqueue(new Callback() {
            @Override
            //请求失败时回调
            public void onFailure(Call call, IOException e) {
                Log.e("failure", "error");
                e.printStackTrace();//打印异常信息
            }

            @Override
            //请求成功之后回调
            public void onResponse(Call call, Response response) throws IOException {
                String reStr = response.body().string();
                cities = gson.fromJson(reStr, new TypeToken<List<City>>() {
                }.getType());

                Log.e("异步GET请求结果", reStr);

                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        if (cities.size() >= 1) {
                            Glide.with(getActivity())
                                    .load(Constant.BASE_IP + "hotCity/" + cities.get(0).getImg())
                                    .apply(options)
                                    .into(imgFirstCity);
                            tvFirstCityName.setText(cities.get(0).getName());
                            tvFirstCityCountry.setText("." + cities.get(0).getCountry());
                        }
                        if (cities.size() >= 2) {
                            Glide.with(getActivity())
                                    .load(Constant.BASE_IP + "hotCity/" + cities.get(1).getImg())
                                    .apply(options)
                                    .into(imgSecondCity);
                            tvSecondCityName.setText(cities.get(1).getName());
                            tvSecondCityCountry.setText("." + cities.get(1).getCountry());
                        }
                        if (cities.size() >= 3) {
                            Glide.with(getActivity())
                                    .load(Constant.BASE_IP + "hotCity/" + cities.get(2).getImg())
                                    .apply(options)
                                    .into(imgThirdCity);
                            tvThirdCityName.setText(cities.get(2).getName());
                            tvThirdCityCountry.setText("." + cities.get(2).getCountry());
                        }
                        if (cities.size() >= 4) {
                            Glide.with(getActivity())
                                    .load(Constant.BASE_IP + "hotCity/" + cities.get(3).getImg())
                                    .apply(options)
                                    .into(imgForthCity);
                            tvForthCityName.setText(cities.get(3).getName());
                            tvForthCityCountry.setText("." + cities.get(3).getCountry());
                        }
                    }
                });


            }
        });
    }

    /*
        请求热门城市详情
     */
    private void getAsync4HotCityPlaces() {
        Log.e("request", "start");
        //2.创建Request对象
        Request request = new Request.Builder()
                .url(Constant.BASE_URL + "placeTheme/searchPlaceByCityId?cityId=" + curCityId)//设置网络请求的URL地址
                .build();
        //3.创建Call对象
        Call call = okHttpClient.newCall(request);
        //4.发送请求
        //异步请求，不需要创建线程
        call.enqueue(new Callback() {
            @Override
            //请求失败时回调
            public void onFailure(Call call, IOException e) {
                Log.e("failure", "error");
                e.printStackTrace();//打印异常信息
            }

            @Override
            //请求成功之后回调
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new GsonBuilder()
                        .setDateFormat("yyyy-MM-dd HH:mm:ss")
                        .create();
                String reStr = response.body().string();
                intent.putExtra("cityPlaces", reStr);
                startActivity(intent);
//                List<City> cities = gson.fromJson(reStr, new TypeToken<List<City>>(){}.getType());
                Log.e("异步GET请求结果", reStr);
            }
        });
    }

    /*
    请求热门景点
     */
    private void getAsync4HotPlace() {
        Log.e("request", "start");
        //2.创建Request对象
        Request request = new Request.Builder()
                .url(Constant.BASE_URL + "placeTheme/searchHotPlace")//设置网络请求的URL地址
                .build();
        //3.创建Call对象
        Call call = okHttpClient.newCall(request);
        //4.发送请求
        //异步请求，不需要创建线程
        call.enqueue(new Callback() {
            @Override
            //请求失败时回调
            public void onFailure(Call call, IOException e) {
                Log.e("failure", "error");
                e.printStackTrace();//打印异常信息
            }

            @Override
            //请求成功之后回调
            public void onResponse(Call call, Response response) throws IOException {
                String reStr = response.body().string();
                places = gson.fromJson(reStr, new TypeToken<List<Place>>() {
                }.getType());
                Log.e("异步GET请求结果", reStr);

                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        if (places.size() > 0) {
                            Glide.with(getActivity())
                                    .load(Constant.BASE_IP + "place/" + places.get(0).getImg())
                                    .apply(options)
                                    .into(imgBigPlace);
                            tvBigPlaceName.setText(places.get(0).getName());
                            tvBigPlaceAddress.setText(places.get(0).getCountry() + " " + places.get(0).getCity());
                        }
                        if (places.size() > 1) {
                            Glide.with(getActivity())
                                    .load(Constant.BASE_IP + "place/" + places.get(1).getImg())
                                    .apply(options)
                                    .into(imgFirstPlace);
                            tvFirstPlace.setText(places.get(1).getName());
                        }
                        if (places.size() > 2) {
                            Glide.with(getActivity())
                                    .load(Constant.BASE_IP + "place/" + places.get(2).getImg())
                                    .apply(options)
                                    .into(imgSecondPlace);
                            tvSecondPlace.setText(places.get(2).getName());
                        }
                        if (places.size() > 3) {
                            Glide.with(getActivity())
                                    .load(Constant.BASE_IP + "place/" + places.get(3).getImg())
                                    .apply(options)
                                    .into(imgThirdPlace);
                            tvThirdPlace.setText(places.get(3).getName());
                        }
                        if (places.size() > 4) {
                            Glide.with(getActivity())
                                    .load(Constant.BASE_IP + "place/" + places.get(4).getImg())
                                    .apply(options)
                                    .into(imgForthPlace);
                            tvForthPlace.setText(places.get(4).getName());
                        }

                    }
                });
                //查询Filmed
                curPlaceId = places.get(0).getPlaceId();
                Log.e("curPlaceId", "" + curPlaceId);
                getAsync4PlaceFilmed();
            }
        });
    }

    /*
    请求热门景点详情
     */
    private void getAsync4HotPlaceDetail() {
        Log.e("request", "start");
        //2.创建Request对象
        Request request = new Request.Builder()
                .url(Constant.BASE_URL + "search/searchMovie?text=")//设置网络请求的URL地址
                .build();
        //3.创建Call对象
        Call call = okHttpClient.newCall(request);
        //4.发送请求
        //异步请求，不需要创建线程
        call.enqueue(new Callback() {
            @Override
            //请求失败时回调
            public void onFailure(Call call, IOException e) {
                Log.e("failure", "error");
                e.printStackTrace();//打印异常信息
            }

            @Override
            //请求成功之后回调
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new GsonBuilder()
                        .setDateFormat("yyyy-MM-dd HH:mm:ss")
                        .create();
                String reStr = response.body().string();
//                List<Place> places = gson.fromJson(reStr, new TypeToken<List<Place>>(){}.getType());
                Log.e("异步GET请求结果", reStr);
            }
        });
    }
}
