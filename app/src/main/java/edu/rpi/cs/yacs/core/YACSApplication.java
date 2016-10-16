package edu.rpi.cs.yacs.core;

import android.app.Application;

import edu.rpi.cs.yacs.retrofit.ServiceHelper;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Mark Robinson on 10/4/16.
 */
public class YACSApplication extends Application {
    private static YACSApplication instance;

    private ServiceHelper serviceHelper = null;
    private Realm realm = null;

    public static YACSApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

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
}