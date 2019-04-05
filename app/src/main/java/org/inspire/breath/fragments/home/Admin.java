package org.inspire.breath.fragments.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.inspire.breath.R;
import org.inspire.breath.adapters.PagerFragmentAdapter;
import org.inspire.breath.adapters.PatientListAdapter;
import org.inspire.breath.data.Patient;
import org.inspire.breath.utils.FragmentedFragment;
import org.inspire.breath.views.StaticPager;
import android.support.v4.app.Fragment;

import java.util.LinkedList;
import java.util.List;

public class Admin extends FragmentedFragment implements PatientListAdapter.PatientCallback {

    private StaticPager mPager;
    private List<Fragment> mFragments;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_admin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragments = new LinkedList<>();
        mFragments.add(new Home());
        mFragments.add(new Patients().setCallback(this));
        mPager = view.findViewById(R.id.admin_pager);
        mPager.setSliding(false);
        PagerFragmentAdapter adapter = new PagerFragmentAdapter(getChildFragmentManager(), mFragments);
        mPager.setAdapter(adapter);

        mPager.setCurrentItem(1, false);
        Patients patients = new Patients().setCallback(this);
    }

    @Override
    public void onSelected(Patient patient) {
        mPager.setCurrentItem(0,true);
    }

    @Override
    public boolean onBackPressed() {
        mPager.setCurrentItem(1, true);
        return true;
    }
}
