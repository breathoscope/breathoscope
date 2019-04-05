package org.inspire.breath.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.inspire.breath.R;
import org.inspire.breath.adapters.PagerFragmentAdapter;
import org.inspire.breath.fragments.patients.Create;
import org.inspire.breath.fragments.patients.Listings;
import org.inspire.breath.utils.FragmentedFragment;
import org.inspire.breath.views.StaticPager;

import java.util.LinkedList;
import java.util.List;

public class Patients extends FragmentedFragment {

    private List<Fragment> mFragments;
    private StaticPager mPager;

    public FloatingActionButton mAddPatientFAB;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_patients, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        setupData();
        setupListeners();
//        replaceFrag(R.id.fragment_patients_container, new Listings());
    }

    private void setupData() {
        mFragments = new LinkedList<>();
        mFragments.add(new Listings());
        mFragments.add(new Create());
        PagerFragmentAdapter adapter = new PagerFragmentAdapter(getFragmentManager(), mFragments);
        mPager.setAdapter(adapter);
    }

    private void setupListeners() {
        mAddPatientFAB.setOnClickListener(v -> {
            mPager.setCurrentItem(mPager.getCurrentItem() + 1, true);
            mAddPatientFAB.hide();
        });
    }

    private void findViews(View view) {
        mPager = view.findViewById(R.id.patient_pager);
        mAddPatientFAB = view.findViewById(R.id.patient_list_add_patient);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mPager.getCurrentItem() > 0) {
            mPager.setCurrentItem(mPager.getCurrentItem() - 1, true);
            mAddPatientFAB.show();
        }
    }
}