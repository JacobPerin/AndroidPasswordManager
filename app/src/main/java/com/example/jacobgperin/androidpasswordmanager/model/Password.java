package com.example.jacobgperin.androidpasswordmanager.model;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Jacob G. Perin on 11/28/2017.
 */

public class Password {

    private UUID mID;
    public String mPassword;
    private ArrayList<PasswordTag> mTags;

    /**
     * Testing purposes for list view ... RecyclerView w/ Existing Values
     */
    public Password() {
        // Universally unique ID f/ each password
        this(UUID.randomUUID());
    }

    public Password(UUID id){
        mID = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Password password = (Password) o;

        return mID == password.mID &&
                (mPassword != null ?
                        mPassword.equals(password.getmPassword()) :
                        password.getmPassword() == null);
    }

    ArrayList<PasswordTag> getmTagsObject() {
        return mTags;
    }

    public void setmTags(ArrayList<PasswordTag> tags) {
        mTags = tags;
    }
}

