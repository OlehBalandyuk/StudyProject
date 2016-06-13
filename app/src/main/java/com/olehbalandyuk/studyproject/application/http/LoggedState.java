package com.olehbalandyuk.studyproject.application.http;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.olehbalandyuk.studyproject.application.data.database.DatabaseHelper;

class LoggedState {
    private static final String TAG = LoggedState.class.getSimpleName();

    private DatabaseHelper mDBHelper;

    LoggedState(Context context) {
        mDBHelper = new DatabaseHelper(context);
    }

    void setLoggedByLogin() {
        Log.v(TAG, ">> Method: setLoggedByLogin()");

        SQLiteDatabase database = mDBHelper.getWritableDatabase();
        Log.v(TAG, "--> Method: setLoggedByLogin(), database is opened - " + database.isOpen());

        database.execSQL("UPDATE " + DatabaseHelper.LOGGED_STATE_TABLE + " SET " +
                DatabaseHelper.LOGGED_IN_APP + " = \"true\", " +
                DatabaseHelper.LOGGED_IN_PACKED + " = \"" + getLogged(database, DatabaseHelper.LOGGED_IN_PACKED) + "\" "
        );

        database.close();
        Log.v(TAG, "--> Method: setLoggedByLogin(), database is closed: " + !database.isOpen());

        Log.v(TAG, "<< Method: setLoggedByLogin()");
    }

    String getLogged(String column) {
        Log.v(TAG, ">> Method: getLogged()");
        SQLiteDatabase database = mDBHelper.getWritableDatabase();

        String logged = getLogged(database, column);

        database.close();
        Log.v(TAG, "--> Method: getLogged(), database is closed: " + !database.isOpen());

        Log.v(TAG, "<< Method: getLogged()");
        return logged;
    }

    String getLogged(SQLiteDatabase database, String column) {
        Log.v(TAG, ">> Method: getLogged(database)");
        Cursor cursor = database.query(DatabaseHelper.LOGGED_STATE_TABLE, null, null, null, null, null, null);

        String logged = "false";
        if (cursor.moveToLast()) {
            logged = cursor.getString(cursor.getColumnIndex(column));
        }

        cursor.close();

        Log.v(TAG, "<< Method: getLogged(database)");
        return logged;
    }

    void logout() {
        Log.v(TAG, ">> Method: logout()");
        SQLiteDatabase database = mDBHelper.getWritableDatabase();
        database.execSQL("INSERT INTO " + DatabaseHelper.LOGGED_STATE_TABLE + " VALUES (" +
                "\"false\", " + // LOGGED_IN_APP
                "\"false\" )"   // LOGGED_IN_PACKET
        );
        database.close();
        Log.v(TAG, "--> Method: logout(), database is closed: " + !database.isOpen());
        Log.v(TAG, "<< Method: logout()");
    }

    boolean isLoggedByLogin() {
        Log.v(TAG, ">> Method: isLoggedByLogin()");
        boolean isLogged = Boolean.parseBoolean(getLogged(DatabaseHelper.LOGGED_IN_APP));
        Log.v(TAG, "<< Method: isLoggedByLogin()");
        return isLogged;
    }
}