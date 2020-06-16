package com.example.newsapp;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.NewsViewHolder holder, int position) {
        NewsItem news = mNewsItem.get(position);
        String title = news.getTitle();
        String description = news.getDescryption();
        String imgurl = news.getImgurl();
        holder.titletext.setText(title);
        holder.description.setText(String.valueOf(description));
        Picasso.get().load(imgurl).into(holder.imgview);
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
        private TextView titletext, description;
        private ImageView imgview;
        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            imgview = itemView.findViewById(R.id.img_iv);
            titletext = itemView.findViewById(R.id.title_tv);
            description = itemView.findViewById(R.id.discryption_tv);
        }
    }
}
