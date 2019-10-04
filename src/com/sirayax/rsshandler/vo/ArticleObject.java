package com.sirayax.rsshandler.vo;

public class ArticleObject {
    private String title;
    private String description;
    private String link;
    private String pubDate;


    public ArticleObject() {
    }

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

    @Override
    public String toString() {
        return "ArticleObject{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", link='" + link + '\'' +
                ", pubDate='" + pubDate + '\'' +
                '}' + "\n\n";
    }

    public String toPublishString() {
        //return title + "\n\n" + description + "\n" + link;
        return title + "\n" + link;
    }
}
