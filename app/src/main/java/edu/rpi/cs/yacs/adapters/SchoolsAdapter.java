package edu.rpi.cs.yacs.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.truizlop.sectionedrecyclerview.SimpleSectionedAdapter;

import java.util.List;

import edu.rpi.cs.yacs.R;
import edu.rpi.cs.yacs.models.Department;
import edu.rpi.cs.yacs.models.School;
import edu.rpi.cs.yacs.viewholders.ItemViewHolder;

/**
 * Created by Mark Robinson on 10/15/16.
 */
public class SchoolsAdapter extends SimpleSectionedAdapter<ItemViewHolder> {

    private List<School> schoolList = null;
    private RecyclerView recyclerView = null;

    public SchoolsAdapter(final RecyclerView recyclerView, List<School> schoolList) {
        this.recyclerView = recyclerView;
        this.schoolList = schoolList;
    }

    @Override
    protected String getSectionHeaderTitle(int section) {
        if (schoolList != null) {
            return schoolList.get(section).getName();
        }

        return "";
    }

    @Override
    protected int getSectionCount() {
        if (schoolList != null) {
            return schoolList.size();
        }

       return 0;
    }

    @Override
    protected int getItemCountForSection(int section) {
        if (schoolList != null) {
            return schoolList.get(section).getDepartments().size();
        }

        return 0;
    }

    @Override
    protected ItemViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.school_view_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    protected void onBindItemViewHolder(ItemViewHolder holder, final int section, int position) {
        final School school = schoolList.get(section);
        final Department department = school.getDepartments().get(position);

        holder.render(department.getName(), department.getCode());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Adapter", "Clicked");

                v.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        schoolList.clear();

                        recyclerView.getRecycledViewPool().clear();
                        recyclerView.removeAllViews();

                        notifyDataSetChanged();
                    }
                }, 500);
            }
        });
    }
}