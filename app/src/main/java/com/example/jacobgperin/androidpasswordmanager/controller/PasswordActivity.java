package com.example.jacobgperin.androidpasswordmanager.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jacobgperin.androidpasswordmanager.R;
import com.example.jacobgperin.androidpasswordmanager.model.Password;
import com.example.jacobgperin.androidpasswordmanager.model.PasswordDataSource;

import java.util.UUID;

public class PasswordActivity extends SingleFragmentActivity {

    public static final String EXTRA_PASSWORD_ID = "PASSWORD";

    private PasswordDataSource data;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);




    }

    public void changePassword(Password password, String newPass){

        String oldPass = password.getmPassword();
        if(newPass == "" || newPass == oldPass)
            return;
        password.setmPassword(newPass);
        PasswordDataSource.get(getApplicationContext()).updatePasswordDB(password);
        PasswordDataSource.get(getApplicationContext()).updatePasswordList(password);
        finish();
    }

    public static Intent newIntent(Context packageContext, UUID passwordId){
        Intent intent = new Intent(packageContext, PasswordActivity.class);
        intent.putExtra(EXTRA_PASSWORD_ID, passwordId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        return new PasswordFragment();
    }
}
