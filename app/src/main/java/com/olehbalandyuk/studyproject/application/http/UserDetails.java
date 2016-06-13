package com.olehbalandyuk.studyproject.application.http;

import android.content.Context;

public class UserDetails {

    public static void logout(Context context) {
        Tokens tokens = new Tokens(context);
        tokens.logout();

        LoggedState logged = new LoggedState(context);
        logged.logout();
    }

    public static boolean isLoggedByLogin(Context context){

        LoggedState logged = new LoggedState(context);
        return logged.isLoggedByLogin();
    }
}
