package com.olehbalandyuk.studyproject.application.http;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class UserInfoSummaryResponse {
    @Expose
    @SerializedName("status")
    private String mStatus;

    @Expose
    @SerializedName("error")
    private String mError;

    @Expose
    @SerializedName("results")
    UserInfoModel mResults;

    public String getStatus() {
        return mStatus;
    }

    public String getError() {
        return mError;
    }

    public UserInfoModel getResults() {
        return mResults;
    }

}
