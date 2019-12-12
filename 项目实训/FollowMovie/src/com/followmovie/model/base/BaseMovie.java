package com.followmovie.model.base;


import com.jfinal.plugin.activerecord.IBean;
import com.jfinal.plugin.activerecord.Model;

@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseMovie<M extends BaseMovie<M>> extends Model<M> implements IBean {

	public java.lang.Integer getMovieId() {
		return getInt("movieId");
	}
	public M setMovieId(java.lang.Integer movieId) {
		set("movieId", movieId);
		return (M)this;
	}
	public M setName(java.lang.String name) {
		set("name", name);
		return (M)this;
	}
	
	public java.lang.String getName() {
		return getStr("name");
	}
	
	public M setImg(java.lang.String img) {
		set("img", img);
		return (M)this;
	}
	
	public java.lang.String getImg() {
		return getStr("img");
	}
	
	public java.util.Date getReleaseYear() {
		return getDate("releaseYear");
	}
	public M setReleaseYear(java.util.Date releaseYear) {
		set("releaseYear",releaseYear);
		return (M)this;
	}
	
	
	public M setCountry(java.lang.String country) {
		set("country", country);
		return (M)this;
	}
	
	public java.lang.String getCountry() {
		return getStr("country");
	}
	
	
	
	public M setScene(java.lang.Integer scene) {
		set("scene", scene);
		return (M)this;
	}
	
	public java.lang.Integer getScene() {
		return getInt("scene");
	}
	
	
	public M setDescription(java.lang.String description) {
		set("description", description);
		return (M)this;
	}
	
	public java.lang.String getDescription() {
		return getStr("description");
	}
	
	public M setEnName(java.lang.String enName) {
		set("enName", enName);
		return (M)this;
	}
	
	public java.lang.String getEnName() {
		return getStr("enName");
	}

}
