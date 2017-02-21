package edu.rpi.cs.yacs.retrofit;

import edu.rpi.cs.yacs.models.Courses;
import edu.rpi.cs.yacs.models.Schools;
import edu.rpi.cs.yacs.models.Sections;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Mark Robinson on 9/24/16.
 */

public interface YACSService {
    /**
     * This method fetches all schools and departments of the college.
     * @return Relevant School objects
     */
    @GET("schools.json?show_departments")
    Call<Schools> loadSchools();

    /**
     * This method fetches all courses given a query to search.
     * @param search query string to search
     * @return Relevant Course objects of query
     */
    @GET("courses.json")
    Call<Courses> loadCoursesBySearch(@Query("search") String search);

    /**
     * This method fetches all courses of a given department.
     * @param department_code department code to fetch courses
     * @return Relevant Course objects of department_code
     */
    @GET("courses.json")
    Call<Courses> loadCoursesByDepartment(@Query("department_code") String department_code);

    /**
     * This method fetches all sections and periods of a given course.
     * @param course_id course id to fetch sections and periods
     * @return Relevant Section objects of course_id
     */
    @GET("sections.json?show_periods=true")
    Call<Sections> loadCourseSections(@Query("course_id") int course_id);
}