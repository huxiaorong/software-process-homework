package com.followmovie.model;

import com.jfinal.plugin.activerecord.Model;

public class SearchHistory extends Model<SearchHistory>{
	public static final SearchHistory dao = new SearchHistory().dao();
}
