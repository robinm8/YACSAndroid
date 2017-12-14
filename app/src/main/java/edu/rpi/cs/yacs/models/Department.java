package edu.rpi.cs.yacs.models;

/**
 * Created by Mark Robinson on 9/24/16.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Department extends RealmObject {
    @PrimaryKey
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("code")
    @Expose
    private String code;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("school_id")
    @Expose
    private Integer schoolId;

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
     * The code
     */
    public String getCode() {
        return code;
    }

    /**
     *
     * @param code
     * The code
     */
    public void setCode(String code) {
        this.code = code;
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
     * The schoolId
     */
    public Integer getSchoolId() {
        return schoolId;
    }

    /**
     *
     * @param schoolId
     * The school_id
     */
    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }
}