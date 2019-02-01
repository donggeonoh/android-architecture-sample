package com.example.android.architecture.redprints.sampleapp;

import android.app.Application;
import android.util.Log;

import java.io.IOException;

import io.reactivex.exceptions.UndeliverableException;
import io.reactivex.plugins.RxJavaPlugins;

/**
 * @author zeropol2
 * @since 2019. 2. 2.
 */
public class SampleApp extends Application {

    private static final String TAG = SampleApp.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        initRxJava();
    }

    private void initRxJava() {
        RxJavaPlugins.setErrorHandler(e -> {
            if (e instanceof UndeliverableException) {
                e = e.getCause();
            }
            if (e instanceof IOException) {
                // fine, irrelevant network problem or API that throws on cancellation
                return;
            }
            if (e instanceof InterruptedException) {
                // fine, some blocking code was interrupted by a dispose call
                return;
            }
            if ((e instanceof NullPointerException) || (e instanceof IllegalArgumentException)) {
                // that's likely a bug in the application
                Thread.currentThread().getUncaughtExceptionHandler()
                        .uncaughtException(Thread.currentThread(), e);
                return;
            }
            if (e instanceof IllegalStateException) {
                // that's a bug in RxJava or in a custom operator
                Thread.currentThread().getUncaughtExceptionHandler()
                        .uncaughtException(Thread.currentThread(), e);
                return;
            }
            if (BuildConfig.DEBUG) {
                Log.w(TAG, e);
            }
        });
    }
}
