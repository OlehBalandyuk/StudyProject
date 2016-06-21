package com.olehbalandyuk.studyproject.application.http;

import android.content.Context;
import android.util.Log;

import com.olehbalandyuk.studyproject.application.data.database.DatabaseConnector;

public class UserDetails {
    private static final String TAG = UserDetails.class.getSimpleName();

    public static void logout(Context context) {
        Log.v(TAG, ">> Method: logout(Context)");

        DatabaseConnector.logout(context);

        Log.v(TAG, "<< Method: logout(Context)");
    }

    public static boolean isLoggedByLogin(Context context){
        Log.v(TAG, ">> Method: isLoggedByLogin(Context)");

        LoggedState logged = new LoggedState(context);

        Log.v(TAG, "<< Method: isLoggedByLogin(Context)");
        return logged.isLoggedByLogin();
    }
}
