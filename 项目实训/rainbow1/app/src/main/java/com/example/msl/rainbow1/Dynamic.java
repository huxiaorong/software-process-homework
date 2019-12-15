package com.example.msl.rainbow1;

import java.util.List;

public class Dynamic {
    private boolean hasLike;
    private int likeCount;
    private int dynamicId;
    private String blog;
    private int userId;
    private String username;
    private String headimg;
    private List<String> imgData;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void addLikeCount(){
        likeCount++;
    }

    public void delLikeCount(){
        likeCount--;
    }

    public boolean isHasLike() {
        return hasLike;
    }

    public void setHasLike(boolean hasLike) {
        this.hasLike = hasLike;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getDynamicId() {
        return dynamicId;
    }

    public void setDynamicId(int dynamicId) {
        this.dynamicId = dynamicId;
    }

    public String getBlog() {
        return blog;
    }

    public void setBlog(String blog) {
        this.blog = blog;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public List<String> getImgData() {
        return imgData;
    }

    public void setImgData(List<String> imgData) {
        this.imgData = imgData;
    }
}
