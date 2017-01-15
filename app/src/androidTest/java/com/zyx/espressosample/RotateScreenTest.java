package com.zyx.espressosample;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.test.InstrumentationRegistry;
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
 * Created by Administrator on 2017/1/15.
 */

public class RotateScreenTest extends ActivityInstrumentationTestCase2<TransitionActivityMain> {
    Activity activity;

    public RotateScreenTest() {
        super(TransitionActivityMain.class);
    }

    @Before
    public void setUp(){
        activity = getActivity();
    }

    public void testRotateScreen() {
        Context context = InstrumentationRegistry.getTargetContext();
        int orientation = context.getResources().getConfiguration().orientation;
        activity.setRequestedOrientation(
                (orientation == Configuration.ORIENTATION_PORTRAIT) ? ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE : ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }

}
