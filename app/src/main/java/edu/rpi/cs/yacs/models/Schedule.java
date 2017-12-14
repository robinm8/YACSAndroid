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

public class Schedule extends RealmObject {
    @SerializedName("sections")
    @Expose
    private RealmList<Section> sections = new RealmList<>();

    /**
     * The sections
     */
    public RealmList<Section> getSections() {
        return sections;
    }

    /**
     *
     * @param sections
     * The sections
     */
    public void setSections(RealmList<Section> sections) {
        this.sections = sections;
    }
}
