package com.example.newsapp;

public class NewsItem {
    String title;
    String description;
    String imgURL;
    String date;

    public NewsItem(String title, String description, String imgURL, String date) {
        this.title = title;
        this.description = description;
        this.imgURL = imgURL;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
