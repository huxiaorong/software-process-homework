package com.example.msl.rainbow1;

public class Count {
    private int dynamicCount;
    private int praiseCount;
    private int collectionCount;

    public int getDynamicCount() {
        return dynamicCount;
    }

    public void setDynamicCount(int dynamicCount) {
        this.dynamicCount = dynamicCount;
    }

    public int getPraiseCount() {
        return praiseCount;
    }

    public void setPraiseCount(int praiseCount) {
        this.praiseCount = praiseCount;
    }

    public int getCollectionCount() {
        return collectionCount;
    }

    public void setCollectionCount(int collectionCount) {
        this.collectionCount = collectionCount;
    }

    @Override
    public String toString() {
        return "Count{" +
                "dynamicCount=" + dynamicCount +
                ", praiseCount=" + praiseCount +
                ", collectionCount=" + collectionCount +
                '}';
    }
}
