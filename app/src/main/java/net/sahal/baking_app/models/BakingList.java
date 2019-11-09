package net.sahal.baking_app.models;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BakingList {

    private int id, servings;
    private String name, image;
    private ArrayList<Ingredients> ingredients;
    private ArrayList<Steps> steps;


    public BakingList(AppCompatActivity activity, JSONObject node) throws JSONException {
        this.id = node.getInt("id");
        this.servings = node.getInt("servings");
        this.name = node.getString("name");
        this.image = node.getString("image");

        this.ingredients = new ArrayList<>();
        JSONArray ingredientsJson = node.getJSONArray("ingredients");
        if (ingredientsJson != null) {
            for (int i = 0; i < ingredientsJson.length(); i++) {
                ingredients.add(new Ingredients(ingredientsJson.getJSONObject(i)));
            }
        }

        this.steps = new ArrayList<>();
        JSONArray stepsJson = node.getJSONArray("steps");
        if (stepsJson != null) {
            for (int i = 0; i < stepsJson.length(); i++) {
                steps.add(new Steps(activity, stepsJson.getJSONObject(i)));
            }
        }
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

    public ArrayList<Ingredients> getIngredients() {
        return ingredients;
    }

    public ArrayList<Steps> getSteps() {
        return steps;
    }
}
