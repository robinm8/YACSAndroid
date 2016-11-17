package edu.rpi.cs.yacs.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import at.blogc.android.views.ExpandableTextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import edu.rpi.cs.yacs.R;
import edu.rpi.cs.yacs.models.Section;

/**
 * Created by Mark Robinson on 11/17/16.
 */
public class CourseItemViewHolder extends RecyclerView.ViewHolder{
    @BindView(R.id.name)
    TextView nameView;

    @BindView(R.id.code)
    TextView codeView;

    @BindView(R.id.credits)
    TextView creditsView;

    @BindView(R.id.description)
    ExpandableTextView descriptionView;

    @BindView(R.id.sectionsRecyclerView)
    RecyclerView recyclerView;

    public CourseItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void render(String name, String code, String credits, String description, List<Section> sections){
        nameView.setText(name);
        codeView.setText(code);
        creditsView.setText(credits);
        descriptionView.setText(description);


    }
}
