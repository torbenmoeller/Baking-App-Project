package com.udacity.bakingapp;

import android.app.Activity;
import android.os.Bundle;

import butterknife.ButterKnife;

public class StepActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
        ButterKnife.bind(this);
    }

}
