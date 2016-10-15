package edu.rpi.cs.yacs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mark Robinson on 9/24/16.
 */

public class Period {
    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("day")
    @Expose
    private Integer day;

    @SerializedName("start")
    @Expose
    private Integer start;

    @SerializedName("end")
    @Expose
    private Integer end;

    /**
     *
     * @return
     * The type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     * The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return
     * The day
     */
    public Integer getDay() {
        return day;
    }

    /**
     *
     * @param day
     * The day
     */
    public void setDay(Integer day) {
        this.day = day;
    }

    /**
     *
     * @return
     * The start
     */
    public Integer getStart() {
        return start;
    }

    /**
     *
     * @param start
     * The start
     */
    public void setStart(Integer start) {
        this.start = start;
    }

    /**
     *
     * @return
     * The end
     */
    public Integer getEnd() {
        return end;
    }

    /**
     *
     * @param end
     * The end
     */
    public void setEnd(Integer end) {
        this.end = end;
    }
}