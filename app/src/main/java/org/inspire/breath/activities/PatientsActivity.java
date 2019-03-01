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
import org.inspire.breath.fragments.patients.Listings;
import org.inspire.breath.adapters.PagerFragmentAdapter;
import org.inspire.breath.fragments.patients.Profile;

import java.util.LinkedList;
import java.util.List;

public class PatientsActivity extends AppCompatActivity implements PatientListAdapter.PatientCallback {

    private final Class<?> RECORDING_ACTIVITY = LoginActivity.class;

    private List<Fragment> mFragments;

    private Profile mProfile;

    private ViewPager mPager;
    private FloatingActionButton mAddPatientFAB;

    private void findViews() {
        mAddPatientFAB = findViewById(R.id.patient_list_add_patient);
        mPager = findViewById(R.id.patient_pager);
    }

    private List<Patient> getDummyPatientData() {
        List<Patient> patients = new LinkedList<>();

        Patient patientMC = new Patient();
        patientMC.setFirstName("Max");
        patientMC.setLastName("Caulfield");
        patientMC.setAge(18);
        patients.add(patientMC);

        Patient patientCP = new Patient();
        patientCP.setFirstName("Chloe");
        patientCP.setLastName("Price");
        patientCP.setAge(19);
        patients.add(patientCP);

        return patients;
    }

    private void initDB() {
        List<Patient> dummies = getDummyPatientData();

        PatientDao dao = AppRoomDatabase.getDatabase(this).patientDao();
        List<Patient> allPatients = dao.getAllPatients();

        if (!allPatients.equals(dummies)) {
            dao.deleteAllPatients();
            for (Patient patient : dummies) {
                dao.insertPatient(patient);
            }
        }
    }


    private void initPager() {
        mFragments = new LinkedList<>();
        mFragments.add(new Listings());
        Profile profile = new Profile();
        profile.setArguments(new Bundle());
        mFragments.add(profile);
        PagerFragmentAdapter adapter = new PagerFragmentAdapter(getSupportFragmentManager(), mFragments);
        mPager.setAdapter(adapter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patients);
        findViews();

        initDB(); // needed for some debugging

        initPager();

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
        Bundle args = new Bundle();
        args.putInt(Profile.PATIENT_ID_KEY,patient.getPatientId());
        mProfile.setArguments(args);
        //startRecordingFor(patient);
    }

    public void startRecordingFor(Patient patient) {
        Intent intent = new Intent(this, RECORDING_ACTIVITY);
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
