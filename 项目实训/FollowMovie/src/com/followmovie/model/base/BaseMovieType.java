package com.followmovie.model.base;

import com.jfinal.plugin.activerecord.IBean;
import com.jfinal.plugin.activerecord.Model;

@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseMovieType <M extends BaseMovieType<M>> extends Model<M> implements IBean {
	public M setMovieTypeId(java.lang.Integer movieTypeId) {
		set("movieTypeId", movieTypeId);
		return (M)this;
	}
	
	public java.lang.Integer getMovieTypeId() {
		return getInt("movieTypeId");
	}
	public M setName(java.lang.String name) {
		set("name", name);
		return (M)this;
	}
	
	public java.lang.String getName() {
		return getStr("name");
	}
}
