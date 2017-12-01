package com.example.jacobgperin.androidpasswordmanager.controller;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

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

    public Password mPassword;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UUID passwordID = (UUID) getActivity()
                .getIntent()
                .getSerializableExtra(PasswordActivity.EXTRA_PASSWORD_ID);

        mPassword = PasswordDataSource
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
                holder.mTagView.setTextSize(18);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.mTagView.setText(mTags.get(position));

            return convertView;
        }
    }
}
