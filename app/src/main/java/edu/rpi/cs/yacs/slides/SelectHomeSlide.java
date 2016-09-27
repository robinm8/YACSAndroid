package edu.rpi.cs.yacs.slides;

import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.ISlidePolicy;

/**
 * Created by Mark Robinson on 9/26/16.
 */

public final class SelectHomeSlide extends Fragment implements ISlidePolicy {
    @Override
    public boolean isPolicyRespected() {
        return false;// If user should be allowed to leave this slide
    }

    @Override
    public void onUserIllegallyRequestedNextPage() {
        // User illegally requested next slide
    }
}