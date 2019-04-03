package org.inspire.breath.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import org.inspire.breath.views.StaticPager;

import java.util.List;

public class PagerFragmentAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mFragments;

    public PagerFragmentAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.mFragments = fragments;
    }

    @Override
    public Fragment getItem(int i) {
        return mFragments.get(i);
    }

    public void focusItem(int i) {
        ((StaticPager.Focusable) mFragments.get(i)).onFocus();
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
