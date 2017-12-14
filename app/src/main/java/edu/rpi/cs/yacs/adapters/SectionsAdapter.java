package edu.rpi.cs.yacs.adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import edu.rpi.cs.yacs.R;
import edu.rpi.cs.yacs.core.YACSApplication;
import edu.rpi.cs.yacs.fragments.RecyclerViewFragment;
import edu.rpi.cs.yacs.models.Section;
import edu.rpi.cs.yacs.retrofit.YACSService;
import edu.rpi.cs.yacs.viewholders.SectionItemViewHolder;

/**
 * Created by mark on 4/5/17.
 */

public class SectionsAdapter extends RecyclerView.Adapter<SectionItemViewHolder> {

    private List<Section> sectionList = null;
    private RecyclerViewFragment recyclerViewFragment = null;

    public SectionsAdapter(List<Section> sectionList, RecyclerViewFragment recyclerViewFragment) {
        this.sectionList = sectionList;
        this.recyclerViewFragment = recyclerViewFragment;
    }

    @Override
    public SectionItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.section_view_item, parent, false);

        return new SectionItemViewHolder(view, recyclerViewFragment);
    }

    @Override
    public void onBindViewHolder(final SectionItemViewHolder holder, int position) {
        final Section section = sectionList.get(position);
        holder.render(section);
    }

    @Override
    public int getItemCount() {
        return sectionList.size();
    }
}