package com.example.application.travelbuddyapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                FindGuideFragment tab1 = new FindGuideFragment();
                return tab1;
            case 1:
                Find_stay_Fragment tab2 = new Find_stay_Fragment();
                return tab2;
            case 2:
                FindTravelFragment tab3 = new FindTravelFragment();
                return tab3;
            case 3:
                LocalCommFragment tab4 = new LocalCommFragment();
                return tab4;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Guides";
            case 1:
                return "Stay";
            case 2:
                return "Trips";
            case 3:
                return "Talk";
            default:
                return null;
        }
    }
    }