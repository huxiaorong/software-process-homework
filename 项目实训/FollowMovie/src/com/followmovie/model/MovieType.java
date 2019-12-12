package com.followmovie.model;

import com.jfinal.plugin.activerecord.Model;

public class MovieType extends Model<MovieType>{
	public static final MovieType dao =  new MovieType().dao();
}
