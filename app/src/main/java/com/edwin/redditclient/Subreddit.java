package com.edwin.redditclient;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

public class Subreddit extends SugarRecord
{
    @Unique
    private String remoteId = "";
    private String displayName = "";
    private String title = "";
    private String headerTitle = "";
    private String description = "";
    private String iconUrl = "";
    private String headerImageUrl = "";
    private String bannerUrl = "";

    public Subreddit()
    {
        //Required by Sugar
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        if (!"null".equals(headerTitle))
            this.headerTitle = headerTitle;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        if (!"null".equals(bannerUrl))
            this.bannerUrl = bannerUrl;
    }

    public String getHeaderImageUrl() {
        return headerImageUrl;
    }

    public void setHeaderImageUrl(String headerImageUrl) {
        this.headerImageUrl = headerImageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRemoteId(String remoteId) {
        this.remoteId = remoteId;
    }
}
