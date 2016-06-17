package com.olehbalandyuk.studyproject.application.data.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import static com.olehbalandyuk.studyproject.application.data.database.DatabaseHelper.USER_BALANCE;
import static com.olehbalandyuk.studyproject.application.data.database.DatabaseHelper.USER_BONUS;
import static com.olehbalandyuk.studyproject.application.data.database.DatabaseHelper.USER_EMAIL;
import static com.olehbalandyuk.studyproject.application.data.database.DatabaseHelper.USER_ID;
import static com.olehbalandyuk.studyproject.application.data.database.DatabaseHelper.USER_INFO_TABLE;
import static com.olehbalandyuk.studyproject.application.data.database.DatabaseHelper.USER_NAME;

public class DatabaseConnector {

    public static String[] loadUserInfo(Context context) {
        String[] result;

        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase database = helper.getReadableDatabase();

        result = loadUserInfo(database);
        database.close();

        return result;
    }

    public static void saveUserEmail(String email, Context context) {

        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase database = helper.getReadableDatabase();

        database.execSQL("UPDATE " + USER_INFO_TABLE + " SET " +
                USER_ID + " = \"" + getColumnValue(database, USER_INFO_TABLE, USER_ID) + "\", " +
                USER_EMAIL + " = \"" + email + "\", " +
                USER_NAME + " = \"" + getColumnValue(database, USER_INFO_TABLE, USER_NAME) + "\", " +
                USER_BALANCE + " = \"" + getColumnValue(database, USER_INFO_TABLE, USER_BALANCE) + "\", " +
                USER_BALANCE + " = \"" + getColumnValue(database, USER_INFO_TABLE, USER_BONUS) + "\" "
        );

        database.close();
    }

    private static String[] loadUserInfo(SQLiteDatabase database) {
        String userId = "null";
        String userEmail = "null";
        String userName = "null";
        String userBalance = "null";
        String userBonus = "null";

        Cursor cursor = database.query(USER_INFO_TABLE, null, null, null, null, null, null);
        if (cursor.moveToLast()) {
            userId = cursor.getString(cursor.getColumnIndex(USER_ID));
            userEmail = cursor.getString(cursor.getColumnIndex(USER_EMAIL));
            userName = cursor.getString(cursor.getColumnIndex(USER_NAME));
            userBalance = cursor.getString(cursor.getColumnIndex(USER_BALANCE));
            userBonus = cursor.getString(cursor.getColumnIndex(USER_BONUS));
        }
        cursor.close();

        return new String[] {userId, userEmail, userName, userBalance, userBonus};
    }

    private static String getColumnValue(SQLiteDatabase database, String tableName, String columnName) {
        String result;
        Cursor cursor = database.rawQuery("SELECT " + columnName + " FROM " + tableName, null);
        if (cursor.moveToLast()) {
            result = cursor.getString(cursor.getColumnIndex(columnName));
            cursor.close();
            return result;
        } else {
            cursor.close();
            return "null";
        }
    }
}
