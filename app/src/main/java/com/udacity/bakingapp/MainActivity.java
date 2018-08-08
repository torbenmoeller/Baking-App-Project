package com.udacity.bakingapp;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.udacity.bakingapp.adapter.CookbookAdapter;
import com.udacity.bakingapp.adapter.RecipeAdapter;
import com.udacity.bakingapp.model.Recipe;
import com.udacity.bakingapp.recipeservice.CookbookService;
import com.udacity.bakingapp.recipeservice.CookbookStore;

import java.util.List;

import butterknife.BindBool;
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
            CookbookService service = new CookbookService();
            List<Recipe> recipeList = service.getRecipes();
            return recipeList;
        }

        @Override
        protected void onPostExecute(List<Recipe> recipeList) {
            CookbookStore.setCookbook(recipeList);
            cookbook = recipeList;
            CookbookAdapter recipeAdapter = new CookbookAdapter(context, recipeList, new ListItemClickListener() {
                @Override
                public void onListItemClick(int recipeId) {
                    onRecipeChosen(recipeId);
                }
            });
            recyclerRecipes.setAdapter(recipeAdapter);
        }

    }

    public void onRecipeChosen(int recipeId) {
        Recipe chosenRecipe = null;
        for (Recipe i: cookbook) {
            if(i.getId() == recipeId){
                chosenRecipe = i;

            }
        }
//        Bundle args = new Bundle();
//        args.putInt("chosenRecipeId", chosenRecipe.getId());
        CookbookStore.setRecipeId(chosenRecipe.getId());
        Intent startChildActivityIntent = new Intent(MainActivity.this, RecipeActivity.class);
        startChildActivityIntent.putExtra(Intent.EXTRA_TEXT, chosenRecipe.getId());
        startActivity(startChildActivityIntent);
    }
}
