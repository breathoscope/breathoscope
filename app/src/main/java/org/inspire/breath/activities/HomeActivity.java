package org.inspire.breath.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;

import org.inspire.breath.R;
import org.inspire.breath.data.Patient;
import org.inspire.breath.fragments.home.Admin;
import org.inspire.breath.fragments.home.Home;
import org.inspire.breath.utils.FragmentedActivity;

public class HomeActivity extends FragmentedActivity {

    public static final String PATIENT_ID_KEY = "PATIENT_ID_KEY";
    public static final String SESSION_ID_KEY = "SESSION_ID_KEY";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        replaceFrag(R.id.home_container, new Admin());
    }

    public void startRecording(Patient patient) {
        replaceFrag(R.id.home_container, new Home());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
