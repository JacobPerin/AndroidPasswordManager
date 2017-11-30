package com.example.jacobgperin.androidpasswordmanager.database;

import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by colli on 11/29/2017.
 */

public class PasswordDBSchema {
    public static final class PasswordTable {
        public static final String NAME = "passwords";

        public static final class Columns {
            public static final String UUID = "uuid";
            public static final String PASSWORD = "password";
        }
    }

    public static final class TagTable {
        public static final String NAME = "tags";

        public static final class Columns {
            public static final String UUID = "uuid";
            public static final String TAG = "tag";
            public static final String TAGID = "tagID";
        }
    }
}
