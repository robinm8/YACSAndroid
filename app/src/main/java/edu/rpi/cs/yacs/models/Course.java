package edu.rpi.cs.yacs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mark Robinson on 9/24/16.
 */

public class Course {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("number")
    @Expose
    private Integer number;
    @SerializedName("min_credits")
    @Expose
    private Integer minCredits;
    @SerializedName("max_credits")
    @Expose
    private Integer maxCredits;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("department_id")
    @Expose
    private Integer departmentId;

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
     * The number
     */
    public Integer getNumber() {
        return number;
    }

    /**
     *
     * @param number
     * The number
     */
    public void setNumber(Integer number) {
        this.number = number;
    }

    /**
     *
     * @return
     * The minCredits
     */
    public Integer getMinCredits() {
        return minCredits;
    }

    /**
     *
     * @param minCredits
     * The min_credits
     */
    public void setMinCredits(Integer minCredits) {
        this.minCredits = minCredits;
    }

    /**
     *
     * @return
     * The maxCredits
     */
    public Integer getMaxCredits() {
        return maxCredits;
    }

    /**
     *
     * @param maxCredits
     * The max_credits
     */
    public void setMaxCredits(Integer maxCredits) {
        this.maxCredits = maxCredits;
    }

    /**
     *
     * @return
     * The description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     * The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     * The departmentId
     */
    public Integer getDepartmentId() {
        return departmentId;
    }

    /**
     *
     * @param departmentId
     * The department_id
     */
    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }
}