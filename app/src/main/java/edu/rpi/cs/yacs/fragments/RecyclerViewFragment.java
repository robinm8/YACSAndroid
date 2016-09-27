package edu.rpi.cs.yacs.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;

import java.util.ArrayList;
import java.util.List;

import edu.rpi.cs.yacs.R;

/**
 * Created by Mark Robinson on 9/24/16.
 */

public class RecyclerViewFragment extends Fragment {

    private static final String ARG_TAB_TITLE = "tabTitle";
    private static final int ITEM_COUNT = 5;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
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

        switch (tabTitle) {
            case "Explore":
                mAdapter = new RecyclerViewMaterialAdapter(new ExploreRecyclerViewAdapter(mContentItems));
                mRecyclerView.setAdapter(mAdapter);

                mContentItems.add(new Object());
                break;
            case "Schedule":
                mAdapter = new RecyclerViewMaterialAdapter(new ScheduleRecyclerViewAdapter(mContentItems));
                mRecyclerView.setAdapter(mAdapter);
                mContentItems.add(new Object());
                break;
        }

        mAdapter.notifyDataSetChanged();
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView);
    }
}