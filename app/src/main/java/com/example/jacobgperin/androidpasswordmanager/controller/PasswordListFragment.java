package com.example.jacobgperin.androidpasswordmanager.controller;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.util.SortedList;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.util.SortedListAdapterCallback;
import android.view.*;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.example.jacobgperin.androidpasswordmanager.databinding.ActivityFragmentBinding;
import com.example.jacobgperin.androidpasswordmanager.databinding.ListItemPasswordBinding;
import com.example.jacobgperin.androidpasswordmanager.model.Password;
import com.example.jacobgperin.androidpasswordmanager.model.PasswordDataSource;
import com.example.jacobgperin.androidpasswordmanager.R;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Jacob G. Perin on 11/28/2017.
 */

public class PasswordListFragment extends Fragment implements SearchView.OnQueryTextListener {

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
    public boolean onQueryTextChange(String query) {
        PasswordDataSource passwordDataSource = PasswordDataSource.get(getActivity());
        final List<Password> filteredPasswordList = filter(passwordDataSource.getPasswords(), query);
        mPasswordAdapter.replaceAll(filteredPasswordList);
        mBinding.passwordRecyclerView.scrollToPosition(0);
        return true;
    }

    private static List<Password> filter(List<Password> passwords, String query) {
        final String lowerCaseQuery = query.toLowerCase();

        final List<Password> filteredPasswordList = new ArrayList<>();

        for(Password password : passwords) {
            // Break tags into string
            final String tags = password.getmTags().toString().toLowerCase();
            // Check if query is contained in set
            if(tags.contains(query)) {
                filteredPasswordList.add(password);
            }
        }

        return filteredPasswordList;
    }

    public ActivityFragmentBinding mBinding;

    /*
        Create RecyclerView w/ holder for each password
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(getActivity(), R.layout.activity_fragment);

        // Retrieve data from singleton class
        PasswordDataSource passwordDataSource = PasswordDataSource.get(getActivity());
        List<Password> passwords = passwordDataSource.getPasswords();

        mPasswordAdapter = new PasswordAdapter(passwords);

        mBinding.passwordRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBinding.passwordRecyclerView.setAdapter(mPasswordAdapter);

        mPasswordAdapter.add(passwords);
    }



    private class PasswordHolder extends RecyclerView.ViewHolder {

        private Password mPassword;
        private final ListItemPasswordBinding mBinding;

        private GridView mTagsView;

        public PasswordHolder(ListItemPasswordBinding binding) {
            super(binding.getRoot());
            mBinding = binding;

            mTagsView = (GridView) itemView.findViewById(R.id.tags);
        }

        public void bind(Password password) {
            mPassword = password;
            mBinding.setPassword(mPassword);
        }
    }

    private class PasswordAdapter extends RecyclerView.Adapter<PasswordHolder> {

        /**
         * Show passwords w/ most tags first
         */
        private final Comparator<Password> mComparator = new Comparator<Password>() {
            @Override
            public int compare(Password p1, Password p2) {
                return p1.getmTags().size() - p2.getmTags().size();
            }
        };

        /**
         * Callback method for SearchView
         */
        private final SortedList<Password> mSortedList = new SortedList<Password>(Password.class, new SortedList.Callback<Password>() {
            @Override
            public void onInserted(int position, int count) {
                notifyItemRangeInserted(position, count);
            }

            @Override
            public void onRemoved(int position, int count) {
                notifyItemRangeRemoved(position, count);
            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {
                notifyItemMoved(fromPosition, toPosition);
            }

            @Override
            public int compare(Password p1, Password p2) {
                return mComparator.compare(p1, p2);
            }

            @Override
            public void onChanged(int position, int count) {
                notifyItemRangeChanged(position, count);
            }

            @Override
            public boolean areContentsTheSame(Password oldItem, Password newItem) {
                return oldItem.equals(newItem);
            }

            @Override
            public boolean areItemsTheSame(Password item1, Password item2) {
                return item1.getId() == item2.getId();
            }
        });

        /*
        Helper Method for CallBack
         */
        public void replaceAll(List<Password> passwords) {
            mSortedList.beginBatchedUpdates();
            for(int i = mSortedList.size() - 1; i >= 0; i--) {
                final Password password = mSortedList.get(i);
                if(!passwords.contains(password)) {
                    mSortedList.remove(password);
                }
            }

            mSortedList.addAll();
            mSortedList.endBatchedUpdates();
        }

        public void add(Password password) {
            mSortedList.add(password);
        }

        public void remove(Password password) {
            mSortedList.remove(password);
        }

        public void add(List<Password> passwords) {
            mSortedList.addAll(passwords);
        }

        public void remove(List<Password> passwords) {
            mSortedList.beginBatchedUpdates();
            for (Password password : passwords) {
                mSortedList.remove(password);
            }
            mSortedList.endBatchedUpdates();
        }

        private List<Password> mPasswords;

        public PasswordHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            final ListItemPasswordBinding binding = ListItemPasswordBinding.inflate(layoutInflater, parent, false );
            return new PasswordHolder(binding);


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

    /**
     * Nested Adapter f/ GridView implementation to hold Tags
     */
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