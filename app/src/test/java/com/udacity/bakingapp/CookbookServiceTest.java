package com.udacity.bakingapp;

import com.udacity.bakingapp.model.Recipe;
import com.udacity.bakingapp.recipeservice.CookbookService;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CookbookServiceTest {
    @Test
    public void recipesAvailable() {
        CookbookService service = new CookbookService();
        List<Recipe> recipeList = service.getRecipes();
        assertNotNull(recipeList);
        assertEquals(4, recipeList.size());
    }
}