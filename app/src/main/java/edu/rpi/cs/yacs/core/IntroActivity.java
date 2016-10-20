package edu.rpi.cs.yacs.core;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntroFragment;

import edu.rpi.cs.yacs.R;
import edu.rpi.cs.yacs.slides.SelectHomeSlide;

/**
 * Created by Mark Robinson on 9/24/16.
 */

public class IntroActivity extends AppIntro2 {

    SelectHomeSlide selectHomeSlide = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        selectHomeSlide = new SelectHomeSlide();

        addSlide(AppIntroFragment.newInstance("Welcome To YACS!", "Simple, Sane Course Scheduling \n", R.mipmap.yacs_launcher, Color.parseColor("#ef5350")));
        addSlide(AppIntroFragment.newInstance("YACS", "100% RPI home-grown and hosted \n", R.mipmap.yacs_launcher, Color.parseColor("#ef5350")));
        addSlide(AppIntroFragment.newInstance("Google Calendar", "If you want, Google Calendar can be used to save your schedules.\n", R.mipmap.yacs_launcher, Color.parseColor("#ef5350")));
        addSlide(selectHomeSlide);
        addSlide(AppIntroFragment.newInstance("All Set!", "You're awesome, enjoy our app! \n", R.mipmap.yacs_launcher, Color.parseColor("#ef5350")));

        setFlowAnimation();

        askForPermissions(new String[]{Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR}, 3);
        setSwipeLock(false);
        setNextPageSwipeLock(false);
        setImmersiveMode(true);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String college = preferences.getString(getString(R.string.college), "unknown");

        if (!currentFragment.getClass().equals(selectHomeSlide.getClass()) && !college.equals("unknown")) {
            super.onSkipPressed(currentFragment);
            launchMain();
        }
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String college = preferences.getString(getString(R.string.college), "unknown");

        if (!currentFragment.getClass().equals(selectHomeSlide.getClass()) && !college.equals("unknown")) {
            super.onDonePressed(currentFragment);
            launchMain();
        }
    }

    public void launchMain() {
        Intent i = new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(i);
        finish();
    }
}