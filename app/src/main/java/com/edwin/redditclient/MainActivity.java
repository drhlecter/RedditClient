package com.edwin.redditclient;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import hugo.weaving.DebugLog;

public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    @DebugLog
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(2);
    }

    @DebugLog
    void moveToDetailsFragment(Subreddit subredditParam) {

        ((SubredditDetailsFragment) mSectionsPagerAdapter.getItem(1)).updateDetails(MainActivity.this, subredditParam);

        mViewPager.setCurrentItem(1);
    }

    @Override
    public void onBackPressed() {
        if (mViewPager!= null && mViewPager.getCurrentItem()==1) {
            mViewPager.setCurrentItem(0);
        } else {
            moveTaskToBack(true);
        }
    }
}
