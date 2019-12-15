package com.example.msl.rainbow1;

public class PlaceTheme {
    private int placeThemeId;
    private String placeThemeName;
    private String img;
    private String brief;
    private String introduce;

    public PlaceTheme() {
        super();
    }

    public PlaceTheme(int placeThemeId, String placeThemeName, String img, String brief, String introduce) {
        this.placeThemeId = placeThemeId;
        this.placeThemeName = placeThemeName;
        this.img = img;
        this.brief = brief;
        this.introduce = introduce;
    }

    public int getPlaceThemeId() {
        return placeThemeId;
    }

    public void setPlaceThemeId(int placeThemeId) {
        this.placeThemeId = placeThemeId;
    }

    public String getPlaceThemeName() {
        return placeThemeName;
    }

    public void setPlaceThemeName(String placeThemeName) {
        this.placeThemeName = placeThemeName;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    @Override
    public String toString() {
        return "placeTheme{" +
                "placeThemeId=" + placeThemeId +
                ", placeThemeName='" + placeThemeName + '\'' +
                ", img='" + img + '\'' +
                ", brief='" + brief + '\'' +
                ", introduce='" + introduce + '\'' +
                '}';
    }
}
