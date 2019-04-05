package org.inspire.breath.utils;

import android.support.v4.app.Fragment;

public class FragmentedFragment extends Fragment {

    public FragmentedFragment currentFragment;

    public void replaceFrag(int id, FragmentedFragment fragment) {
        this.currentFragment = fragment;
        getChildFragmentManager().beginTransaction()
                .replace(id, currentFragment)
                .commit();
    }

    public void onBackPressed() {
        currentFragment.onBackPressed();
    }
}
