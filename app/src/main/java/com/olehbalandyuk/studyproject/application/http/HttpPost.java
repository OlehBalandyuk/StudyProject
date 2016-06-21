package com.olehbalandyuk.studyproject.application.http;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

class HttpPost<T> extends AsyncTask<Void, Void, T> {
    private static final String TAG = HttpPost.class.getSimpleName();

    private static final String POST = "POST";
    private static final String ENCODING_TYPE = "UTF-8";

    private String mQuery;
    private HttpPostCallback<T> mCallback;
    private String mUrl;
    private Class<T> mType;

    public HttpPost(String query, String url, HttpPostCallback<T> callback, Class<T> responseType) {
        Log.v(TAG, ">> Constructor HttpPost(String, String, HttpPostCallback, Class)");

        mQuery = query;
        mCallback = callback;
        mUrl = url;
        mType = responseType;

        Log.v(TAG, "<< Constructor HttpPost(String, String, HttpPostCallback, Class)");
    }

    @Override
    protected T doInBackground(Void... params) {
        Log.v(TAG, ">> Method: doInBackground()");

        try {
            URL url = new URL(mUrl);
            Gson gson = new Gson();

            Log.v(TAG, "<< Method: doInBackground()");
            return gson.fromJson(obtainResponse(connect(url)), mType);
        } catch (MalformedURLException e) {
            Log.e(TAG, "--> Method: doInBackground(), error while converting string to url ");

            Log.v(TAG, "<< Method: doInBackground()");
            return null;
        }
    }

    protected void onPostExecute(T response) {
        Log.v(TAG, ">> Method: onPostExecute()");

        if (response != null) {
            mCallback.onResponse(response);

        } else {
            // TODO send error if response is null
        }

        Log.v(TAG, "<< Method: onPostExecute()");
    }

    private HttpURLConnection connect(URL url) {
        Log.v(TAG, ">> Method: connect(URL)");

        try {
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            byte[] outputBytes = mQuery.getBytes(ENCODING_TYPE);

            urlConnection.setRequestMethod(POST);
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.connect();
            OutputStream os = urlConnection.getOutputStream();
            os.write(outputBytes);
            os.close();

            Log.v(TAG, "<< Method: connect(URL)");
            return urlConnection;
        } catch (IOException e) {

            Log.e(TAG, "<< Method: connect(URL), finished with error, returning null");
            return null;
        }
    }

    private String obtainResponse(HttpURLConnection urlConnection) {
        Log.v(TAG, ">> Method: obtainResponse(HttpURLConnection)");

        try {

            int statusCode = urlConnection.getResponseCode();
            Log.v(TAG, "--> Method: obtainResponse(HttpURLConnection), status code = " + statusCode);

            if (statusCode == HttpsURLConnection.HTTP_OK) {
                InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                return convertStreamToString(inputStream);
            } else if (statusCode == HttpsURLConnection.HTTP_UNAUTHORIZED) {
                Log.e(TAG, "--> Method: obtainResponse(HttpURLConnection), error while obtaining response");
                Log.v(TAG, "<< Method: obtainResponse(HttpURLConnection)");
                return "{\"status\": \"ERROR\", \"error\": \"Access denied\"}";
            } else {
                return "";
            }
        } catch (IOException e) {
            Log.e(TAG, "Error while decoding stream", e);

            Log.v(TAG, "<< Method: obtainResponse(HttpURLConnection)");
            return null;
        } catch (NullPointerException e) {
            Log.e(TAG, "Error while decoding stream", e);

            Log.v(TAG, "<< Method: obtainResponse(HttpURLConnection)");
            return "";
        }
    }

    public static String convertStreamToString(InputStream is) {
        Log.v(TAG, ">> Method: convertStreamToString(InputStream)");

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
                Log.e(TAG, "--> Method: convertStreamToString(InputStream), error while closing input stream");
            }
        }

        Log.v(TAG, "<< Method: convertStreamToString(InputStream)");
        return builder.toString();
    }
}