package com.example.jacobgperin.androidpasswordmanager.controller;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.example.jacobgperin.androidpasswordmanager.R;
import com.example.jacobgperin.androidpasswordmanager.model.Password;
import com.example.jacobgperin.androidpasswordmanager.model.PasswordDataSource;

import java.util.UUID;


/**
 * Created by Jacob G. Perin on 11/28/2017.
 */

public class PasswordFragment extends Fragment{

    private TextView mPasswordView;
    private GridView mTagsView;

    public Password mPassword;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UUID passwordID = (UUID) getActivity()
                .getIntent()
                .getSerializableExtra(PasswordActivity.EXTRA_PASSWORD_ID);

        mPassword = PasswordDataSource
                .get(getActivity())
                .getPassword(passwordID);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_password, container, false);

        // Access TextView && Set Text
        mPasswordView = (TextView) v.findViewById(R.id.password);
        mPasswordView.setText(mPassword.getmPassword());



        return super.onCreateView(inflater, container, savedInstanceState); //FIXME
    }
}
