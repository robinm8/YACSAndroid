package edu.rpi.cs.yacs.core;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.squareup.leakcanary.LeakCanary;

import edu.rpi.cs.yacs.enums.RecyclerViewMode;
import edu.rpi.cs.yacs.models.Course;
import edu.rpi.cs.yacs.retrofit.ServiceHelper;

import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.realm.RealmConfiguration;

import java.util.ArrayList;

/**
 * Created by Mark Robinson on 10/4/16.
 */
public class YACSApplication extends Application {
    private static YACSApplication instance;

    private ServiceHelper serviceHelper = null;
    private Realm realm = null;
    private RecyclerViewMode recyclerViewMode = RecyclerViewMode.DEPARTMENTS;
    private ArrayList<Course> selectedCourses = new ArrayList<>();

    public static YACSApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            return;
        }

        Fabric.with(this, new Crashlytics());

        instance = this;

        LeakCanary.install(this);

        serviceHelper = new ServiceHelper(getApplicationContext());

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(realmConfiguration);

        realm = Realm.getDefaultInstance();
    }

    public ServiceHelper getServiceHelper() {
        return serviceHelper;
    }

    public Realm getRealm() {
        return realm;
    }

    public RecyclerViewMode getRecyclerViewMode() {
        return recyclerViewMode;
    }

    public void setRecyclerViewMode(RecyclerViewMode recyclerViewMode) {
        this.recyclerViewMode = recyclerViewMode;
    }

    public ArrayList<Course> getSelectedCourses() {
        return selectedCourses;
    }
}