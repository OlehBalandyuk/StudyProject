package com.olehbalandyuk.studyproject.application.http;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserInfoModel {

    @Expose
    @SerializedName("id")
    private String mId;

    @Expose
    @SerializedName("name")
    private String mName;

    @Expose
    @SerializedName("balance")
    private String mBalance;

    @Expose
    @SerializedName("bonus")
    private String mBonus;

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getBalance() {
        return mBalance;
    }

    public String getBonus() {
        return mBonus;
    }

}
