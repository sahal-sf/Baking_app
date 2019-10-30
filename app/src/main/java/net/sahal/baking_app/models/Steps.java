package net.sahal.baking_app.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Steps {

    private int id;
    private String shortDescription, description, videoURL, thumbnailURL;

    public Steps(JSONObject node) throws JSONException {
        this.id = node.getInt("id");
        this.shortDescription = node.getString("shortDescription");
        this.description = node.getString("description");
        this.videoURL = node.getString("videoURL");
        this.thumbnailURL = node.getString("thumbnailURL");
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

    public String getVideoURL() {
        return videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }
}
