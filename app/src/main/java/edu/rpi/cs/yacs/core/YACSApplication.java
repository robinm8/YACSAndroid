package edu.rpi.cs.yacs.core;

import android.app.Application;

import edu.rpi.cs.yacs.retrofit.ServiceHelper;

/**
 * Created by Mark Robinson on 10/4/16.
 */
public class YACSApplication extends Application {
    ServiceHelper serviceHelper = new ServiceHelper(getApplicationContext());
}