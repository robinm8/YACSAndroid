package edu.rpi.cs.yacs.core;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.mikepenz.aboutlibraries.Libs;
import com.mikepenz.aboutlibraries.LibsBuilder;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.rpi.cs.yacs.R;
import edu.rpi.cs.yacs.enums.RecyclerViewMode;
import edu.rpi.cs.yacs.fragments.RecyclerViewFragment;

/**
 * Created by Mark Robinson on 9/23/16.
 */

public class MainActivity extends AppCompatActivity {

    private Drawer mDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar toolbar;

    @BindView(R.id.materialViewPager)
    MaterialViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        setTitle("");

        ButterKnife.bind(this);

        final Activity activity = this;

        ActionBar actionBar = getSupportActionBar();

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

        mDrawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withActionBarDrawerToggleAnimated(true)
                .withActionBarDrawerToggle(true)
                .addDrawerItems(
                        new PrimaryDrawerItem()
                                .withName(R.string.drawer_item_home)
                                .withIcon(FontAwesome.Icon.faw_home).withIdentifier(4)
                )
                .addStickyDrawerItems(
                        new PrimaryDrawerItem()
                                .withName(R.string.drawer_item_settings)
                                .withIcon(FontAwesome.Icon.faw_cog)
                                .withIdentifier(1)
                                .withSelectable(false),
                        new PrimaryDrawerItem()
                                .withName(R.string.drawer_item_open_source)
                                .withIcon(FontAwesome.Icon.faw_github)
                                .withIdentifier(2)
                                .withSelectable(false)
//                        new PrimaryDrawerItem()
//                                .withName(R.string.drawer_item_feedback)
//                                .withIcon(FontAwesome.Icon.faw_question_circle)
//                                .withIdentifier(3)
//                                .withSelectable(false)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem != null) {
                            Intent intent = null;

                            if (drawerItem.getIdentifier() == 1) {
                                intent = new Intent(activity, SettingsActivity.class);
                            } else if (drawerItem.getIdentifier() == 2) {
                                intent = new LibsBuilder()
                                        .withFields(R.string.class.getFields())
                                        .withActivityStyle(Libs.ActivityStyle.LIGHT_DARK_TOOLBAR)
                                        .intent(MainActivity.this);
                            } else if (drawerItem.getIdentifier() == 3) {
//                                 TODO: Make Feedback Dialog
                            }
                            if (intent != null) {
                                startActivity(intent);
                                return true;
                            }
                        }
                        return false;
                    }
                })
                .withShowDrawerOnFirstLaunch(false)
                .build();


        mDrawerToggle = mDrawer.getActionBarDrawerToggle();

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

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (mDrawer != null && mDrawer.isDrawerOpen()) {
            mDrawer.closeDrawer();
        } else if (YACSApplication.getInstance().getRecyclerViewMode() == RecyclerViewMode.COURSES) {
            // TODO: On back pressed, clear Main RecylcerView and load departments
        } else {
            super.onBackPressed();
        }
    }
}