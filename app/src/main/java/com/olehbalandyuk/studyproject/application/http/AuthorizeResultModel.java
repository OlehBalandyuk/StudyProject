package com.olehbalandyuk.studyproject.application.http;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

class AuthorizeResultModel implements Serializable {

    @Expose
    @SerializedName("status")
    private String mStatus;

    @Expose
    @SerializedName("login_token")
    private String mLoginAccessToken;

    @Expose
    @SerializedName("refresh_token")
    private String mLoginRefreshToken;

    @Expose
    @SerializedName("user_id")
    private String mUserId;

    public String getStatus() {
        return mStatus;
    }

    public String getLoginAccessToken() {
        return mLoginAccessToken;
    }

    public String getLoginRefreshToken() {
        return mLoginRefreshToken;
    }

    public String getUserId() {
        return mUserId;
    }

}
