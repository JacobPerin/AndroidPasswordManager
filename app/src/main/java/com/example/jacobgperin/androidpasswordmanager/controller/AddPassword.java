package com.example.jacobgperin.androidpasswordmanager.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
                String passText = password.getText().toString();
                pass.setmPassword(passText);
                ArrayList<PasswordTag> passwordTags = new ArrayList<>();
                String tagString = tags.getText().toString();

                if(tagString.length() == 0 || passText.length() == 0){
                    Toast.makeText(getApplicationContext(), "Can not leave Password or tags blank", Toast.LENGTH_SHORT).show();
                    return;
                }
                String delim = "[,]";
                String[] tokens = tagString.split(delim);

                for(int i = 0; i < tokens.length; i++){
                    PasswordTag passTag = new PasswordTag();
                    passTag.setName(tokens[i]);
                    passTag.setmTagID(pass.getId());
                    passwordTags.add(passTag);
                }

                pass.setmTags(passwordTags);


                data.addPassword(pass);

                finish();

                //TODO :: add intent and update list
            }
        });
    }
}


