package com.udacity.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.udacity.bakingapp.adapter.CookbookAdapter;
import com.udacity.bakingapp.model.Recipe;
import com.udacity.bakingapp.recipeservice.CookbookService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recycler_recipes)
    RecyclerView recyclerRecipes;
    @BindInt(R.integer.spancount)
    int spancount;

    @Nullable
    private ServiceIdlingResource serviceIdlingResource;

    Context context;
    List<Recipe> cookbook = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        context = this;

        GridLayoutManager layoutManager = new GridLayoutManager(this, spancount);
        recyclerRecipes.setLayoutManager(layoutManager);

        CookBookServiceTask task = new CookBookServiceTask();
        task.execute();
    }

    class CookBookServiceTask extends AsyncTask<Void, Void, List<Recipe>> {

        @Override
        protected List<Recipe> doInBackground(Void... params) {

            if (serviceIdlingResource != null) {
                serviceIdlingResource.setIdleState(false);
            }

            List<Recipe> recipeList = CookbookService.getRecipes();
            return recipeList;
        }

        @Override
        protected void onPostExecute(List<Recipe> recipeList) {
            cookbook = recipeList;
            CookbookAdapter recipeAdapter = new CookbookAdapter(context, recipeList, recipeId -> onRecipeChosen(recipeId));
            recyclerRecipes.setAdapter(recipeAdapter);


            Handler handler = new Handler();
            handler.postDelayed(() -> {
                MainActivity.this.onDone();
                if (serviceIdlingResource != null) {
                    serviceIdlingResource.setIdleState(true);
                }
            }, 10000);
        }

    }

    public void onRecipeChosen(int recipeId) {
        Recipe chosenRecipe = cookbook.stream().filter(x -> x.getId() == recipeId).findFirst().get();
        Bundle bundle = new Bundle();
        bundle.putInt(Keys.chosenRecipeId, chosenRecipe.getId());
        Intent intent = new Intent(MainActivity.this, RecipeActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (serviceIdlingResource == null) {
            serviceIdlingResource = new ServiceIdlingResource();
        }
        return serviceIdlingResource;
    }

    public void onDone() {

    }

}
