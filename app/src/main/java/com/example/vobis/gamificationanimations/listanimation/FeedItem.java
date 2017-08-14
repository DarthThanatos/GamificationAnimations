package com.example.vobis.gamificationanimations.listanimation;

/**
 * Created by Vobis on 2017-08-14
 */
public class FeedItem {
    private String title;
    private String thumbnail;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Override
    public String toString(){
        return "\t-> title: " + title + "\n"
                + "\t -> thumbnaild: " + thumbnail + "\n";

    }
}
