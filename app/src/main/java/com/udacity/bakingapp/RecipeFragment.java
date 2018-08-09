package com.udacity.bakingapp;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.udacity.bakingapp.adapter.RecipeAdapter;
import com.udacity.bakingapp.model.Ingredient;
import com.udacity.bakingapp.model.Recipe;
import com.udacity.bakingapp.model.Step;
import com.udacity.bakingapp.recipeservice.CookbookService;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RecipeFragment extends android.support.v4.app.Fragment {

    @BindView(R.id.ingredients)
    TextView ingredients;

    @BindView(R.id.recipe_steps)
    RecyclerView recipeSteps;

    @BindView(R.id.nested_scrollview)
    NestedScrollView nestedScrollview;



    private Unbinder unbinder;
    Context context;
    Recipe recipe = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe, parent, false);
        unbinder = ButterKnife.bind(this, rootView);
        context = getContext();
        Bundle bundle = getArguments();
        int recipeId  = bundle.getInt("chosenRecipeId");
        this.recipe = CookbookService.getRecipes().stream().filter(x -> x.getId() == recipeId).findFirst().get();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        RecipeAdapter adapter = new RecipeAdapter(context, recipe, recipeId -> onStepChosen(recipeId));
        ingredients.setText(getIngredientsList(recipe.getIngredients()));
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        recipeSteps.setLayoutManager(layoutManager);
        recipeSteps.setAdapter(adapter);
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    public String getIngredientsList(List<Ingredient> ingredients){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Ingredients:\r\n");
        for (int i = 0; i< ingredients.size(); i++){
            Ingredient ingredient = ingredients.get(i);
            stringBuilder.append(ingredient.getQuantity());
            stringBuilder.append(" ");
            stringBuilder.append(ingredient.getMeasure().toUpperCase().equals("UNIT") ? "" : ingredient.getMeasure());
            stringBuilder.append(" ");
            stringBuilder.append(" ");
            stringBuilder.append(ingredient.getIngredient());
            stringBuilder.append("\r\n");
        }
        return stringBuilder.toString().trim();
    }


    public void onStepChosen(int recipeId) {
        Step chosenStep = recipe.getSteps().stream().filter(x -> x.getId() == recipeId).findFirst().get();
        Bundle bundle = new Bundle();
        bundle.putInt("chosenStep", chosenStep.getId());
        Intent startChildActivityIntent = new Intent(context, StepActivity.class);
        startChildActivityIntent.putExtras(bundle);
        startActivity(startChildActivityIntent);
    }
}
