package com.udacity.bakingapp.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.udacity.bakingapp.Keys;
import com.udacity.bakingapp.StepFragment;
import com.udacity.bakingapp.model.Recipe;
import com.udacity.bakingapp.model.Step;

public class StepPagerAdapter extends FragmentPagerAdapter {

    Recipe recipe;

    public StepPagerAdapter(FragmentManager fragmentManager, Recipe recipe) {
        super(fragmentManager);
        this.recipe = recipe;
    }

    // Returns total number of pages
    @Override
    public int getCount() {
        return recipe.getSteps().size();
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {
        StepFragment stepFragment = new StepFragment();
        Step step = recipe.getSteps().get(position);

        Bundle bundle = new Bundle();
        bundle.putInt(Keys.chosenRecipeId, recipe.getId());
        bundle.putInt(Keys.chosenStepId, step.getId());
        stepFragment.setArguments(bundle);

        return stepFragment;
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        return "Step " + (position + 1);
    }

}

