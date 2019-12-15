package com.followmovie.movietheme.controller;

import java.util.ArrayList;
import java.util.List;

import com.followmovie.model.Movie;
import com.followmovie.model.MoviePlaceComparison;
import com.followmovie.model.MovieTheme;
import com.followmovie.model.MovieType;
import com.followmovie.model.MovieTypeComparison;
import com.followmovie.model.Place;
import com.followmovie.model.ThemeMovieComparison;
import com.jfinal.core.Controller;

public class MovieThemeController extends Controller {
	//查询全部电影主题
	public void searchMovieTheme() {
//		String query = getPara("searchMovieTheme");
		List<MovieTheme> list = MovieTheme.dao.find("select * from movietheme");
		renderJson(list);
	}
	//根据点击事件的电影主题id查询电影主题详情
	public void searchMovieThemeById() {
		int movieThemeId = Integer.parseInt(getPara("movieThemeId"));
		List<ThemeMovieComparison> list =  ThemeMovieComparison.dao.find("select * from thememoviecomparison where movieThemeId=?",movieThemeId);
		List<String> idList = new ArrayList<>();
		for(int i=0;i<list.size();i++){
			idList.add(list.get(i).getInt("movieId")+"");
			
		}
		String sql = "select * from movie where movieId in"+"("+String.join(",", idList)+")";
		List<Movie> movieList = Movie.dao.find(sql);
		renderJson(movieList);
	}
	
	//根据主题id查询type
	public void searchTypeListByMovieThemeId(){
		int movieThemeId = Integer.parseInt(getPara("movieThemeId"));
		List<ThemeMovieComparison> list =  ThemeMovieComparison.dao.find("select * from thememoviecomparison where movieThemeId=?",movieThemeId);
		List<String> idList = new ArrayList<>();
		List<String> typeList = new ArrayList<>();
		for(int i=0;i<list.size();i++){
			idList.add(list.get(i).getInt("movieId")+"");
		}
		String sql = "select * from movie where movieId in"+"("+String.join(",", idList)+")";
		List<Movie> movieList = Movie.dao.find(sql);
		for(int i=0;i<movieList.size();i++){
			int movieId = movieList.get(i).getInt("movieId");
			List<MovieTypeComparison> movieTypeIdlist = MovieTypeComparison.dao.find("select * from movietypecomparison where movieId = ?",movieId);
			List<String> movieTypeIdList2 = new ArrayList<>();
			for(int j = 0;j<movieTypeIdlist.size();j++){
				movieTypeIdList2.add(movieTypeIdlist.get(j).getInt("movieTypeId")+"");
			}
			String sql1 = "select *from movietype where movieTypeId in"+"("+String.join(",", movieTypeIdList2)+")";
			List<MovieType> movieTypeList = MovieType.dao.find(sql1);
			List<String> typeNameList = new ArrayList<>();
			for(int k = 0;k<movieTypeList.size();k++){
				typeNameList.add(movieTypeList.get(k).getStr("name"));
			}
			typeList.add(typeToString(typeNameList));
		}
		renderJson(typeList);
		
	}
	//根据主题id查询city
	public void searchCityListByMovieThemeId(){
		int movieThemeId = Integer.parseInt(getPara("movieThemeId"));
		List<ThemeMovieComparison> list =  ThemeMovieComparison.dao.find("select * from thememoviecomparison where movieThemeId=?",movieThemeId);
		List<String> idList = new ArrayList<>();
		List<String> cityList = new ArrayList<>();
		for(int i=0;i<list.size();i++){
			idList.add(list.get(i).getInt("movieId")+"");
			
		}
		String sql = "select * from movie where movieId in"+"("+String.join(",", idList)+")";
		List<Movie> movieList = Movie.dao.find(sql);
		for(int i=0;i<movieList.size();i++){
			int movieId = movieList.get(i).getInt("movieId");
			List<MoviePlaceComparison> placeIdList = MoviePlaceComparison.dao.find("select * from movieplacecomparison where movieId = ?",movieId);
			List<String> placeId = new ArrayList<>();
			for(int j = 0;j<placeIdList.size();j++){
				placeId.add(placeIdList.get(j).getInt("placeId")+"");
			}
			String sql1 = "select *from place where placeId in"+"("+String.join(",", placeId)+")";
			List<Place> placeList = Place.dao.find(sql1);
			List<String> placeCityList = new ArrayList<>();
			for(int k = 0;k<placeList.size();k++){
				if (!placeCityList.contains(placeList.get(k).getStr("city"))) {
					placeCityList.add(placeList.get(k).getStr("city"));
				}
				
			}
			cityList.add(cityToString(placeCityList));
		}
		renderJson(cityList);
	}
	//字符串数组转化为字符串
	private String typeToString(List<String>types){
		String str = "";
        for (int i = 0;i<types.size()-1;i++){
            str = str+types.get(i)+"/";
        }
        str=str+types.get(types.size()-1);

        return str;
    }
    private String cityToString(List<String> types){
        String str = "";
        for (int i = 0;i<types.size()-1;i++){
            str = str+types.get(i)+"/";
        }
        str=str+types.get(types.size()-1);
        return str;
    }
	//根据电影id查找电影类型
	public void searchTypeByMovieId(){
		int movieId = Integer.parseInt(getPara("movieId"));
		List<MovieTypeComparison> movieTypeIdlist = MovieTypeComparison.dao.find("select * from movietypecomparison where movieId = ?",movieId);
		List<String> movieTypeIdList2 = new ArrayList<>();
		for(int i = 0;i<movieTypeIdlist.size();i++){
			movieTypeIdList2.add(movieTypeIdlist.get(i).getInt("movieTypeId")+"");
		}
		String sql = "select *from movietype where movieTypeId in"+"("+String.join(",", movieTypeIdList2)+")";
		List<MovieType> movieTypeList = MovieType.dao.find(sql);
		List<String> typeNameList = new ArrayList<>();
		for(int i = 0;i<movieTypeList.size();i++){
			typeNameList.add(movieTypeList.get(i).getStr("name"));
		}
		renderJson(typeNameList);
	}
	//根据电影id查找地点城市
	public void searchCityByMovieId(){
		int movieId = Integer.parseInt(getPara("movieId"));
		List<MoviePlaceComparison> placeIdList = MoviePlaceComparison.dao.find("select * from movieplacecomparison where movieId = ?",movieId);
		List<String> placeId = new ArrayList<>();
		for(int i = 0;i<placeIdList.size();i++){
			placeId.add(placeIdList.get(i).getInt("placeId")+"");
		}
		String sql = "select *from place where placeId in"+"("+String.join(",", placeId)+")";
		List<Place> placeList = Place.dao.find(sql);
		List<String> placeCityList = new ArrayList<>();
		for(int i = 0;i<placeList.size();i++){
			placeCityList.add(placeList.get(i).getStr("city"));
		}
		renderJson(placeCityList);
	}
	//查找电影类型
	public void searchMovieType(){
		List<MovieType> list = MovieType.dao.find("select * from movietype");
		renderJson(list);
	}
	//根据点击的电影类型查找相应电影
	public void searchMovieTypeById(){
		int movieTypeId = Integer.parseInt(getPara("movieTypeId"));
		List<MovieType> list = MovieType.dao.find("select * from movietypecomparison where movieTypeId=?",movieTypeId);
		List<String> idList = new ArrayList<>();
		for(int i=0;i<list.size();i++){
			idList.add(list.get(i).getInt("movieId")+"");
			
		}
		String sql = "select *from movie where movieId in"+"("+String.join(",", idList)+")";
		List<Movie> movieList = Movie.dao.find(sql);
		renderJson(movieList);
	}
}
