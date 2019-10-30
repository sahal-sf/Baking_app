//package net.sahal.baking_app;
//
//import android.content.Context;
//import android.content.res.AssetManager;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.net.Uri;
//import android.util.JsonReader;
//import android.widget.Toast;
//
//import com.google.android.exoplayer2.upstream.DataSource;
//import com.google.android.exoplayer2.upstream.DataSourceInputStream;
//import com.google.android.exoplayer2.upstream.DataSpec;
//import com.google.android.exoplayer2.upstream.DefaultDataSource;
//import com.google.android.exoplayer2.util.Util;
//
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.util.ArrayList;
//
//
//public class Sample {
//
//    static Bitmap getComposerArtBySampleID(Context context, int sampleID) {
//        Sample sample = Sample.getSampleByID(context, sampleID);
//        int albumArtID = context.getResources().getIdentifier(
//                sample != null ? sample.getName() : null, "drawable",
//                context.getPackageName());
//        return BitmapFactory.decodeResource(context.getResources(), albumArtID);
//    }
//
//    static Sample getSampleByID(Context context, int sampleID) {
//        JsonReader reader;
//        try {
//            reader = readJSONFile(context);
//            reader.beginArray();
//            while (reader.hasNext()) {
//                Sample currentSample = readEntry(reader);
//                if (currentSample.getId() == sampleID) {
//                    reader.close();
//                    return currentSample;
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }
//
//    static ArrayList<Integer> getAllSampleIDs(Context context) {
//        JsonReader reader;
//        ArrayList<Integer> sampleIDs = new ArrayList<>();
//        try {
//            reader = readJSONFile(context);
//            reader.beginArray();
//            while (reader.hasNext()) {
//                sampleIDs.add(readEntry(reader).getId());
//            }
//            reader.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return sampleIDs;
//    }
//
//    private static Sample readEntry(JsonReader reader) {
//        int id = -1;
//        String name = null;
//        int servings = -1;
//        String image = null;
//
//        try {
//            reader.beginObject();
//            while (reader.hasNext()) {
//                String line = reader.nextName();
//                switch (line) {
//                    case "name":
//                        name = reader.nextString();
//                        break;
//                    case "id":
//                        id = reader.nextInt();
//                        break;
//                    case "servings":
//                        servings = reader.nextInt();
//                        break;
//                    case "image":
//                        image = reader.nextString();
//                        break;
//                    default:
//                        break;
//                }
//            }
//            reader.endObject();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return new BakingList(id, servings, name, image);
//    }
//
//    private static JsonReader readJSONFile(Context context) throws IOException {
//        AssetManager assetManager = context.getAssets();
//        String uri = null;
//
//        try {
//            for (String asset : assetManager.list("")) {
//                if (asset.equals("baking.json")) {
//                    uri = "asset:///" + asset;
//                }
//            }
//        } catch (IOException e) {
//            Toast.makeText(context, R.string.sample_list_load_error, Toast.LENGTH_LONG)
//                    .show();
//        }
//
//        String userAgent = Util.getUserAgent(context, "Baking_app");
//        DataSource dataSource = new DefaultDataSource(context, null, userAgent, false);
//        DataSpec dataSpec = new DataSpec(Uri.parse(uri));
//        InputStream inputStream = new DataSourceInputStream(dataSource, dataSpec);
//
//        JsonReader reader = null;
//        try {
//            reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
//        } finally {
//            Util.closeQuietly(dataSource);
//        }
//
//        return reader;
//    }
//
//}
