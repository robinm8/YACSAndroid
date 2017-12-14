package edu.rpi.cs.yacs.fragments;

import android.Manifest;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import butterknife.BindView;
import edu.rpi.cs.yacs.R;
import edu.rpi.cs.yacs.adapters.CoursesAdapter;
import edu.rpi.cs.yacs.adapters.SchoolsAdapter;
import edu.rpi.cs.yacs.core.YACSApplication;
import edu.rpi.cs.yacs.enums.ViewPagerMode;
import edu.rpi.cs.yacs.models.Course;
import edu.rpi.cs.yacs.models.Courses;
import edu.rpi.cs.yacs.models.Period;
import edu.rpi.cs.yacs.models.School;
import edu.rpi.cs.yacs.models.Schools;
import edu.rpi.cs.yacs.models.Section;
import edu.rpi.cs.yacs.models.Sections;
import edu.rpi.cs.yacs.retrofit.YACSService;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import edu.rpi.cs.yacs.share.ShareHelper;
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

    private static final String ARG_TAB_TITLE = "tabTitle";

    private CoordinatorLayout coordinatorLayout = null;

    private RecyclerView mRecyclerView;
    private WeekView weekView;

    private LinearLayout weekViewHolder;

    public RecyclerView.Adapter getMAdapter() {
        return mAdapter;
    }
    public RecyclerView getMRecyclerView() {
        return mRecyclerView;
    }

    private RecyclerView.Adapter mAdapter = null;
    private AlphaInAnimationAdapter alphaInAnimationAdapter = null;
    private ScaleInAnimationAdapter scaleInAnimationAdapter = null;
    private String tabTitle;

    private List<Course> courseList = null;
    private YACSService webService = null;
    private ShareHelper shareHelper = null;

    private boolean madeSchedule = false;

    private SimpleDateFormat startDateForm = new SimpleDateFormat("hh:mm", Locale.US);
    private SimpleDateFormat startDateFormSym = new SimpleDateFormat("hh:mm a", Locale.US);

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
        shareHelper = YACSApplication.getInstance().getShareHelper();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recyclerview, container, false);
    }

    MonthLoader.MonthChangeListener mMonthChangeListener = new MonthLoader.MonthChangeListener() {
        @Override
        public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
            List<WeekViewEvent> events = new ArrayList<>();

            final int[] colors = {
                    getResources().getColor(R.color.colorPrimary),
                    getResources().getColor(R.color.accent_color),
                    getResources().getColor(R.color.green),
                    getResources().getColor(R.color.cyan),
                    getResources().getColor(R.color.lime),
                    getResources().getColor(R.color.purple),
                    getResources().getColor(R.color.red),
            };

            if (!madeSchedule) {
                madeSchedule = true;

                for (Section s : YACSApplication.getInstance().getSelectedSections()) {
                    int rnd = new Random().nextInt(colors.length);
                    int color = colors[rnd];

                    for (Period period : s.getPeriods()) {
                        int day = period.getDay() - 1;
                        String startTime = "";
                        String endTime = "";
                        String blockTime = "";

                        try {
                            Date startDate = new SimpleDateFormat("hhmm", Locale.US).parse(String.format(Locale.US, "%04d", period.getStart()));
                            Date endDate = new SimpleDateFormat("hhmm", Locale.US).parse(String.format(Locale.US, "%04d", period.getEnd()));

                            startTime = startDateForm.format(startDate);
                            endTime = startDateFormSym.format(endDate);

                        } catch (ParseException ignored) {}
//                                    blockTime = String.format("%s -- %s", startTime, endTime);
//                                    periods.get(day).add(blockTime);
                    }
                }
            }

            return events;
        }
    };

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        coordinatorLayout = view.findViewById(R.id.coordinatorLayout);

        mRecyclerView = view.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManagerWithSmoothScroller(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new SlideInUpAnimator());
        mRecyclerView.addItemDecoration(new MaterialViewPagerHeaderDecorator());

        weekViewHolder = view.findViewById(R.id.weekViewHolder);
        weekView = view.findViewById(R.id.weekView);

        switch (tabTitle) {
            case ViewPagerMode.EXPLORE:
                weekViewHolder.setVisibility(View.GONE);

                populateSchoolsAdapter();

                break;
            case ViewPagerMode.SCHEDULE:
                List<School> list = new ArrayList<>();
                createSchoolsAdapter(list);

                weekViewHolder.setVisibility(View.VISIBLE);

                Calendar c = Calendar.getInstance();
                c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                weekView.goToDate(c);
                weekView.goToHour(8);

                weekView.setMonthChangeListener(mMonthChangeListener);

                WeekView.EventClickListener mEventClickListener= (event, eventRect) -> {

                };

                weekView.setOnEventClickListener(mEventClickListener);

                WeekView.EventLongPressListener mEventLongPressListener = (event, eventRect) -> {

                };

                weekView.setEventLongPressListener(mEventLongPressListener);

                WeekView.EmptyViewLongPressListener mEmptyViewLongPressListener = time -> new MaterialDialog.Builder(getActivity())
                .title("Share Schedule")
                .items(R.array.sharables)
                .itemsCallback((dialog, itemView, position, text) -> {
                    if (position == 0) {
                        shareHelper.saveToGallery(getActivity(), getContext(), weekView);
                    } else if (position == 1) {
                        shareHelper.shareToFacebook(getActivity(), getContext(), weekView);
                    } else if (position == 2) {
                        //copy yacs link
                    } else if (position == 3) {
                        //copy crns
                    } else if (position == 4) {
                        PermissionListener permissionlistener = new PermissionListener() {
                            @Override
                            public void onPermissionGranted() {
                                shareHelper.shareViewToApps(getActivity(), getContext(), weekView);
                            }

                            @Override
                            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                            }
                        };

                        TedPermission.with(getContext())
                                .setPermissionListener(permissionlistener)
                                .setDeniedMessage("Since you rejected this permission, I cannot save your schedule image.\n\nTurn on permission at [Setting] > [Permission] to use this feature")
                                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                .check();
                    }
                }).show();

                weekView.setEmptyViewLongPressListener(mEmptyViewLongPressListener);

                break;
        }
    }

    public void repopulateWeekView() {
        madeSchedule = false;

        mMonthChangeListener.onMonthChange(2017, 11);
        weekView.setMonthChangeListener(mMonthChangeListener);

        weekView.notifyDatasetChanged();
        weekView.invalidate();
    }
    public void populateSchoolsAdapter() {
        Call<Schools> schoolCall = webService.loadSchools();

        schoolCall.enqueue(new Callback<Schools>() {
            @Override
            public void onResponse(@NonNull Call<Schools> call, @NonNull Response<Schools> response) {
                List<School> schoolList = new ArrayList<>();

                if (response.isSuccessful() && response.code() == 200) {
                    Schools schools = response.body();

                    schoolList = schools.getSchools();
                } else {
                    int statusCode = response.code();

                    ResponseBody errorBody = response.errorBody();

                    Log.d("Retrofit Call", "Server error " + statusCode);

                    try {
                        Log.d("Retrofit Call Error", errorBody.string());
                    } catch (IOException ignored) {}

                    createFailedSnackBar();
                }

                createSchoolsAdapter(schoolList);
            }

            @Override
            public void onFailure(Call<Schools> call, Throwable t) {
                Log.d("Retrofit Call", "Failed.");

                List<School> list = new ArrayList<>();
                createSchoolsAdapter(list);

                createFailedSnackBar();
            }
        });
    }

    public void populateCoursesAdapter(final String department) {
        Call<Courses> coursesCall = webService.loadCoursesByDepartment(department);

        coursesCall.enqueue(new Callback<Courses>() {
            @Override
            public void onResponse(Call<Courses> call, Response<Courses> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    Courses courses = response.body();

                    courseList = courses.getCourses();
                } else {
                    int statusCode = response.code();

                    ResponseBody errorBody = response.errorBody();

                    Log.d("Retrofit Call", "Server error " + statusCode);

                    try {
                        Log.d("Retrofit Call Error", errorBody.string());
                    } catch (IOException ignored) {}

                    createFailedSnackBar();
                }

                createCoursesAdapter(department, courseList);
            }

            @Override
            public void onFailure(Call<Courses> call, Throwable t) {
                Log.d("Retrofit Call", "Failed.");

                List<Course> list = new ArrayList<>();
                createCoursesAdapter(department, list);

                createFailedSnackBar();
            }
        });
    }

    public void loadCourseSections(final int courseId, final Callback<Sections> callback) {
        Call<Sections> sectionsCall = webService.loadCourseSections(courseId);

        sectionsCall.enqueue(new Callback<Sections>() {
            @Override
            public void onResponse(Call<Sections> call, Response<Sections> response) {
                if (response.isSuccessful() && response.code() == 200) {

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

    private void createSchoolsAdapter(List<School> schoolList) {
        mAdapter = new SchoolsAdapter(this, schoolList); //new RecyclerViewMaterialAdapter(schoolsAdapter);

        alphaInAnimationAdapter = new AlphaInAnimationAdapter(mAdapter);
        alphaInAnimationAdapter.setFirstOnly(false);
        alphaInAnimationAdapter.setDuration(250);

        scaleInAnimationAdapter = new ScaleInAnimationAdapter(alphaInAnimationAdapter);
        scaleInAnimationAdapter.setFirstOnly(false);
        scaleInAnimationAdapter.setInterpolator(new OvershootInterpolator());
        alphaInAnimationAdapter.setDuration(250);

        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView);

        mRecyclerView.swapAdapter(scaleInAnimationAdapter, true);
        mAdapter.notifyItemRangeInserted(0, schoolList.size());
    }

    private void createCoursesAdapter(String department, List<Course> courseList) {
        mAdapter = new CoursesAdapter(this, department, courseList); //new RecyclerViewMaterialAdapter(coursesAdapter);

        alphaInAnimationAdapter = new AlphaInAnimationAdapter(mAdapter);
        alphaInAnimationAdapter.setFirstOnly(false);
        alphaInAnimationAdapter.setDuration(250);

        scaleInAnimationAdapter = new ScaleInAnimationAdapter(alphaInAnimationAdapter);
        scaleInAnimationAdapter.setFirstOnly(false);
        scaleInAnimationAdapter.setInterpolator(new OvershootInterpolator());
        alphaInAnimationAdapter.setDuration(250);

        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView);

        mRecyclerView.swapAdapter(scaleInAnimationAdapter, true);
        mAdapter.notifyItemRangeInserted(0, courseList.size());
    }

    private void createFailedSnackBar() {
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, "Connection timed out.", Snackbar.LENGTH_INDEFINITE)
                .setAction("RETRY", view -> view.postDelayed(this::populateSchoolsAdapter, 250));

        snackbar.show();
    }

    public List<Course> getCourseList() {
        return courseList;
    }
}