package edu.rpi.cs.yacs.viewholders;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import edu.rpi.cs.yacs.R;

import jp.wasabeef.recyclerview.animators.holder.AnimateViewHolder;

/**
 * Created by Mark Robinson on 10/15/16.
 */
public class SchoolItemViewHolder extends RecyclerView.ViewHolder implements AnimateViewHolder {
    @BindView(R.id.name)
    TextView nameView;

    @BindView(R.id.code)
    TextView codeView;

    public SchoolItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

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

    public void render(String name, String code){
        nameView.setText(name);
        codeView.setText(code);
    }
}