package org.inspire.breath.activities;

import android.support.v4.app.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import org.inspire.breath.R;
import org.inspire.breath.adapters.PatientListAdapter;
import org.inspire.breath.data.Patient;
import org.inspire.breath.fragments.Home;
import org.inspire.breath.fragments.Patients;
import org.inspire.breath.utils.FragmentedActivity;

public class HomeActivity extends FragmentedActivity {

    public static final String PATIENT_ID_KEY = "PATIENT_ID_KEY";
    public static final String SESSION_ID_KEY = "SESSION_ID_KEY";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        replaceFrag(R.id.home_container, new Patients());
    }

    public void startRecording(Patient patient) {
        replaceFrag(R.id.home_container, new Home());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
