package org.inspire.breath.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
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
import org.inspire.breath.data.Patient;

import java.util.List;

public class PatientsActivity extends AppCompatActivity {

    private RecyclerView mPatientList;
    private List<Patient> dummyPatientData = null;

    private void findViews() {
        mPatientList = findViewById(R.id.patient_list_recycler);
    }

    private List<Patient> getPatients() {
        return dummyPatientData;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patients);
        findViews();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mPatientList.setLayoutManager(layoutManager);

        mPatientList.setAdapter(new PatientListAdapter(getPatients()));
    }

    public void onPatientSelected(int id) {
        System.out.println(id);
    }

    public class PatientListAdapter extends RecyclerView.Adapter {

        public class PatientListViewHolder extends RecyclerView.ViewHolder {

            private TextView mText;
            private Button mSelecter;

            public int id;

            public PatientListViewHolder(@NonNull CardView root) {
                super(root);
                mText = root.findViewById(R.id.patient_list_holder_name);
                mSelecter = root.findViewById(R.id.patient_list_select);
                mSelecter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PatientsActivity.this.onPatientSelected(id);
                    }
                });
            }

            public void setId(int id) {
                this.id = id;
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
            return new PatientListViewHolder(rootView);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            PatientListViewHolder holder = (PatientListViewHolder) viewHolder;
            holder.setId(i);
//            Patient item = mPatients.get(i);
        }

        @Override
        public int getItemCount() {
            return 5;
        }
    }
}
