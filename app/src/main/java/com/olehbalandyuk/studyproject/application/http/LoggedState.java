package com.olehbalandyuk.studyproject.application.http;

import android.content.Context;
import android.util.Log;

import com.olehbalandyuk.studyproject.application.data.database.DatabaseConnector;

class LoggedState {
    private static final String TAG = LoggedState.class.getSimpleName();

    private Context mContext;

    LoggedState(Context context) {
        Log.v(TAG, ">> Constructor LoggedState(Context)");

        mContext = context;

        Log.v(TAG, "<< Constructor LoggedState(Context)");
    }

    void setLoggedByLogin() {
        Log.v(TAG, ">> Method: setLoggedByLogin()");

        DatabaseConnector.setLoggedByLogin(mContext);

        Log.v(TAG, "<< Method: setLoggedByLogin()");
    }

    boolean isLoggedByLogin() {
        Log.v(TAG, ">> Method: isLoggedByLogin()");

        boolean isLogged = DatabaseConnector.isLoggedByLogin(mContext);

        Log.v(TAG, "<< Method: isLoggedByLogin()");
        return isLogged;
    }
}