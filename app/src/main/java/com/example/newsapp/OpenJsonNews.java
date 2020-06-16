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
        final String OWM_DESCRYPTION = "description";
        final String OWM_TITLE = "title";
        final String OWM_IMAGEURL = "urlToImage";
        final String OWM_ARTICLE = "articles";

        List<NewsItem> arrmodel = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(jsonNewsResponse);
        if (jsonObject.has(NEWS_MESSAGE_CODE)) {
            String errorcode = jsonObject.getString(NEWS_MESSAGE_CODE);
            switch (errorcode) {
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

        JSONArray NewsArray = jsonObject.getJSONArray(OWM_ARTICLE);
        for (int i = 0; i < NewsArray.length(); i++) {
            String title;
            String description;
            String imageurl;

            JSONObject newsobj = NewsArray.getJSONObject(i);

            String originalTitle = null;
            if (newsobj.has(OWM_TITLE)) {
                // Extract the value for the key called "original_title"
                originalTitle = newsobj.getString(OWM_TITLE);
            }


            String originalDes = null;
            if (newsobj.has(OWM_DESCRYPTION)) {
                // Extract the value for the key called "original_description"
                originalDes = newsobj.getString(OWM_DESCRYPTION);
            }

            String imgurl = null;
            if (newsobj.has(OWM_IMAGEURL)) {
                // Extract the value for the key called "original_description"
                imgurl = newsobj.getString(OWM_IMAGEURL);
            }


            // Create a new {@link Movie} object
            NewsItem news = new NewsItem(originalTitle,originalDes,imgurl);
            // Add the new {@link Movie} to the list of movies
            arrmodel.add(news);

        }


        return  arrmodel;


    }
}
