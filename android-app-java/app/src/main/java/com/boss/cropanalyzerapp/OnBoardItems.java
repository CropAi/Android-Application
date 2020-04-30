package com.boss.cropanalyzerapp;

public class OnBoardItems {
    private int imgRes;
    private String Title;
    private String Description;
    public OnBoardItems(int imgRes, String title, String description) {
        this.imgRes = imgRes;
        Title = title;
        Description = description;
    }
    public int getImgRes() {
        return imgRes;
    }

    public void setImgRes(int imgRes) {
        this.imgRes = imgRes;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

}
