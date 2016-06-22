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

    /**
     * Loads user info from the database
     *
     * @param context for access to the database
     * @return String[] of user info
     */
    public static String[] loadUserInfo(Context context) {
        Log.v(TAG, ">> Method: loadUserInfo(Context)");
        String[] result;

        SQLiteDatabase database = getReadableDatabase(context);

        result = loadUserInfo(database);
        database.close();

        Log.v(TAG, "<< Method: loadUserInfo(Context)");
        return result;
    }

    /**
     * Save user email to the database.
     * NOTE: the method won't save any other user data. Only email.
     *
     * @param context for access to the database
     * @param email value of user email
     */
    public static void saveUserEmail(Context context, String email) {
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

    /**
     * Saves user info to the database.
     * NOTE: no more than one user info can be saved at one time, so previous user info
     * will be overwritten.
     *
     * @param context for access to the database
     * @param response model of user info response
     */
    public static void saveUserInfo(Context context, UserInfoModel response) {
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

    /**
     * Loads all packets from the database
     *
     * @param context for access to the database
     * @return ArrayList<String[]> of packets
     */
    public static ArrayList<String[]> loadPacketsFromDB(Context context) {
        Log.v(TAG, ">> loadPacketsFromDB(Context)");

        SQLiteDatabase database = getReadableDatabase(context);
        ArrayList<String[]> result = loadPacketsFromDB(database);
        database.close();

        Log.v(TAG, "<< loadPacketsFromDB(Context)");
        return result;
    }

    /**
     * Saves tokens whether they are login tokens or packet tokens
     *
     * @param context for access to the database
     * @param areLoginTokens whether passed tokens are authorization tokens or not
     * @param accessToken value of access token
     * @param refreshToken value of refresh token
     */
    public static void saveTokens(Context context, boolean areLoginTokens, String accessToken, String refreshToken) {
        Log.v(TAG, ">> Method: saveTokens(Context, boolean, String, String)");

        SQLiteDatabase database = getWritableDatabase(context);

        String values;
        if (areLoginTokens) {
            values = "\"" + accessToken + "\", " +
                    "\"" + refreshToken + "\", " +
                    "\"" + getToken(database, PACKET_ACCESS_TOKEN) + "\", " +
                    "\"" + getToken(database, PACKET_REFRESH_TOKEN) + "\" ";
        } else {
            values = "\"" + getToken(database, LOGIN_ACCESS_TOKEN) + "\", " +
                    "\"" + getToken(database, LOGIN_REFRESH_TOKEN) + "\", " +
                    "\"" + accessToken + "\", " +
                    "\"" + refreshToken + "\" ";
        }

        database.execSQL("INSERT INTO " + TOKENS_TABLE + " VALUES (" + values + ");");
        database.close();

        Log.v(TAG, "<< Method: saveTokens(Context, boolean, String, String)");
    }

    public static String getLoginAccessToken(Context context) {
        Log.v(TAG, ">> Method: getLoginAccessToken(Context)");

        SQLiteDatabase database = getReadableDatabase(context);
        String result = getToken(database, LOGIN_ACCESS_TOKEN);
        database.close();

        Log.v(TAG, "<< Method: getLoginAccessToken(Context)");
        return result;
    }

    public static String getLoginRefreshToken(Context context) {
        Log.v(TAG, ">> Method: getPacketAccessToken(Context)");

        SQLiteDatabase database = getReadableDatabase(context);
        String result = getToken(database, LOGIN_REFRESH_TOKEN);
        database.close();

        Log.v(TAG, "<< Method: getPacketAccessToken(Context)");
        return result;
    }

    public static String getPacketAccessToken(Context context) {
        Log.v(TAG, ">> Method: getPacketAccessToken(Context)");

        SQLiteDatabase database = getReadableDatabase(context);
        String result = getToken(database, PACKET_ACCESS_TOKEN);
        database.close();

        Log.v(TAG, "<< Method: getPacketAccessToken(Context)");
        return result;
    }

    public static String getPacketRefreshToken(Context context) {
        Log.v(TAG, ">> Method: getPacketRefreshToken(Context)");

        SQLiteDatabase database = getReadableDatabase(context);
        String result = getToken(database, PACKET_REFRESH_TOKEN);
        database.close();

        Log.v(TAG, "<< Method: getPacketRefreshToken(Context)");
        return result;
    }

    /**
     * Saves logged state as logged (true)
     *
     * @param context for access to the database
     */
    public static void setLoggedByLogin(Context context) {
        Log.v(TAG, ">> Method: setLoggedByLogin(Context)");

        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase database = helper.getWritableDatabase();
        database.execSQL("UPDATE " + LOGGED_STATE_TABLE + " SET " +
                LOGGED_IN_APP + " = \"true\", " +
                LOGGED_IN_PACKED + " = \"" + getLogged(database, LOGGED_IN_PACKED) + "\" "
        );
        database.close();

        Log.v(TAG, "<< Method: setLoggedByLogin(Context)");
    }

    /**
     *Method checks  whether user is logged in application or not
     *
     * @param context for access to the database
     * @return whether user is logged or not
     */
    public static boolean isLoggedByLogin(Context context) {
        Log.v(TAG, ">> Method: isLoggedByLogin(Context)");

        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase database = helper.getReadableDatabase();
        boolean isLogged = Boolean.parseBoolean(getLogged(database, LOGGED_IN_APP));
        database.close();

        Log.v(TAG, "<< Method: isLoggedByLogin(Context)");
        return isLogged;
    }

    /**
     * Method saves the title of the packet is which user had logged
     *
     * @param context for access to the database
     * @param packetTitle to save title of last visited packet
     */
    public static void setLoggedInPacket(Context context, String packetTitle) {
        Log.v(TAG, ">> Method: setLoggedInPacket(Context, String)");

        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase database = helper.getWritableDatabase();
        database.execSQL("UPDATE " + LOGGED_STATE_TABLE + " SET " +
                LOGGED_IN_APP + " = \"" + getLogged(database, LOGGED_IN_APP) + "\", " +
                LOGGED_IN_PACKED  + " = \"" + packetTitle + "\" "
        );
        database.close();

        Log.v(TAG, ">> Method: setLoggedInPacket(Context, String)");
    }

    /**
     Method saves in which exact packet user is logged now
     *
     * @param context for access to the database
     * @return title of packet in which user is logged last time
     */
    public static boolean isLoggedInPacket(Context context, String expectedPacketTitle) {
        Log.v(TAG, ">> Method: isLoggedByPacket(Context, String)");

        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase database = helper.getReadableDatabase();
        boolean logged = expectedPacketTitle.equals(getLogged(database, LOGGED_IN_PACKED));
        database.close();

        Log.v(TAG, "<< Method: isLoggedByPacket(Context, String)");
        return logged;
    }

    /**
     Method logs out userut frothe m application.
     * This method will delete all data from the database
     *
     * @param context for access to the database
     */
    public static void logout(Context context) {
        Log.v(TAG, ">> Method: logout(Context)");

        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase database = helper.getWritableDatabase();
        DatabaseHelper.logout(database);
        database.close();

        Log.v(TAG, "<< Method: logout(Context)");
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
        Log.v(TAG, ">> Method: getReadableDatabase(Context)");

        DatabaseHelper helper = new DatabaseHelper(context);

        Log.v(TAG, "<< Method: getReadableDatabase(Context)");
        return helper.getReadableDatabase();
    }

    private static SQLiteDatabase getWritableDatabase(Context context) {
        Log.v(TAG, ">> Method: getWritableDatabase(Context)");

        DatabaseHelper helper = new DatabaseHelper(context);

        Log.v(TAG, "<< Method: getWritableDatabase(Context)");
        return helper.getWritableDatabase();
    }

}
