package com.udacity.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;
import android.util.Log;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Recipe implements Parcelable {

    @JsonProperty("id")
    private int id;
    @JsonProperty("image")
    private String image;
    @JsonProperty("ingredients")
    private List<Ingredient> ingredients;
    @JsonProperty("name")
    private String name;
    @JsonProperty("servings")
    private int servings;
    @JsonProperty("steps")
    private List<Step> steps;

    public Recipe() {
        this.id = 0;
        this.image = "";
        this.ingredients = new ArrayList<>();
        this.name = "";
        this.servings = 0;
        this.steps = new ArrayList<>();
    }

    protected Recipe(Parcel in) {
        this.id = in.readInt();
        this.image = in.readString();
        this.ingredients = new ArrayList<>();
        in.readList(this.ingredients, Ingredient.class.getClassLoader());
        this.name = in.readString();
        this.servings = in.readInt();
        this.steps = new ArrayList<>();
        in.readList(this.steps, Step.class.getClassLoader());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.image);
        dest.writeList(this.ingredients);
        dest.writeString(this.name);
        dest.writeInt(this.servings);
        dest.writeList(this.steps);
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public static String toJson(Recipe recipe) {
        try {
            Gson gson = new Gson();
            return gson.toJson(recipe);
        } catch (Exception e) {
            Log.e("Problem parsing object to json", "Recipe_Parsing");
            return null;
        }
    }

    public static Recipe fromJson(String json) {
        try {
            Gson gson = new Gson();
            Recipe recipe = gson.fromJson(json, Recipe.class);
            return recipe;
        } catch (Exception e) {
            Log.e("Problem parsing json", "Recipe_Parsing");
            return null;
        }
    }
}
