package com.edwin.redditclient;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragList = new ArrayList<>();

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);

        fragList.add(new SubredditsListFragment());
        fragList.add(new SubredditDetailsFragment());
    }

    @Override
    public Fragment getItem(int position) {
        return fragList.get(position);
    }

    @Override
    public int getCount() {
        return fragList.size();
    }
}