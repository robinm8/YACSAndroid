package edu.rpi.cs.yacs.slides;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.github.paolorotolo.appintro.AppIntroBaseFragment;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.github.paolorotolo.appintro.ISlidePolicy;

/**
 * Created by Mark on 12/8/17.
 */

public class PermissionSlide extends AppIntroBaseFragment implements ISlidePolicy {
    public static AppIntroFragment newInstance(CharSequence title, CharSequence description, @DrawableRes int imageDrawable, @ColorInt int bgColor) {
        AppIntroFragment slide = new AppIntroFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title.toString());
        args.putString(ARG_TITLE_TYPEFACE, null);
        args.putString(ARG_DESC, description.toString());
        args.putString(ARG_DESC_TYPEFACE, null);
        args.putInt(ARG_DRAWABLE, imageDrawable);
        args.putInt(ARG_BG_COLOR, bgColor);
        args.putInt(ARG_TITLE_COLOR, 0);
        args.putInt(ARG_DESC_COLOR, 0);
        slide.setArguments(args);

        return slide;
    }

    @Override
    protected int getLayoutId() {
        return com.github.paolorotolo.appintro.R.layout.fragment_intro2;
    }

    @Override
    public boolean isPolicyRespected() {
        return ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onUserIllegallyRequestedNextPage() {
        Log.d("AppIntro", "Permission request");
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
    }
}
