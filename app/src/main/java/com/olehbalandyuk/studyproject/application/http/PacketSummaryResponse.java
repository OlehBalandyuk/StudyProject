package com.olehbalandyuk.studyproject.application.http;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;


class PacketSummaryResponse implements Serializable {

    @Expose
    @SerializedName("status")
    private String mStatus;

    @Expose
    @SerializedName("error")
    private String mError;

    @Expose
    @SerializedName("results")
    private ArrayList<PacketModel> mResults;

    public String getStatus() {
        return mStatus;
    }

    public String getError() {
        return mError;
    }

    public ArrayList<PacketModel> getResults() {
        return mResults;
    }

}

