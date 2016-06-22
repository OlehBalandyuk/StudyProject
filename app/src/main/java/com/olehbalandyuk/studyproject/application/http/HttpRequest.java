package com.olehbalandyuk.studyproject.application.http;


import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

abstract class HttpRequest extends AsyncTask<Void, Void, Void> {
    private static final String TAG = HttpRequest.class.getSimpleName();
    private static final String ENCODING_TYPE = "UTF-8";
    private String mUrl;
    private String mQuery;

    private HttpRequestCallback mCallback;

    public HttpRequest(String query, String url, HttpRequestCallback callback) {
        Log.d(TAG, ">> Constructor HttpRequest(String, HttpRequestCallback, Class)");
        mCallback = callback;
        mQuery = query;
        mUrl = url;
        Log.d(TAG, "<< Constructor HttpRequest(String, HttpPostCallback, Class)");
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            Log.v(TAG, "<< Method: doInBackground()");

            URL url = new URL(mUrl);

            try {
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                byte[] outputBytes = mQuery.getBytes(ENCODING_TYPE);

                addRequestProperties(urlConnection);

                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                urlConnection.connect();

                OutputStream os = urlConnection.getOutputStream();
                os.write(outputBytes);
                os.close();

                obtainResponse(urlConnection);

            } catch (IOException e) {
                Log.e(TAG, "Incorrect URL");
                e.printStackTrace();
                mCallback.onError(new HttpRequestResult(HttpRequestStatus.NETWORK_ERROR));

            }

        } catch (MalformedURLException e) {
            Log.e(TAG, "--> Method: doInBackground(), error while converting string to url ");

            Log.v(TAG, "<< Method: doInBackground()");
        }

        return null;
    }

    abstract void addRequestProperties(HttpURLConnection urlConnection);

    public void sendHttpRequest() {
        super.execute();
    }


    private HttpRequestResult obtainResponse(HttpURLConnection urlConnection) {
        Log.v(TAG, ">> Method: obtainResponse()");
        HttpRequestResult response = null;
        try {

            int statusCode = urlConnection.getResponseCode();
            Log.v(TAG, "--> Method: obtainResponse(), status code = " + statusCode);


            InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());


            switch (statusCode) {
                case HttpURLConnection.HTTP_OK:
                    response = new HttpRequestResult(HttpRequestStatus.OK, statusCode, convertStreamToString(inputStream));
                    mCallback.onResponse(response);
                    break;
                default:
                    response = new HttpRequestResult(HttpRequestStatus.HTTP_REQUEST_ERROR, statusCode, convertStreamToString(inputStream));
                    mCallback.onError(response);
                    break;

            }
        } catch (IOException e) {
            Log.e(TAG, "Error while decoding stream", e);
            mCallback.onError(new HttpRequestResult(HttpRequestStatus.OTHER));

        }
        return response;
    }

    public static String convertStreamToString(InputStream is) {
        Log.v(TAG, ">> Method: convertStreamToString()");
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder builder = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                builder.append((line + "\n"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                Log.e(TAG, "--> Method: convertStreamToString(), error while closing input stream");
            }
        }
        Log.v(TAG, "<< Method: convertStreamToString()");
        return builder.toString();
    }

}
