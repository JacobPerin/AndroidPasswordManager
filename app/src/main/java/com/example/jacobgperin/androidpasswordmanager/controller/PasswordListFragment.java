package com.example.jacobgperin.androidpasswordmanager.controller;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.*;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.example.jacobgperin.androidpasswordmanager.model.Password;
import com.example.jacobgperin.androidpasswordmanager.model.PasswordDataSource;
import com.example.jacobgperin.androidpasswordmanager.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jacob G. Perin on 11/28/2017.
 */

public class PasswordListFragment extends Fragment implements SearchView.OnQueryTextListener {

    private RecyclerView mPasswordRecyclerView;

    private PasswordAdapter mPasswordAdapter;

    /*
       Initialize SearchView, and use f/ RecyclerView to filter data tags
     */

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
    }

    @Override

    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        //TODO :: Filter Logic
        return false;
    }

    /*
        Create RecyclerView w/ holder for each password
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_password_list, container, false);

        mPasswordRecyclerView = (RecyclerView) view.findViewById(R.id.password_recycler_view);
        mPasswordRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    /**
     * Helper method to initialize super adapter for password
     */
    private void updateUI() {

        PasswordDataSource passwordDataSource = PasswordDataSource.get(getActivity());
        List<Password> passwords = passwordDataSource.getPasswords();

        mPasswordAdapter = new PasswordAdapter(passwords);
        mPasswordRecyclerView.setAdapter(mPasswordAdapter);
    }

    private class PasswordHolder extends RecyclerView.ViewHolder {

        private Password mPassword;
        private TextView mPasswordView;
        private GridView mTagsView;

        public PasswordHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_password, parent, false));

            mPasswordView = (TextView) itemView.findViewById(R.id.password);
            mTagsView = (GridView) itemView.findViewById(R.id.tags);
        }

        public void bind(Password password) {
            mPassword = password;
            mPasswordView.setText(mPassword.getmPassword());
        }
    }

    private class PasswordAdapter extends RecyclerView.Adapter<PasswordHolder> {

        private List<Password> mPasswords;

        public PasswordHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new PasswordHolder(layoutInflater, parent);
        }

        public PasswordAdapter(List<Password> passwords) {
            mPasswords = passwords;
        }

        @Override
        public void onBindViewHolder(PasswordHolder holder, int position) {
            Password password = mPasswords.get(position);
            holder.bind(password);

            // Create a GridView f/ For Tags
            TagAdapter tagAdapter = new TagAdapter(getActivity(), password.getmTags());
            holder.mTagsView.setAdapter(tagAdapter);
        }

        @Override
        public int getItemCount() {
            return mPasswords.size();
        }
    }

    private class TagAdapter extends BaseAdapter {

        private ArrayList<String> mTags;
        private Activity mActivity;

        public TagAdapter(Activity activity, ArrayList<String> tags) {
            super();

            this.mTags = tags;
            this.mActivity = activity;
        }

        @Override
        public int getCount() {
            return mTags.size();
        }

        @Override
        public Object getItem(int i) {
            return mTags.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        public class ViewHolder {
            public TextView mTagView;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {

            final ViewHolder holder;

            LayoutInflater inflater = LayoutInflater.from(getActivity());

            if (convertView == null) {

                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.gridview_item_tag, null);

                holder.mTagView = (TextView) convertView.findViewById(R.id.tag);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.mTagView.setText(mTags.get(i));

            return convertView;
        }
    }
}