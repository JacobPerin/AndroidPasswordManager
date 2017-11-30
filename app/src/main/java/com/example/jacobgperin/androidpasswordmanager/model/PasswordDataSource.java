package com.example.jacobgperin.androidpasswordmanager.model;

import android.content.Context;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Jacob G. Perin on 11/28/2017.
 */

public class PasswordDataSource {
    private static PasswordDataSource mPasswordDataSource;

    private List<Password> mPasswords;

    public static PasswordDataSource get(Context context){
        if(mPasswordDataSource == null){
            mPasswordDataSource = new PasswordDataSource(context);
        }

        return mPasswordDataSource;
    }

    private PasswordDataSource(Context context){
        mPasswords = new ArrayList<>();

        // FIXME :: filled w/ dummy data until database is set up IN this file (Chapter 14)

        for(int i = 0; i < 20; i++){
            Password password = new Password();

            // Generate some tags
            ArrayList<String> tags = new ArrayList<>();

            for(int j = 0; j < 5; j++){
                tags.add(randomString(5));
            }

            // Set w/ dummy data
            password.setmTags(tags);
            password.setmPassword(randomString(10));
        }

    }

    /**
     *  DUMMY DATA GENERATOR ***START :: REMOVE THIS ONCE DATABASE IS DONE***
     */

    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static SecureRandom rnd = new SecureRandom();

    String randomString( int len ){
        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ )
            sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
        return sb.toString();
    }

    /**
     * ***END :: REMOVE THIS ONCE DATABASE IS DONE***
     */

    public List<Password> getPasswords(){
        return mPasswords;
    }

    public Password getPassword(UUID id) {
        for(Password password : mPasswords) {
            if(password.getId().equals(id)){
                return password;
            }
        }
        return null;
    }

}
