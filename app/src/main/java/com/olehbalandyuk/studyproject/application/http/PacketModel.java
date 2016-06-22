package com.olehbalandyuk.studyproject.application.http;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

class PacketModel implements Serializable {

    @Expose
    @SerializedName("id")
    private String mId;

    @Expose
    @SerializedName("pass")
    private String mPassword;

    @Expose
    @SerializedName("title")
    private String mTitle;

    @Expose
    @SerializedName("date_end")
    private String mEndDate;

    @Expose
    @SerializedName("status")
    private String mStatus;


    public String getPassword() {
        return mPassword;
    }

    public String getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getStatus() {
        return mStatus;
    }

    public String getEndDate() {
        return mEndDate;
    }
}
