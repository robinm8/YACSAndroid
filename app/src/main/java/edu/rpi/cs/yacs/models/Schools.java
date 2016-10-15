package edu.rpi.cs.yacs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mark Robinson on 10/12/16.
 */

public class Schools {
    @SerializedName("schools")
    @Expose
    private List<School> schools = new ArrayList<>();

    /**
     *
     * @return
     * The schools
     */
    public List<School> getSchools() {
        return schools;
    }

    /**
     *
     * @param schools
     * The schools
     */
    public void setSchools(List<School> schools) {
        this.schools = schools;
    }
}