package org.inspire.breath.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.inspire.breath.data.AppRoomDatabase;
import org.inspire.breath.data.Recording;
import org.inspire.breath.data.RecordingDao;
import org.inspire.breath.fragments.ThermometerFragment;
import org.inspire.breath.R;
public class ThermometerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thermometer_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, ThermometerFragment.newInstance())
                    .commitNow();
        }
     }
}
