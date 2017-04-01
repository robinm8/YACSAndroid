package edu.rpi.cs.yacs.viewholders;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageButton;
import android.widget.TextView;

import at.blogc.android.views.ExpandableTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import butterknife.OnClick;
import edu.rpi.cs.yacs.R;
import edu.rpi.cs.yacs.models.Section;

import java.util.List;

import jp.wasabeef.recyclerview.animators.holder.AnimateViewHolder;

/**
 * Created by Mark Robinson on 11/17/16.
 */
public class CourseItemViewHolder extends AnimateViewHolder {

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
            recyclerView.setEnabled(false);
            descriptionView.collapse();

            final int[] stateSet = {android.R.attr.state_checked * (-1)};
            toggleButton.setImageState(stateSet, true);
        } else {
            descriptionView.expand();

            final int[] stateSet = {android.R.attr.state_checked};
            toggleButton.setImageState(stateSet, true);

            recyclerView.setEnabled(true);
        }
    }

    public CourseItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void render(String name, String code, String credits, String description, List<Section> sections) {
        nameView.setText(name);
        codeView.setText(code);
        creditsView.setText(credits);

        description = description.replaceAll("(?<=\\p{Ll})\\.(?=\\p{Lu})", ".\n\n");
        description = description.replaceAll("([\\p{L}\\d])\\.(\\p{Lu})", "$1.\n\n$2");
        description = description.replaceAll("([\\d])(\\p{Lu})", "$1 | $2");

        descriptionView.setText(description);
        descriptionView.setInterpolator(new OvershootInterpolator());

        recyclerView.setEnabled(false);
    }

    private Runnable pressRunnable = new Runnable() {
        @Override
        public void run() {
            descriptionView.setPressed(true);
            descriptionView.postOnAnimationDelayed(unpressRunnable, 20);
        }
    };

    private Runnable unpressRunnable = new Runnable() {
        @Override
        public void run() {
            descriptionView.setPressed(false);
        }
    };

    @Override
    public void animateRemoveImpl(ViewPropertyAnimatorListener listener) {
        ViewCompat.animate(itemView)
                .translationY(-itemView.getHeight() * 0.3f)
                .alpha(0)
                .setDuration(400)
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
                .setDuration(400)
                .setListener(listener)
                .start();
    }
}
