package com.example.jacobgperin.androidpasswordmanager.controller;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jacobgperin.androidpasswordmanager.R;
import com.example.jacobgperin.androidpasswordmanager.model.Password;
import com.example.jacobgperin.androidpasswordmanager.model.PasswordDataSource;

import java.util.ArrayList;
import java.util.UUID;


/**
 * Created by Jacob G. Perin on 11/28/2017.
 */

public class PasswordFragment extends Fragment{

    private TextView mPasswordView;
    private GridView mTagsView;
    private EditText mNewPasswordView;
    private Button changeButton;
    private PasswordDataSource data;
    private PasswordActivity changePassword;

    public Password mPassword;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        changePassword = (PasswordActivity) getActivity();
        data = PasswordDataSource.get(getActivity());
        UUID passwordID = (UUID) getActivity()
                .getIntent()
                .getSerializableExtra(PasswordActivity.EXTRA_PASSWORD_ID);

        mPassword = data
                .get(getActivity())
                .getPassword(passwordID);


    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_password, container, false);

        // Access TextView && Set Text
        mPasswordView = (TextView) v.findViewById(R.id.password);
        mPasswordView.setText(mPassword.getmPassword());

        // Create a GridView f/ For Tags
        mTagsView = (GridView)v.findViewById(R.id.tags);
        TagAdapter tagAdapter = new TagAdapter(mPassword.getmTags());
        mTagsView.setAdapter(tagAdapter);

        mNewPasswordView = (EditText) v.findViewById(R.id.ChangePass);
        changeButton = (Button) v.findViewById(R.id.ChangeButton);

        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword.changePassword(mPassword, mNewPasswordView.getText().toString());
            }
        });

        return v;
    }

    public class TagAdapter extends BaseAdapter {

        public ArrayList<String> mTags;

        public TagAdapter(ArrayList<String> tags) {
            super();

            mTags = tags;
        }

        public int getCount() {
            return mTags.size();
        }

        public Object getItem(int position) {
            return mTags.get(position);
        }

        public long getItemId(int position) {
            return 0;
        }

        public class ViewHolder {
            public TextView mTagView;
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            final ViewHolder holder;

            LayoutInflater inflater = LayoutInflater.from(getActivity());

            if (convertView == null) {

                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.gridview_item_tag, null);

                holder.mTagView = (TextView) convertView.findViewById(R.id.tag);

                holder.mTagView.setGravity(Gravity.CENTER);
                holder.mTagView.setTextSize(22);
                holder.mTagView.setTextColor(getResources().getColor(R.color.white));

                holder.mTagView.setBackground(getResources().getDrawable(R.drawable.rounded_corner));

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.mTagView.setText(mTags.get(position));

            return convertView;
        }
    }
}
