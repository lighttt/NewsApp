package com.example.newsapp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder>{

    List<NewsItem> mNewsItem;
    public NewsAdapter(List<NewsItem> news) {
        this.mNewsItem = news;
    }

    public void clearAll() {
        mNewsItem.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<NewsItem> news) {
        mNewsItem.clear();
        mNewsItem.addAll(news);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NewsAdapter.NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_news_item, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.NewsViewHolder holder, int position) {
        NewsItem news = mNewsItem.get(position);
        String title = news.getTitle();
        String description = news.getDescription();
        String imgurl = news.getImgURL();
        String date = news.getDate();
        String[] dateTime = date.split("T");
        Log.e("The first date is", dateTime[0]);
        Log.e("The second date is", dateTime[1]);
        holder.tvTitle.setText(title);
        holder.tvDescription.setText(String.valueOf(description));
        String convertedDT = dateTime[0] + " " + dateTime[1].replace("Z","");
        holder.tvDate.setText(convertedDT);
        Picasso.get().load(imgurl).into(holder.ivNewsImg);
    }

    @Override
    public int getItemCount() {
        if (mNewsItem ==null){
            return 0;
        }else {
            return mNewsItem.size();
        }
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle, tvDescription,tvDate;
        private ImageView ivNewsImg;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            ivNewsImg = itemView.findViewById(R.id.img_iv);
            tvTitle = itemView.findViewById(R.id.title_tv);
            tvDate = itemView.findViewById(R.id.date_tv);
            tvDescription = itemView.findViewById(R.id.discryption_tv);
        }
    }
}
