package com.olehbalandyuk.studyproject.application.http;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.olehbalandyuk.studyproject.application.data.database.DatabaseHelper;

class Tokens {
    private static final String TAG = Tokens.class.getSimpleName();

    private DatabaseHelper mDBHelper;

    private String mLoginAccessToken = "null";
    private String mLoginRefreshToken = "null";
    private String mPacketAccessToken = "null";
    private String mPacketRefreshToken = "null";

    Tokens(Context context) {
        mDBHelper = new DatabaseHelper(context);
    }

    void setLoginTokens(AuthorizeResultModel response) {
        Log.v(TAG, ">> Method: setLoginTokens()");

        mLoginAccessToken = response.getLoginAccessToken();
        mLoginRefreshToken = response.getLoginRefreshToken();

        updateTokens();
        Log.v(TAG, "<< Method: setLoginTokens()");
    }

    private void updateTokens() {
        Log.v(TAG, ">> Method: updateTokens()");
        SQLiteDatabase database = mDBHelper.getWritableDatabase();

        database.execSQL("UPDATE " + DatabaseHelper.TOKENS_TABLE + " SET " +
                DatabaseHelper.LOGIN_ACCESS_TOKEN + " = \"" + mLoginAccessToken + "\", " +
                DatabaseHelper.LOGIN_REFRESH_TOKEN + " = \"" + mLoginRefreshToken + "\", " +
                DatabaseHelper.PACKET_ACCESS_TOKEN + " = \"" + mPacketAccessToken + "\", " +
                DatabaseHelper.PACKET_REFRESH_TOKEN + " = \"" + mPacketRefreshToken + "\" "
        );

        tempMethod_createLog(database);

        database.close();
        Log.v(TAG, "--> Method: updateTokens(), database is closed " + !database.isOpen());
        Log.v(TAG, "<< Method: updateTokens()");
    }

    String getLoginAccessToken() {
        SQLiteDatabase database = mDBHelper.getWritableDatabase();
        Cursor cursor = database.query(DatabaseHelper.TOKENS_TABLE, null, null, null, null, null, null);

        String loginAccessToken = cursor.getString(cursor.getColumnIndex(DatabaseHelper.LOGIN_ACCESS_TOKEN));

        cursor.close();

        database.close();
        Log.v(TAG, "--> Method: getLoginAccessToken(), database is closed " + !database.isOpen());

        return loginAccessToken;
    }

    String getLoginRefreshToken() {
        SQLiteDatabase database = mDBHelper.getWritableDatabase();
        Cursor cursor = database.query(DatabaseHelper.TOKENS_TABLE, null, null, null, null, null, null);

        String loginRefreshToken = cursor.getString(cursor.getColumnIndex(DatabaseHelper.LOGIN_REFRESH_TOKEN));

        cursor.close();

        database.close();
        Log.v(TAG, "--> Method: getLoginRefreshToken(), database is closed " + !database.isOpen());

        return loginRefreshToken;
    }

    private void tempMethod_createLog(SQLiteDatabase database) {
        Log.v(TAG, ">> Method: tempMethod_createLog()");

        String forLog = "";
        Cursor cursor = database.query(DatabaseHelper.TOKENS_TABLE, null, null, null, null, null, null);
        if (cursor.moveToLast()) {
            String loginAccessToken = cursor.getString(
                    cursor.getColumnIndex(DatabaseHelper.LOGIN_ACCESS_TOKEN));
            String loginRefreshToken = cursor.getString(
                    cursor.getColumnIndex(DatabaseHelper.LOGIN_REFRESH_TOKEN));
            forLog = "Login token saved as : " + loginAccessToken +
                    ", refresh token saved as : " + loginRefreshToken;
        }
        Log.w(TAG, "--> Method: tempMethod_createLog, " + forLog);
        cursor.close();

        Log.v(TAG, "<< Method: tempMethod_createLog()");
    }

    void logout() {
        SQLiteDatabase database = mDBHelper.getWritableDatabase();
        Log.v(TAG, ">> Method: logout()");
        database.execSQL("INSERT INTO " + DatabaseHelper.TOKENS_TABLE + " VALUES (" +
                "\"null\", " + // LOGIN_ACCESS_TOKEN
                "\"null\", " + // LOGIN_REFRESH_TOKEN
                "\"null\", " + // PACKET_ACCESS_TOKEN
                "\"null\" )"   // PACKET_REFRESH_TOKEN
        );

        database.close();
        Log.v(TAG, "--> Method: logout(), database is closed " + !database.isOpen());
        Log.v(TAG, "<< Method: logout()");
    }
}
