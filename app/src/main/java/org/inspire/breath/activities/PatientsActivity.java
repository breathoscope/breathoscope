package org.inspire.breath.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.inspire.breath.R;
import org.inspire.breath.data.AppRoomDatabase;
import org.inspire.breath.data.Patient;
import org.inspire.breath.data.PatientDao;

import java.util.LinkedList;
import java.util.List;

public class PatientsActivity extends AppCompatActivity {

    // TODO change this to be an add patient screen
    private final Class<?> NEXT_ACTIVITY = PatientsActivity.class;

    private RecyclerView mPatientList;
    private FloatingActionButton mAddPatientFAB;

    private void findViews() {
        mPatientList = findViewById(R.id.patient_list_recycler);
        mAddPatientFAB = findViewById(R.id.patient_list_add_patient);
    }

    private List<Patient> getAllPatients() {
        return AppRoomDatabase.getDatabase(this).patientDao().getAllPatients();
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

    private void initList() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mPatientList.setLayoutManager(layoutManager);

        mPatientList.setAdapter(new PatientListAdapter(getAllPatients()));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patients);
        findViews();

        initDB(); // needed for some debugging
        initList();

        // init the floating action button for adding patients
        mAddPatientFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PatientsActivity.this, NEXT_ACTIVITY));
            }
        });
    }

    public void onPatientSelected(int id) {
        System.out.println(id);
    }

    public class PatientListAdapter extends RecyclerView.Adapter {

        public class PatientListViewHolder extends RecyclerView.ViewHolder {

            private TextView mText;
            private Button mSelecter;

            private Patient mPatient;

            public int id;

            public PatientListViewHolder(@NonNull CardView root, Patient patient) {
                super(root);
                mText = root.findViewById(R.id.patient_list_holder_name);
                root.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PatientsActivity.this.onPatientSelected(id);
                    }
                });
                this.setPatient(patient);
            }

            public void setId(int id) {
                this.id = id;
            }

            public void setPatient(Patient patient) {
                this.mPatient = patient;
                this.mText.setText(mPatient.getFirstName() + ' ' + mPatient.getLastName());
            }

        }


        private List<Patient> mPatients;

        public PatientListAdapter(List<Patient> patients) {
            mPatients = patients;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            CardView rootView = (CardView) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.patient_list_holder, parent, false);
            return new PatientListViewHolder(rootView, mPatients.get(i));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            PatientListViewHolder holder = (PatientListViewHolder) viewHolder;
            holder.setId(i);
            holder.setPatient(mPatients.get(i));
        }

        @Override
        public int getItemCount() {
            return mPatients.size();
        }
    }
}
