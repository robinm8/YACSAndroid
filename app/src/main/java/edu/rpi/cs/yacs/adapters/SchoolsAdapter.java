package edu.rpi.cs.yacs.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.truizlop.sectionedrecyclerview.SimpleSectionedAdapter;

import java.util.List;

import edu.rpi.cs.yacs.R;
import edu.rpi.cs.yacs.fragments.RecyclerViewFragment;
import edu.rpi.cs.yacs.models.Department;
import edu.rpi.cs.yacs.models.School;
import edu.rpi.cs.yacs.viewholders.SchoolItemViewHolder;

/**
 * Created by Mark Robinson on 10/15/16.
 */
public class SchoolsAdapter extends SimpleSectionedAdapter<SchoolItemViewHolder> {

    private List<School> schoolList = null;
    private RecyclerViewFragment recyclerViewFragment = null;

    public SchoolsAdapter(RecyclerViewFragment recyclerViewFragment, List<School> schoolList) {
        this.schoolList = schoolList;
        this.recyclerViewFragment = recyclerViewFragment;
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
    protected SchoolItemViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.school_view_item, parent, false);

        return new SchoolItemViewHolder(view);
    }

    @Override
    protected void onBindItemViewHolder(final SchoolItemViewHolder holder, final int section, final int position) {
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
//                        int modPosition = holder.getLayoutPosition();
//
//                        Log.d("school list 0 0", schoolList.get(0).getDepartments().get(0).getName());
//
//                        List<Department> departments =  schoolList.get(0).getDepartments();
//                        departments.remove(0);
//                        schoolList.get(0).setDepartments(departments);
//
//                        Log.d("school list 0 0", schoolList.get(0).getDepartments().get(0).getName());
//
//                        notifyItemRemoved(modPosition);
//                        recyclerViewFragment.getMAdapter().notifyItemRemoved(modPosition);


//                        Expected behavior: Clear list, animate removal of all items

                        int count = getItemCount();

                        schoolList.clear();

                        notifyItemRangeRemoved(0, count);
                        recyclerViewFragment.getMAdapter().notifyItemRangeRemoved(0, count);
                    }
                }, 500);
            }
        });
    }
}