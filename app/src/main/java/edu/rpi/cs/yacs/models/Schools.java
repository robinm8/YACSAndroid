package edu.rpi.cs.yacs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Mark Robinson on 10/12/16.
 */

public class Schools extends RealmObject {
    @SerializedName("schools")
    @Expose
    private RealmList<School> schools = new RealmList<>();

    /**
     *
     * @return
     * The schools
     */
    public RealmList<School> getSchools() {
        return schools;
    }

    /**
     *
     * @param schools
     * The schools
     */
    public void setSchools(RealmList<School> schools) {
        this.schools = schools;
    }
}