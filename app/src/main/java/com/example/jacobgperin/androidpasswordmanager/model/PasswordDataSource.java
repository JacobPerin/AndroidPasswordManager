package com.example.jacobgperin.androidpasswordmanager.model;

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

    private List<Password> mPasswords;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static PasswordDataSource get(Context context){
        if(mPasswordDataSource == null){
            mPasswordDataSource = new PasswordDataSource(context);
        }

        return mPasswordDataSource;
    }

    private PasswordDataSource(Context context){
        mContext = context.getApplicationContext();
        mDatabase = new PasswordBaseHelper(mContext).getWritableDatabase();
        mPasswords = new ArrayList<>();


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
    public void addPasswordList(Password password) {
        // Add password to the password list
        mPasswords.add(password);
    }

    public void addPassword(Password password){

        // Add password to the database
        ContentValues values = getPasswordContentValues(password);
        mDatabase.insert(PasswordTable.NAME, null, values);

        values.clear();
        for(int i = 0; i < password.getmTags().size(); i++){
            values = getTagContentValues(password.getmTagsObject().get(i), password.getId().toString());
            mDatabase.insert(TagTable.NAME, null, values);
        }
    }

    public void updatePassword(Password password){
        String id = password.getId().toString();
        ContentValues values = getPasswordContentValues(password);

        mDatabase.update(PasswordTable.NAME, values,
                PasswordTable.Columns.UUID + " = ?",
                new String[] {id + ""});

        values.clear();
    }

    public Password getPassword(UUID id) {
        String selection =PasswordTable.Columns.UUID + " = ?";
        String[] selectionArgs = { id.toString() };

        ArrayList<Password> passwords = new ArrayList<>();
        passwords = makeQueries(selection, selectionArgs, passwords);

        return passwords.get(0);
    }

    //Gets the passwords into arrayList of Passwords
    public List<Password> getPasswords(){
        ArrayList<Password> passwords = new ArrayList<>();
        passwords = makeQueries(null, null, passwords);

        return passwords;
    }


    public ArrayList<Password> makeQueries(String whereClause, String[] whereArgs, ArrayList<Password> passwords){
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
    private PasswordCursorWrapper queryTags(String id){
        String selection = TagTable.Columns.TAGID + " = ?";
        String[] selectionArgs = { id };
        Cursor cursor = mDatabase.query(
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

    private PasswordCursorWrapper queryPasswords(String whereClause, String[] whereArgs){
        Cursor cursor = mDatabase.query(
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

    private static ContentValues getPasswordContentValues(Password password){
        ContentValues values = new ContentValues();
        values.put(PasswordTable.Columns.UUID, password.getId().toString());
        values.put(PasswordTable.Columns.PASSWORD, password.getmPassword());

        return values;
    }

    private static ContentValues getTagContentValues(PasswordTag tag, String id){
        ContentValues values = new ContentValues();

        values.put(TagTable.Columns.TAG, tag.getName());
        values.put(TagTable.Columns.TAGID, id);

        return values;
    }
}