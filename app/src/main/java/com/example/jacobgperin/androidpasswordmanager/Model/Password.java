package com.example.jacobgperin.androidpasswordmanager.Model;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Jacob G. Perin on 11/28/2017.
 */

public class Password {

    private UUID mID;
    private String mPassword;
    private ArrayList<String> mTags;

    /**
     * Testing purposes for list view ... RecyclerView w/ Existing Values
     */
    public Password() {
        // Universally unique ID f/ each password
        mID = UUID.randomUUID();
    }

    public UUID getId() {
        return mID;
    }

    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(String password) {
        mPassword = password;
    }

    public ArrayList<String> getmTags() {
        return mTags;
    }

    public void setmTags(ArrayList<String> tags) {
        mTags = tags;
    }
}
