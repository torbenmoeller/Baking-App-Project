package com.udacity.bakingapp.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.udacity.bakingapp.ItemClickListener;
import com.udacity.bakingapp.R;
import com.udacity.bakingapp.model.Step;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StepViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.recipe_step_short_description)
    public TextView recipe_step_short_description;

    Step step;
    Context context;
    ItemClickListener itemClickListener;


    public StepViewHolder(View itemView, Context context, ItemClickListener itemClickListener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.context = context;
        this.itemClickListener = itemClickListener;
    }

    public void bind(Step step) {
        this.step = step;
        recipe_step_short_description.setText(step.getShortDescription());
    }

    @OnClick
    public void onClick(){
        itemClickListener.onItemClick(step.getId());
    }

}
