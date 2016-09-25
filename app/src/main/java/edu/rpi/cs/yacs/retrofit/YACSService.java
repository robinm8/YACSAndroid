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
    @GET("courses.json?search={query}") // <100ms response time
    Call<Course> courseSearch(@Path("query") String query);

    @GET("/schools.json?show_departments=true") // <50ms response time
    Call<School> loadDepartments();

    @GET("/courses.json?department_id={department_id}") // <100ms response time
    Call<Course> loadCoursesByDepartment(@Path("department_id") String department_id);

    @GET("/sections.json?course_id={course_id}&show_periods=true") // <30ms response time
    Call<Section> loadCourseSections(@Path("course_id") String course_id);
}