package edu.rpi.cs.yacs.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.rpi.cs.yacs.R;
import edu.rpi.cs.yacs.adapters.SchoolsAdapter;
import edu.rpi.cs.yacs.core.YACSApplication;
import edu.rpi.cs.yacs.models.School;
import edu.rpi.cs.yacs.models.Schools;
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

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private SchoolsAdapter schoolsAdapter;
    private AlphaInAnimationAdapter alphaInAnimationAdapter;
    private List<Object> mContentItems = new ArrayList<>();
    private String tabTitle;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recyclerview, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new SlideInUpAnimator());

        switch (tabTitle) {
            case "Explore":
                Call<Schools> schoolCall = YACSApplication.getInstance().getServiceHelper().getService().loadSchools();

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

                            getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=t3otBjVZzT0")));

                            Log.d("Retrofit Call", "Server error " + statusCode);

                            try {
                                Log.d("Retrofit Call", errorBody.string());
                            } catch (IOException ignored) {
                            }
                        }

                        createSchoolsAdapter(schoolList);
                    }

                    @Override
                    public void onFailure(Call<Schools> call, Throwable t) {
                        Log.d("Retrofit Call", "Server failed :(");

                        List<School> list = new ArrayList<>();
                        createSchoolsAdapter(list);

                        getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=t3otBjVZzT0")));
                    }
                });

                break;
            case "Schedule":
                //TODO create schedule adapter

                List<School> list = new ArrayList<>();
                createSchoolsAdapter(list);

                break;
        }
    }

    public void createSchoolsAdapter(List<School> schoolList) {
        schoolsAdapter = new SchoolsAdapter(schoolList);
        mAdapter = new RecyclerViewMaterialAdapter(schoolsAdapter);

        alphaInAnimationAdapter = new AlphaInAnimationAdapter(mAdapter);
        alphaInAnimationAdapter.setDuration(1000);
        alphaInAnimationAdapter.setFirstOnly(false);
        alphaInAnimationAdapter.setInterpolator(new OvershootInterpolator());

        mRecyclerView.setAdapter(new ScaleInAnimationAdapter(alphaInAnimationAdapter));

        mContentItems.add(new Object());

        mAdapter.notifyDataSetChanged();
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView);
    }
}