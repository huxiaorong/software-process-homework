package com.followmovie.model.base;

import com.jfinal.plugin.activerecord.IBean;
import com.jfinal.plugin.activerecord.Model;

@SuppressWarnings({"serial", "unchecked"})
public abstract class BasePlace<M extends BasePlace<M>> extends Model<M> implements IBean {
	public M setPlaceId(java.lang.Integer placeId) {
		set("placeId", placeId);
		return (M)this;
	}
	
	public java.lang.Integer getPlaceId() {
		return getInt("placeId");
	}
	
	public M setName(java.lang.String name) {
		set("name", name);
		return (M)this;
	}
	
	public java.lang.String getName() {
		return getStr("name");
	}

	public M setEnName(java.lang.String enName) {
		set("enName", enName);
		return (M)this;
	}
	
	public java.lang.String getEnName() {
		return getStr("enName");
	}
	
	public M setImg(java.lang.String img) {
		set("img", img);
		return (M)this;
	}
	
	public java.lang.String getImg() {
		return getStr("img");
	}
	
	public M setCountry(java.lang.String country) {
		set("country", country);
		return (M)this;
	}
	
	public java.lang.String getCountry() {
		return getStr("country");
	}
	
	public M setProvince(java.lang.String province) {
		set("province", province);
		return (M)this;
	}
	
	public java.lang.String getProvince() {
		return getStr("province");
	}
	
	public M setCity(java.lang.String city) {
		set("city", city);
		return (M)this;
	}
	
	public java.lang.String getCity() {
		return getStr("city");
	}
	
	public M setDescription(java.lang.String description) {
		set("description", description);
		return (M)this;
	}
	
	public java.lang.String getDescription() {
		return getStr("description");
	}
}
