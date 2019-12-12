package com.followmovie.model;

import com.jfinal.plugin.activerecord.Model;

public class MovieTheme extends Model<MovieTheme> {
	public static final MovieTheme dao = new MovieTheme().dao();
}
