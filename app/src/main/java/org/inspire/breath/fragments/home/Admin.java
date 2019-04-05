package org.inspire.breath.fragments.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.inspire.breath.R;
import org.inspire.breath.adapters.PatientListAdapter;
import org.inspire.breath.data.Patient;
import org.inspire.breath.utils.FragmentedFragment;

public class Admin extends FragmentedFragment implements PatientListAdapter.PatientCallback {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_admin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Patients patients = new Patients().setCallback(this);
        replaceFrag(R.id.admin_container, patients);
    }

    @Override
    public void onSelected(Patient patient) {
        Home home = new Home();
        home.setPatient(patient);
        getChildFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .setCustomAnimations(R.animator.slide_in_right, R.animator.slide_out_left,0, 0)
                .replace(R.id.admin_container, new Home())
                .addToBackStack(null)
                .commit();
    }
}
