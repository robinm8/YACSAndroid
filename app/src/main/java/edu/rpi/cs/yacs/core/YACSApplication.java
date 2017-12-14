package edu.rpi.cs.yacs.core;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.squareup.leakcanary.LeakCanary;

import edu.rpi.cs.yacs.BuildConfig;
import edu.rpi.cs.yacs.enums.RecyclerViewMode;
import edu.rpi.cs.yacs.models.Course;
import edu.rpi.cs.yacs.models.Section;
import edu.rpi.cs.yacs.retrofit.ServiceHelper;

import edu.rpi.cs.yacs.share.ShareHelper;
import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;

import java.util.ArrayList;

/**
 * Created by Mark Robinson on 10/4/16.
 */
public class YACSApplication extends Application {
    private static YACSApplication instance;

    private ServiceHelper serviceHelper = null;
    private ShareHelper shareHelper = null;
    private RecyclerViewMode recyclerViewMode = RecyclerViewMode.DEPARTMENTS;

    private Realm realm = null;
    private RealmList<Course> starredCourses = new RealmList<>();
    private RealmList<Section> selectedSections = new RealmList<>();

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

        if (BuildConfig.USE_CRASHLYTICS) {
            Fabric.with(this, new Crashlytics());
        }

        instance = this;

        LeakCanary.install(this);

        serviceHelper = new ServiceHelper(getApplicationContext());
        shareHelper = new ShareHelper();

        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .schemaVersion(0)
                .build();

        realm = Realm.getInstance(realmConfiguration);
    }

    public ServiceHelper getServiceHelper() {
        return serviceHelper;
    }

    public ShareHelper getShareHelper() {
        return shareHelper;
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

//    public boolean starCourse(Course course) {
//        boolean rc = starredCourses.add(course);
//
//        realm.beginTransaction();
//        realm.copyToRealmOrUpdate(starredCourses);
//        realm.commitTransaction();
//
//        return rc;
//    }
//
//    public boolean unstarCourse(Course course) {
//        boolean rc = starredCourses.remove(course);
//
//        realm.beginTransaction();
//        realm.copyToRealmOrUpdate(starredCourses);
//        realm.commitTransaction();
//
//        return rc;
//    }
//
//    public RealmList<Course> getStarredCourses() {
//        return starredCourses;
//    }

    public RealmList<Section> getSelectedSections() {
        return selectedSections;
    }

    public void addSection(Section s) {
//        try(Realm realmInstance = Realm.getDefaultInstance()) {
//            realmInstance.executeTransaction((realm) -> realm.insertOrUpdate(s));
//        }

        selectedSections.add(s);
    }

    public void removeSection(Section s) {
//        try(Realm realmInstance = Realm.getDefaultInstance()) {
//            realmInstance.executeTransaction((realm) -> selectedSections.remove(s));
//        }

        selectedSections.remove(s);
    }

    public boolean isSectionSelected(Section section) {
        for (Section other : selectedSections) {
            if (other.getId().equals(section.getId()) && other.getCrn().equals(section.getCrn())) {
                return true;
            }
        }

        return false;
    }
}