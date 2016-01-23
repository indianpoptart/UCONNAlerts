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
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.util.StringTokenizer;

public class GcmNotificationIntentService extends IntentService {
    // Sets an ID for the notification, so it can be updated
    public static final int notifyID = 9001;
    String notif;
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
                sendNotification("" + extras.get(ApplicationConstants.MSG_KEY));
            }
        }
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    private void sendNotification(String msg) {
        SharedPreferences sp = getSharedPreferences("prefs", MODE_PRIVATE);
        StringTokenizer tokens = new StringTokenizer(msg, ":");
        String avery = tokens.nextToken();// this will contain Avery Point data
        String hartford = tokens.nextToken();
        String law = tokens.nextToken();
        String stamford = tokens.nextToken();
        String storrs = tokens.nextToken();
        String torrington = tokens.nextToken();
        String waterbury = tokens.nextToken();
        Log.d("UCONN-CAMPUSES", msg);


        if(sp.getInt("checked",0) == 2131558512){
            notif = stamford;
        }
        else if(sp.getInt("checked",0) == 2131558513){
            notif = storrs;
        }

        PendingIntent pi = PendingIntent.getActivity(this, 1, new Intent(this, MainActivity.class), 0);
        //Resources r = getResources();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.uconn)
                .setContentTitle("UCONN Alert")
                .setContentIntent(pi)
                .setContentText(notif)
                .setPriority(Notification.PRIORITY_HIGH)
                .setAutoCancel(true)
                .build();

        notification.defaults|= Notification.DEFAULT_SOUND;
        notificationManager.notify(1, notification);
    }
}