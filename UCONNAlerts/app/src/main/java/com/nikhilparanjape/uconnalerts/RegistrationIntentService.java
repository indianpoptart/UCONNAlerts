package com.nikhilparanjape.uconnalerts;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;

import java.io.IOException;


public class RegistrationIntentService extends IntentService {
    public static final String SENT_TOKEN_TO_SERVER = "sentTokenToServer";
    public static final String FCM_TOKEN = "FCMToken";

    // abbreviated tag name
    private static final String TAG = "RegIntentService";

    public RegistrationIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        // Make a call to Instance API
        String senderId = "321641182890";
        // request token that will be used by the server to send push notifications
        String token = FirebaseInstanceId.getInstance().getToken();

        Log.d("UCONNAlerts", "FCM Registration Token: " + token);
        sharedPreferences.edit().putString(FCM_TOKEN, token).apply();

        // pass along this data
        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {
        // send network request

        // if registration sent was successful, store a boolean that indicates whether the generated token has been sent to server
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.edit().putBoolean(SENT_TOKEN_TO_SERVER, true).apply();
    }
}