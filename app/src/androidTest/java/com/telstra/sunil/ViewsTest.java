package com.telstra.sunil;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.telstra.sunil.utility.Utils;
import com.telstra.sunil.view.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class ViewsTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testBasicAppLaunched() {
        onView(withText(R.string.load_data)).check(matches(isDisplayed()));
    }

    @Test
    public void testBasicNetworkError() {
        if (!Utils.isNetworkAvailable(mActivityRule.getActivity().getApplicationContext())) {
            onView(withText(R.string.callback_error)).check(matches(isDisplayed()));
        }
    }

    @Test
    public void testClickOnFAB() {
        onView(withId(R.id.fab)).perform(click());
    }


    //TODO : Implement idling resources for testing RecyclerView after network call


}