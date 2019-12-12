package com.followmovie.config;

import com.followmovie.masuli.controller.CenterController;
import com.followmovie.model.HotCity;
import com.followmovie.model.HotPlace;
import com.followmovie.model.Movie;
import com.followmovie.model.MoviePlaceComparison;
import com.followmovie.model.MovieTheme;
import com.followmovie.model.MovieType;
import com.followmovie.model.MovieTypeComparison;
import com.followmovie.model.Place;
import com.followmovie.model.PlaceTheme;
import com.followmovie.model.SearchHistory;
import com.followmovie.model.ThemeMovieComparison;
import com.followmovie.model.ThemePlaceComparison;
import com.followmovie.movietheme.controller.MovieThemeController;
import com.followmovie.placetheme.controller.PlaceThemeController;
import com.followmovie.search.controller.SearchController;
import com.followmovie.weixinxin.controller.IndexController;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.render.ViewType;
import com.jfinal.template.Engine;




public class AppConfig extends JFinalConfig {

	@Override
	public void configConstant(Constants me) {
		me.setDevMode(true);
		me.setViewType(ViewType.JSP);
		me.setError404View("/404.jsp");
		
//		me.setBaseUploadPath(baseUploadPath);
	}

	@Override
	public void onStart() {
		System.out.println("start");
	}

	@Override
	public void onStop() {
		System.out.println("stop");
	}

	@Override
	public void configRoute(Routes me) {
		me.add("search", SearchController.class);
		me.add("placeTheme",PlaceThemeController.class);
		me.add("movieTheme",MovieThemeController.class);
		me.add("/center",CenterController.class);
		me.add("/index", IndexController.class);

	}

	@Override
	public void configEngine(Engine me) {
		// TODO Auto-generated method stub

	}

	@Override
	public void configPlugin(Plugins me) {
		// TODO Auto-generated method stub
		DruidPlugin dp = new DruidPlugin("jdbc:mysql://localhost:3306/followmovie?useUnicode=true&characterEncoding=UTF-8", "root", "");
		me.add(dp);
		ActiveRecordPlugin arp = new ActiveRecordPlugin(dp);
		me.add(arp);
		arp.setDialect(new MysqlDialect());
		arp.addMapping("movie",  Movie.class);
		arp.addMapping("place", Place.class);
		arp.addMapping("searchhistory", SearchHistory.class);
		arp.addMapping("movietheme", MovieTheme.class);
		arp.addMapping("placetheme", PlaceTheme.class);
		arp.addMapping("movieplacecomparison", MoviePlaceComparison.class);
		arp.addMapping("movietype", MovieType.class);
		arp.addMapping("movietypecomparison", MovieTypeComparison.class);
		arp.addMapping("thememoviecomparison", ThemeMovieComparison.class);
		arp.addMapping("themeplacecomparison", ThemePlaceComparison.class);
		arp.addMapping("hotcity", HotCity.class);
		arp.addMapping("hotplace", HotPlace.class);
	}

	@Override
	public void configInterceptor(Interceptors me) {
		// TODO Auto-generated method stub

	}

	@Override
	public void configHandler(Handlers me) {
		// TODO Auto-generated method stub

	}

}
