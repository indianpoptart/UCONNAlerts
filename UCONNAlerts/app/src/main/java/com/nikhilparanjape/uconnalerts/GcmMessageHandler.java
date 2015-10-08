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

        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        messageType = gcm.getMessageType(intent);

        mes = extras.getString("title");
        showToast();
        Log.i("GCM", "Received : (" + messageType + ")  " + mes);
        Toast.makeText(getApplicationContext(), mes,
                Toast.LENGTH_LONG).show();


        GcmBroadcastReceiver.completeWakefulIntent(intent);


    }
    public String getMsg(String mesg){
        mesg = mes;
        return mesg;
    }

    public void showToast(){
        handler.post(new Runnable() {
            public void run() {


                Toast.makeText(getApplicationContext(),mes , Toast.LENGTH_LONG).show();
            }
        });

    }
}