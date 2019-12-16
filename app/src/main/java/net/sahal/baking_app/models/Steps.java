package net.sahal.baking_app.models;

import java.io.Serializable;

public class Steps implements Serializable {

    private int id;
    private String shortDescription, description, videoURL, thumbnailURL;

    public Steps(int id, String shortDescription, String description, String videoURL, String thumbnailURL) {
        this.id = id;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
        this.thumbnailURL = thumbnailURL;
    }

    public int getId() {
        return id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public String getVideoURL(){
        return videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }
}
