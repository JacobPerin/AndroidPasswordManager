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
        UUID uuid = UUID.fromString(this.getString(this.getColumnIndex(PasswordTable.Columns.UUID)));
        String passName = this.getString(this.getColumnIndex(PasswordTable.Columns.PASSWORD));

        Password pass = new Password(uuid);
        pass.setmPassword(passName);
        return pass;
    }

    public PasswordTag getTag (){
        String tagName = this.getString(this.getColumnIndex(TagTable.Columns.TAG));
        UUID tagID = UUID.fromString(this.getString(this.getColumnIndex(TagTable.Columns.TAGID)));

        PasswordTag passTag = new PasswordTag();
        passTag.setName(tagName);
        passTag.setmTagID(tagID);

        return passTag;
    }
}
