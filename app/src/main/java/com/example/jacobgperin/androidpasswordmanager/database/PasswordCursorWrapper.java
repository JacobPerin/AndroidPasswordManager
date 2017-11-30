package com.example.jacobgperin.androidpasswordmanager.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.jacobgperin.androidpasswordmanager.model.Password;
import com.example.jacobgperin.androidpasswordmanager.model.PasswordTag;

import java.util.UUID;

import static com.example.jacobgperin.androidpasswordmanager.database.PasswordDBSchema.*;

/**
 * Created by colli on 11/29/2017.
 */

public class PasswordCursorWrapper extends CursorWrapper {
    public PasswordCursorWrapper(Cursor cursor){
        super(cursor);
    }

    public Password getPassword(){
        UUID uuid = UUID.fromString(getString(getColumnIndex(PasswordTable.Columns.UUID)));
        String passName = getString(getColumnIndex(PasswordTable.Columns.PASSWORD));

        Password pass = new Password(uuid);
        pass.setmPassword(passName);
        return pass;
    }

    public PasswordTag getTag (){
        String tagName = getString(getColumnIndex(TagTable.Columns.TAG));
        UUID tagID = UUID.fromString(getString(getColumnIndex(TagTable.Columns.TAGID)));
        UUID uuid = UUID.fromString(getString(getColumnIndex(TagTable.Columns.UUID)));

        PasswordTag passTag = new PasswordTag(uuid);
        passTag.setName(tagName);
        passTag.setmTagID(tagID);

        return passTag;
    }
}
