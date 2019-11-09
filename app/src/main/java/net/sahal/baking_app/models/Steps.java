package net.sahal.baking_app.models;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class Steps {

    private int id;
    private String shortDescription, description, videoURL, thumbnailURL;
    private Video video;

    public Steps(AppCompatActivity activity, JSONObject node) throws JSONException {
        this.id = node.getInt("id");
        this.shortDescription = node.getString("shortDescription");
        this.description = node.getString("description");
        this.videoURL = node.getString("videoURL");
        this.thumbnailURL = node.getString("thumbnailURL");

        this.video = new Video(videoURL, activity);
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

    public Video getVideo() {
        return video;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }
}
