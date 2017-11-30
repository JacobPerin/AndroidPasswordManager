package com.example.jacobgperin.androidpasswordmanager.controller;

import android.support.v4.app.Fragment;
import android.view.Menu;

public class MainActivity extends SingleFragmentActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        // TODO :: Set up options menu
        //TODO :: Set up SearchView
        return true;
    }

    @Override
    protected Fragment createFragment() {
        return new PasswordListFragment();
    }
}
