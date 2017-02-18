package edu.rpi.cs.yacs.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import edu.rpi.cs.yacs.R;
import edu.rpi.cs.yacs.adapters.CoursesAdapter;
import edu.rpi.cs.yacs.adapters.SchoolsAdapter;
import edu.rpi.cs.yacs.core.MainActivity;
import edu.rpi.cs.yacs.core.YACSApplication;
import edu.rpi.cs.yacs.models.Course;
import edu.rpi.cs.yacs.models.Courses;
import edu.rpi.cs.yacs.models.School;
import edu.rpi.cs.yacs.models.Schools;
import edu.rpi.cs.yacs.models.Section;
import edu.rpi.cs.yacs.models.Sections;
import edu.rpi.cs.yacs.retrofit.YACSService;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Mark Robinson on 9/24/16.
 */

public class RecyclerViewFragment extends Fragment {
    private MainActivity activity;

    private static final String ARG_TAB_TITLE = "tabTitle";

    CoordinatorLayout coordinatorLayout = null;

    private RecyclerView mRecyclerView;

    public RecyclerView.Adapter getMAdapter() {
        return mAdapter;
    }
    public RecyclerView getMRecyclerView() {
        return mRecyclerView;
    }

    private RecyclerView.Adapter mAdapter = null;
    private MaterialViewPager mViewPager = null;
    private SchoolsAdapter schoolsAdapter = null;
    private CoursesAdapter coursesAdapter = null;
    private AlphaInAnimationAdapter alphaInAnimationAdapter = null;
    private ScaleInAnimationAdapter scaleInAnimationAdapter = null;
    private String tabTitle;
    private YACSService webService = null;

    public static RecyclerViewFragment newInstance(String tabTitle) {
        RecyclerViewFragment fragment = new RecyclerViewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TAB_TITLE, tabTitle);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tabTitle = getArguments().getString(ARG_TAB_TITLE);
        }

        webService = YACSApplication.getInstance().getServiceHelper().getService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recyclerview, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        coordinatorLayout = (CoordinatorLayout) view.findViewById(R.id.coordinatorLayout);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManagerWithSmoothScroller(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new SlideInUpAnimator());
        mRecyclerView.addItemDecoration(new MaterialViewPagerHeaderDecorator());

        activity = (MainActivity) getActivity();

        switch (tabTitle) {
            case "Explore":
                populateSchoolsAdapter();

                break;
            case "Schedule":
                //TODO: create schedule adapter

                List<School> list = new ArrayList<>();
                createSchoolsAdapter(list);

                break;
        }
    }

    public void populateSchoolsAdapter() {
        Call<Schools> schoolCall = webService.loadSchools();

        schoolCall.enqueue(new Callback<Schools>() {
            @Override
            public void onResponse(Call<Schools> call, Response<Schools> response) {
                List<School> schoolList = null;

                if (response.isSuccessful()) {
                    Schools schools = response.body();

                    schoolList = schools.getSchools();
                } else {
                    int statusCode = response.code();

                    ResponseBody errorBody = response.errorBody();

                    Log.d("Retrofit Call", "Server error " + statusCode);

                    try {
                        Log.d("Retrofit Call Error", errorBody.string());
                    } catch (IOException ignored) {}

                    createFailedSnackbar();
                }

                createSchoolsAdapter(schoolList);
            }

            @Override
            public void onFailure(Call<Schools> call, Throwable t) {
                Log.d("Retrofit Call", "Failed.");

                List<School> list = new ArrayList<>();
                createSchoolsAdapter(list);

                createFailedSnackbar();
            }
        });
    }

    public void populateCoursesAdapter(final String department) {
        Call<Courses> coursesCall = webService.loadCoursesByDepartment(department);

        coursesCall.enqueue(new Callback<Courses>() {
            @Override
            public void onResponse(Call<Courses> call, Response<Courses> response) {
                List<Course> courseList = null;

                if (response.isSuccessful()) {
                    Courses courses = response.body();

                    courseList = courses.getCourses();
                } else {
                    int statusCode = response.code();

                    ResponseBody errorBody = response.errorBody();

                    Log.d("Retrofit Call", "Server error " + statusCode);

                    try {
                        Log.d("Retrofit Call Error", errorBody.string());
                    } catch (IOException ignored) {}

                    createFailedSnackbar();
                }

                createCoursesAdapter(department, courseList);
            }

            @Override
            public void onFailure(Call<Courses> call, Throwable t) {
                Log.d("Retrofit Call", "Failed.");

                List<Course> list = new ArrayList<>();
                createCoursesAdapter(department, list);

                createFailedSnackbar();
            }
        });
    }

    public void loadCourseSections(final int courseId, final Callback<Sections> callback) {
        Call<Sections> sectionsCall = webService.loadCourseSections(courseId);

        sectionsCall.enqueue(new Callback<Sections>() {
            @Override
            public void onResponse(Call<Sections> call, Response<Sections> response) {

                if (response.isSuccessful()) {

                    callback.onResponse(call, response);
                } else {
                    int statusCode = response.code();

                    ResponseBody errorBody = response.errorBody();

                    Log.d("Retrofit Call", "Server error " + statusCode);

                    try {
                        Log.d("Retrofit Call Error", errorBody.string());
                    } catch (IOException ignored) {}
                }
            }

            @Override
            public void onFailure(Call<Sections> call, Throwable t) {
                Log.d("Retrofit Call", "Failed.");

                callback.onFailure(call, t);
            }
        });
    }

    public void createSchoolsAdapter(List<School> schoolList) {
        schoolsAdapter = new SchoolsAdapter(this, schoolList);
        mAdapter = schoolsAdapter; //new RecyclerViewMaterialAdapter(schoolsAdapter);

        alphaInAnimationAdapter = new AlphaInAnimationAdapter(mAdapter);
        alphaInAnimationAdapter.setFirstOnly(false);
        alphaInAnimationAdapter.setDuration(250);

        scaleInAnimationAdapter = new ScaleInAnimationAdapter(alphaInAnimationAdapter);
        scaleInAnimationAdapter.setFirstOnly(false);
        scaleInAnimationAdapter.setInterpolator(new OvershootInterpolator());

        mRecyclerView.setAdapter(scaleInAnimationAdapter);

        mAdapter.notifyDataSetChanged();
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView);
    }

    public void createCoursesAdapter(String department, List<Course> courseList) {
        coursesAdapter = new CoursesAdapter(this, department, courseList);
        mAdapter = coursesAdapter; //new RecyclerViewMaterialAdapter(coursesAdapter);

        alphaInAnimationAdapter = new AlphaInAnimationAdapter(mAdapter);
        alphaInAnimationAdapter.setFirstOnly(false);
        alphaInAnimationAdapter.setDuration(250);

        scaleInAnimationAdapter = new ScaleInAnimationAdapter(alphaInAnimationAdapter);
        scaleInAnimationAdapter.setFirstOnly(false);
        scaleInAnimationAdapter.setInterpolator(new OvershootInterpolator());

        mRecyclerView.swapAdapter(scaleInAnimationAdapter, true);

        mAdapter.notifyDataSetChanged();
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView);
    }

    public void createFailedSnackbar() {
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, "Connection timed out.", Snackbar.LENGTH_INDEFINITE)
                .setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        view.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                populateSchoolsAdapter();
                            }
                        }, 250);
                    }
                });

        snackbar.show();
    }
}