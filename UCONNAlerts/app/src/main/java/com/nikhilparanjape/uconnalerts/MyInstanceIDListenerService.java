package com.nikhilparanjape.uconnalerts;

import android.content.Intent;
import android.util.Log;

import com.google.android.gms.iid.InstanceIDListenerService;
import com.google.firebase.iid.FirebaseInstanceId;

/**
 * Created by admin on 8/26/2016.
 */
public class MyInstanceIDListenerService extends InstanceIDListenerService {

    @Override
    public void onTokenRefresh() {
        // Fetch updated Instance ID token and notify of changes
        Intent intent = new Intent(this, RegistrationIntentService.class);
        startService(intent);
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d("UCONNAlerts", "Refreshed token: " + token);
    }
}