package org.inspire.breath.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.inspire.breath.R;
import org.inspire.breath.data.Patient;
import org.inspire.breath.fragments.home.Admin;
import org.inspire.breath.fragments.home.Home;
import org.inspire.breath.utils.FragmentedActivity;
import org.inspire.breath.utils.FragmentedFragment;
import org.inspire.breath.data.Session;
import org.inspire.breath.data.blobs.BreathTestResult;
import org.inspire.breath.data.blobs.DangerTestResult;
import org.inspire.breath.data.blobs.DiarrhoeaTestResult;
import org.inspire.breath.data.blobs.FeverTestResult;
import org.inspire.breath.data.blobs.HeartRateTestResult;
import org.inspire.breath.data.blobs.HrCountTest;
import org.inspire.breath.data.blobs.MalariaTestResult;
import org.inspire.breath.data.blobs.RecommendActionsResult;

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
