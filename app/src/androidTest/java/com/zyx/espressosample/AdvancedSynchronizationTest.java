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
import static android.support.test.espresso.Espresso.registerIdlingResources;
import static android.support.test.espresso.Espresso.unregisterIdlingResources;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.google.common.base.Preconditions.checkNotNull;

import com.zyx.espressosample.SyncActivity.HelloWorldServer;

import android.support.test.espresso.idling.CountingIdlingResource;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;

/**
 * Example for {@link CountingIdlingResource}. Demonstrates how to wait on a delayed response from
 * request before continuing with a test.
 */
@LargeTest
public class AdvancedSynchronizationTest extends ActivityInstrumentationTestCase2<SyncActivity> {
  private CountingIdlingResource countingResource;

  private class DecoratedHelloWorldServer implements HelloWorldServer {
    private final HelloWorldServer realHelloWorldServer;
    private final CountingIdlingResource helloWorldServerIdlingResource;

    private DecoratedHelloWorldServer(HelloWorldServer realHelloWorldServer,
        CountingIdlingResource helloWorldServerIdlingResource) {
      this.realHelloWorldServer = checkNotNull(realHelloWorldServer);
      this.helloWorldServerIdlingResource = checkNotNull(helloWorldServerIdlingResource);
    }

    @Override
    public String getHelloWorld() {
      // Use CountingIdlingResource to track in-flight calls to getHelloWorld (a simulation of a
      // network call). Whenever the count goes to zero, Espresso will be notified that this
      // resource is idle and the test will be able to proceed.
      helloWorldServerIdlingResource.increment();
      try {
        return realHelloWorldServer.getHelloWorld();
      } finally {
        helloWorldServerIdlingResource.decrement();
      }
    }
  }

  @SuppressWarnings("deprecation")
  public AdvancedSynchronizationTest() {
    // This constructor was deprecated - but we want to support lower API levels.
    super("com.zyx.espressosample", SyncActivity.class);
  }

  @Override
  public void setUp() throws Exception {
    super.setUp();
    SyncActivity activity = getActivity();
    HelloWorldServer realServer = activity.getHelloWorldServer();
    // Here, we use CountingIdlingResource - a common convenience class - to track the idle state of
    // the server. You could also do this yourself, by implementing the IdlingResource interface.
    countingResource = new CountingIdlingResource("HelloWorldServerCalls");
    activity.setHelloWorldServer(new DecoratedHelloWorldServer(realServer, countingResource));
    assertTrue(registerIdlingResources(countingResource));
  }

  @Override
  public void tearDown() throws Exception {
    assertTrue(unregisterIdlingResources(countingResource));
    super.tearDown();
  }

  public void testCountingIdlingResource() {
    // Request the "hello world!" text by clicking on the request button.
    onView(withId(R.id.request_button)).perform(click());

    // Espresso waits for the resource to go idle and then continues.

    // The check if the text is visible can pass now.
    onView(withId(R.id.status_text)).check(matches(withText(R.string.hello_world)));
  }
}
