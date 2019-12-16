package net.sahal.baking_app.models;

import java.io.Serializable;

public class Ingredients implements Serializable {

    private double quantity;
    private String measure, ingredient;

    public Ingredients(int quantity, String measure, String ingredient){
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    public double getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredient() {
        return ingredient;
    }
}
