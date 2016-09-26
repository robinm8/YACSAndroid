package edu.rpi.cs.yacs.core;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;

import edu.rpi.cs.yacs.R;
import edu.rpi.cs.yacs.fragments.RecyclerViewFragment;

/**
 * Created by Mark Robinson on 9/23/16.
 */

public class MainActivity extends AppCompatActivity {

    private MaterialViewPager mViewPager;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        setTitle("");

        final Activity activity = this;

        ActionBar actionBar = getSupportActionBar();

        mViewPager = (MaterialViewPager) findViewById(R.id.materialViewPager);
        toolbar = mViewPager.getToolbar();

        if (toolbar != null) {
            setSupportActionBar(toolbar);

            toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.background));
            toolbar.setPopupTheme(R.style.MyDarkToolbarStyle);

            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setDisplayShowTitleEnabled(true);
                actionBar.setDisplayUseLogoEnabled(false);
                actionBar.setHomeButtonEnabled(true);
            }
        }

        mViewPager.getViewPager().setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                String title = String.valueOf(getPageTitle(position));
                switch (position % 2) {
                    case 0:
                        return RecyclerViewFragment.newInstance(title);
                    case 1:
                        return RecyclerViewFragment.newInstance(title);
                    default:
                        return RecyclerViewFragment.newInstance("");
                }
            }

            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position % 2) {
                    case 0:
                        return "Explore";
                    case 1:
                        return "Schedule";
                }
                return "";
            }
        });

        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                switch (page) {
                    case 0:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.primary,
                                "");
                    case 1:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.primary,
                                "");
                }

                return null;
            }
        });

        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());
    }
}