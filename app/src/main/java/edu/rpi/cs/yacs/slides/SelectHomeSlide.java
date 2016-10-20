package edu.rpi.cs.yacs.slides;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.github.paolorotolo.appintro.ISlidePolicy;

import edu.rpi.cs.yacs.R;
import edu.rpi.cs.yacs.core.YACSApplication;

/**
 * Created by Mark Robinson on 9/26/16.
 */

public final class SelectHomeSlide extends Fragment implements ISlidePolicy {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.select_home_slide, container, false);
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String college = preferences.getString(getString(R.string.college), "unknown");

            if (college.equals("unknown")) {
                makeYACSHomeDialog();
            }
        }
    }

    @Override
    public boolean isPolicyRespected() {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String college = preferences.getString(getString(R.string.college), "unknown");

            return !(college.equals("unknown")); // If user should be allowed to leave this slide
    }

    @Override
    public void onUserIllegallyRequestedNextPage() {
        makeYACSHomeDialog();
    }

    public void makeYACSHomeDialog() {
        new MaterialDialog.Builder(getContext())
                .title(R.string.college)
                .items(R.array.colleges)
                .itemsCallbackSingleChoice(0, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                        preferences.edit().putString(getString(R.string.college), String.valueOf(text)).apply();

                        YACSApplication.getInstance().getServiceHelper().invalidateService();

                        return true;
                    }
                })
                .positiveText(R.string.Select)
                .show();
    }
}