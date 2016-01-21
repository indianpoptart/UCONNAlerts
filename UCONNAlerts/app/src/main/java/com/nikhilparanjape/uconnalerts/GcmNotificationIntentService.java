package com.nikhilparanjape.uconnalerts;

/**
 * Created by Nikhil on 1/21/2016.
 */
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.gcm.GoogleCloudMessaging;

public class GcmNotificationIntentService extends IntentService {
    // Sets an ID for the notification, so it can be updated
    public static final int notifyID = 9001;
    NotificationCompat.Builder builder;

    public GcmNotificationIntentService() {
        super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);

        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) {
            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR
                    .equals(messageType)) {
                sendNotification("Send error: " + extras.toString());
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED
                    .equals(messageType)) {
                sendNotification("Deleted messages on server: "
                        + extras.toString());
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE
                    .equals(messageType)) {
                sendNotification("Message Received from Google GCM Server:nn"
                        + extras.get(ApplicationConstants.MSG_KEY));
            }
        }
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    private void sendNotification(String msg) {
        PendingIntent pi = PendingIntent.getActivity(this, 1, new Intent(this, MainActivity.class), 0);
        //Resources r = getResources();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.uconn)
                .setContentTitle("UCONN Alert")
                .setContentIntent(pi)
                .setContentText(msg)
                .setPriority(Notification.PRIORITY_HIGH)
                .setAutoCancel(true)
                .build();

        notification.defaults|= Notification.DEFAULT_SOUND;
        notificationManager.notify(1, notification);
    }
}