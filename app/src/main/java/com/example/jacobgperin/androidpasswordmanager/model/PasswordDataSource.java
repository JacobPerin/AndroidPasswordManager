package com.example.jacobgperin.androidpasswordmanager.model;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.jacobgperin.androidpasswordmanager.database.PasswordBaseHelper;
import com.example.jacobgperin.androidpasswordmanager.database.PasswordCursorWrapper;
import com.example.jacobgperin.androidpasswordmanager.database.PasswordDBSchema;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.example.jacobgperin.androidpasswordmanager.database.PasswordDBSchema.*;

/**
 * Created by Jacob G. Perin on 11/28/2017.
 */

public class PasswordDataSource {
    private static PasswordDataSource mPasswordDataSource;

    private ArrayList<Password> mPasswords;
    private SQLiteDatabase mDatabase;

    public static PasswordDataSource get(Context context){
        if(mPasswordDataSource == null){
            mPasswordDataSource = new PasswordDataSource(context);
        }

        return mPasswordDataSource;
    }

    private PasswordDataSource(Context context){
        Context mContext = context.getApplicationContext();
        mDatabase = new PasswordBaseHelper(mContext).getWritableDatabase();
        mPasswords = new ArrayList<>();
        mPasswords = makeQueries(null, null, mPasswords);
    }

    /**
     * Add a new password to the DB and passwords array list
     */
    public void addPasswordList(Password password) {
        // Add password to the password list
        mPasswords.add(password);
    }


    public void addPasswordDatabase(Password password){

        // Add password to the database
        ContentValues values = getPasswordContentValues(password);
        mDatabase.insert(PasswordTable.NAME, null, values);

        values.clear();
        for(int i = 0; i < password.getmTags().size(); i++){
            values = getTagContentValues(password.getmTagsObject().get(i), password.getId().toString());
            mDatabase.insert(TagTable.NAME, null, values);
        }
    }


    public void updatePasswordList(Password password){
        int i = getPasswordIndex(password.getId());
        if(i == -1)
            return;
        mPasswords.set(i, password);
    }
    /**
     * Make an update to a password in the DB
     */
    public void updatePasswordDB(Password password){

        String id = password.getId().toString();
        ContentValues values = getPasswordContentValues(password);

        mDatabase.update(PasswordTable.NAME, values,
                PasswordTable.Columns.UUID + " = ?",
                new String[] {id + ""});

        values.clear();

        mDatabase.delete(TagTable.NAME, TagTable.Columns.TAGID + "=" + password.getId(), null);

        for(int i = 0; i < password.getmTags().size(); i++){
            values = getTagContentValues(password.getmTagsObject().get(i), password.getId().toString());
            mDatabase.insert(TagTable.NAME, null, values);
        }
    }

    /**
     * This function retrieves a single password.
     */
    public Password getPassword(UUID id) {
        for(Password password : mPasswords) {
            if(password.getId().equals(id)){
                return password;
            }
        }
        return null;
    }

    private int getPasswordIndex(UUID id){
        for(int i = 0; i < mPasswords.size(); i++){
            if(mPasswords.get(i).getId() == id)
                return i;
        }
        return -1;
    }
    /**
     * Get the passwords
     */
    public List<Password> getPasswords(){
        return mPasswords;
    }


    /**
     * This functions executes queries using the cursor Wrapper
     * and returns a list of passwords from the DB
     * based on the where clause.
     */
    private ArrayList<Password> makeQueries(String whereClause, String[] whereArgs, ArrayList<Password> passwords){
        //Create password table cursor
        PasswordCursorWrapper passCursor = queryPasswords(whereClause, whereArgs);

        try{
            passCursor.moveToFirst();
            //Parse through cursor and create password objects
            while(!passCursor.isAfterLast()){
                Password pass = passCursor.getPassword();

                PasswordCursorWrapper tagCursor = queryTags(pass.getId().toString());
                ArrayList<PasswordTag> tagList = new ArrayList<>();
                tagCursor.moveToFirst();
                //Parse through TagTable and get tags connected to current Password
                while(!tagCursor.isAfterLast()){
                    tagList.add(tagCursor.getTag());
                    tagCursor.moveToNext();
                }
                tagCursor.close();

                pass.setmTags(tagList);
                passwords.add(pass);
                passCursor.moveToNext();
            }
        }
        finally {
            passCursor.close();
        }

        return passwords;
    }

    /**
     *  Return passwords from TagTable.
     * Number of Tags returned based on where clause.
     * Return Cursor wrapper.
     */
    private PasswordCursorWrapper queryTags(String id){
        String selection = TagTable.Columns.TAGID + " = ?";
        String[] selectionArgs = { id };
        @SuppressLint("Recycle") Cursor cursor = mDatabase.query(
                TagTable.NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        return new PasswordCursorWrapper(cursor);
    }

    /**
     * Return passwords from PasswordTable.
     * Number of passwords returned based on where clause.
     * Return Cursor wrapper.
     */
    private PasswordCursorWrapper queryPasswords(String whereClause, String[] whereArgs){
        @SuppressLint("Recycle") Cursor cursor = mDatabase.query(
                PasswordTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );

        return new PasswordCursorWrapper(cursor);
    }

    /**
     * This function creates Content Values object for PasswordDB
     */
    private static ContentValues getPasswordContentValues(Password password){
        ContentValues values = new ContentValues();
        values.put(PasswordTable.Columns.UUID, password.getId().toString());
        values.put(PasswordTable.Columns.PASSWORD, password.getmPassword());

        return values;
    }

    /**
     * This function creates Content Values object for TagDB
     */
    private static ContentValues getTagContentValues(PasswordTag tag, String id){
        ContentValues values = new ContentValues();

        values.put(TagTable.Columns.TAG, tag.getName());
        values.put(TagTable.Columns.TAGID, id);

        return values;
    }
}