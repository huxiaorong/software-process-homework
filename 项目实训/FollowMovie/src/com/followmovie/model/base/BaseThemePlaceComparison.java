package com.followmovie.model.base;

import com.jfinal.plugin.activerecord.IBean;
import com.jfinal.plugin.activerecord.Model;

@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseThemePlaceComparison <M extends BaseThemePlaceComparison<M>> extends Model<M> implements IBean{
	public java.lang.Integer getId() {
		return getInt("id");
	}
	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}
	
	public java.lang.Integer getPlaceThemeId() {
		return getInt("placeThemeId");
	}
	public M setPlaceThemeId(java.lang.Integer placeThemeId) {
		set("placeThemeId", placeThemeId);
		return (M)this;
	}
	
	public java.lang.Integer getPlaceId() {
		return getInt("placeId");
	}
	public M setPlaceId(java.lang.Integer placeId) {
		set("placeId", placeId);
		return (M)this;
	}
}
