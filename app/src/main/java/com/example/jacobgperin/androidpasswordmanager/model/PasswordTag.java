package com.example.jacobgperin.androidpasswordmanager.model;

import java.util.UUID;

/**
 * Created by colli on 11/29/2017.
 */

public class PasswordTag {
    private UUID mID;
    private String mName;
    private UUID mTagID;

    public PasswordTag() {
        this(UUID.randomUUID());
    }

    public PasswordTag(UUID id){
        mID = id;
    }

    public void setmTagID(UUID id){
        mTagID = id;
    }

    public UUID getmTagID(){ return mTagID; }

    public UUID getId() {
        return mID;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name){
        mName = name;
    }
}