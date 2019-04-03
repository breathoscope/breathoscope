package org.inspire.breath.activities;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import org.inspire.breath.R;

import org.inspire.breath.fragments.BreathRateFragment;
import org.inspire.breath.fragments.BreathRateViewModel;

public class BreathRateActivity extends TestActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.breath_rate_activity);
        Intent i = getIntent();
        int id = i.getIntExtra(SESSION_ID_KEY, 0);
        BreathRateViewModel viewModel = ViewModelProviders.of(this).get(BreathRateViewModel.class);
        viewModel.sessionID = id;
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, BreathRateFragment.newInstance())
                    .commitNow();
        }
    }
}
