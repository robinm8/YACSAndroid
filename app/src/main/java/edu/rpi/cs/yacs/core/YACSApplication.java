package edu.rpi.cs.yacs.core;

import android.app.Application;

import edu.rpi.cs.yacs.retrofit.ServiceHelper;

/**
 * Created by Mark Robinson on 10/4/16.
 */
public class YACSApplication extends Application {
    private static YACSApplication instance;

    private ServiceHelper serviceHelper = null;

    public static YACSApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        serviceHelper = new ServiceHelper(getApplicationContext());
    }

    public ServiceHelper getServiceHelper() {
        return serviceHelper;
    }
}