package com.udacity.bakingapp;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.udacity.bakingapp.adapter.RecipeAdapter;
import com.udacity.bakingapp.model.Recipe;
import com.udacity.bakingapp.recipeservice.CookbookStore;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CookbookFragment extends Fragment {

    @BindView(R.id.recipe_steps)
    RecyclerView recipe_steps;

    private Unbinder unbinder;
    Context context;
    Recipe recipe = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cookbook, parent, false);
        unbinder = ButterKnife.bind(this, rootView);
        context = getContext();

//        int recipeId  = savedInstanceState.getInt("chosenRecipeId");
        int recipeId = CookbookStore.getRecipeId();
        this.recipe = CookbookStore.getCookbook().get(recipeId);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        RecipeAdapter adapter = new RecipeAdapter(context, recipe, new ListItemClickListener() {
            @Override
            public void onListItemClick(int recipeId) {
//                onRecipeChosen(recipeId);
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        recipe_steps.setLayoutManager(layoutManager);
        recipe_steps.setAdapter(adapter);
    }


    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
