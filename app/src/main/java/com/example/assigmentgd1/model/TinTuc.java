package com.example.assigmentgd1.model;

public class TinTuc {
    private String title;
    private String description;
    private String pubDate;
    private String link;
    private String imageURL;

    public TinTuc(String title, String description, String pubDate, String link, String imageURL) {
        this.title = title;
        this.description = description;
        this.pubDate = pubDate;
        this.link = link;
        this.imageURL = imageURL;
    }

    public TinTuc(String title, String description, String pubDate, String link) {
        this.title = title;
        this.description = description;
        this.pubDate = pubDate;
        this.link = link;
    }

    // Các phương thức getter/setter
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}

