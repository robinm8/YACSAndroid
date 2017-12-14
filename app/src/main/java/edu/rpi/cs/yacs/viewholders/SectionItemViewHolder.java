package edu.rpi.cs.yacs.viewholders;

import android.graphics.Color;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.rpi.cs.yacs.R;
import edu.rpi.cs.yacs.core.YACSApplication;
import edu.rpi.cs.yacs.fragments.RecyclerViewFragment;
import edu.rpi.cs.yacs.models.Period;
import edu.rpi.cs.yacs.models.Section;
import jp.wasabeef.recyclerview.animators.holder.AnimateViewHolder;

/**
 * Created by mark on 4/4/17.
 */

public class SectionItemViewHolder extends RecyclerView.ViewHolder implements AnimateViewHolder {

    @BindView(R.id.name)
    TextView name;

    @BindView(R.id.seats)
    TextView seats;

    @BindView(R.id.crn)
    TextView crn;

    @BindView(R.id.instructors)
    TextView instructors;

    @BindView(R.id.monday)
    TextView monday;

    @BindView(R.id.tuesday)
    TextView tuesday;

    @BindView(R.id.wednesday)
    TextView wednesday;

    @BindView(R.id.thursday)
    TextView thursday;

    @BindView(R.id.friday)
    TextView friday;

    private ArrayList<ArrayList<String>> periods = new ArrayList<>();
    private ArrayList<TextView> days = new ArrayList<>();

    private RecyclerViewFragment recyclerViewFragment = null;

    public SectionItemViewHolder(View itemView, RecyclerViewFragment recyclerViewFragment) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        this.recyclerViewFragment = recyclerViewFragment;

        days.add(monday);
        days.add(tuesday);
        days.add(wednesday);
        days.add(thursday);
        days.add(friday);
    }

    public void render(final Section section) {
        SimpleDateFormat startDateForm = new SimpleDateFormat("hh:mm", Locale.US);
        SimpleDateFormat startDateFormSym = new SimpleDateFormat("hh:mm a", Locale.US);

        periods.clear();
        for (int i = 0; i < 5; i++) {
            periods.add(new ArrayList<>());
        }

        String seatsTotal = String.valueOf(section.getSeatsTotal());
        String seatsRemaining = String.valueOf(section.getSeatsTotal() - section.getSeatsTaken());

        name.setText(section.getName());
        crn.setText(String.valueOf(section.getCrn()));
        instructors.setText(section.getInstructors().isEmpty() ? "Instructors TBA" : TextUtils.join("; ", section.getInstructors()));
        seats.setText(String.format("%s/%s seats", seatsRemaining, seatsTotal));

        String startTime = "", endTime = "", blockTime;
        int day;

        for (Period period : section.getPeriods()) {
            day = period.getDay() - 1;

            try {
                Date startDate = new SimpleDateFormat("hhmm", Locale.US).parse(String.format(Locale.US, "%04d", period.getStart()));
                Date endDate = new SimpleDateFormat("hhmm", Locale.US).parse(String.format(Locale.US, "%04d", period.getEnd()));

                startTime = startDateForm.format(startDate);
                endTime = startDateFormSym.format(endDate);
            } catch (ParseException ignored) {}

            blockTime = String.format("%s -- %s", startTime, endTime);

            periods.get(day).add(blockTime);
        }

        for (int i = 0; i < 5; i++) {
            if (periods.get(i).size() > 0) {
                days.get(i).setText(TextUtils.join("\n", periods.get(i)));
            }
        }

        // recyclerview items are reused, best reset the view
        if (YACSApplication.getInstance().isSectionSelected(section)) {
            itemView.setBackgroundResource(R.color.colorPrimaryLight);
        }else{
            itemView.setBackgroundColor(Color.TRANSPARENT);
        }

        itemView.setOnClickListener(v -> {
        Log.d("SectionsAdapter", "Clicked");

        if (!YACSApplication.getInstance().isSectionSelected(section)) {
            itemView.setBackgroundResource(R.color.colorPrimaryLight);
            YACSApplication.getInstance().addSection(section);
        }else{
            itemView.setBackgroundColor(Color.TRANSPARENT);

            for (Section s : YACSApplication.getInstance().getSelectedSections()) {
                if (s.getCrn().equals(section.getCrn())) {
                    YACSApplication.getInstance().removeSection(s);

                    break;
                }
            }
        }

        recyclerViewFragment.repopulateWeekView();
        });
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
                .setDuration(400)
                .setListener(listener)
                .start();
    }

    @Override
    public void animateRemoveImpl(RecyclerView.ViewHolder holder, ViewPropertyAnimatorListener listener) {
        ViewCompat.animate(itemView)
                .translationY(-itemView.getHeight() * 0.3f)
                .alpha(0)
                .setDuration(400)
                .setListener(listener)
                .start();
    }
}