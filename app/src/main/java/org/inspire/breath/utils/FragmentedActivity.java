package org.inspire.breath.utils;

import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;

public class FragmentedActivity extends AppCompatActivity {

    private FragmentedFragment currentFragment;

    public void replaceFrag(int id, FragmentedFragment fragment) {
        currentFragment = fragment;
        getSupportFragmentManager().beginTransaction()
                .replace(id, currentFragment)
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (!this.currentFragment.onBackPressed()) {
            super.onBackPressed();
        }
    }
}
