package com.example.jacobgperin.androidpasswordmanager.model;

import java.util.UUID;

/**
 * Created by colli on 11/29/2017.
 */

public class PasswordTag {
    private UUID mID;
    /**
     *
     */
    private String mName;

    public PasswordTag() {
        mID = UUID.randomUUID();
    }

    public UUID getId() {
        return mID;
    }

    public String getName() {
        return mName;
    }
    public void setPassword(String name){
        mName = name;
    }
}