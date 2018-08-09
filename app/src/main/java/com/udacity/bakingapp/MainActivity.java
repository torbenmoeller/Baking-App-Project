package com.udacity.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.udacity.bakingapp.adapter.CookbookAdapter;
import com.udacity.bakingapp.model.Recipe;
import com.udacity.bakingapp.recipeservice.CookbookService;

import java.util.List;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recycler_recipes)
    RecyclerView recyclerRecipes;
    @BindInt(R.integer.spancount)
    int spancount;

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
            List<Recipe> recipeList = CookbookService.getRecipes();
            return recipeList;
        }

        @Override
        protected void onPostExecute(List<Recipe> recipeList) {
            cookbook = recipeList;
            CookbookAdapter recipeAdapter = new CookbookAdapter(context, recipeList, recipeId -> onRecipeChosen(recipeId));
            recyclerRecipes.setAdapter(recipeAdapter);
        }

    }

    public void onRecipeChosen(int recipeId) {
        Recipe chosenRecipe = cookbook.stream().filter(x -> x.getId() == recipeId).findFirst().get();
        Bundle bundle = new Bundle();
        bundle.putInt("chosenRecipeId", chosenRecipe.getId());
        Intent intent = new Intent(MainActivity.this, RecipeActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
