package com.example.jacobgperin.androidpasswordmanager.controller;

import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.jacobgperin.androidpasswordmanager.R;

/**
 * Created by Jacob G. Perin on 11/28/2017.
 */

public class PasswordListActivity extends SingleFragmentActivity{

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_new :
                //TODO
                return true;
            case R.id.action_preferences :
                //TODO
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected Fragment createFragment() {
        return new PasswordListFragment();
    }
}
