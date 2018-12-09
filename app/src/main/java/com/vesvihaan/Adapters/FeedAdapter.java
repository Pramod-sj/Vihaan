package com.vesvihaan.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.vesvihaan.Helper.FancyLike;
import com.vesvihaan.Helper.FancyTime;
import com.vesvihaan.GlideApp;
import com.vesvihaan.Model.Feed;
import com.vesvihaan.Model.User;
import com.vesvihaan.Helper.OnLikeButtonClickListener;
import com.vesvihaan.Helper.OnLikerClickListener;
import com.vesvihaan.R;

import java.util.ArrayList;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {
    Context context;
    OnLikeButtonClickListener onLikeButtonClickListener;
    OnLikerClickListener onLikerClickListener;
    public FeedAdapter(Context context, ArrayList<Feed> feeds, OnLikeButtonClickListener onLikeButtonClickListener, OnLikerClickListener onLikerClickListener) {
        this.context = context;
        this.feeds = feeds;
        this.onLikeButtonClickListener=onLikeButtonClickListener;
        this.onLikerClickListener=onLikerClickListener;
    }

    ArrayList<Feed> feeds;

    @NonNull
    @Override
    public FeedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.feed_item_layout,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedAdapter.ViewHolder viewHolder, int i) {
        GlideApp.with(context).load(feeds.get(i).getFeedImageUrl()).transition(DrawableTransitionOptions.withCrossFade(300)).into(viewHolder.feedImage);
        viewHolder.feedTitle.setText(feeds.get(i).getFeedTitle());
        viewHolder.feedAgo.setText(FancyTime.getTimeLine(feeds.get(i).getFeedDateTime()));
        if(feeds.get(i).getFeedLikes()==null || feeds.get(i).getFeedLikes().size()==0){
            viewHolder.feedLikedBy.setText("Be first to like");
        }
        else {
            viewHolder.feedLikedBy.setText("Liked by "+ FancyLike.showFancyLikes(new ArrayList<User>(feeds.get(i).getFeedLikes().values())));
        }
        if(feeds.get(i).isLiked()){
            GlideApp.with(context).load(R.drawable.ic_thumb_up_black_24dp).into(viewHolder.feedLikeButton);
        }
        else{

            GlideApp.with(context).load(R.drawable.ic_thumb_up_white_24dp).into(viewHolder.feedLikeButton);
        }
    }

    @Override
    public int getItemCount() {
        return feeds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView feedTitle,feedAgo,feedLikedBy;
        ImageView feedImage;
        ImageView feedLikeButton;
        boolean isLiked=false;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            feedTitle=itemView.findViewById(R.id.feedTitleTextView);
            feedAgo=itemView.findViewById(R.id.feedAgoTextView);
            feedLikedBy=itemView.findViewById(R.id.feedLikedByTextView);
            feedLikedBy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onLikerClickListener.onClick(feeds.get(getLayoutPosition()));
                }
            });
            feedImage=itemView.findViewById(R.id.feedImageView);
            feedLikeButton=itemView.findViewById(R.id.feedlikeButton);
            feedLikeButton.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            onLikeButtonClickListener.onLikeButtonClick(feeds.get(getLayoutPosition()).getFeedId(),feeds.get(getLayoutPosition()).isLiked());
        }


    }



}
