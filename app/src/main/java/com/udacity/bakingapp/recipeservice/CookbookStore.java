package com.udacity.bakingapp.recipeservice;

import com.udacity.bakingapp.model.Recipe;

import java.util.List;

public class CookbookStore {

    private static List<Recipe> cookbook;
    private static int recipeId;

    public static int getRecipeId() {
        return recipeId;
    }

    public static void setRecipeId(int recipeId) {
        CookbookStore.recipeId = recipeId;
    }

    public static List<Recipe> getCookbook() {
        return cookbook;
    }

    public static void setCookbook(List<Recipe> cookbook) {
        CookbookStore.cookbook = cookbook;
    }
}
