package org.inspire.breath.utils;

import android.support.v4.app.Fragment;

public class FragmentedFragment extends Fragment {

    public FragmentedFragment currentFragment;
    public FragmentedFragment parentFragment;

    public void setParent(FragmentedFragment fragmentedFragment) {
        this.parentFragment = fragmentedFragment;
    }

    public void replaceFrag(int id, FragmentedFragment fragment) {
        this.currentFragment = fragment;
        this.currentFragment.setParent(this);
        getChildFragmentManager().beginTransaction()
                .replace(id, currentFragment)
                .addToBackStack(null)
                .commit();
    }

    public boolean onBackPressed() {
        if (currentFragment != null)
            return currentFragment.onBackPressed();
        else
            return false;
    }
}
