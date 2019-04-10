package org.inspire.breath.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;

import org.inspire.breath.fragments.MalariaFragment;
import org.inspire.breath.R;
import org.inspire.breath.fragments.MalariaViewModel;

public class MalariaActivity extends TestActivity {
    int patientID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.malaria_activity);
        Intent i = getIntent();
        int id = i.getIntExtra(SESSION_ID_KEY, 0);
        MalariaViewModel viewModel = ViewModelProviders.of(this).get(MalariaViewModel.class);
        viewModel.sessionID = id;
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MalariaFragment.newInstance(), "MalariaCamera")
                    .commitNow();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        MalariaFragment current = (MalariaFragment) getSupportFragmentManager().findFragmentByTag("MalariaCamera");
        if (current != null && current.isVisible()) {
            current.onActivityResult(requestCode, resultCode, data);
        }
    }
}
