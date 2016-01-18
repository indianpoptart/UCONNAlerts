package com.nikhilparanjape.uconnalerts;


import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.parse.Parse;
import com.parse.ParseInstallation;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import static com.nikhilparanjape.uconnalerts.CommonUtilities.SERVER_URL;
import static com.nikhilparanjape.uconnalerts.CommonUtilities.TAG;


public class MainActivity extends Activity implements View.OnClickListener {
    String SERVER_URL = "https://nikhilp.org/gcm/register.php";

    Button btnRegId;
    EditText etRegId;
    GoogleCloudMessaging gcm;
    String regid;
    String PROJECT_NUMBER = "527242612864";
    static TextView mDisplay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sp = getSharedPreferences("prefs", MODE_PRIVATE);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        Parse.initialize(this, "e8IO5XPl2qh0LBzU6Kjdu5mO2gcWeLwyHj20TSnH", "jidDCFQ8JMfoVmyjNV7HtJ1nSebOmuYlRe363Yym");
        ParseInstallation.getCurrentInstallation().saveInBackground();
        getRegId();
    }
    public void getRegId(){
        new AsyncTask<Void, Void, String>() {
            @Override

            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
                    }
                    regid = gcm.register(PROJECT_NUMBER);
                    msg = regid;
                    Log.i("GCM",  msg);

                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();

                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                Map<String, String> params = new HashMap<String, String>();
                String serverUrl = SERVER_URL;
                params.put("regId", msg);
                try {
                    post(serverUrl, msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.execute(null, null, null);
    }
    @Override
    public void onClick(View v) {
        getRegId();

    }
    private static void post(String endpoint, String gcmRegId)
            throws IOException {

        URL url;
        try {
            url = new URL(endpoint);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("invalid url: " + endpoint);
        }
        String body = gcmRegId;
        Log.v(TAG, "Posting '" + body + "' to " + url);
        byte[] bytes = body.getBytes();
        HttpURLConnection conn = null;
        try {
            Log.e("URL", "> " + url);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setFixedLengthStreamingMode(bytes.length);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded;charset=UTF-8");
            // post the request
            OutputStream out = conn.getOutputStream();
            out.write(body.getBytes());
            out.close();
            // handle the response
            int status = conn.getResponseCode();
            if (status != 200) {
                throw new IOException("Post failed with error code " + status);
            }
            else if(status == 200){
                Log.v(TAG, "Success");
            }
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_about:
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
