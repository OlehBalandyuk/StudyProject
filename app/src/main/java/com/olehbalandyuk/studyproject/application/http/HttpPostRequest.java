package com.olehbalandyuk.studyproject.application.http;

import android.util.Log;

import java.net.HttpURLConnection;
import java.net.ProtocolException;

class HttpPostRequest extends HttpRequest {
    private static final String TAG = HttpPostRequest.class.getSimpleName();
    private static final String POST = "POST";

    public HttpPostRequest(String query, String url, HttpRequestCallback callback) {
        super(query, url, callback);
        Log.v(TAG, " Constructor HttpPostRequest(String, String, HttpRequestCallback)");
    }

    @Override
    void addRequestProperties(HttpURLConnection urlConnection) {
        try {
            urlConnection.setRequestMethod(POST);
        } catch (ProtocolException e) {
            Log.e(TAG, "Error while setting Request Method", e);
        }
    }


}