package com.followmovie.model.base;

import com.jfinal.plugin.activerecord.IBean;
import com.jfinal.plugin.activerecord.Model;

@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseHotCity <M extends BaseHotCity<M>> extends Model<M> implements IBean{
	public M setCityId(java.lang.Integer cityId) {
		set("cityId", cityId);
		return (M)this;
	}
	public java.lang.Integer getCityId() {
		return getInt("cityId");
	}
	
	public M setCountry(java.lang.String country) {
		set("country", country);
		return (M)this;
	}
	
	public java.lang.String getCountry() {
		return getStr("country");
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
	public M setCityDescription(java.lang.String cityDescription) {
		set("cityDescription", cityDescription);
		return (M)this;
	}
	
	public java.lang.String getCityDescription() {
		return getStr("cityDescription");
	}
}
