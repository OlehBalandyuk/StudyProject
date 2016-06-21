package com.olehbalandyuk.studyproject.application.http;

import android.content.Context;
import android.util.Log;

import com.olehbalandyuk.studyproject.application.data.database.DatabaseConnector;

import static com.olehbalandyuk.studyproject.application.data.database.DatabaseConnector.saveTokens;
import static com.olehbalandyuk.studyproject.application.data.database.DatabaseConnector.TOKENS_ARE_LOGIN;

class Tokens {
    private static final String TAG = Tokens.class.getSimpleName();

    private Context mContext;

    Tokens(Context context) {
        Log.v(TAG, ">> Constructor Tokens(Context)");

        mContext = context;

        Log.v(TAG, "<< Constructor Tokens(Context)");
    }

    void setLoginTokens(AuthorizeResultModel response) {
        Log.v(TAG, ">> Method: setLoginTokens(AuthorizeResultModel)");

        String accessToken = response.getLoginAccessToken();
        String refreshToken = response.getLoginRefreshToken();
        saveTokens(
                mContext, TOKENS_ARE_LOGIN, accessToken, refreshToken);

        Log.v(TAG, "<< Method: setLoginTokens(AuthorizeResultModel)");
    }

    // TODO uncomment this when packet response model will exist
/*    void setPacketTokens(PacketResultModel response) {
        Log.v(TAG, ">> Method: setLoginTokens(AuthorizeResultModel)");

        String accessToken = response.getLoginAccessToken();
        String refreshToken = response.getLoginRefreshToken();
        saveTokens(
                mContext, !TOKENS_ARE_LOGIN, accessToken, refreshToken);

        Log.v(TAG, "<< Method: setLoginTokens(AuthorizeResultModel)");
    }*/

    String getLoginAccessToken() {
        return DatabaseConnector.getLoginAccessToken(mContext);
    }

    String getLoginRefreshToken() {
        return DatabaseConnector.getLoginRefreshToken(mContext);
    }

    String getPacketAccessToken() {
        return DatabaseConnector.getPacketAccessToken(mContext);
    }

    String getPacketRefreshToken() {
        return DatabaseConnector.getPacketRefreshToken(mContext);
    }
}
