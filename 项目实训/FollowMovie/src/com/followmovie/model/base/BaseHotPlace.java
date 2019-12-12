package com.followmovie.model.base;

import com.jfinal.plugin.activerecord.IBean;
import com.jfinal.plugin.activerecord.Model;

@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseHotPlace <M extends BaseHotPlace<M>> extends Model<M> implements IBean{
	public M setHotPlaceId(java.lang.Integer hotPlaceId) {
		set("hotPlaceId", hotPlaceId);
		return (M)this;
	}
	public java.lang.Integer getHotPlaceId() {
		return getInt("hotPlaceId");
	}
	public M setPlaceId(java.lang.Integer placeId) {
		set("placeId", placeId);
		return (M)this;
	}
	public java.lang.Integer getPlaceId() {
		return getInt("placeId");
	}
}
