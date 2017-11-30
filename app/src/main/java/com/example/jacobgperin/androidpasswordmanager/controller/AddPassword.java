package com.example.jacobgperin.androidpasswordmanager.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.jacobgperin.androidpasswordmanager.R;
import com.example.jacobgperin.androidpasswordmanager.model.Password;
import com.example.jacobgperin.androidpasswordmanager.model.PasswordDataSource;
import com.example.jacobgperin.androidpasswordmanager.model.PasswordTag;

import java.util.ArrayList;

public class AddPassword extends AppCompatActivity {

    private PasswordDataSource data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_password);

        data = PasswordDataSource.get(getApplicationContext());
        Button submit = (Button) findViewById(R.id.button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText password = (EditText) findViewById(R.id.PasswordInput);
                EditText tags = (EditText) findViewById(R.id.TagsInput);

                Password pass = new Password();
                pass.setmPassword(password.getText().toString());
                ArrayList<PasswordTag> passwordTags = new ArrayList<>();
                String tagString = tags.getText().toString();
                String delim = "[,]";
                String[] tokens = tagString.split(delim);

                for(int i = 0; i < tokens.length; i++){
                    PasswordTag passTag = new PasswordTag();
                    passTag.setName(tokens[i]);
                    passTag.setmTagID(pass.getId());
                    passwordTags.add(passTag);
                }

                pass.setmTags(passwordTags);

                // Add password to list and database
                data.addPassword(pass);
                data.addPasswordList(pass);

                // Go back to main activity
                finish();
            }
        });
    }
}


