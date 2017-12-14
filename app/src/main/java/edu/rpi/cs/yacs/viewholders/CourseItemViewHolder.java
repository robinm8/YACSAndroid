package edu.rpi.cs.yacs.viewholders;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageButton;
import android.widget.TextView;

import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;
import com.mikepenz.iconics.context.IconicsLayoutInflater2;

import at.blogc.android.views.ExpandableTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import butterknife.OnClick;
import edu.rpi.cs.yacs.R;
import edu.rpi.cs.yacs.adapters.SchoolsAdapter;
import edu.rpi.cs.yacs.adapters.SectionsAdapter;
import edu.rpi.cs.yacs.fragments.LinearLayoutManagerWithSmoothScroller;
import edu.rpi.cs.yacs.fragments.RecyclerViewFragment;
import edu.rpi.cs.yacs.models.Section;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
import jp.wasabeef.recyclerview.animators.holder.AnimateViewHolder;
import jp.wasabeef.recyclerview.internal.ViewHelper;

/**
 * Created by Mark Robinson on 11/17/16.
 */
public class CourseItemViewHolder extends RecyclerView.ViewHolder implements AnimateViewHolder {

    private ScaleInAnimationAdapter scaleInAnimationAdapter = null;
    private List<Section> sections = null, sectionsCopy = new ArrayList<>();

    private RecyclerViewFragment recyclerViewFragment;

    @BindView(R.id.code)
    TextView codeView;

    @BindView(R.id.credits)
    TextView creditsView;

    @BindView(R.id.description)
    ExpandableTextView descriptionView;

    @BindView(R.id.name)
    TextView nameView;

    @BindView(R.id.sectionsRecyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.toggle)
    ImageButton toggleButton;

    @OnClick({ R.id.code, R.id.credits, R.id.description, R.id.name, R.id.toggle })
    void toggleAction() {
        pressRunnable.run();

        if (descriptionView.isExpanded()) {
            sections.clear();
            scaleInAnimationAdapter.notifyItemRangeRemoved(0, sectionsCopy.size());

            descriptionView.postOnAnimationDelayed(unpopulateRecyclerView, 333);
        } else {
            descriptionView.expand();

            final int[] stateSet = {android.R.attr.state_checked};
            toggleButton.setImageState(stateSet, true);

            descriptionView.postOnAnimationDelayed(populateRecyclerView, 333);
        }
    }

    public CourseItemViewHolder(View itemView, RecyclerViewFragment recyclerViewFragment) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        this.recyclerViewFragment = recyclerViewFragment;
    }

    public void render(String name, String code, String credits, String description, List<Section> sections, Activity activity) {
        nameView.setText(name);
        codeView.setText(code);
        creditsView.setText(credits);

        description = description.replaceAll("(?<=\\p{Ll})\\.(?=\\p{Lu})", ".\n\n");
        description = description.replaceAll("([\\p{L}\\d])\\.(\\p{Lu})", "$1.\n\n$2");
        description = description.replaceAll("([\\d])(\\p{Lu})", "$1 | $2");
        description = description.replaceAll("([\\d])(\\p{Lu})", "$1 | $2");

        descriptionView.setText(description);
        descriptionView.setInterpolator(new OvershootInterpolator());

        this.sections = sections;
        sectionsCopy.clear();
        sectionsCopy.addAll(sections);

        recyclerView.setEnabled(false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManagerWithSmoothScroller(activity);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(false);
        recyclerView.setItemAnimator(new SlideInUpAnimator());

        SectionsAdapter sectionsAdapter = new SectionsAdapter(sections, recyclerViewFragment);

        AlphaInAnimationAdapter alphaInAnimationAdapter = new AlphaInAnimationAdapter(sectionsAdapter);
        alphaInAnimationAdapter.setFirstOnly(false);
        alphaInAnimationAdapter.setDuration(250);

        scaleInAnimationAdapter = new ScaleInAnimationAdapter(alphaInAnimationAdapter);
        scaleInAnimationAdapter.setFirstOnly(false);
        alphaInAnimationAdapter.setDuration(250);
        scaleInAnimationAdapter.setInterpolator(new OvershootInterpolator());

        recyclerView.swapAdapter(scaleInAnimationAdapter, true);

        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        sections.clear();
        scaleInAnimationAdapter.notifyItemRangeRemoved(0, sectionsCopy.size());

        unpopulateRecyclerView.run();
    }

    private Runnable pressRunnable = new Runnable() {
        @Override
        public void run() {
            descriptionView.setPressed(true);
            descriptionView.postOnAnimationDelayed(unpressRunnable, 1000);
        }
    };

    private Runnable unpressRunnable = new Runnable() {
        @Override
        public void run() {
            descriptionView.setPressed(false);
        }
    };

    private Runnable populateRecyclerView = new Runnable() {
        @Override
        public void run() {
            sections.clear();
            scaleInAnimationAdapter.notifyItemRangeRemoved(0, sectionsCopy.size());

            sections.addAll(sectionsCopy);
            scaleInAnimationAdapter.notifyItemRangeInserted(0, sectionsCopy.size());

            recyclerView.setHasFixedSize(true);
        }
    };

    private Runnable unpopulateRecyclerView = new Runnable() {
        @Override
        public void run() {
            descriptionView.collapse();

            recyclerView.setHasFixedSize(false);

            sections.clear();
            scaleInAnimationAdapter.notifyItemRangeRemoved(0, sectionsCopy.size());

            final int[] stateSet = {android.R.attr.state_checked * (-1)};
            toggleButton.setImageState(stateSet, true);
        }
    };

    @Override
    public void preAnimateAddImpl(RecyclerView.ViewHolder holder) {
        ViewCompat.setTranslationY(itemView, -itemView.getHeight() * 0.3f);
        ViewCompat.setAlpha(itemView, 0);
    }

    @Override
    public void preAnimateRemoveImpl(RecyclerView.ViewHolder holder) {
        ViewCompat.setTranslationY(itemView, 0);
        ViewCompat.setAlpha(itemView, 1);
    }

    @Override
    public void animateAddImpl(RecyclerView.ViewHolder holder, ViewPropertyAnimatorListener listener) {
        ViewCompat.animate(itemView)
                .translationY(0)
                .alpha(1)
                .setDuration(300)
                .setListener(listener)
                .start();
    }

    @Override
    public void animateRemoveImpl(RecyclerView.ViewHolder holder, ViewPropertyAnimatorListener listener) {
        ViewCompat.animate(itemView)
                .translationY(-itemView.getHeight() * 0.3f)
                .alpha(0)
                .setDuration(300)
                .setListener(listener)
                .start();
    }
}