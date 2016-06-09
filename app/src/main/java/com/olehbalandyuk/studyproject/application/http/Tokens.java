package com.olehbalandyuk.studyproject.application.http;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.olehbalandyuk.studyproject.application.data.database.DatabaseHelper;

class Tokens {
    private static final String TAG = Tokens.class.getSimpleName();

    private static DatabaseHelper sDBHelper;
    private static Tokens sInstance = new Tokens();

    private String mLoginAccessToken = "null";
    private String mLoginRefreshToken = "null";
    private String mPacketAccessToken = "null";
    private String mPacketRefreshToken = "null";

    private Tokens() {
    }

    public static Tokens getInstance(Context context) {
        if (sDBHelper == null) {
            sDBHelper = new DatabaseHelper(context);
        }
        return sInstance;
    }

    public void setLoginTokens(AuthorizeResultModel response) {
        Log.v(TAG, ">> Method: setLoginTokens()");

        mLoginAccessToken = response.getLoginAccessToken();
        mLoginRefreshToken = response.getLoginRefreshToken();

        updateTokens();
        Log.v(TAG, "<< Method: setLoginTokens()");
    }

    private void updateTokens() {
        Log.v(TAG, ">> Method: updateTokens()");
        SQLiteDatabase database = sDBHelper.getWritableDatabase();

        database.execSQL("UPDATE " + DatabaseHelper.TOKENS_TABLE + " SET " +
                DatabaseHelper.LOGIN_ACCESS_TOKEN + " = \"" + mLoginAccessToken + "\", " +
                DatabaseHelper.LOGIN_REFRESH_TOKEN + " = \"" + mLoginRefreshToken + "\", " +
                DatabaseHelper.PACKET_ACCESS_TOKEN + " = \"" + mPacketAccessToken + "\", " +
                DatabaseHelper.PACKET_REFRESH_TOKEN + " = \"" + mPacketRefreshToken + "\" "
        );

        tempMethod_createLog();

        database.close();
        Log.v(TAG, "<< Method: updateTokens()");
    }

    public String getLoginAccessToken(){
        SQLiteDatabase database = sDBHelper.getWritableDatabase();
        Cursor cursor = database.query(DatabaseHelper.TOKENS_TABLE, null, null, null, null, null, null);

        String loginAccessToken = cursor.getString(cursor.getColumnIndex(DatabaseHelper.LOGIN_ACCESS_TOKEN));

        cursor.close();
        database.close();

        return loginAccessToken;
    }

    public String getLoginRefreshToken() {
        SQLiteDatabase database = sDBHelper.getWritableDatabase();
        Cursor cursor = database.query(DatabaseHelper.TOKENS_TABLE, null, null, null, null, null, null);

        String loginRefreshToken = cursor.getString(cursor.getColumnIndex(DatabaseHelper.LOGIN_REFRESH_TOKEN));

        cursor.close();
        database.close();

        return loginRefreshToken;
    }

    private void tempMethod_createLog(){
        Log.v(TAG, ">> Method: tempMethod_createLog()");
        SQLiteDatabase database = sDBHelper.getWritableDatabase();

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

        database.close();
        Log.v(TAG, "<< Method: tempMethod_createLog()");
    }
}
