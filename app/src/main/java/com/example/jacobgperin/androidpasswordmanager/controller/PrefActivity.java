package com.example.jacobgperin.androidpasswordmanager.controller;

import android.content.Intent;
import android.content.res.Configuration;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.os.Bundle;

import com.example.jacobgperin.androidpasswordmanager.R;
import com.example.jacobgperin.androidpasswordmanager.model.PasswordDataSource;

import java.util.Locale;

public class PrefActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction()
                .replace(android.R.id.content,
                        new PreferenceFrag()).commit();
    }

    public static class PreferenceFrag extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref);

            Preference pref = findPreference("data");
            Preference lang = findPreference("lang");
            pref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    PasswordDataSource data = PasswordDataSource.get(getActivity());
                    data.clearList();
                    data.clearDB();
                    return false;
                }
            });
        }
    }

}
