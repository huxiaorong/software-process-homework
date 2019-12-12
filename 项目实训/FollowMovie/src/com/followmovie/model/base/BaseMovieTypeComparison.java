package com.followmovie.model.base;

import com.jfinal.plugin.activerecord.IBean;
import com.jfinal.plugin.activerecord.Model;

@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseMovieTypeComparison <M extends BaseMovieTypeComparison<M>> extends Model<M> implements IBean {
	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}
	public java.lang.Integer getId() {
		return getInt("id");
	}
	
	public java.lang.Integer getMovieId() {
		return getInt("movieId");
	}
	public M setMovieId(java.lang.Integer movieId) {
		set("movieId", movieId);
		return (M)this;
	}
	
	public M setMovieTypeId(java.lang.Integer movieTypeId) {
		set("movieTypeId", movieTypeId);
		return (M)this;
	}
	public java.lang.Integer getMovieTypeId() {
		return getInt("movieTypeId");
	}
}
