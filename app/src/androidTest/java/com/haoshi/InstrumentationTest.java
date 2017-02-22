package com.haoshi;

import android.support.test.rule.ActivityTestRule;

import com.haoshi.androidtest.AndroidTestActivity;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by yugu on 2017/2/22.
 */

public class InstrumentationTest {

    private static final String CONTENT = "haoshi";

    @Rule
    public ActivityTestRule<AndroidTestActivity> mainActivity = new ActivityTestRule<>(AndroidTestActivity.class);

    @Test
    public void send() {
        onView(withId(R.id.edit)).perform(typeText(CONTENT), closeSoftKeyboard());


        onView(withText("点击")).perform(click());


        onView(withId(R.id.text)).check(matches(withText(CONTENT)));
    }
}
