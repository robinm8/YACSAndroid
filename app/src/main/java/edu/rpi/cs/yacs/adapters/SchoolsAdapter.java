package edu.rpi.cs.yacs.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.truizlop.sectionedrecyclerview.SimpleSectionedAdapter;

import java.util.List;

import edu.rpi.cs.yacs.R;
import edu.rpi.cs.yacs.models.School;
import edu.rpi.cs.yacs.viewholders.ItemViewHolder;

/**
 * Created by Mark Robinson on 10/15/16.
 */
public class SchoolsAdapter extends SimpleSectionedAdapter<ItemViewHolder> {

    private List<School> schoolList = null;

    public SchoolsAdapter(final List<School> schoolList) {
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
        View view = inflater.inflate(R.layout.view_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    protected void onBindItemViewHolder(ItemViewHolder holder, int section, int position) {
        String departmentName = schoolList.get(section).getDepartments().get(position).getName();
        String departmentCode = schoolList.get(section).getDepartments().get(position).getCode();

        holder.render(departmentName, departmentCode);
    }
}