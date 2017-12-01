package com.example.jacobgperin.androidpasswordmanager.controller;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.util.SortedList;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.method.PasswordTransformationMethod;
import android.text.method.SingleLineTransformationMethod;
import android.view.*;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

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

    public ActivityFragmentBinding mBinding;
    private PasswordAdapter mPasswordAdapter;


    /**
     *  Initialize SearchView to Filter Tags
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
    }

    /**
     * Text listener(s) for SearchView
     */
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

    /**
     * Helper Method f/ onQueryTextChange to filter results
     */
    private static List<Password> filter(List<Password> passwords, String query) {
        final String lowerCaseQuery = query.toLowerCase();

        final List<Password> filteredPasswordList = new ArrayList<>();

        for(Password password : passwords) {
            // Break tags into string
            final String tags = password.getmTags().toString().toLowerCase();
            // Check if query is contained in set
            if(tags.contains(lowerCaseQuery)) {
                filteredPasswordList.add(password);
            }
        }

        return filteredPasswordList;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        mBinding = DataBindingUtil.setContentView(getActivity(), R.layout.activity_fragment);

        // Retrieve data from singleton class
        final PasswordDataSource passwordDataSource = PasswordDataSource.get(getActivity());
        List<Password> passwords = passwordDataSource.getPasswords();

        mPasswordAdapter = new PasswordAdapter(TagSizeComparator);

        mBinding.passwordRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBinding.passwordRecyclerView.setAdapter(mPasswordAdapter);

        mPasswordAdapter.add(passwords);
        
        RecyclerView mRecyclerView = (getActivity()).findViewById(R.id.password_recycler_view);
        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), mRecyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                            Password password = mPasswordAdapter.mSortedList.get(position);
                            TextView tex = (TextView) view.findViewById(R.id.password);
                            if (tex.getTransformationMethod() == PasswordTransformationMethod.getInstance())
                                tex.setTransformationMethod(SingleLineTransformationMethod.getInstance());
                            else
                                tex.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        Password password = mPasswordAdapter.mSortedList.get(position);
                        Intent intent = PasswordActivity.newIntent(getActivity(), password.getId());
                        startActivity(intent);
                    }
                }));



    }

    /**
     * Tag Comparator :: Sort passwords in ascending order based on the # of tags they have
     */
    private final Comparator<Password> TagSizeComparator = new Comparator<Password>() {
        @Override
        public int compare(Password p1, Password p2) {
            return p2.getmTags().size() - p1.getmTags().size();
        }
    };

    @Override
    public void onResume(){
        super.onResume();

        // Retrieve data from singleton class
        PasswordDataSource passwordDataSource = PasswordDataSource.get(getActivity());
        List<Password> passwords = passwordDataSource.getPasswords();

        // Update RecyclerView if adapter has been updated
        if(mPasswordAdapter != null){
            if(!passwords.isEmpty())
                mPasswordAdapter.add(passwords.get(passwords.size() - 1));
            mPasswordAdapter.notifyDataSetChanged();
        }
    }


    /**
     *  PasswordAdapter for interpreting how data is shown on the screen
     */
    private class PasswordAdapter extends RecyclerView.Adapter<PasswordHolder> {

        /**
         * Callback method for SearchView
         */
        final SortedList<Password> mSortedList = new SortedList<>(Password.class, new SortedList.Callback<Password>() {
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
        void replaceAll(List<Password> passwords) {
            mSortedList.beginBatchedUpdates();
            for(int i = mSortedList.size() - 1; i >= 0; i--) {
                final Password password = mSortedList.get(i);
                if(!passwords.contains(password)) {
                    mSortedList.remove(password);
                }
            }
            mSortedList.addAll(passwords);
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


        public PasswordHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            final ListItemPasswordBinding binding = ListItemPasswordBinding.inflate(layoutInflater, parent, false );
            return new PasswordHolder(binding);
        }

        private Comparator<Password> mComparator;

        PasswordAdapter(Comparator<Password> comparator) {
            mComparator = comparator;
        }

        @Override
        public void onBindViewHolder(PasswordHolder holder, int position) {
            Password password = mSortedList.get(position);
            holder.bind(password);

            // Create a "nested" GridView f/ Tags
            TagAdapter tagAdapter = new TagAdapter(password.getmTags());
            holder.mTagsView.setAdapter(tagAdapter);
        }

        @Override
        public int getItemCount() {
            return mSortedList.size();
        }
    }

    /**
     *  Holder for the Password(s) in the RecyclerView
     */
    private class PasswordHolder extends RecyclerView.ViewHolder{

        private Password mPassword;
        private final ListItemPasswordBinding mBinding;

        private GridView mTagsView;

        PasswordHolder(ListItemPasswordBinding binding) {
            super(binding.getRoot());
            mBinding = binding;

            mTagsView = (GridView) itemView.findViewById(R.id.tags);

        }


        void bind(Password password) {
            mPassword = password;
            mBinding.setPassword(mPassword);
        }
    }

    /**
     * Nested Adapter f/ GridView implementation to hold Tags
     */
    private class TagAdapter extends BaseAdapter {

        private ArrayList<String> mTags;

        TagAdapter(ArrayList<String> tags) {
            super();

            this.mTags = tags;
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

        class ViewHolder {
            TextView mTagView;
        }

        @SuppressWarnings("deprecation")
        @SuppressLint("InflateParams")
        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {

            final ViewHolder holder;

            LayoutInflater inflater = LayoutInflater.from(getActivity());

            if (convertView == null) {

                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.gridview_item_tag, null);

                holder.mTagView = (TextView) convertView.findViewById(R.id.tag);

                holder.mTagView.setGravity(Gravity.CENTER);
                holder.mTagView.setTextSize(16);
                holder.mTagView.setTextColor(getResources().getColor(R.color.white));

                holder.mTagView.setBackground(getResources().getDrawable(R.drawable.rounded_corner));

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.mTagView.setText(mTags.get(i));

            return convertView;
        }
    }
}