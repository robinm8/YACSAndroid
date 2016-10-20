package edu.rpi.cs.yacs.core;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;

import edu.rpi.cs.yacs.R;

/**
 * Created by Mark Robinson on 9/25/16.
 */

public class SettingsActivity extends PreferenceActivity {

    public static class SettingsFragment extends PreferenceFragment {

        SharedPreferences sp = null;

        @Override
        public boolean onPreferenceTreeClick(final PreferenceScreen preferenceScreen, final Preference preference) {
            if (preference.getKey().equals(getString(R.string.college))) {
                new MaterialDialog.Builder(getActivity())
                        .title(R.string.college)
                        .items(R.array.colleges)
                        .itemsCallbackSingleChoice(0, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                                preference.setSummary(text);
                                preference.setDefaultValue(text);

                                sp.edit().putString(getString(R.string.college), String.valueOf(text)).apply();

                                YACSApplication.getInstance().getServiceHelper().invalidateService();
                                return true;
                            }
                        })
                        .positiveText(R.string.Select)
                        .show();
            }

            return super.onPreferenceTreeClick(preferenceScreen, preference);
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            addPreferencesFromResource(R.xml.prefs);

            sp = getPreferenceScreen().getSharedPreferences();

            Preference college = findPreference(getString(R.string.college));
            college.setSummary(sp.getString(getString(R.string.college), "unknown"));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(android.R.id.content, new SettingsFragment())
                    .commit();
        }
    }

    @Override
    protected boolean isValidFragment(String fragmentName) {
        return SettingsFragment.class.getName().equals(fragmentName);
    }
}