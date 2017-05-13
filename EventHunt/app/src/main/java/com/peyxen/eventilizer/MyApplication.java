package com.peyxen.eventilizer;

import android.app.Application;
import android.content.Context;

/**
 * Created by Pci on 10/9/2015.
 */
public class MyApplication extends Application {
    private static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static MyApplication getmInstance() {
        return mInstance;
    }

    public static Context getAppContext() {
        return mInstance.getApplicationContext();
    }
}
