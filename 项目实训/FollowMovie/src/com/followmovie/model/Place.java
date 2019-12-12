package com.followmovie.model;

import com.jfinal.plugin.activerecord.Model;

public class Place extends Model<Place> {
	public static final Place dao = new Place().dao();
}
