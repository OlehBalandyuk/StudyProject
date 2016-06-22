package com.olehbalandyuk.studyproject.application.http;

import android.content.Context;
import android.util.Log;

import java.net.HttpURLConnection;
import java.net.ProtocolException;

class HttpGetRequest extends HttpRequest {
    private static final String TAG = HttpGetRequest.class.getSimpleName();
    private static final String GET = "GET";

    private Context mContext;

    public HttpGetRequest(String query, String url, HttpRequestCallback callback, Context context) {
        super(query, url, callback);
        mContext = context;
        Log.v(TAG, "Constructor HttpGetRequest(String, String, HttpRequestCallback, Context)");
    }

    @Override
    void addRequestProperties(HttpURLConnection urlConnection) {

        try {
            urlConnection.setRequestMethod(GET);
        } catch (ProtocolException e) {
            Log.e(TAG, "Error while setting Request Method", e);
            e.printStackTrace();
        }
        Tokens tokens = new Tokens(mContext);
        String loginToken = tokens.getLoginAccessToken();

        urlConnection.addRequestProperty("Accept", "application/json");
        urlConnection.addRequestProperty("Accept-Language", "ru-RU");
        urlConnection.addRequestProperty("Login", "Bearer " + loginToken);
    }


}