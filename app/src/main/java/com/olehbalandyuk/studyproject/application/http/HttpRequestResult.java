package com.olehbalandyuk.studyproject.application.http;

class HttpRequestResult {

    int mStatusCode;
    String mResponse;
    HttpRequestStatus mRequestStatus;

    public HttpRequestResult(HttpRequestStatus status, int statusCode, String response) {
        mStatusCode = statusCode;
        mResponse = response;
        mRequestStatus = status;
    }

    public HttpRequestResult(HttpRequestStatus status) {
        mRequestStatus = status;
    }


    public String getResponse() {
        return mResponse;
    }
}
