package com.example.jacobgperin.androidpasswordmanager.controller;

import android.os.Build;
import android.support.v7.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.preference.*;
import android.preference.PreferenceActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.SupportActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.jacobgperin.androidpasswordmanager.R;

/**
 * Created by Jacob G. Perin on 11/28/2017.
 */

public class PasswordListActivity extends SingleFragmentActivity{

    private static final int ADD_PASSWORD = 1;
    private static final int PREFERENCE = 2;
    private SearchView mSearchView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) searchItem.getActionView();

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_new :
                Intent addIntent = new Intent(this, AddPassword.class);
                startActivityForResult(addIntent, ADD_PASSWORD);
                return true;
            case R.id.action_preferences :
                Intent intent = new Intent(this,PrefActivity.class);
                startActivityForResult(intent, PREFERENCE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        switch(requestCode) {
            case ADD_PASSWORD:
                if (requestCode == 1) {
                    //The password was added successfully
                }
                break;
            case PREFERENCE:
                    getPreferenceValues();
                break;
            default:
                break;
        }
    }


    private void getPreferenceValues() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean themeToDark = sharedPref.getBoolean("theme", false);
        Boolean toggle = sharedPref.getBoolean("toggle", false);
        if(themeToDark){
            ActionBar bar = getSupportActionBar();
            bar.setBackgroundDrawable(new ColorDrawable(Color.BLACK));
            Window win = this.getWindow();
            win.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            win.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                win.setStatusBarColor(this.getResources().getColor(R.color.black));
            }
        }
        else if(toggle){
            
        }
        else if(getIntent().getExtras().getInt("data") == 1){
            recreate();
        }
    }

    @Override
    protected Fragment createFragment() {
        return new PasswordListFragment();
    }
}
