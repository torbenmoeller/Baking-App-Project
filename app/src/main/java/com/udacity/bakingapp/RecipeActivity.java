package com.udacity.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.udacity.bakingapp.adapter.StepPagerAdapter;
import com.udacity.bakingapp.model.Recipe;
import com.udacity.bakingapp.model.Step;
import com.udacity.bakingapp.recipeservice.CookbookService;

public class RecipeActivity extends AppCompatActivity implements OnStepSelected {

    ViewPager vpPager = null;
    int recipeId;
    Recipe recipe = null;
    StepPagerAdapter stepPagerAdapter = null;
    boolean viewPagerAvailable = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        setContentView(R.layout.activity_recipe);

        this.recipeId = bundle.getInt("chosenRecipeId");
        this.recipe = CookbookService.getRecipes().stream().filter(x -> x.getId() == recipeId).findFirst().get();

        RecipeFragment recipeFragment = new RecipeFragment();
        recipeFragment.setArguments(bundle);
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.frame_cookbook, recipeFragment)
                .commit();
//        ButterKnife.bind(this);


        vpPager = (ViewPager) findViewById(R.id.vpPager);
        viewPagerAvailable = vpPager != null;
        if (viewPagerAvailable) {
            stepPagerAdapter = new StepPagerAdapter(getSupportFragmentManager(), recipe.getSteps());
            vpPager.setAdapter(stepPagerAdapter);
        }
    }


    @Override
    public void onStepSelection(int stepId) {
        Step chosenStep = recipe.getSteps().stream().filter(x -> x.getId() == stepId).findFirst().get();
        if (viewPagerAvailable) {
            int index = recipe.getSteps().indexOf(chosenStep);
            vpPager.setCurrentItem(index);
        } else {
            Bundle bundle = new Bundle();
            bundle.putInt("chosenRecipeId", recipeId);
            bundle.putInt("chosenStepId", chosenStep.getId());
            Intent startChildActivityIntent = new Intent(this, StepActivity.class);
            startChildActivityIntent.putExtras(bundle);
            startActivity(startChildActivityIntent);
        }
    }


}
