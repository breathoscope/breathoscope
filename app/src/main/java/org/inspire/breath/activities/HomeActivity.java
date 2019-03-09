package org.inspire.breath.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import org.inspire.breath.R;
import org.inspire.breath.adapters.TestListAdapter;
import org.inspire.breath.data.AppRoomDatabase;
import org.inspire.breath.data.Patient;
import org.inspire.breath.data.Recording;
import org.inspire.breath.data.Test;
import org.inspire.breath.data.blobs.FeverTestResult;
import org.inspire.breath.data.blobs.MalariaTestResult;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    public static final String PATIENT_ID_KEY = "PATIENT_ID_KEY";
    public static final String SESSION_ID_KEY = "SESSION_ID_KEY";
    private static final int TEST_COUNT = 5;

    private Patient mPatient;
    private Recording mSession;
    private RecyclerView mTestsList;
    private TextView mPatientName;
    private TextView mPatientAge;
    private TextView mPatientSex;
    private ImageView mPatientPicture;

    public void getData() {
        Intent intent = getIntent();
        int patient_id = intent.getIntExtra(PATIENT_ID_KEY, -1);
        int session_id = intent.getIntExtra(SESSION_ID_KEY, -1);
        this.mPatient = AppRoomDatabase.getDatabase().patientDao().getPatientById(patient_id).get(0);
        this.mSession = AppRoomDatabase.getDatabase().recordingDao().getRecordingById(session_id).get(0);
    }


    private void findViews() {
        this.mPatientName = findViewById(R.id.home_patient_name);
        this.mPatientAge = findViewById(R.id.home_patient_age);
        this.mPatientSex = findViewById(R.id.home_patient_sex);
        this.mPatientPicture = findViewById(R.id.home_patient_picture);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        findViews();
        getData();
    }

}
