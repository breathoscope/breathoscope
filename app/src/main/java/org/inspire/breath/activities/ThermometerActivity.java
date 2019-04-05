package org.inspire.breath.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;

import org.inspire.breath.fragments.FeverFragment;
import org.inspire.breath.fragments.ThermometerFragment;
import org.inspire.breath.R;
import org.inspire.breath.fragments.ThermometerViewModel;

public class ThermometerActivity extends TestActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thermometer_activity);
        Intent i = getIntent();
        int id = i.getIntExtra(SESSION_ID_KEY, 0);
        ThermometerViewModel viewModel = ViewModelProviders.of(this).get(ThermometerViewModel.class);
        viewModel.sessionID = id;
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, ThermometerFragment.newInstance())
                    .commitNow();
        }
     }
}
