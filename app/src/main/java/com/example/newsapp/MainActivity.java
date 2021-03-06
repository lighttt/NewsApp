package com.example.newsapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.github.ybq.android.spinkit.SpinKitView;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<NewsItem>> {

    RecyclerView mRecycler;
    NewsAdapter myNewsAdapter;
    private TextView mErrorMessageDisplay;
    private static final int NEWS_LOADER_ID = 0;
    private SpinKitView mLoadingIndicator;
    Context mContext = MainActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecycler = findViewById(R.id.recycler_view);
        mErrorMessageDisplay = findViewById(R.id.tv_error_message);
        mLoadingIndicator = findViewById(R.id.loading_indicator);
        //set rv
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(linearLayoutManager);
        mRecycler.setHasFixedSize(true);
        List<NewsItem> news = new ArrayList<>();
        //adapter
        myNewsAdapter = new NewsAdapter(news);
        mRecycler.setAdapter(myNewsAdapter);

        //initilizing the loader
        getSupportLoaderManager().initLoader(NEWS_LOADER_ID, null, this);
    }

    /*
     *  ----------------------  Loading Functions --------------------------
     * */


    private void showNewsDataView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecycler.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
        mRecycler.setVisibility(View.INVISIBLE);
    }

    @NonNull
    @Override
    public Loader<List<NewsItem>> onCreateLoader(int id, @Nullable Bundle args) {
        // create async task loader
        return new AsyncTaskLoader<List<NewsItem>>(this) {
            List<NewsItem> mNewsData = null;

            @Override
            protected void onStartLoading() {

                if (mNewsData != null) {
                    deliverResult(mNewsData);
                }
                mLoadingIndicator.setVisibility(View.VISIBLE);
                // triggers the load in background function to load data
                forceLoad();
            }

            @Nullable
            @Override
            public List<NewsItem> loadInBackground() {
                String location = LocationPrefrences.getPreferedWeatherLocation(mContext);
                URL newsRequestUrl = NetworkUtils.buildUrl(location);
                List<NewsItem> newsDataFromJson = null;
                try {
                    String jsonNewsResponse = NetworkUtils.getResponseFromHttpUrl(newsRequestUrl);
                    newsDataFromJson = OpenJsonNews.getWeatherDataFromJson(MainActivity.this,jsonNewsResponse);
                    return newsDataFromJson;
                } catch (JSONException e) {
                    e.printStackTrace();
                    return null;
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            public void deliverResult(@Nullable List<NewsItem> data) {
                mNewsData=data;
                super.deliverResult(data);
            }
        };
    }


    @Override
    public void onLoadFinished(@NonNull Loader<List<NewsItem>> loader, List<NewsItem> data) {

        mLoadingIndicator.setVisibility(View.INVISIBLE);
        myNewsAdapter.clearAll();

        // Add the movie data
        if (data != null && !data.isEmpty()) {
            showNewsDataView();
            myNewsAdapter.addAll(data);
        }

        if (!isOnline()) {
            showOfflineMessage();
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<NewsItem>> loader) {

    }

    private boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    private void showOfflineMessage() {
        mRecycler.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }
}