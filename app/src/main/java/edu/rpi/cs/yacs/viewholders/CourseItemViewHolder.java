package edu.rpi.cs.yacs.viewholders;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import at.blogc.android.views.ExpandableTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import edu.rpi.cs.yacs.R;
import edu.rpi.cs.yacs.models.Section;

import java.util.List;

import jp.wasabeef.recyclerview.animators.holder.AnimateViewHolder;

/**
 * Created by Mark Robinson on 11/17/16.
 */
public class CourseItemViewHolder extends AnimateViewHolder {
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

    @Override
    public void animateRemoveImpl(ViewPropertyAnimatorListener listener) {
        ViewCompat.animate(itemView)
                .translationY(-itemView.getHeight() * 0.3f)
                .alpha(0)
                .setDuration(300)
                .setListener(listener)
                .start();
    }

    @Override
    public void preAnimateAddImpl() {
        ViewCompat.setTranslationY(itemView, -itemView.getHeight() * 0.3f);
        ViewCompat.setAlpha(itemView, 0);
    }

    @Override
    public void animateAddImpl(ViewPropertyAnimatorListener listener) {
        ViewCompat.animate(itemView)
                .translationY(0)
                .alpha(1)
                .setDuration(300)
                .setListener(listener)
                .start();
    }

    public void render(String name, String code, String credits, String description, List<Section> sections){
        nameView.setText(name);
        codeView.setText(code);
        creditsView.setText(credits);
        descriptionView.setText(description);


    }
}
