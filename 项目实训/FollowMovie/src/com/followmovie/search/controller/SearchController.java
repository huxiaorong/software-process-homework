package com.followmovie.search.controller;

import java.util.ArrayList;
import java.util.List;


import com.followmovie.model.Movie;
import com.followmovie.model.MoviePlaceComparison;
import com.followmovie.model.Place;
import com.followmovie.model.SearchHistory;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;

public class SearchController extends Controller {
	public void searchMovie(){
		System.out.println("请求查询电影");
		String query = getPara("text");
		System.out.println("获取到查询字段："+query);
		List<Movie> list1 = Movie.dao.find("select * from movie where name like ?", "%"+query+"%");
		
		List<Place> list = Place.dao.find("select * from place where name like ? or country like ? or province like ? or city like ?", "%"+query+"%","%"+query+"%","%"+query+"%","%"+query+"%");
		List<String> placeIdList = new ArrayList<>();
		for(int i = 0;i<list.size();i++){
			placeIdList.add(list.get(i).getInt("placeId")+"");
		}
		
		String sql = "select * from movieplacecomparison where placeId in"+"("+String.join(",", placeIdList)+")";
		System.out.println(sql);
		if(placeIdList.size()!=0){
			List<MoviePlaceComparison> moviePlaceList = MoviePlaceComparison.dao.find(sql);
			List<String> movieIdList = new ArrayList<>();
			if(null!=moviePlaceList){
				for(int i =0;i<moviePlaceList.size();i++){
					if(!movieIdList.contains(moviePlaceList.get(i).getInt("movieId")+"")){
						movieIdList.add(moviePlaceList.get(i).getInt("movieId")+"");
					}
				}
				String sql1 = "select * from movie where movieId in"+"("+String.join(",", movieIdList)+")";
				List<Movie> movieList = Movie.dao.find(sql1);
				
				list1.addAll(movieList);
				System.out.println(list1);
				renderJson(list1);
			}
			
		}
		renderJson();
		
		
		
	}
	public void searchPlace(){
		String query = getPara("text");
		List<Place> list1 = Place.dao.find("select * from place where name like ? or country like ? or province like ? or city like ?", "%"+query+"%","%"+query+"%","%"+query+"%","%"+query+"%");
		
		List<Movie> list = Movie.dao.find("select * from movie where name like ?", "%"+query+"%");
		List<String> movieIdList = new ArrayList<>();
		for(int i = 0;i<list.size();i++){
			movieIdList.add(list.get(i).getInt("movieId")+"");
		}
		String sql = "select * from movieplacecomparison where movieId in"+"("+String.join(",", movieIdList)+")";
		
		if(movieIdList.size()!=0){
			List<MoviePlaceComparison> moviePlaceList = MoviePlaceComparison.dao.find(sql);
			List<String> placeIdList = new ArrayList<>();
			if(null!=moviePlaceList){
				for(int i =0;i<moviePlaceList.size();i++){
					if(!placeIdList.contains(moviePlaceList.get(i).getInt("placeId")+"")){
						placeIdList.add(moviePlaceList.get(i).getInt("placeId")+"");
					}
				}
				String sql1 = "select * from place where placeId in"+"("+String.join(",", placeIdList)+")";
				List<Place> placeList = Place.dao.find(sql1);
				
				list1.addAll(placeList);
				System.out.println(list1);
				
				renderJson(list1);
			}
		}
		renderJson();
	}
	//插入历史纪录
	public void insertSearchHistory(){
		String query = getPara("query");
		Boolean isSave = false;
		int count;
		List<SearchHistory> list = SearchHistory.dao.find("select * from searchhistory");
		List<String> recordList = new ArrayList<>();
		for(int i=0;i<list.size();i++){
			recordList.add(list.get(i).getStr("record"));
			
		}
		System.out.println(recordList);
		if(recordList.contains(query)){
			for(int i =0;i<list.size();i++){
				if(list.get(i).getStr("record").equals(query)){
					isSave = Db.update("update searchhistory set count=? where record=?",list.get(i).getInt("count")+1,query)>0;
				}
			}
		}else{
			SearchHistory searchHistory = getModel(SearchHistory.class,"searchhistory");
			searchHistory.set("record", query);
			searchHistory.set("count", 1);
			isSave =  searchHistory.save();
			System.out.println(isSave);
		}
		System.out.println(isSave);

		renderJson(isSave);
		
	}
	//搜索最热门的五条搜索记录
	public void searchHotSearch(){
		List<SearchHistory> list = SearchHistory.dao.find("select * from searchhistory order by count desc");
		List<String> recordList = new ArrayList<>();
		for(int i=0;i<5;i++){
			recordList.add(list.get(i).getStr("record"));
		}
		renderJson(recordList);
	}
	
}
