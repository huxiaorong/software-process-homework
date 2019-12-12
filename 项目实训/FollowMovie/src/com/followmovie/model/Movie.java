package com.followmovie.model;

import com.jfinal.plugin.activerecord.Model;

public class Movie extends Model<Movie>{
	public static final Movie dao = new Movie().dao();
}
