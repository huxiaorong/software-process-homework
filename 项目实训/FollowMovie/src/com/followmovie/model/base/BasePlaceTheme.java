package com.followmovie.model.base;

import com.jfinal.plugin.activerecord.IBean;
import com.jfinal.plugin.activerecord.Model;

@SuppressWarnings({"serial", "unchecked"})
public abstract class BasePlaceTheme <M extends BasePlaceTheme<M>> extends Model<M> implements IBean{
	public java.lang.Integer getPlaceThemeId() {
		return getInt("placeThemeId");
	}
	public M setPlaceThemeId(java.lang.Integer placeThemeId) {
		set("placeThemeId", placeThemeId);
		return (M)this;
	}
	public M setPlaceThemeName(java.lang.String placeThemeName) {
		set("placeThemeName", placeThemeName);
		return (M)this;
	}
	
	public java.lang.String getPlaceThemeName() {
		return getStr("placeThemeName");
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
