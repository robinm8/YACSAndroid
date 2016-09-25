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

    @GET("/schools.json?show_departments") // <50ms response time
    Call<School> loadDepartments();

    @GET("/courses.json") // <300ms response time
    Call<Course> loadCourses();

    @GET("/sections.json?course_id={course_id}&show_periods") // <30ms response time
    Call<Section> loadCourse(@Path("course_id") String course_id);
}