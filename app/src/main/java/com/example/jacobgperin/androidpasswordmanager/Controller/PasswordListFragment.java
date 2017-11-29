package com.example.jacobgperin.androidpasswordmanager.Controller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.*;

import com.example.jacobgperin.androidpasswordmanager.R;

/**
 * Created by Jacob G. Perin on 11/28/2017.
 */

public class PasswordListFragment extends Fragment{
    private RecyclerView mPasswordRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_password_list, container, false);

        mPasswordRecyclerView = (RecyclerView) view.findViewById(R.id.password_recycler_view);
        mPasswordRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }
}
