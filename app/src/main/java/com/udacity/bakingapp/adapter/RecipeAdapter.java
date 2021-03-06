package com.udacity.bakingapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.bakingapp.ItemClickListener;
import com.udacity.bakingapp.R;
import com.udacity.bakingapp.model.Recipe;
import com.udacity.bakingapp.model.Step;
import com.udacity.bakingapp.viewholder.StepViewHolder;

public class RecipeAdapter extends RecyclerView.Adapter<StepViewHolder> {

    private Context context;
    private Recipe recipe;
    private ItemClickListener clickListener;

    public RecipeAdapter(Context context, Recipe recipe, ItemClickListener onItemClickListener) {
        this.context = context;
        this.recipe = recipe;
        this.clickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layout = R.layout.step_viewholder;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layout, viewGroup, false);
        return new StepViewHolder(view, context, clickListener);

    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, final int position) {
        final Step step = recipe.getSteps().get(position);
        holder.bind(step);
    }


    @Override
    public int getItemCount() {
        return recipe.getSteps().size();
    }
}
