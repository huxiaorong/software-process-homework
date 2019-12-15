package com.example.msl.rainbow1;

public class City {
    private int cityId;
    private String country;
    private String name;
    private String img;
    private String cityDescription;

    @Override
    public String toString() {
        return "City{" +
                "cityId=" + cityId +
                ", country='" + country + '\'' +
                ", name='" + name + '\'' +
                ", img='" + img + '\'' +
                ", cityDescription='" + cityDescription + '\'' +
                '}';
    }

    public City() {
    }

    public City(int cityId, String country, String name, String img, String cityDescription) {
        this.cityId = cityId;
        this.country = country;
        this.name = name;
        this.img = img;
        this.cityDescription = cityDescription;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getCityDescription() {
        return cityDescription;
    }

    public void setCityDescription(String cityDescription) {
        this.cityDescription = cityDescription;
    }
}
