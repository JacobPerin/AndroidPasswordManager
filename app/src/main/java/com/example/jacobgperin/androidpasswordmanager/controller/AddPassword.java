package com.example.jacobgperin.androidpasswordmanager.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.jacobgperin.androidpasswordmanager.R;
import com.example.jacobgperin.androidpasswordmanager.model.PasswordDataSource;

public class AddPassword extends AppCompatActivity {

    private PasswordDataSource data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_password);

        data = new PasswordDataSource();
    }
}
