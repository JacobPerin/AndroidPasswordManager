package com.example.jacobgperin.androidpasswordmanager.database;

import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by colli on 11/29/2017.
 */

public class PasswordDBSchema {

    //Table holding password data
    public static final class PasswordTable {
        public static final String NAME = "passwords";

        public static final class Columns {
            //Unique ID for each password object
            public static final String UUID = "uuid";
            //The literal password - may try to hash later on.
            public static final String PASSWORD = "password";
        }
    }

    //Table for the multiple tags the user will add later on
    public static final class TagTable {
        public static final String NAME = "tags";

        public static final class Columns {
            //Tag name
            public static final String TAG = "tag";
            //ID that will link with password table
            public static final String TAGID = "tagID";
        }
    }
}
