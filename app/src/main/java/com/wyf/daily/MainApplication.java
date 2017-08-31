package com.wyf.daily;

import android.app.Application;
import android.content.res.Configuration;

/**
 * This is used to control application behavior.
 */

public class MainApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
