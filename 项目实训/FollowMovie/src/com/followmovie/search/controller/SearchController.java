package com.followmovie.search.controller;

import java.util.List;


import com.followmovie.model.Movie;
import com.followmovie.model.Place;
import com.followmovie.model.SearchHistory;
import com.jfinal.core.Controller;

public class SearchController extends Controller {
	public void searchMovie(){
		System.out.println("请求查询电影");
		String query = getPara("text");
		System.out.println("获取到查询字段："+query);
		List<Movie> list = Movie.dao.find("select * from movie where name like ?", "%"+query+"%");
		renderJson(list);
		
	}
	public void searchPlace(){
		String query = getPara("text");
		List<Place> list = Place.dao.find("select * from place where name like ? or country like ? or province like ? or city like ?", "%"+query+"%","%"+query+"%","%"+query+"%","%"+query+"%");
		renderJson(list);
	}
	
	//查询历史记录
	public void searchHistory(){
		String userId = getPara("");
		List<SearchHistory> list = SearchHistory.dao.find("select * from searchhistory where userId =?","%"+userId+"%");
		renderJson(list);
	}
	//插入历史纪录
	public void insertSearchHistory(){
		String userId = getPara();
		String query = getPara("text");
		SearchHistory searchHistory = new SearchHistory();
		searchHistory.set("userId", userId);
		searchHistory.set("record", query);
		searchHistory.save();
	}
	
}
