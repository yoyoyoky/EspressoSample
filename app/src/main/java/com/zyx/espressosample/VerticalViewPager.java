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

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Vertical ViewPager which overrides onTouchEvent to swap the MotionEvent
 */
public class VerticalViewPager extends ViewPager {

  public VerticalViewPager(Context context) {
    super(context);
    setPageTransformer(true, new VerticalPageTransformer());
    setOverScrollMode(OVER_SCROLL_NEVER);
  }

  public VerticalViewPager(Context context, AttributeSet attrs) {
    super(context, attrs);
    setPageTransformer(true, new VerticalPageTransformer());
    setOverScrollMode(OVER_SCROLL_NEVER);
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    event.setLocation(event.getY() * getWidth() / getHeight(),
        event.getX() * getHeight() / getWidth());
    return super.onTouchEvent(event);
  }

  /**
   * Vertical PageTransformer which changes the animation for from horizontal to vertical.
   */
  private class VerticalPageTransformer implements PageTransformer {

    @Override
    public void transformPage(View  view, float position) {
        view.setTranslationX(-view.getWidth() * position);
        view.setTranslationY(view.getHeight() * position);
    }}
}
