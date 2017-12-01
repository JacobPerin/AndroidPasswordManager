package com.example.jacobgperin.androidpasswordmanager.controller;

import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.os.Bundle;

import com.example.jacobgperin.androidpasswordmanager.R;

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
        }
    }

}
