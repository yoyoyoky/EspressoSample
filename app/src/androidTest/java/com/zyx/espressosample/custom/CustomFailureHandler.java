package com.zyx.espressosample.custom;

import android.support.test.espresso.FailureHandler;
import android.view.View;

import org.hamcrest.Matcher;

/**
 * Created by zhangyouxuan on 2016/10/28.
 */
public class CustomFailureHandler implements FailureHandler {
    @Override
    public void handle(Throwable error, Matcher<View> viewMatcher) {
        throw new MySpecialException(error);
    }

    private static class MySpecialException extends RuntimeException {
        MySpecialException(Throwable cause) {
            super(cause);
        }
    }
}
