package com.followmovie.model.base;

import com.jfinal.plugin.activerecord.IBean;
import com.jfinal.plugin.activerecord.Model;

@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseMovieTheme <M extends BaseMovieTheme<M>> extends Model<M> implements IBean{
	public java.lang.Integer getMovieThemeId() {
		return getInt("movieThemeId");
	}
	public M setMovieThemeId(java.lang.Integer movieThemeId) {
		set("movieThemeId", movieThemeId);
		return (M)this;
	}
	public M setMovieThemeName(java.lang.String movieThemeName) {
		set("movieThemeName", movieThemeName);
		return (M)this;
	}
	
	public java.lang.String getMovieThemeName() {
		return getStr("movieThemeName");
	}
	public M setImg(java.lang.String img) {
		set("img", img);
		return (M)this;
	}
	
	public java.lang.String getImg() {
		return getStr("img");
	}
	public M setBrief(java.lang.String brief) {
		set("brief", brief);
		return (M)this;
	}
	
	public java.lang.String getBrief() {
		return getStr("brief");
	}
	public M setIntroduce(java.lang.String introduce) {
		set("introduce", introduce);
		return (M)this;
	}
	
	public java.lang.String getIntroduce() {
		return getStr("introduce");
	}
}
