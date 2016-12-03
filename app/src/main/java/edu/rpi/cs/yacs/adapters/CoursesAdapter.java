package edu.rpi.cs.yacs.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import edu.rpi.cs.yacs.R;
import edu.rpi.cs.yacs.fragments.RecyclerViewFragment;
import edu.rpi.cs.yacs.models.Course;
import edu.rpi.cs.yacs.models.Sections;
import edu.rpi.cs.yacs.viewholders.CourseItemViewHolder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mark on 11/20/16.
 */

public class CoursesAdapter extends RecyclerView.Adapter<CourseItemViewHolder> {

    private List<Course> courseList = null;
    private RecyclerViewFragment recyclerViewFragment = null;
    private String departmentCode = "";

    public CoursesAdapter(RecyclerViewFragment recyclerViewFragment, String departmentCode, List<Course> courseList) {
        this.courseList = courseList;
        this.recyclerViewFragment = recyclerViewFragment;
        this.departmentCode = departmentCode;
    }

    @Override
    public CourseItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.course_view_item, parent, false);

        return new CourseItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CourseItemViewHolder holder, int position) {
        final Course course = courseList.get(position);

        final String courseCode = departmentCode + " " + course.getNumber();
        final String credits = course.getMaxCredits() + " credits";

        recyclerViewFragment.loadCourseSections(course.getId(), new Callback<Sections>() {
            @Override
            public void onResponse(Call<Sections> call, Response<Sections> response) {
                Sections sections = response.body();

                String description = course.getDescription();

                if (description.isEmpty()) {
                    description = "No description available";
                }

                holder.render(course.getName(), courseCode, credits, description, sections.getSections());
            }

            @Override
            public void onFailure(Call<Sections> call, Throwable t) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }
}