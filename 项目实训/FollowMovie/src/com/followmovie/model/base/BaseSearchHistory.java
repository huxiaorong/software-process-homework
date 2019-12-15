package com.followmovie.model.base;

import com.jfinal.plugin.activerecord.IBean;
import com.jfinal.plugin.activerecord.Model;

@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseSearchHistory<M extends BaseSearchHistory<M>> extends Model<M> implements IBean {
	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}
	
	public java.lang.Integer getId() {
		return getInt("id");
	}
	
	public M setCount(java.lang.Integer count) {
		set("count", count);
		return (M)this;
	}
	
	public java.lang.Integer getCount() {
		return getInt("count");
	}
	
	public M setRecord(java.lang.String record) {
		set("record", record);
		return (M)this;
	}
	
	public java.lang.String getRecord() {
		return getStr("record");
	}
	

}
