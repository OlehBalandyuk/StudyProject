package com.olehbalandyuk.studyproject.application.http;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

import com.olehbalandyuk.studyproject.application.API;

import java.io.Serializable;

public class NetworkService extends Service {
    private static final String TAG = NetworkService.class.getSimpleName();

    public static final String BROADCAST_ACTION = "response";

    public static final String HTTP_STATUS = "status";
    public static final int OK = 200;
    public static final int BAD_REQUEST = 400;
    public static final int ERROR = 600;

    private static final String GRANT_TYPE = "grant_type";
    private static final String USERNAME_PARAM = "username";
    private static final String PASSWORD_PARAM = "password";

    private static final String QUERY = "query";
    private static final String REQUEST = "request";
    private static final String RESPONSE = "response";

    private enum ServiceRequests {
        AUTHORIZE_IN_APP,
        AUTHORIZE_IN_PACKET,
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw null;
    }

    public int onStartCommand(Intent intent, int flag, int startId) {
        if (intent == null) return super.onStartCommand(null, flag, startId);

        ServiceRequests requestType = (ServiceRequests) intent.getSerializableExtra(REQUEST);

        switch (requestType) {
            case AUTHORIZE_IN_APP:
                new HttpPost<>(intent.getStringExtra(QUERY),
                        API.LOGIN,
                        new HttpPostCallback<AuthorizeResultModel>() {

                            @Override
                            public void onResponse(AuthorizeResultModel response) {
                                handleAuthorizeResponse(response);
                            }

                            @Override
                            public void onError(Errors error) {
                                handleError(error);
                            }
                        },
                        AuthorizeResultModel.class)
                        .execute();
                break;
            case AUTHORIZE_IN_PACKET:
                // TODO
                break;
        }

        return super.onStartCommand(intent, flag, startId);
    }

    private void handleAuthorizeResponse(AuthorizeResultModel response) {
        Log.v(TAG, ">> Method: handleResponse()");

        if (response.getLoginAccessToken() != null) {
            Tokens tokens = new Tokens(getApplicationContext());
            tokens.setLoginTokens(response);

            LoggedState logged = new LoggedState(getApplicationContext());
            logged.setLoggedByLogin();

            sendBroadcast(OK, response);
        } else if (response.getStatus().equals("ERROR")) {
            sendBroadcast(BAD_REQUEST, response);
        }

        Log.v(TAG, "<< Method: handleResponse()");
    }

    private void handleError(Errors error) {
        // TODO
    }

    private void sendBroadcast(int status, Serializable response) {
        Intent intent = new Intent(BROADCAST_ACTION);
        intent.putExtra(HTTP_STATUS, status);
        intent.putExtra(RESPONSE, response);
        sendBroadcast(intent);
    }

    private static String authorizeUser(String email, String password) {
        Log.v(TAG, ">> Method: authorizeUser()");

        Uri.Builder builder = new Uri.Builder()
                .appendQueryParameter(GRANT_TYPE, PASSWORD_PARAM)
                .appendQueryParameter(USERNAME_PARAM, email)
                .appendQueryParameter(PASSWORD_PARAM, password);

        String encodedQuery = builder.build().getEncodedQuery();

        Log.v(TAG, "<< Method: authorizeUser()");
        return encodedQuery;
    }

    public static void authorizeUser(Context context, String email, String password) {
        Intent result = new Intent(context, NetworkService.class);

        result.putExtra(QUERY, authorizeUser(email, password));
        result.putExtra(REQUEST, ServiceRequests.AUTHORIZE_IN_APP);
        context.startService(result);
    }
}
