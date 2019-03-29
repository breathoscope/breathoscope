package org.inspire.breath.fragments.patients;

import android.support.v4.app.Fragment;

import org.inspire.breath.activities.PatientsActivity;
import org.inspire.breath.views.StaticPager;

public class PatientsFragment extends Fragment implements StaticPager.Focusable {

    PatientsActivity getPatientsActivity() {
        return (PatientsActivity) getActivity();
    }

    @Override
    public void onFocus() {
    }
}
