package com.udacity.bakingapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RecipeFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe, parent, false);
        return  rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
    }
}
