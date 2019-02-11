package org.inspire.breath.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.inspire.breath.fragments.MalariaFragment;
import org.inspire.breath.R;

public class MalariaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.malaria_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MalariaFragment.newInstance())
                    .commitNow();
        }
    }
}
