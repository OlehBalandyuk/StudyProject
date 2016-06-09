package com.olehbalandyuk.studyproject.application.ui.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.dd.processbutton.iml.ActionProcessButton;
import com.olehbalandyuk.studyproject.R;
import com.olehbalandyuk.studyproject.application.http.NetworkService;
import com.olehbalandyuk.studyproject.application.utils.NetworkState;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();

    private static final String USERNAME_KEY = "username";
    private static final String PASSWORD_KEY = "password";
    private static final String LOGIN_PROGRESS = "progress";

    private static final int NETWORK_ERROR_TITLE_STRING_RES_ID = R.string.network_error_title;
    private static final int NETWORK_ERROR_CONTENT_STRING_RES_ID = R.string.network_error_content;

    private static final int WRONG_INPUT_DATA_TITLE_STRING_RES_ID = R.string.wrong_input_data_title;
    private static final int WRONG_INPUT_DATA_CONTENT_STRING_RES_ID = R.string.wrong_input_data_content;

    private static final int SERVER_ERROR_TITLE_STRING_RES_ID = R.string.server_error_title;
    private static final int SERVER_ERROR_CONTENT_STRING_RES_ID = R.string.server_error_content;

    private EditText mUsernameField;
    private EditText mPasswordField;
    private ActionProcessButton mLogin;
    private String mUsername;
    private String mPassword;

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.v(TAG, "anonymous class BroadcastReceiver, Method onReceive()");
            int status = intent.getIntExtra(NetworkService.HTTP_STATUS, NetworkService.ERROR);

            if (status == NetworkService.OK) {
                Log.v(TAG, "anonymous class BroadcastReceiver, Method onReceive()" +
                        ", response in OK, redirecting to the MainScreenActivity...");
                redirectToTheMainActivity();
            } else if (status == NetworkService.BAD_REQUEST) {
                Log.v(TAG, "anonymous class BroadcastReceiver, Method onReceive()" +
                        ", response in BAD, showing wrong input data dialog...");
                handleWrongInputData();
            } else if (status == NetworkService.ERROR) {
                Log.e(TAG, "anonymous class BroadcastReceiver, Method onReceive()" +
                        ", error, should show error dialog...");
                // TODO show error dialog
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, ">> Method: onCreate()");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mUsernameField = (EditText) findViewById(R.id.login_field);
        mPasswordField = (EditText) findViewById(R.id.password_field);

        mLogin = (ActionProcessButton) findViewById(R.id.login_button);
        handleLoginButton();

        if (savedInstanceState != null) {
            mUsername = savedInstanceState.getString(USERNAME_KEY);
            mPassword = savedInstanceState.getString(PASSWORD_KEY);
            mLogin.setProgress(savedInstanceState.getInt(LOGIN_PROGRESS));
        }

        Log.v(TAG, "<< Method: onCreate()");
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        Log.v(TAG, ">> Method: onSaveInstanceState()");

        mUsername = mUsernameField.getText().toString();
        mPassword = mPasswordField.getText().toString();

        savedInstanceState.putString(USERNAME_KEY, mUsername);
        savedInstanceState.putString(PASSWORD_KEY, mPassword);
        savedInstanceState.putInt(LOGIN_PROGRESS, mLogin.getProgress());

        Log.v(TAG, "<< Method: onSaveInstanceState()");
    }

    private void handleLoginButton() {
        Log.v(TAG, ">> Method: handleLoginButton()");

        mLogin.setMode(ActionProcessButton.Mode.PROGRESS);
        mLogin.setProgress(0);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v(TAG, "--> Method: handleLoginButton(), anonymous class OnClickListener, " +
                        "Method onClick");

                mLogin.setMode(ActionProcessButton.Mode.ENDLESS);

                if (isNetworkConnected()) {
                    if (mLogin.getProgress() == 0) {
                        sendRequest();
                        Log.v(TAG, "--> Method: handleLoginButton(), anonymous class OnClickListener" +
                                ", Method onClick, request in sent, waiting for response");
                    }
                } else {
                    abortSendingRequest();
                    Log.v(TAG, "--> Method: handleLoginButton(), anonymous class OnClickListener" +
                            ", Method onClick, request in NOT sent, network error");

                }
            }
        });

        Log.v(TAG, "<< Method: handleLoginButton()");
    }

    private boolean isNetworkConnected() {
        Log.v(TAG, ">> Method: isNetworkConnected()");

        boolean isNetworkEnabled = NetworkState.networkIsAvailable(this);

        Log.v(TAG, "--> Method: isNetworkConnected(), network is available - " + isNetworkEnabled);
        Log.v(TAG, "<< Method: isNetworkConnected()");
        return isNetworkEnabled;
    }

    private void showNetworkErrorDialog() {
        Log.v(TAG, ">> Method: showNetworkErrorDialog()");

        new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(getString(NETWORK_ERROR_TITLE_STRING_RES_ID))
                .setContentText(getString(NETWORK_ERROR_CONTENT_STRING_RES_ID))
                .show();

        Log.v(TAG, "<< Method: showNetworkErrorDialog()");
    }

    private void redirectToTheMainActivity() {
        Log.v(TAG, ">> Method: redirectToTheMainActivity()");

        unregisterReceiver(mReceiver);

        mLogin.setProgress(100);
        Intent mainScreen = new Intent(this, MainScreenActivity.class);
        startActivity(mainScreen);

        Log.v(TAG, "<< Method: redirectToTheMainActivity()");
    }

    private void handleWrongInputData() {
        Log.v(TAG, ">> Method: handleWrongInputData()");

        unregisterReceiver(mReceiver);

        mLogin.setProgress(0);

        new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(getString(WRONG_INPUT_DATA_TITLE_STRING_RES_ID))
                .setContentText(getString(WRONG_INPUT_DATA_CONTENT_STRING_RES_ID))
                .show();

        Log.v(TAG, "<< Method: handleWrongInputData()");
    }

    private void sendService() {
        Log.v(TAG, ">> Method: sendService()");

        NetworkService.authorizeUser(getApplicationContext(), mUsernameField.getText().toString(), mPasswordField.getText().toString());

        Log.v(TAG, "<< Method: sendService()");
    }

    private void sendRequest() {
        Log.v(TAG, ">> Method: sendRequest()");

        mLogin.setProgress(1);

        IntentFilter filter = new IntentFilter(NetworkService.BROADCAST_ACTION);
        registerReceiver(mReceiver, filter);

        sendService();
        Log.v(TAG, "<< Method: sendRequest()");
    }

    private void abortSendingRequest() {
        Log.v(TAG, ">> Method: abortSendingRequest()");
        showNetworkErrorDialog();
        mLogin.setMode(ActionProcessButton.Mode.PROGRESS);
        Log.v(TAG, "<< Method: abortSendingRequest()");
    }
}
