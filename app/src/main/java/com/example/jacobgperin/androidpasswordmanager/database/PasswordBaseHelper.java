package com.example.jacobgperin.androidpasswordmanager.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.jacobgperin.androidpasswordmanager.model.Password;

import static com.example.jacobgperin.androidpasswordmanager.database.PasswordDBSchema.*;

/**
 * Created by colli on 11/29/2017.
 */

public class PasswordBaseHelper extends SQLiteOpenHelper{
    private static final String DBNAME = "PasswordManager";
    private static final int VERSION = 1;

    public PasswordBaseHelper(Context context){
        super(context, DBNAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        //Password table
        db.execSQL("create table " + PasswordTable.NAME + "(" +
                PasswordTable.Columns.UUID + " integer primary key, " +
                PasswordTable.Columns.PASSWORD +
                ")"
        );

        //Create the tag table based on password
        db.execSQL("CREATE TABLE " + TagTable.NAME + "(" +
                TagTable.Columns.UUID + " INTEGER PRIMARY KEY, " +
                TagTable.Columns.TAG + ", " +
                TagTable.Columns.TAGID + " INTEGER NOT NULL, " +
                "FOREIGN KEY (" + TagTable.Columns.TAGID +
                ") REFERENCES " + PasswordTable.NAME +
                " (" + PasswordTable.Columns.UUID + ")" +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
}
