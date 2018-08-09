package com.udacity.bakingapp;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;

import butterknife.ButterKnife;

public class RecipeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Bundle b = getIntent().getExtras();
        setContentView(R.layout.activity_recipe);
        RecipeFragment recipeFragment = new RecipeFragment();
        recipeFragment.setArguments(b);
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.frame_cookbook, recipeFragment)
                .commit();
        ButterKnife.bind(this);
    }
}
