package edu.rpi.cs.yacs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Mark Robinson on 9/24/16.
 */

public class Section extends RealmObject {
    @PrimaryKey
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("crn")
    @Expose
    private Integer crn;

    @SerializedName("seats")
    @Expose
    private Integer seats;

    @SerializedName("seats_taken")
    @Expose
    private Integer seatsTaken;

    @SerializedName("instructors")
    @Expose
    private RealmList<String> instructors = new RealmList<>();

    @SerializedName("num_periods")
    @Expose
    private Integer numPeriods;

    @SerializedName("course_id")
    @Expose
    private Integer courseId;

    @SerializedName("periods")
    @Expose
    private RealmList<Period> periods = new RealmList<>();

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The crn
     */
    public Integer getCrn() {
        return crn;
    }

    /**
     *
     * @param crn
     * The crn
     */
    public void setCrn(Integer crn) {
        this.crn = crn;
    }

    /**
     *
     * @return
     * The seats
     */
    public Integer getSeatsTotal() {
        return seats;
    }

    /**
     *
     * @param seats
     * The seats
     */
    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    /**
     *
     * @return
     * The seatsTaken
     */
    public Integer getSeatsTaken() {
        return seatsTaken;
    }

    /**
     *
     * @param seatsTaken
     * The seats_taken
     */
    public void setSeatsTaken(Integer seatsTaken) {
        this.seatsTaken = seatsTaken;
    }

    /**
     *
     * @return
     * The instructors
     */
    public List<String> getInstructors() {
        return instructors;
    }

    /**
     *
     * @param instructors
     * The instructors
     */
    public void setInstructors(RealmList<String> instructors) {
        this.instructors = instructors;
    }

    /**
     *
     * @return
     * The numPeriods
     */
    public Integer getNumPeriods() {
        return numPeriods;
    }

    /**
     *
     * @param numPeriods
     * The num_periods
     */
    public void setNumPeriods(Integer numPeriods) {
        this.numPeriods = numPeriods;
    }

    /**
     *
     * @return
     * The courseId
     */
    public Integer getCourseId() {
        return courseId;
    }

    /**
     *
     * @param courseId
     * The course_id
     */
    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    /**
     *
     * @return
     * The periods
     */
    public List<Period> getPeriods() {
        return periods;
    }

    /**
     *
     * @param periods
     * The periods
     */
    public void setPeriods(RealmList<Period> periods) {
        this.periods = periods;
    }
}