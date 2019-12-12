package com.example.msl.rainbow1;

public class MovieTheme {
    private int movieThemeId;
    private String movieThemeName;
    private String img;
    private String brief;
    private String introduce;

    public MovieTheme(int movieThemeId, String movieThemeName, String img, String brief, String introduce) {
        this.movieThemeId = movieThemeId;
        this.movieThemeName = movieThemeName;
        this.img = img;
        this.brief = brief;
        this.introduce = introduce;
    }

    public MovieTheme() {
    }

    @Override
    public String toString() {
        return "MovieTheme{" +
                "placeThemeId=" + movieThemeId +
                ", placeThemeName='" + movieThemeName + '\'' +
                ", img='" + img + '\'' +
                ", brief='" + brief + '\'' +
                ", introduce='" + introduce + '\'' +
                '}';
    }

    public int getMovieThemeId() {
        return movieThemeId;
    }

    public void setMovieThemeId(int placeThemeId) {
        this.movieThemeId = placeThemeId;
    }

    public String getMovieThemeName() {
        return movieThemeName;
    }

    public void setMovieThemeName(String placeThemeName) {
        this.movieThemeName = placeThemeName;
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
}
