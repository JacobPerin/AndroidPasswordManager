package com.example.jacobgperin.androidpasswordmanager.controller;

import android.content.Intent;
import android.support.annotation.BoolRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.text.method.SingleLineTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

        //Get Database instance
        data = PasswordDataSource.get(getApplicationContext());

        Button hidePass = (Button) findViewById(R.id.hidePass);
        Button submit = (Button) findViewById(R.id.button);

        hidePass.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                EditText password = (EditText) findViewById(R.id.PasswordInput);
                CheckBox check = (CheckBox) findViewById(R.id.hidePass);
                Boolean checked = check.isChecked();
                if(checked){
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                else{
                    password.setTransformationMethod(SingleLineTransformationMethod.getInstance());
                }

            }
        });
        //Create onClick Listener for users on submit of new password
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

                //Make sure user enter information
                if(tagString.length() == 0 || passText.length() == 0){
                    Toast.makeText(getApplicationContext(), "Can not leave Password or tags blank", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Get the different tags based on comma seperation
                String delim = "[,]";
                String[] tokens = tagString.split(delim);

                //Create ArrayList of Tags
                for (String token : tokens) {
                    PasswordTag passTag = new PasswordTag();
                    passTag.setName(token);
                    passTag.setmTagID(pass.getId());
                    passwordTags.add(passTag);
                }

                pass.setmTags(passwordTags);

                //Add password to databse.
                data.addPasswordDatabase(pass);
                data.addPasswordList(pass);

                // Go back to main activity
                finish();

            }
        });
    }
}


