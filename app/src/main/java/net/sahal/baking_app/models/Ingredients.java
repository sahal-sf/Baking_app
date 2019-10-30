package net.sahal.baking_app.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Ingredients {

    private int quantity;
    private String measure, ingredient;

    public Ingredients(JSONObject node) throws JSONException {
        this.quantity = node.getInt("quantity");
        this.measure = node.getString("measure");
        this.ingredient = node.getString("ingredient");
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
}
