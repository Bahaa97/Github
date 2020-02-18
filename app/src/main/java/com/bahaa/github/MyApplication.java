package com.bahaa.github;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {

    private static Application application;

    public static Application getApplication() {
        return application;
    }

    public static synchronized Context getContext() {
        return getApplication().getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }
}
