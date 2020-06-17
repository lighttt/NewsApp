package com.example.newsapp;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OpenJsonNews {
    public static List<NewsItem> getWeatherDataFromJson(Context context, String jsonNewsResponse) throws JSONException {
        final String NEWS_MESSAGE_CODE = "status";
        final String NEWS_DESCRIPTION = "description";
        final String NEWS_TITLE = "title";
        final String NEWS_IMG_URL = "urlToImage";
        final String NEWS_ARTICLES = "articles";
        final String NEWS_DATE = "publishedAt";

        List<NewsItem> newsItemArrayList = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(jsonNewsResponse);
        if (jsonObject.has(NEWS_MESSAGE_CODE)) {
            String errorCode = jsonObject.getString(NEWS_MESSAGE_CODE);
            switch (errorCode) {
                case "ok":
                    break;
                case "error":
                    /* Location invalid */
                    return null;
                default:
                    /* Server probably down */
                    return null;
            }
        }

        JSONArray NewsArray = jsonObject.getJSONArray(NEWS_ARTICLES);
        for (int i = 0; i < NewsArray.length(); i++) {

            JSONObject newsObject = NewsArray.getJSONObject(i);

            String originalTitle = null;
            if (newsObject.has(NEWS_TITLE)) {
                // Extract the value for the key called "original_title"
                originalTitle = newsObject.getString(NEWS_TITLE);
            }


            String originalDes = null;
            if (newsObject.has(NEWS_DESCRIPTION)) {
                // Extract the value for the key called "original_description"
                originalDes = newsObject.getString(NEWS_DESCRIPTION);
            }

            String imgurl = null;
            if (newsObject.has(NEWS_IMG_URL)) {
                // Extract the value for the key called "imgurl"
                imgurl = newsObject.getString(NEWS_IMG_URL);
            }
            String date = null;
            if (newsObject.has(NEWS_DATE)) {
                // Extract the value for the key called "imgurl"
                date = newsObject.getString(NEWS_DATE);
            }

            // Create a new {@link Movie} object
            NewsItem news = new NewsItem(originalTitle,originalDes,imgurl,date);
            // Add the new {@link Movie} to the list of movies
            newsItemArrayList.add(news);
        }
        return  newsItemArrayList;
    }
}
