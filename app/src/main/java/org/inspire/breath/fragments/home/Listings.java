package org.inspire.breath.fragments.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.inspire.breath.R;
import org.inspire.breath.adapters.PatientListAdapter;
import org.inspire.breath.data.AppRoomDatabase;
import org.inspire.breath.data.Patient;
import org.inspire.breath.views.StaticPager;

import android.support.v4.app.Fragment;
import android.view.animation.Animation;


import java.util.List;

public class Listings extends Fragment implements PatientListAdapter.PatientCallback, StaticPager.Focusable {

    private RecyclerView mPatientList;

    private View.OnClickListener mListener;
    private PatientListAdapter.PatientCallback callback;

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
        mPatientList.setAdapter(new PatientListAdapter(getAllPatients(), this.callback));
    }

    @Override
    public void onFocus() {
        updateRecycler();
    }

    public void updateRecycler() {
        List<Patient> patients = getAllPatients();
        if (mPatientList != null) {
            ((PatientListAdapter) mPatientList.getAdapter()).setPatients(patients);
        }
    }



    @Override
    public void onSelected(Patient patient) {
        callback.onSelected(patient);
    }

    public Listings setCallback(PatientListAdapter.PatientCallback callback) {
        this.callback = callback;
        return this;
    }
    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        System.out.println("animated");
//        System.out.println(R.animator.)
        System.out.println(transit + " " + enter + " " + nextAnim);

        return super.onCreateAnimation(transit, enter, nextAnim);
    }
}
