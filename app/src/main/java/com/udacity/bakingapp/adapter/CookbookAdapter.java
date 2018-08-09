package com.udacity.bakingapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.bakingapp.ItemClickListener;
import com.udacity.bakingapp.R;
import com.udacity.bakingapp.model.Recipe;
import com.udacity.bakingapp.viewholder.RecipeViewHolder;

import java.util.List;

public class CookbookAdapter extends RecyclerView.Adapter<RecipeViewHolder> {

    private Context context;
    private List<Recipe> cookbook;
    private ItemClickListener clickListener;

    public CookbookAdapter(Context context, List<Recipe> cookbook, ItemClickListener onItemClickListener) {
        this.context = context;
        this.cookbook = cookbook;
        this.clickListener = onItemClickListener;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layout = R.layout.recipe_viewholder;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layout, viewGroup, false);
        return new RecipeViewHolder(view, context, clickListener);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, final int position) {
        final Recipe recipe = cookbook.get(position);
        holder.bind(recipe);
    }

    @Override
    public int getItemCount() {
        return cookbook.size();
    }


}
