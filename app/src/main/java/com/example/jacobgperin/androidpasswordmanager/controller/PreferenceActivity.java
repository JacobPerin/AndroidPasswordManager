package com.example.jacobgperin.androidpasswordmanager.controller;

import android.preference.PreferenceFragment;
import android.support.v4.app.BundleCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

import com.example.jacobgperin.androidpasswordmanager.R;

public class PreferenceActivity extends android.preference.PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.pref);
//        getFragmentManager().beginTransaction()
//                .replace(android.R.id.content,
//                        new PreferenceFrag()).commit();
    }
//
//    public static class PreferenceFrag extends PreferenceFragment {
//        @Override
//        public void onCreate(Bundle savedInstanceState){
//            super.onCreate(savedInstanceState);
//            addPreferencesFromResource(R.xml.pref);
//        }
//    }

}