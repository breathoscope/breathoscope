package org.inspire.breath.fragments.home;

import android.content.Intent;
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
import org.inspire.breath.adapters.PatientListAdapter;
import org.inspire.breath.utils.FragmentedFragment;
import org.inspire.breath.views.StaticPager;

import java.util.LinkedList;
import java.util.List;

public class Patients extends FragmentedFragment implements StaticPager.Focusable {

    private List<Fragment> mFragments;
    private StaticPager mPager;
    private Create create;
    public FloatingActionButton mAddPatientFAB;

    PatientListAdapter.PatientCallback callback;

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
    }

    private void setupData() {
        mFragments = new LinkedList<>();
        Listings listings = new Listings().setCallback(this.callback);
        mFragments.add(listings);
        create = new Create();
        mFragments.add(create);
        PagerFragmentAdapter adapter = new PagerFragmentAdapter(getChildFragmentManager(), mFragments);
        mPager.setAdapter(adapter);
    }

    public void setFab(FloatingActionButton fab) {
        this.mAddPatientFAB = fab;
    }

    private void setupListeners() {
//        mAddPatientFAB.setOnClickListener(v -> {
//            mPager.setCurrentItem(mPager.getCurrentItem() + 1, true);
//            mAddPatientFAB.hide();
//        });
    }

    public void moveToCreate() {
        mPager.setCurrentItem(mPager.getCurrentItem() + 1, true);
        mAddPatientFAB.hide();
    }

    private void findViews(View view) {
        mPager = view.findViewById(R.id.patient_pager);
//        mAddPatientFAB = view.findViewById(R.id.patient_list_add_patient);
    }

    @Override
    public boolean onBackPressed() {
        if (mPager.getCurrentItem() > 0) {
            mPager.setCurrentItem(mPager.getCurrentItem() - 1, true);
            mAddPatientFAB.show();
            return true;
        }
        return false;
    }

    public Patients setCallback(PatientListAdapter.PatientCallback callback) {
        this.callback = callback;
        return this;
    }

    @Override
    public void onFocus() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mPager.getCurrentItem() == 1) {
            create.onActivityResult(requestCode, resultCode, data);
        }
    }
}
