package com.edwin.redditclient;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class MyUiTests {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void clickingOnSubredditsItemGoesToDetailsPage() {
        onView(withId(R.id.subreddits_recyclerview))
                .perform(actionOnItemAtPosition(0, click()));

        onView(withId(R.id.details_description_textview)).check(matches(isDisplayed()));
    }

    @Test
    public void pressingBackInDetailsPageGoesToSubredditsList() {
        clickingOnSubredditsItemGoesToDetailsPage();

        pressBack();

        onView(withId(R.id.subreddits_recyclerview)).check(matches(isDisplayed()));
    }
}