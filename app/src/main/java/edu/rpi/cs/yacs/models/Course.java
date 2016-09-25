package edu.rpi.cs.yacs.models;

import java.util.ArrayList;

/**
 * Created by Mark Robinson on 9/24/16.
 */

public class Course {
    private int id = 0, number = 0, min_credits = 0, max_credits = 0, department_id = 0;
    private String name = "", description = "";
    private ArrayList<Section> sections;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Section> getSections() {
        return sections;
    }

    public void setSections(ArrayList<Section> sections) {
        this.sections = sections;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(int department_id) {
        this.department_id = department_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMax_credits() {
        return max_credits;
    }

    public void setMax_credits(int max_credits) {
        this.max_credits = max_credits;
    }

    public int getMin_credits() {
        return min_credits;
    }

    public void setMin_credits(int min_credits) {
        this.min_credits = min_credits;
    }
}
