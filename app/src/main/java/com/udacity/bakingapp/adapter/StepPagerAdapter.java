package com.udacity.bakingapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.udacity.bakingapp.StepFragment;
import com.udacity.bakingapp.model.Step;

import java.util.List;

public class StepPagerAdapter extends FragmentPagerAdapter {

    List<Step> stepList;

    public StepPagerAdapter(FragmentManager fragmentManager, List<Step> stepList) {
        super(fragmentManager);
        this.stepList = stepList;
    }

    // Returns total number of pages
    @Override
    public int getCount() {
        return stepList.size();
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {
        StepFragment stepFragment = new StepFragment();
        Step step = stepList.get(position);
        stepFragment.bind(step);
        return stepFragment;
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        return "Step " + (position + 1);
    }

}

