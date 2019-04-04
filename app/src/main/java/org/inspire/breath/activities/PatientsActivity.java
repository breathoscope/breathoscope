package org.inspire.breath.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.inspire.breath.R;
import org.inspire.breath.adapters.PatientListAdapter;
import org.inspire.breath.data.AppRoomDatabase;
import org.inspire.breath.data.Patient;
import org.inspire.breath.data.PatientDao;

import org.inspire.breath.data.Session;import org.inspire.breath.fragments.patients.Create;
import org.inspire.breath.fragments.patients.Listings;
import org.inspire.breath.adapters.PagerFragmentAdapter;

import java.util.LinkedList;
import java.util.List;

public class PatientsActivity extends AppCompatActivity implements PatientListAdapter.PatientCallback {

    private final Class<?> RECORDING_ACTIVITY = HomeActivity.class;

    private List<Fragment> mFragments;

    private Listings mListings;
    private Create mCreate;

    private ViewPager mPager;
    public FloatingActionButton mAddPatientFAB;

    private Session mSession;

    private void findViews() {
        mAddPatientFAB = findViewById(R.id.patient_list_add_patient);
        mPager = findViewById(R.id.patient_pager);
    }

    private List<Patient> getDummyPatientData() {
        List<Patient> patients = new LinkedList<>();

        Patient patientMC = new Patient();
        patientMC.setFirstName("Max");
        patientMC.setLastName("Caulfield");
        patientMC.setAge("18");
        patientMC.setSex(Patient.FEMALE);
        patients.add(patientMC);

        Patient patientCP = new Patient();
        patientCP.setFirstName("Chloe");
        patientCP.setLastName("Price");
        patientCP.setAge("19");
        patientCP.setSex(Patient.FEMALE);
        patients.add(patientCP);

        return patients;
    }

    private void initDummyDb() {
        List<Patient> dummyData = getDummyPatientData();
        PatientDao dao = AppRoomDatabase.getDatabase().patientDao();
        for (Patient patient : dummyData) {
            dao.insertPatient(patient);
        }
    }

    private void clearDb() {
        AppRoomDatabase.getDatabase()
                .patientDao()
                .deleteAllPatients();
    }

    private void initPager() {
        mFragments = new LinkedList<>();
        mFragments.add(new Listings());
        mFragments.add(new Create());
        PagerFragmentAdapter adapter = new PagerFragmentAdapter(getSupportFragmentManager(), mFragments);
        mPager.setAdapter(adapter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patients);
        findViews();

//        clearDb(); // for debugging always clear the db first
//        initDummyDb();

        initPager();

        mSession = new Session();
        mSession.setTimestamp(System.currentTimeMillis()/1000);

        // init the floating action button for adding patients
        mAddPatientFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayAddPatient();
            }
        });
    }


    public void displayAddPatient() {
        mPager.setCurrentItem(mPager.getCurrentItem() + 1, true);
    }

    @Override
    public void onSelected(Patient patient) {
        startRecordingFor(patient);
    }

    public void startRecordingFor(Patient patient) {
        mSession.setPatientId(patient.getPatientId());

        AppRoomDatabase.getDatabase().sessionDao().insertRecording(mSession);
        Session session = AppRoomDatabase.getDatabase().sessionDao().getRecordings(patient.getPatientId()).get(0);
        Intent intent = new Intent(this, RECORDING_ACTIVITY);
        intent.putExtra(HomeActivity.PATIENT_ID_KEY, patient.getPatientId());
        intent.putExtra(HomeActivity.SESSION_ID_KEY, session.getId());
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() > 0) {
            mPager.setCurrentItem(mPager.getCurrentItem() - 1, true);
        }
        else {
            super.onBackPressed();
        }
    }
}
