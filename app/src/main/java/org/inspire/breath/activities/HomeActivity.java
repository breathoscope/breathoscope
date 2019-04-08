package org.inspire.breath.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import org.inspire.breath.R;
import org.inspire.breath.data.Patient;
import org.inspire.breath.fragments.home.Admin;
import org.inspire.breath.fragments.home.Home;
import org.inspire.breath.utils.FragmentedActivity;
import org.inspire.breath.utils.FragmentedFragment;

public class HomeActivity extends FragmentedActivity {

    public static final String PATIENT_ID_KEY = "PATIENT_ID_KEY";
    public static final String SESSION_ID_KEY = "SESSION_ID_KEY";

    FragmentedFragment current;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        current = new Admin();
        replaceFrag(R.id.home_container, current);
    }

    public void startRecording(Patient patient) {
        current = new Home();
        replaceFrag(R.id.home_container, current);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        this.current.onActivityResult(requestCode, resultCode, data);
    }
}
