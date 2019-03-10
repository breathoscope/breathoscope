package org.inspire.breath.fragments.patients;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.inspire.breath.R;
import org.inspire.breath.activities.PatientsActivity;
import org.inspire.breath.adapters.PatientListAdapter;
import org.inspire.breath.data.AppRoomDatabase;
import org.inspire.breath.data.Patient;

import java.util.List;

public class Listings extends Fragment {

    private RecyclerView mPatientList;

    private View.OnClickListener mListener;

    private void findViews() {
        mPatientList = getView().findViewById(R.id.patient_list_recycler);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_patient_list, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews();
        initList();
    }

    private List<Patient> getAllPatients() {
        return AppRoomDatabase.getDatabase().patientDao().getAllPatients();
    }


    private void initList() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mPatientList.setLayoutManager(layoutManager);
        mPatientList.setAdapter(new PatientListAdapter(getAllPatients(), (PatientsActivity) getActivity()));
    }
}
