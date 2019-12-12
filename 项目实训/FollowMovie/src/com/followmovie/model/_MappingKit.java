package com.followmovie.model;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;

/**
 * Generated by JFinal, do not modify this file.
 * <pre>
 * Example:
 * public void configPlugin(Plugins me) {
 *     ActiveRecordPlugin arp = new ActiveRecordPlugin(...);
 *     _MappingKit.mapping(arp);
 *     me.add(arp);
 * }
 * </pre>
 */
public class _MappingKit {
	
	public static void mapping(ActiveRecordPlugin arp) {
		arp.addMapping("movie", "movieId", Movie.class);
		arp.addMapping("place", "placeId", Place.class);
		arp.addMapping("searchhistory", "id", SearchHistory.class);
		arp.addMapping("movietheme","movieThemeId",MovieTheme.class);
		arp.addMapping("placetheme", "placeThemeId",PlaceTheme.class);
		arp.addMapping("movieplacecomparison","id", MoviePlaceComparison.class);
		arp.addMapping("movietype", "movieTypeId",MovieType.class);
		arp.addMapping("movietypecomparison", "id",MovieTypeComparison.class);
		arp.addMapping("thememoviecomparison","id", ThemeMovieComparison.class);
		arp.addMapping("themeplacecomparison","id", ThemePlaceComparison.class);
		arp.addMapping("hotcity", "cityId",HotCity.class);
		arp.addMapping("hotplace","hotPlaceId", HotPlace.class);
	}
}

