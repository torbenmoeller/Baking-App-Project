package com.udacity.bakingapp.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udacity.bakingapp.ItemClickListener;
import com.udacity.bakingapp.R;
import com.udacity.bakingapp.model.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecipeViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.recipe_image)
    ImageView imageView;

    @BindView(R.id.recipe_image_alternative)
    TextView recipeName;

    Recipe recipe;
    Context context;
    ItemClickListener itemClickListener;



    public RecipeViewHolder(View itemView, Context context, ItemClickListener itemClickListener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.context = context;
        this.itemClickListener = itemClickListener;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public TextView getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(TextView recipeName) {
        this.recipeName = recipeName;
    }

    public void bind(Recipe recipe) {
        this.recipe = recipe;
        String image = recipe.getImage();
        if (!image.isEmpty()) {
            Picasso.with(this.context)
                    .load(image)
                    .into(imageView);
            imageView.setVisibility(View.VISIBLE);
        }else{
            imageView.setVisibility(View.INVISIBLE);
            recipeName.setText(recipe.getName());
        }
    }

    @OnClick
    public void OnClick(View view){
        this.itemClickListener.onItemClick(recipe.getId());
    }
}
