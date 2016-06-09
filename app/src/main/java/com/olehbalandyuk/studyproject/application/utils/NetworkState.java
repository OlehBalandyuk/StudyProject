package com.olehbalandyuk.studyproject.application.utils;

import android.content.Context;
import android.net.ConnectivityManager;

public class NetworkState {

    public static boolean networkIsAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}

