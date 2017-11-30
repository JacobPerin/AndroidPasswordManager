package com.example.jacobgperin.androidpasswordmanager.model;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Jacob G. Perin on 11/28/2017.
 */

public class Password {

    private UUID mID;
    private String mPassword;
    private ArrayList<PasswordTag> mTags;

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
        ArrayList<String> stringTag = new ArrayList<>();
        for(int i = 0; i < mTags.size(); i++){
            stringTag.add(mTags.get(i).getName());
        }
        return stringTag;
    }

    public ArrayList<PasswordTag> getmTagsObject() {
        return mTags;
    }

    public void setmTags(ArrayList<PasswordTag> tags) {
        mTags = tags;
    }
}

