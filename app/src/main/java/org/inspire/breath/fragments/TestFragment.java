package org.inspire.breath.fragments;

import android.support.v4.app.Fragment;

import org.inspire.breath.activities.TestActivity;
import org.inspire.breath.data.Recording;

public class TestFragment extends Fragment {

    TestActivity getTestActivity() {
        return (TestActivity) getActivity();
    }

    Recording getSession() {
        return getTestActivity().getSession();
    }
}
