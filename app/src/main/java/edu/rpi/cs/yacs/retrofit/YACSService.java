package edu.rpi.cs.yacs.retrofit;

import edu.rpi.cs.yacs.models.Course;
import edu.rpi.cs.yacs.models.School;
import edu.rpi.cs.yacs.models.Section;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Mark Robinson on 9/24/16.
 */

interface YACSService {
    /**
     * This method fetches all courses given a query to search.
     * @param query string to search
     * @return Relevant Course objects of query
     */
    @GET("courses.json?search={query}")
    Call<Course> courseSearch(@Path("query") String query);

    /**
     * This method fetches all schools and departments of the college.
     * @return Relevant School objects
     */
    @GET("/schools.json?show_departments=true")
    Call<School> loadDepartments();

    /**
     * This method fetches all courses of a given department.
     * @param department_id department id to fetch courses
     * @return Relevant Course objects of department_id
     */
    @GET("/courses.json?department_id={department_id}")
    Call<Course> loadCoursesByDepartment(@Path("department_id") int department_id);

    /**
     * This method fetches all sections and periods of a given course.
     * @param course_id course id to fetch sections and periods
     * @return Relevant Section objects of course_id
     */
    @GET("/sections.json?course_id={course_id}&show_periods=true")
    Call<Section> loadCourseSections(@Path("course_id") int course_id);
}