package com.nikhilparanjape.uconnalerts;

/**
 * Created by admin on 8/26/2016.
 */
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.Map;

public class FCMMessageHandler extends FirebaseMessagingService {
    public static final int MESSAGE_NOTIFICATION_ID = 435345;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Map<String, String> data = remoteMessage.getData();
        Log.d("UCONNAlerts", "From: " + remoteMessage.getFrom());
        RemoteMessage.Notification notification = remoteMessage.getNotification();
        createNotification(notification);
    }

    // Creates notification based on title and body received
    private void createNotification(RemoteMessage.Notification notification) {
        Log.d("UCONNAlert","Note sent " + notification.getSound());
        Context context = getBaseContext();
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.uconn)
                .setContentTitle(notification.getTitle())
                .setContentText(notification.getBody());

        NotificationManager mNotificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(MESSAGE_NOTIFICATION_ID, mBuilder.build());
    }

}