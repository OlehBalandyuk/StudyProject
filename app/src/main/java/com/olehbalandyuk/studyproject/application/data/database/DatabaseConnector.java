package com.olehbalandyuk.studyproject.application.data.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.olehbalandyuk.studyproject.application.http.UserInfoModel;

import java.util.ArrayList;

import static com.olehbalandyuk.studyproject.application.data.database.DatabaseHelper.*;

public class DatabaseConnector {
    private static final String TAG = DatabaseConnector.class.getSimpleName();

    public static final boolean TOKENS_ARE_LOGIN = true;

    public static String[] loadUserInfo(Context context) {
        String[] result;

        SQLiteDatabase database = getReadableDatabase(context);

        result = loadUserInfo(database);
        database.close();

        return result;
    }

    public static void saveUserEmail(String email, Context context) {
        Log.v(TAG, ">> Method: saveUserEmail(String, Context)");

        SQLiteDatabase database = getWritableDatabase(context);

        database.execSQL("INSERT INTO  " + USER_INFO_TABLE + " VALUES (" +
                "\"" + getColumnValue(database, USER_INFO_TABLE, USER_ID) + "\", " +
                "\"" + email + "\", " +
                "\"" + getColumnValue(database, USER_INFO_TABLE, USER_NAME) + "\", " +
                "\"" + getColumnValue(database, USER_INFO_TABLE, USER_BALANCE) + "\", " +
                "\"" + getColumnValue(database, USER_INFO_TABLE, USER_BONUS) + "\" " +
                "); "
        );

        Log.v(TAG, ">> Method: saveUserEmail(String, Context)");
        database.close();
    }

    // TODO check method on a real response
    public static void saveUserInfo(UserInfoModel response, Context context) {
        Log.v(TAG, ">> saveUserInfo(UserInfoModel, Context)");
        String userName = response.getName();
        String userId = response.getId();
        String userBalance = response.getBalance();
        String userBonus = response.getBonus();

        SQLiteDatabase database = getWritableDatabase(context);

        database.execSQL("UPDATE " + USER_INFO_TABLE + " SET " +
                USER_ID + " = \"" + userId + "\", " +
                USER_EMAIL + " = \"" + getColumnValue(database, USER_INFO_TABLE, USER_EMAIL) + "\", " +
                USER_NAME + " = \"" + userName + "\", " +
                USER_BALANCE + " = \"" + userBalance + "\", " +
                USER_BONUS + " = \"" + userBonus + "\" "
        );

        database.close();
        Log.v(TAG, ">> saveUserInfo(UserInfoModel, Context)");
    }

    public static ArrayList<String[]> loadPacketsFromDB(Context context) {
        Log.v(TAG, ">> loadPacketsFromDB(Context)");

        SQLiteDatabase database = getReadableDatabase(context);
        ArrayList<String[]> result = loadPacketsFromDB(database);
        database.close();

        Log.v(TAG, "<< loadPacketsFromDB(Context)");
        return result;
    }

    public static void saveTokens(Context context, boolean areLoginTokens, String loginToken, String refreshToken) {

        SQLiteDatabase database = getWritableDatabase(context);

        String values;
        if (areLoginTokens) {
            values = "\"" + loginToken + "\", " +
                    "\"" + refreshToken + "\", " +
                    "\"" + getToken(database, PACKET_ACCESS_TOKEN) + "\", " +
                    "\"" + getToken(database, PACKET_REFRESH_TOKEN) + "\" ";
        } else {
            values = "\"" + getToken(database, LOGIN_ACCESS_TOKEN) + "\", " +
                    "\"" + getToken(database, LOGIN_REFRESH_TOKEN) + "\", " +
                    "\"" + loginToken + "\", " +
                    "\"" + refreshToken + "\" ";
        }

        database.execSQL("INSERT INTO " + TOKENS_TABLE + " VALUES (" + values + ");");
        database.close();
    }

    public static String getLoginAccessToken(Context context) {

        SQLiteDatabase database = getReadableDatabase(context);
        String result = getToken(database, LOGIN_ACCESS_TOKEN);
        database.close();

        return result;
    }

    public static String getLoginRefreshToken(Context context) {

        SQLiteDatabase database = getReadableDatabase(context);
        String result = getToken(database, LOGIN_REFRESH_TOKEN);
        database.close();

        return result;
    }

    public static String getPacketAccessToken(Context context) {

        SQLiteDatabase database = getReadableDatabase(context);
        String result = getToken(database, PACKET_ACCESS_TOKEN);
        database.close();

        return result;
    }

    public static String getPacketRefreshToken(Context context) {

        SQLiteDatabase database = getReadableDatabase(context);
        String result = getToken(database, PACKET_REFRESH_TOKEN);
        database.close();

        return result;
    }

    public static void setLoggedByLogin(Context context) {
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase database = helper.getWritableDatabase();
        database.execSQL("UPDATE " + DatabaseHelper.LOGGED_STATE_TABLE + " SET " +
                DatabaseHelper.LOGGED_IN_APP + " = \"true\", " +
                DatabaseHelper.LOGGED_IN_PACKED + " = \"" + getLogged(database, DatabaseHelper.LOGGED_IN_PACKED) + "\" "
        );
        database.close();
    }

    public static boolean isLoggedByLogin(Context context) {
        Log.v(TAG, ">> Method: isLoggedByLogin()");

        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase database = helper.getReadableDatabase();
        boolean isLogged = Boolean.parseBoolean(getLogged(database, DatabaseHelper.LOGGED_IN_APP));
        database.close();

        Log.v(TAG, "<< Method: isLoggedByLogin()");
        return isLogged;
    }

    public static void logout(Context context) {
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase database = helper.getWritableDatabase();
        DatabaseHelper.logout(database);
        database.close();
    }

    private static String[] loadUserInfo(SQLiteDatabase database) {
        Log.v(TAG, ">> Method: loadUserInfo(SQLiteDatabase)");

        String userId = getColumnValue(database, USER_INFO_TABLE, USER_ID);
        String userEmail = getColumnValue(database, USER_INFO_TABLE, USER_EMAIL);
        String userName = getColumnValue(database, USER_INFO_TABLE, USER_NAME);
        String userBalance = getColumnValue(database, USER_INFO_TABLE, USER_BALANCE);
        String userBonus = getColumnValue(database, USER_INFO_TABLE, USER_BONUS);

        Log.v(TAG, "<< Method: loadUserInfo(SQLiteDatabase)");
        return new String[]{userId, userEmail, userName, userBalance, userBonus};
    }

    private static ArrayList<String[]> loadPacketsFromDB(SQLiteDatabase database) {
        Log.v(TAG, ">> Method: loadPacketsFromDB(SQLiteDatabase)");

        ArrayList<String[]> result = new ArrayList<>();
        String id;
        String password;
        String title;
        String dateEnd;
        String status;

        Cursor cursor = database.query(PACKET_TABLE, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            id = cursor.getString(cursor.getColumnIndex(PACKET_ID));
            password = cursor.getString(cursor.getColumnIndex(PACKET_PASSWORD));
            title = cursor.getString(cursor.getColumnIndex(PACKET_TITLE));
            dateEnd = cursor.getString(cursor.getColumnIndex(PACKET_DATE_END));
            status = cursor.getString(cursor.getColumnIndex(PACKET_STATUS));
            result.add(new String[]{id, password, title, dateEnd, status});
        }

        cursor.close();

        Log.v(TAG, "<< Method: loadPacketsFromDB(SQLiteDatabase)");
        return result;
    }

    private static String getToken(SQLiteDatabase database, String column) {
        Log.v(TAG, ">> Method: getToken(SQLiteDatabase, String)");

        String result = getColumnValue(database, TOKENS_TABLE, column);

        Log.v(TAG, "<< Method: getToken(SQLiteDatabase, String)");
        return result;
    }

    private static String getLogged(SQLiteDatabase database, String column) {
        Log.v(TAG, ">> Method: getLogged(SQLiteDatabase, String)");

        String logged = getColumnValue(database, LOGGED_STATE_TABLE, column);

        Log.v(TAG, "<< Method: getLogged(SQLiteDatabase, String)");
        return logged;
    }

    private static String getColumnValue(SQLiteDatabase database, String tableName, String columnName) {
        Log.v(TAG, ">> getColumnValue(SQLiteDatabase, String, String)");
        String value;
        Cursor cursor = database.rawQuery("SELECT " + columnName + " FROM " + tableName, null);
        if (cursor.moveToLast()) {
            value = cursor.getString(cursor.getColumnIndex(columnName));
            cursor.close();
            Log.v(TAG, "--> getColumnValue(SQLiteDatabase, String, String), value = " + value);
            Log.v(TAG, "<< getColumnValue(SQLiteDatabase, String, String)");
            return value;
        } else {
            cursor.close();
            Log.v(TAG, "<< getColumnValue(SQLiteDatabase, String, String)");
            return "null";
        }
    }

    private static SQLiteDatabase getReadableDatabase(Context context) {
        DatabaseHelper helper = new DatabaseHelper(context);
        return helper.getReadableDatabase();
    }

    private static SQLiteDatabase getWritableDatabase(Context context) {
        DatabaseHelper helper = new DatabaseHelper(context);
        return helper.getWritableDatabase();
    }

}
