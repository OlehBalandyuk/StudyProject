package com.olehbalandyuk.studyproject.application.http;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

class PacketModel implements Serializable {

    @Expose
    @SerializedName("id")
    private String mPackageId;

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


    public String getPackageId() {
        return mPackageId;
    }

    public String getPassword() {
        return mPassword;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getEndDate() {
        return mEndDate;
    }

    public String getStatus() {
        return mStatus;
    }
}
