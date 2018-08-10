package com.udacity.bakingapp;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.udacity.bakingapp.adapter.StepPagerAdapter;
import com.udacity.bakingapp.model.Recipe;
import com.udacity.bakingapp.recipeservice.CookbookService;

import butterknife.ButterKnife;

public class StepActivity extends AppCompatActivity {

    ViewPager vpPager = null;
    Recipe recipe = null;
    int stepId;
    StepPagerAdapter stepPagerAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        int recipeId = bundle.getInt("chosenRecipeId");
        stepId = bundle.getInt("chosenStepId");
        this.recipe = CookbookService.getRecipes().stream().filter(x -> x.getId() == recipeId).findFirst().get();


        vpPager = (ViewPager) findViewById(R.id.vpPager);
        stepPagerAdapter = new StepPagerAdapter(getSupportFragmentManager(), recipe.getSteps());
        vpPager.setAdapter(stepPagerAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        vpPager.setCurrentItem(stepId);
    }

}
