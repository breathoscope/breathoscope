package org.inspire.breath.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import org.inspire.breath.R;
import org.inspire.breath.data.AppRoomDatabase;
import org.inspire.breath.data.Patient;
import org.inspire.breath.data.Recording;

public class HomeActivity extends AppCompatActivity {

    public static final String PATIENT_ID_KEY = "PATIENT_ID_KEY";

    private Patient mPatient;
    private Recording mSession;

    public void getData() {
        Intent intent = getIntent();
        int patient_id = intent.getIntExtra(PATIENT_ID_KEY, -1);
        this.mPatient = AppRoomDatabase.getDatabase(this).patientDao().getPatientById(patient_id).get(0);
//        this.mSession = AppRoomDatabase.getDatabase(this).recordingDao().getAllRecordings().get(0);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getData();

    }
}
