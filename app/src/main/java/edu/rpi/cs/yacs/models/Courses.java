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
public class Courses extends RealmObject {
    @SerializedName("courses")
    @Expose
    private RealmList<Course> courses = new RealmList<>();

    /**
     *
     * @return
     * The courses
     */
    public List<Course> getCourses() {
        return courses;
    }

    /**
     *
     * @param courses
     * The courses
     */
    public void setCourses(RealmList<Course> courses) {
        this.courses = courses;
    }
}