package com.boss.cropanalyzerapp.onboarding;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class FragmentAdapter extends FragmentStatePagerAdapter {
    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new OnBoardingOneFragment();
        } else if (position == 1) {
            return new OnBoardingSecondFragment();
        } else if (position == 2) {
            return new OnBoardingThreeFragment();
        } else {
            return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
