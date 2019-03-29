package org.inspire.breath.fragments.patients;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.inspire.breath.R;

public class Create extends PatientsFragment  {

    Spinner mSexSpinner;

    void findViews(View root) {
        this.mSexSpinner = root.findViewById(R.id.create_sex_spinner);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_patient_create, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        setupData();
    }

    private void setupData() {
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.sexes_array,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.mSexSpinner.setAdapter(adapter);

    }

    @Override
    public void onFocus() {
        super.onFocus();
        getPatientsActivity().mAddPatientFAB.hide();
    }
}
