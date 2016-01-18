package com.nikhilparanjape.uconnalerts;

/**
 * Created by Nikhil on 10/8/2015.
 */
import com.google.android.gms.gcm.GoogleCloudMessaging;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.service.media.MediaBrowserService;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class GcmMessageHandler extends IntentService {
    String mes;
    String messageType;
    String campus = "storrs";


            NotificationCompat.Builder notification;
    PendingIntent pIntent;
    NotificationManager manager;

    Intent resultIntent;
    TaskStackBuilder stackBuilder;
    private Handler handler;
    public GcmMessageHandler() {
        super("GcmMessageHandler");
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        handler = new Handler();
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        SharedPreferences sp = getSharedPreferences("prefs", MODE_PRIVATE);
        int radio = sp.getInt("checked",0);

        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        messageType = gcm.getMessageType(intent);
        if(radio == 2131558509){
            campus = "stamford";
        }
        else if(radio == 2131558510){
            campus = "storrs";
        }

        mes = extras.getString(campus);

        //showToast();
        Log.i("GCM", "Received : (" + messageType + ")  " + mes + "Campus : " + AboutActivity.getCampus());
        Toast.makeText(getApplicationContext(), mes,
                Toast.LENGTH_LONG).show();
        sendNote(mes);
        GcmBroadcastReceiver.completeWakefulIntent(intent);


    }
    public void sendNote(String mes){
        PendingIntent pi = PendingIntent.getActivity(this, 1, new Intent(this, MainActivity.class), 0);
        //Resources r = getResources();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.uconn)
                .setContentTitle("UCONN Alert")
                .setContentIntent(pi)
                .setContentText(mes)
                .setPriority(Notification.PRIORITY_HIGH)
                .setAutoCancel(true)
                .build();

        notification.defaults|= Notification.DEFAULT_SOUND;
        notificationManager.notify(1, notification);

    }

    public String getMsg(String mesg){
        mesg = mes;
        return mesg;
    }

}