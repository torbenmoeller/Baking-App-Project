package com.udacity.bakingapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.udacity.bakingapp.adapter.RecipeAdapter;
import com.udacity.bakingapp.model.Ingredient;
import com.udacity.bakingapp.model.Recipe;
import com.udacity.bakingapp.recipeservice.CookbookService;
import com.udacity.bakingapp.widget.AppWidgetService;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class RecipeFragment extends android.support.v4.app.Fragment {

    @BindView(R.id.ingredients)
    TextView ingredients;

    @BindView(R.id.recipe_steps)
    RecyclerView recipeSteps;

    @BindView(R.id.nested_scrollview)
    NestedScrollView nestedScrollview;

    @BindString(R.string.widget_update_success)
    String widgetUpdateSuccess;


    private Unbinder unbinder;
    OnStepSelected mCallback;
    Context context;
    Recipe recipe = null;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe, parent, false);
        unbinder = ButterKnife.bind(this, rootView);
        context = getContext();
        Bundle bundle = getArguments();
        int recipeId  = bundle.getInt(Keys.chosenRecipeId);
        this.recipe = CookbookService.getRecipes().stream().filter(x -> x.getId() == recipeId).findFirst().get();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        RecipeAdapter adapter = new RecipeAdapter(context, recipe, recipeId -> mCallback.onStepSelection(recipeId));
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

    @OnClick(R.id.add_to_widget)
    public void onAddWidgetClick(){
            AppWidgetService.updateWidget(context, recipe);

        Toast.makeText(getActivity(), widgetUpdateSuccess, Toast.LENGTH_LONG).show();
//            Misc.makeSnackBar(this, mParentLayout, String.format(getString(R.string.added_to_widget), mRecipe.getName()), false);

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

    // Override onAttach to make sure that the container activity has implemented the callback
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            mCallback = (OnStepSelected) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement "+ OnStepSelected.class.getSimpleName());
        }
    }


}
