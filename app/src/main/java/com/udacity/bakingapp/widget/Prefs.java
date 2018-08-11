package com.udacity.bakingapp.widget;

import android.content.Context;
import android.content.SharedPreferences;

import com.udacity.bakingapp.Keys;
import com.udacity.bakingapp.model.Recipe;

//Source https://github.com/dnKaratzas/udacity-baking-recipes
public class Prefs {

    public static final String PREFS_NAME = "prefs";

    public static void saveRecipe(Context context, Recipe recipe) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
        prefs.putString(Keys.widgetRecipeKey, Recipe.toJson(recipe));
        prefs.apply();
    }

    public static Recipe loadRecipe(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String recipeString = prefs.getString(Keys.widgetRecipeKey, "");
        if("".equals(recipeString)){
            return null;
        }else{
            return Recipe.fromJson(recipeString);
        }
    }
}
