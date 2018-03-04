package apps_x.com.newsapi.adapter;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import apps_x.com.newsapi.R;
import apps_x.com.newsapi.model.NewsData;
import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.v4.content.ContextCompat.startActivity;

/*
 * This class is a customized adapter for processing about displaying news data,
 * received from newsapi.org.
 */
public class NewsViewAdapter extends RecyclerView.Adapter<NewsViewAdapter.NewsViewHolder> {
    // Contains a list of models with data and news
    private ArrayList<NewsData> newsList;

    private View holderView;

    /**
     * @param newsList Contains a list of models with data and news
     */
    public NewsViewAdapter(ArrayList<NewsData> newsList) {
        this.newsList = newsList;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        holderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        return new NewsViewHolder(holderView);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, final int position) {
        holder.newsDescription.setText(newsList.get(position).getDescription());
        holder.newsDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open the browser and go to the news URL.
                Uri address = Uri.parse(newsList.get(position).getUrl());
                Intent openlink = new Intent(Intent.ACTION_VIEW, address);
                startActivity(view.getContext(), openlink, null);
            }
        });
        holder.newsTitle.setText(newsList.get(position).getTitle());
        // Declare and initialize the variable to store the news image url
        String imgUrl = "";
        try {
            // Check if the url model contains images for news
            if (!newsList.get(position).getUrlToImage().isEmpty()) {
                // model contains the image model url for news, we store it in a variable
                imgUrl = newsList.get(position).getUrlToImage();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        if (imgUrl.isEmpty()) {
            // If the variable with url address is empty, set the standard image
            holder.newsImag.setBackgroundResource(R.drawable.no_img_icon);
        } else {
            // If the variable with url address is not empty, load the image with Glide
            Glide.with(holderView.getContext()).load(imgUrl)
                    .into(holder.newsImag);
        }

    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {
        // Setup news item holderView
        @BindView(R.id.ni_news_card_view)
        CardView cardView;
        @BindView(R.id.ni_news_descript)
        TextView newsDescription;
        @BindView(R.id.ni_news_img)
        ImageView newsImag;
        @BindView(R.id.ni_news_title)
        TextView newsTitle;

        NewsViewHolder(View itemView) {
            super(itemView);
            // Setup ButterKnife
            ButterKnife.bind(this, itemView);
        }
    }


}
