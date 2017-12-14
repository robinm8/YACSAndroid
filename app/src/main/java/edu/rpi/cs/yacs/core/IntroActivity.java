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

import net.alhazmy13.catcho.library.Catcho;

import edu.rpi.cs.yacs.R;
import edu.rpi.cs.yacs.slides.PermissionSlide;
import edu.rpi.cs.yacs.slides.SelectHomeSlide;

import java.io.Serializable;

/**
 * Created by Mark Robinson on 9/24/16.
 */

public class IntroActivity extends AppIntro2 implements Serializable {

    SelectHomeSlide selectHomeSlide = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Catcho.Builder(this)
                .recipients("mark@codecornerapps.com")
                .build();

        selectHomeSlide = new SelectHomeSlide();

        Bundle bundle = new Bundle();
        bundle.putSerializable("activity", this);

        showSkipButton(false);

        selectHomeSlide.setArguments(bundle);

        askForPermissions(new String[]{Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 3);

        addSlide(AppIntroFragment.newInstance("Welcome To YACS Android!", "Simple, Sane Course Scheduling \n\n( Now On-The-Go! )", R.mipmap.yacs_launcher, Color.parseColor("#ef5350")));
        addSlide(AppIntroFragment.newInstance("YACS Android", "100% RPI home-grown \n", R.mipmap.yacs_launcher, Color.parseColor("#ef5350")));
        addSlide(PermissionSlide.newInstance("Your Calendar", "Save your schedule \nTo your favorite calendar", R.mipmap.yacs_launcher, Color.parseColor("#ef5350")));

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String college = preferences.getString(getString(R.string.college), "unknown");

        if (college.equals("unknown")) {
            addSlide(selectHomeSlide);
        }else{
            launchMain();
        }

        addSlide(AppIntroFragment.newInstance("All Set!", "You're awesome, enjoy! \n", R.mipmap.yacs_launcher, Color.parseColor("#ef5350")));

        setFlowAnimation();
        setSwipeLock(false);
        setNextPageSwipeLock(false);
        setImmersiveMode(true);
    }

    public void clickNextButton() {
        nextButton.performClick();
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