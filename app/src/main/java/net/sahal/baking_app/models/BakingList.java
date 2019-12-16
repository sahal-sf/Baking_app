package net.sahal.baking_app.models;

import java.io.Serializable;
import java.util.ArrayList;

public class BakingList implements Serializable {

    private int id, servings;
    private String name, image;
    private ArrayList<Ingredients> ingredients;
    private ArrayList<Steps> steps;

    public BakingList(int id, int servings, String name, String image, ArrayList<Ingredients> ingredients, ArrayList<Steps> steps){
        this.id = id;
        this.servings = servings;
        this.name = name;
        this.image = image;
        this.ingredients = ingredients;
        this.steps = steps;
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
