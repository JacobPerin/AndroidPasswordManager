package com.example.jacobgperin.androidpasswordmanager.model;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by colli on 11/29/2017.
 */

public class PasswordBaseHelper extends SQLiteOpenHelper{
    static final String dbName = "PasswordManager";
    static final String passwordTable = "passwords";
    static final String colID = "passID";
    static final String colPass = "password";

    static final String tagsTable = "tags";
    static final String colTagID = "tagID";
    static final String colTagName = "tagName";

    public PasswordBaseHelper()

    @Override
    public void onCreate(SQLiteDatabase db){

    }
}
