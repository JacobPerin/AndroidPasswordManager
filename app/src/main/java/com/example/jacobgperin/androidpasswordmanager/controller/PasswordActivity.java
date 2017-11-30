package com.example.jacobgperin.androidpasswordmanager.controller;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.jacobgperin.androidpasswordmanager.R;
import com.example.jacobgperin.androidpasswordmanager.model.Password;

import java.util.UUID;

public class PasswordActivity extends SingleFragmentActivity {

    public static final String EXTRA_PASSWORD_ID = "PASSWORD";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
    }

    @Override
    protected Fragment createFragment() {
        return new PasswordFragment();
    }

    public static Intent newIntent(Context packageContext, UUID passwordId){
        Intent intent = new Intent(packageContext, PasswordActivity.class);
        intent.putExtra(EXTRA_PASSWORD_ID, passwordId);
        return intent;
    }
}
