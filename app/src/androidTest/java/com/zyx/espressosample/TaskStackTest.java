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

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import android.support.test.filters.SdkSuppress;
import android.support.test.espresso.NoActivityResumedException;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.Suppress;

public class TaskStackTest extends ActivityInstrumentationTestCase2<TaskStackActivity> {

  public TaskStackTest() {
    super(TaskStackActivity.class);
  }

  @Override
  public void setUp() throws Exception {
    super.setUp();
    getActivity();
  }

  // The task stack behavior is available on API level 11 and up.
  @SdkSuppress(minSdkVersion=11)
  public void testTaskStack() {
    onView(withText("display activity")).check(matches(isDisplayed()));
    pressBack();
    onView(withText("tool bar activity")).check(matches(isDisplayed()));
    pressBack();
    onView(withText("drawer activity")).check(matches(isDisplayed()));
  }

  /**
   * Test only passes if run in isolation. Unless Gradle supports a single instrumentation
   * per test this test is ignored"
   */
  @Suppress
  @SdkSuppress(minSdkVersion=11)
  public void testBackExitsApp() throws InterruptedException {
    onView(withText("display activity")).check(matches(isDisplayed()));
    pressBack();
    pressBack();
    try {
      pressBack();
      fail("Should have exited the app and thrown a NoActivityResumedException");
    } catch (NoActivityResumedException e) {
      return;
    }
  }
}
