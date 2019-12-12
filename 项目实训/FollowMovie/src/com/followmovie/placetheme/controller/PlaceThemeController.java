package com.followmovie.placetheme.controller;

import java.util.ArrayList;
import java.util.List;

import com.followmovie.model.HotCity;
import com.followmovie.model.HotPlace;
import com.followmovie.model.Movie;
import com.followmovie.model.MoviePlaceComparison;
import com.followmovie.model.Place;
import com.followmovie.model.PlaceTheme;
import com.followmovie.model.ThemePlaceComparison;
import com.jfinal.core.Controller;

public class PlaceThemeController extends Controller {
	//查询全部地点主题
	public void searchPlaceTheme(){
		List<PlaceTheme> list = PlaceTheme.dao.find("select * from placetheme");
		if(null!=list){
			renderJson(list);
		}
		
	}
	//根据点击事件的地点主题id查询地点主题详情
	public void searchPlaceThemeById(){
		int placeThemeId = Integer.parseInt(getPara("placeThemeId"));
		List<ThemePlaceComparison> list = ThemePlaceComparison.dao.find("select * from themeplacecomparison where placeThemeId = ?",placeThemeId);
		List<String> idList = new ArrayList<>();
		for(int i=0;i<list.size();i++){
			idList.add(list.get(i).getInt("placeId")+"");
		}
		if(idList.size()!=0){
			String sql = "select * from place where placeId in"+"("+String.join(",", idList)+")";
			List<Place> placeList = Place.dao.find(sql);
			if(null!=placeList){
				renderJson(placeList);
			}
		}
	}
	
	//查询全部地点类型
	public void searchPlaceType(){
		List<Place> list = Place.dao.find("select * from place");
		if(null!=list){
			renderJson(list);
		}
	}
	
	//查询全部热门城市
	public void searchHotCity(){
		List<HotCity> list = HotCity.dao.find("select * from hotcity");
		if(null!=list){
			renderJson(list);
		}
	}
	//查询全部热门景点
	public void searchHotPlace(){
		List<HotPlace> list = HotPlace.dao.find("select * from hotplace");
		List<String> list1 = new ArrayList<>();
		 if(null!=list){
			 for(int i=0;i<list.size();i++){
				 int placeId = list.get(i).getInt("placeId");
				 list1.add(placeId+"");
				 
			 }
			 String sql = "select * from place where placeId in " +"("+String.join(",", list1)+")";
			 List<Place> placeList = Place.dao.find(sql);
			 
			 renderJson(placeList);
		 }
	}
	//根据景点查询拍摄电影
	public void searchFilmByPlace(){
		List<HotPlace> list = HotPlace.dao.find("select * from hotplace");
		List<String> list1 = new ArrayList<>();
		List<String> list2 = new ArrayList<>();
		if(null!=list){
			 for(int i=0;i<list.size();i++){
				 int placeId = list.get(i).getInt("placeId");
				 list1.add(placeId+"");
			 }
			 String sql = "select * from movieplacecomparison where placeId in"+"("+String.join(",", list1)+")";
			 if(null!=list1){List<MoviePlaceComparison> moviePlaceList = MoviePlaceComparison.dao.find(sql);
				 for(int i=0;i<moviePlaceList.size();i++){
					 int movieId = moviePlaceList.get(i).getInt("movieId");
					 list2.add(movieId+"");
				 }
			 }
			 if(null!=list2){
				 String sql1 = "select * from movie where movieId in"+"("+String.join(",", list2)+")";
				 List<Movie> movieList  = Movie.dao.find(sql1);
				 List<String> movieName = new ArrayList<>();
				 for(int i= 0;i<movieList.size();i++){
					 movieName.add(movieList.get(i).getStr("name"));
				 }
				 renderJson(movieName);
			 }
		 }
	}
	//根据城市id查询相关景点
	 public void searchPlaceByCityId(){
		 int cityId = Integer.parseInt(getPara("cityId"));
		 List<HotCity> list = HotCity.dao.find("select * from hotcity where cityId =?",cityId);
		 if(null!=list){
			 String name = list.get(0).getStr("name");
			 List<Place> placeList = Place.dao.find("select * from place where city = ?",name);
			 renderJson(placeList);
		 }
		 
	 }
	//根据景点查询详情
	public void searchPlaceDetails(){
		int hotPlaceId = Integer.parseInt(getPara("hotPlaceId"));
		List<HotPlace> list = HotPlace.dao.find("select * from hotplace where hotPlaceId=?",hotPlaceId);
		if(null!=list){
			int placeId = list.get(0).getInt("placeId");
			List<Place> place = Place.dao.find("select * from place where placeId = ?",placeId);
			renderJson(place);
		}
		
	}
	
	//根据placeId查询filmed
	public void searchFilmedByPlaceId(){
		int placeId = Integer.parseInt(getPara("placeId"));
		System.out.println(placeId);
		List<MoviePlaceComparison> list = MoviePlaceComparison.dao.find("select * from movieplacecomparison where placeId = ?",placeId);
		System.out.println(list.toString());
		String filmedMovie;
		if(null!=list && list.size()!=0){
			int movieId = list.get(0).getInt("movieId");
			System.out.println(movieId);
			List<Movie> movie = Movie.dao.find("select * from movie where movieId = ?",movieId);
			if(null!=movie && movie.size()!=0){
				filmedMovie= movie.get(0).getStr("name");
				System.out.println(filmedMovie);
				renderJson(filmedMovie);
			}
			
		}
	}
	
}
