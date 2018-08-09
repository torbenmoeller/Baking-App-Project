package com.udacity.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.udacity.bakingapp.model.Recipe;
import com.udacity.bakingapp.model.Step;
import com.udacity.bakingapp.recipeservice.CookbookService;

import butterknife.ButterKnife;

public class RecipeActivity extends AppCompatActivity implements OnStepSelected {


    Recipe recipe = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        setContentView(R.layout.activity_recipe);

        int recipeId = bundle.getInt("chosenRecipeId");
        this.recipe = CookbookService.getRecipes().stream().filter(x -> x.getId() == recipeId).findFirst().get();

        RecipeFragment recipeFragment = new RecipeFragment();
        recipeFragment.setArguments(bundle);
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.frame_cookbook, recipeFragment)
                .commit();
        ButterKnife.bind(this);
    }


    @Override
    public void onStepSelection(int stepId) {
        Step chosenStep = recipe.getSteps().stream().filter(x -> x.getId() == stepId).findFirst().get();
        Bundle bundle = new Bundle();
        bundle.putInt("chosenStep", chosenStep.getId());
        Intent startChildActivityIntent = new Intent(this, StepActivity.class);
        startChildActivityIntent.putExtras(bundle);
        startActivity(startChildActivityIntent);
    }


}
