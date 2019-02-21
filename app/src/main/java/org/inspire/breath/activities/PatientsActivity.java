package org.inspire.breath.activities;

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
import org.inspire.breath.fragments.Listings;
import org.inspire.breath.adapters.PagerFragmentAdapter;

import java.util.LinkedList;
import java.util.List;

public class PatientsActivity extends AppCompatActivity implements PatientListAdapter.PatientCallback {

    // TODO change this to be an add patient screen
    private final Class<?> NEXT_ACTIVITY = PatientsActivity.class;

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
        List<Fragment> fragments = new LinkedList<>();

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PatientsActivity.this.onPatientSelected(0); // TODO pass this in correctly
            }
        };
        fragments.add(new Listings());
        fragments.add(new Listings());
        PagerFragmentAdapter adapter = new PagerFragmentAdapter(getSupportFragmentManager(),fragments);
        mPager.setAdapter(adapter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patients);
        findViews();

        initDB(); // needed for some debugging
//        initList();

        initPager();

        // init the floating action button for adding patients TODO remove this but implement in listings
//        mAddPatientFAB.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(PatientsActivity.this, NEXT_ACTIVITY));
//            }
//        });
    }

    public void onPatientSelected(int id) {
        System.out.println(id);
    }


    @Override
    public void onSelected(Patient patient) {
        System.out.println(patient.getFirstName());
    }
}
