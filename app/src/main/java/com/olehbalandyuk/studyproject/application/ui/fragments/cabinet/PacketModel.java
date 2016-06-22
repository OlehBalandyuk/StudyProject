package com.olehbalandyuk.studyproject.application.ui.fragments.cabinet;

import android.util.Log;

import java.text.DateFormat;
import java.util.Date;

class PacketModel {
    private static final String TAG = PacketModel.class.getSimpleName();

    private static final  int PARAMETERS_SIZE = 5;

    private static final int PACKET_ID = 0;
    private static final int PACKET_PASSWORD = 1;
    private static final int PACKET_TITLE = 2;
    private static final int PACKET_DATE_END = 3;
    private static final int PACKET_STATUS = 4;

    private static final String AVAILABLE = "1";

    private String mId;
    private String mTitle;
    private String mDate;
    private String mPassword;
    private boolean mStatus;

    PacketModel(String[] parameters) {
        Log.v(TAG, ">> Constructor PacketModel(String[])");

        if (parameters.length == PARAMETERS_SIZE) {
            mId = parameters[PACKET_ID];
            mPassword = parameters[PACKET_PASSWORD];
            mTitle = parameters[PACKET_TITLE];
            mDate = DateFormat.getDateTimeInstance().format(new Date(Long.parseLong(parameters[PACKET_DATE_END])));
            mStatus = parameters[PACKET_STATUS].equals(AVAILABLE);
        }
        Log.v(TAG, "<< Constructor PacketModel(String[])");
    }

    String getId() {
        Log.v(TAG, ">> << Method getId()");
        return mId;
    }

    String getDate() {
        Log.v(TAG, ">> << Method getDate()");
        return mDate;
    }

    String getTitle() {
        Log.v(TAG, ">> << Method getTitle()");
        return mTitle;
    }

    String getPassword() {
        Log.v(TAG, ">> << Method getPassword()");
        return mPassword;
    }

    boolean getStatus() {
        Log.v(TAG, ">> << Method getStatus()");
        return mStatus;
    }
}