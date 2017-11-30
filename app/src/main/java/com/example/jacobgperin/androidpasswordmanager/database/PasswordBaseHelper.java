package com.example.jacobgperin.androidpasswordmanager.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.jacobgperin.androidpasswordmanager.model.Password;

import static com.example.jacobgperin.androidpasswordmanager.database.PasswordDBSchema.*;

/**
 * Created by colli on 11/29/2017.
 */

public class PasswordBaseHelper extends SQLiteOpenHelper{
    private static final String DBNAME = "PasswordManager.db";
    private static final int VERSION = 5;

    public PasswordBaseHelper(Context context){
        super(context, DBNAME, null, VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("DROP TABLE IF EXISTS " + PasswordTable.NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TagTable.NAME);
        //Password table
        db.execSQL("CREATE TABLE " + PasswordTable.NAME + "(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                PasswordTable.Columns.UUID + ", " +
                PasswordTable.Columns.PASSWORD +
                ")"
        );

        //Create the tag table based on password
        db.execSQL("CREATE TABLE " + TagTable.NAME + "(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                TagTable.Columns.TAG + "," +
                TagTable.Columns.TAGID + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        Log.w(PasswordBaseHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");

        onCreate(db);
    }
}
