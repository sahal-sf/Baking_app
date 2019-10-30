package net.sahal.baking_app;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.JsonReader;
import android.widget.Toast;

import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSourceInputStream;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.DefaultDataSource;
import com.google.android.exoplayer2.util.Util;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;



public class Sample {


    // ----------------------------- models -----------------------------------//
    private int id, servings;
    private String name, image;

    public Sample(int id, int servings, String name, String image) {
        this.id = id;
        this.servings = servings;
        this.name = name;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public int getServings() {
        return servings;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }


    //----------------------------------------------------------------------------------

    private int quantity;
    private String measure, ingredient;

    public Sample(int quantity, String measure, String ingredient) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    //----------------------------------------------------------------------------------

//    private int id;
    private String shortDescription, description, videoURL, thumbnailURL;

    public Sample(int id, String shortDescription, String description, String videoURL, String thumbnailURL) {
        this.id = id;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
        this.thumbnailURL = thumbnailURL;
    }

//    public int getId() {
//        return id;
//    }

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

    //----------------------------------------------------------------------------------

    /**
     * Gets portrait of the composer for a sample by the sample ID.
     * @param context The application context.
     * @param sampleID The sample ID.
     * @return The portrait Bitmap.
     */
    static Bitmap getComposerArtBySampleID(Context context, int sampleID){
        Sample sample = Sample.getSampleByID(context, sampleID);
        int albumArtID = context.getResources().getIdentifier(
                sample != null ? sample.getName() : null, "drawable",
                context.getPackageName());
        return BitmapFactory.decodeResource(context.getResources(), albumArtID);
    }

    /**
     * Gets a single sample by its ID.
     * @param context The application context.
     * @param sampleID The sample ID.
     * @return The sample object.
     */
    static Sample getSampleByID(Context context, int sampleID) {
        JsonReader reader;
        try {
            reader = readJSONFile(context);
            reader.beginArray();
            while (reader.hasNext()) {
                Sample currentSample = readEntry(reader);
                if (currentSample.getId() == sampleID) {
                    reader.close();
                    return currentSample;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Gets and ArrayList of the IDs for all of the Samples from the JSON file.
     * @param context The application context.
     * @return The ArrayList of all sample IDs.
     */
    static ArrayList<Integer> getAllSampleIDs(Context context){
        JsonReader reader;
        ArrayList<Integer> sampleIDs = new ArrayList<>();
        try {
            reader = readJSONFile(context);
            reader.beginArray();
            while (reader.hasNext()) {
                sampleIDs.add(readEntry(reader).getId());
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sampleIDs;
    }

    /**
     * Method used for obtaining a single sample from the JSON file.
     * @param reader The JSON reader object pointing a single sample JSON object.
     * @return The Sample the JsonReader is pointing to.
     */
    private static Sample readEntry(JsonReader reader) {
        Integer id = -1;
        String composer = null;
        String title = null;
        String uri = null;
        String albumArtID = null;

        try {
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                switch (name) {
                    case "name":
                        title = reader.nextString();
                        break;
                    case "id":
                        id = reader.nextInt();
                        break;
                    case "composer":
                        composer = reader.nextString();
                        break;
                    case "uri":
                        uri = reader.nextString();
                        break;
                    case "albumArtID":
                        albumArtID = reader.nextString();
                        break;
                    default:
                        break;
                }
            }
            reader.endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Sample(id, composer, title, uri, albumArtID);
    }

    /**
     * Method for creating a JsonReader object that points to the JSON array of samples.
     * @param context The application context.
     * @return The JsonReader object pointing to the JSON array of samples.
     * @throws IOException Exception thrown if the sample file can't be found.
     */
    private static JsonReader readJSONFile(Context context) throws IOException {
        AssetManager assetManager = context.getAssets();
        String uri = null;

        try {
            for (String asset : assetManager.list("")) {
                if (asset.endsWith(".exolist.json")) {
                    uri = "asset:///" + asset;
                }
            }
        } catch (IOException e) {
            Toast.makeText(context, R.string.sample_list_load_error, Toast.LENGTH_LONG)
                    .show();
        }

        String userAgent = Util.getUserAgent(context, "Baking_app");
        DataSource dataSource = new DefaultDataSource(context, null, userAgent, false);
        DataSpec dataSpec = new DataSpec(Uri.parse(uri));
        InputStream inputStream = new DataSourceInputStream(dataSource, dataSpec);

        JsonReader reader = null;
        try {
            reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
        } finally {
            Util.closeQuietly(dataSource);
        }

        return reader;
    }

}
