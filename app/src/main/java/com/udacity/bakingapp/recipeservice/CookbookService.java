package com.udacity.bakingapp.recipeservice;

import com.udacity.bakingapp.model.Recipe;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class CookbookService {

    ICookbookService service;

    public CookbookService() {

        this.service = new Retrofit.Builder()
                .baseUrl("https://d17h27t6h515a5.cloudfront.net/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build()
                .create(ICookbookService.class);

    }


    public List<Recipe> getRecipes() {
        try {
            Response response = service.getRecipes().execute();
            List<Recipe> recipe = (List<Recipe>)response.body();
            return recipe;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
