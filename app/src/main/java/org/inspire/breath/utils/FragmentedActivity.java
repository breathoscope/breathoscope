package org.inspire.breath.utils;

import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;

public class FragmentedActivity extends AppCompatActivity {

    private Fragment currentFragment;

    public void replaceFrag(int id, Fragment fragment) {
        currentFragment = fragment;
        getSupportFragmentManager().beginTransaction()
                .replace(id, currentFragment)
                .commit();
    }
}
