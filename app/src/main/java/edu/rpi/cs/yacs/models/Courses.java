package edu.rpi.cs.yacs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mark Robinson on 10/12/16.
 */
public class Courses {
    @SerializedName("courses")
    @Expose
    private List<Course> courses = new ArrayList<>();

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
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}