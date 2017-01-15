package com.zyx.espressosample;

import android.support.test.filters.SdkSuppress;
import android.test.ActivityInstrumentationTestCase2;

import org.junit.Before;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

/**
 * Created by zhangyouxuan on 2016/11/1.
 */
public class ToastTest extends ActivityInstrumentationTestCase2<TransitionActivityMain> {

    public ToastTest() {
        super(TransitionActivityMain.class);
    }

    @Before
    public void setUp(){
        getActivity();
    }

    // This test only applies to Lollipop+
    @SdkSuppress(minSdkVersion=21)
    public void testInterruptedBackDoesntExit() {
        // Set a flag in the activity to intercept the back button.
        ((TransitionActivityMain) getActivity()).setExitOnBackPressed(false);
        pressBack();
        pressBack();
        pressBack();
        // toast show that activity doesn't exit.
        onView(withText("Back was pressed but intercepted.")).inRoot(withDecorView(not(is(getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        onView(withId(R.id.grid)).check(matches(isDisplayed()));
    }
}
