package com.example.jacobgperin.androidpasswordmanager.controller;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.jacobgperin.androidpasswordmanager.R;

/**
 * Created by Jacob G. Perin on 11/28/2017.
 */

public class PasswordListActivity extends SingleFragmentActivity{

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
                startActivityForResult(addIntent, 1);
                return true;
            case R.id.action_preferences :
                //TODO
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == 1){
            if(requestCode == 1){
                //The password was added successfully
            }
        }
    }

    @Override
    protected Fragment createFragment() {
        return new PasswordListFragment();
    }
}
