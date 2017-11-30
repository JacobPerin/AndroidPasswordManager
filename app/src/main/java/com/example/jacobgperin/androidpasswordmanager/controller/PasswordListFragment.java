package com.example.jacobgperin.androidpasswordmanager.controller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.*;

import com.example.jacobgperin.androidpasswordmanager.model.Password;
import com.example.jacobgperin.androidpasswordmanager.model.PasswordDataSource;
import com.example.jacobgperin.androidpasswordmanager.R;

import java.util.List;

/**
 * Created by Jacob G. Perin on 11/28/2017.
 */

public class PasswordListFragment extends Fragment{

    private RecyclerView mPasswordRecyclerView;

    private PasswordAdapter mPasswordAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_password_list, container, false);

        mPasswordRecyclerView = (RecyclerView) view.findViewById(R.id.password_recycler_view);
        mPasswordRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    private void updateUI(){

        PasswordDataSource passwordDataSource = PasswordDataSource.get(getActivity());
        List<Password> passwords = passwordDataSource.getPasswords();

        mPasswordAdapter = new PasswordAdapter(passwords);
        mPasswordRecyclerView.setAdapter(mPasswordAdapter);
    }

    private class PasswordHolder extends RecyclerView.ViewHolder {

        public PasswordHolder(LayoutInflater inflater, ViewGroup parent) {
            //TODO :: Make it so that this creates a nested adapter for GridView
            super(inflater.inflate(R.layout.list_item_password, parent, false));
        }
    }

    private class PasswordAdapter extends RecyclerView.Adapter<PasswordHolder> {

        private List<Password> mPasswords;

        public PasswordHolder onCreateViewHolder(ViewGroup parent, int viewType){
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new PasswordHolder(layoutInflater, parent);
        }

        public PasswordAdapter (List<Password> passwords) {
            mPasswords = passwords;
        }

        @Override
        public void onBindViewHolder(PasswordHolder holder, int position){
            //TODO
        }

        @Override
        public int getItemCount(){
            return mPasswords.size();
        }
    }
}
