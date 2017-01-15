/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zyx.espressosample;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.setFailureHandler;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.FailureHandler;

import android.support.test.espresso.base.DefaultFailureHandler;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;
import android.util.Log;
import android.view.View;

import org.hamcrest.Matcher;

/**
 * A sample of how to set a non-default {@link FailureHandler}.
 */
@LargeTest
public class CustomFailureHandlerTest extends ActivityInstrumentationTestCase2<MainActivity> {

  private static final String TAG = "CustomFailureHandlerTest";

  @SuppressWarnings("deprecation")
  public CustomFailureHandlerTest() {
    // This constructor was deprecated - but we want to support lower API levels.
    super("com.zyx.espressosample", MainActivity.class);
  }

  @Override
  public void setUp() throws Exception {
    super.setUp();
    getActivity();
    setFailureHandler(new CustomFailureHandler());
  }

  @Override
  public void tearDown() throws Exception {
    super.tearDown();
    Espresso.setFailureHandler(new DefaultFailureHandler(getTargetContext()));
  }

  public void testWithCustomFailureHandler() {
    try {
      onView(withText("does not exist")).perform(click());
    } catch (MySpecialException expected) {
      Log.e(TAG, "Special exception is special and expected: ", expected);
    }
  }

  /**
   * A {@link FailureHandler} that re-throws all exceptions as
   * {@link MySpecialException}.
   */
  private static class CustomFailureHandler implements FailureHandler {
    @Override
    public void handle(Throwable error, Matcher<View> viewMatcher) {
      throw new MySpecialException(error);
    }
  }

  private static class MySpecialException extends RuntimeException {
    MySpecialException(Throwable cause) {
      super(cause);
    }
  }
}
