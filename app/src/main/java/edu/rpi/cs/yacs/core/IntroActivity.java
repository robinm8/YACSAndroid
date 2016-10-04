package edu.rpi.cs.yacs.core;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntroFragment;

import edu.rpi.cs.yacs.R;
import edu.rpi.cs.yacs.slides.SelectHomeSlide;

/**
 * Created by Mark Robinson on 9/24/16.
 */

public class IntroActivity extends AppIntro2 {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(AppIntroFragment.newInstance("Welcome To YACS!", "Short description \n", R.drawable.indicator_dot_white, Color.parseColor("#ef5350")));
        addSlide(AppIntroFragment.newInstance("YACS", "Description of features\n", R.drawable.indicator_dot_white, Color.parseColor("#ef5350")));
        addSlide(AppIntroFragment.newInstance("Google Calendar", "If you want, Google Calendar can be used to save your schedules.\n", R.drawable.indicator_dot_white, Color.parseColor("#ef5350")));
        addSlide(new SelectHomeSlide());
        addSlide(AppIntroFragment.newInstance("All Set!", "You're awesome, enjoy our app! \n", R.drawable.indicator_dot_white, Color.parseColor("#ef5350")));

        askForPermissions(new String[]{Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR}, 2);

        setFlowAnimation();
        setSwipeLock(false);
        setNextPageSwipeLock(false);
        setImmersiveMode(true);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);

        launchMain();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);

        launchMain();
    }

    public void launchMain() {
        Intent i = new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(i);
        finish();
    }
}