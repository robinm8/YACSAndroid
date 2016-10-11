package edu.rpi.cs.yacs.models;

import java.util.ArrayList;

/**
 * Created by Mark Robinson on 9/24/16.
 */

public class School {
    private int id = 0;
    private String name = "";
    private ArrayList<Department> departments;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(ArrayList<Department> departments) {
        this.departments = departments;
    }
}
