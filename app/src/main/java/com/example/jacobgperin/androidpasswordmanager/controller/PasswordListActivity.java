package com.example.jacobgperin.androidpasswordmanager.Controller;

import android.support.v4.app.Fragment;

/**
 * Created by Jacob G. Perin on 11/28/2017.
 */

public class PasswordListActivity extends SingleFragmentActivity{

    @Override
    protected Fragment createFragment() {
        return new PasswordListFragment();
    }
}
