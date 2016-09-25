package edu.rpi.cs.yacs.models;

import java.util.ArrayList;

/**
 * Created by Mark Robinson on 9/24/16.
 */

public class Section {
    private int id = 0, crn = 0, seats = 0, seats_taken = 0, num_periods = 0, course_id = 0;
    private String name;
    private ArrayList<String> instructors;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCrn() {
        return crn;
    }

    public void setCrn(int crn) {
        this.crn = crn;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public int getSeats_taken() {
        return seats_taken;
    }

    public void setSeats_taken(int seats_taken) {
        this.seats_taken = seats_taken;
    }

    public int getNum_periods() {
        return num_periods;
    }

    public void setNum_periods(int num_periods) {
        this.num_periods = num_periods;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getInstructors() {
        return instructors;
    }

    public void setInstructors(ArrayList<String> instructors) {
        this.instructors = instructors;
    }
}
