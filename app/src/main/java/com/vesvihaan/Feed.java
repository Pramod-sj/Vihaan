package com.vesvihaan;

import java.util.ArrayList;
import java.util.Map;

public class Feed {
    String feedId;
    String feedImageUrl;
    String feedDateTime;


    public String getFeedTitle() {
        return feedTitle;
    }

    public void setFeedTitle(String feedTitle) {
        this.feedTitle = feedTitle;
    }

    String feedTitle;
    Map<String,User> feedLikes;
    public Feed(){}

    public String getFeedId() {
        return feedId;
    }

    public void setFeedId(String feedId) {
        this.feedId = feedId;
    }

    public String getFeedImageUrl() {
        return feedImageUrl;
    }

    public void setFeedImageUrl(String feedImageUrl) {
        this.feedImageUrl = feedImageUrl;
    }

    public String getFeedDateTime() {
        return feedDateTime;
    }

    public void setFeedDateTime(String feedDateTime) {
        this.feedDateTime = feedDateTime;
    }

    public Map<String, User> getFeedLikes() {
        return feedLikes;
    }

    public void setFeedLikes(Map<String, User> feedLikes) {
        this.feedLikes = feedLikes;
    }

    public boolean isLiked=false;

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }
}
