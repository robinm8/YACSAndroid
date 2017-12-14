package edu.rpi.cs.yacs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by mark on 3/31/17.
 */

public class Schedules extends RealmObject {
    @SerializedName("schedules")
    @Expose
    private RealmList<Schedule> schedules = new RealmList<>();

    /**
     *
     * @return
     * The Schedules
     */
    public RealmList<Schedule> getSchedules() {
        return schedules;
    }

    /**
     *
     * @param schedules
     * The schedules
     */
    public void setSchedules(RealmList<Schedule> schedules) {
        this.schedules = schedules;
    }
}