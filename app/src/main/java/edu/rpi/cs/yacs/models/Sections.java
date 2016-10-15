package edu.rpi.cs.yacs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mark Robinson on 10/12/16.
 */

public class Sections {
    @SerializedName("sections")
    @Expose
    private List<Section> sections = new ArrayList<>();

    /**
     *
     * @return
     * The sections
     */
    public List<Section> getSections() {
        return sections;
    }

    /**
     *
     * @param sections
     * The sections
     */
    public void setSections(List<Section> sections) {
        this.sections = sections;
    }
}