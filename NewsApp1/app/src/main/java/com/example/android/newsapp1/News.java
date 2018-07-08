package com.example.android.newsapp1;

/**
 * Created by lhu513 on 5/2/18.
 */

public class News {

    private String newsTitle;

    private String newsSection;

    private String newsUrl;

    private String webTitle;

    private String webPublicationDate;

    public News(String newsTitle, String newsSection, String newsUrl, String webTitle, String webPublicationDate) {
        this.newsTitle = newsTitle;
        this.newsSection = newsSection;
        this.newsUrl = newsUrl;
        this.webTitle = webTitle;
        this.webPublicationDate = webPublicationDate;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public String getNewsLink() {
        return newsUrl;
    }

    public String getNewsSection() {
        return newsSection;
    }

    public String getWebTitle() {
        return webTitle;
    }

    public String getWebPublicationDate() {
        return webPublicationDate;
    }


}
