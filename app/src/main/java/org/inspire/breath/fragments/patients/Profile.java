package org.inspire.breath.fragments.patients;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.inspire.breath.R;


public class Profile extends Fragment {

    public static final String PATIENT_ID_KEY = "PATIENT_ID_KEY";
    public static final int DEFAULT_PATIENT_ID = -1;

    private int mPatientId;

    private void findViews() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_patient_profile, container, false);

        mPatientId = getArguments().getInt(PATIENT_ID_KEY, DEFAULT_PATIENT_ID);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews();
    }
}
