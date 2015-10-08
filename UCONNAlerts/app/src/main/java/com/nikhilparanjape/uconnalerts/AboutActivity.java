package com.nikhilparanjape.uconnalerts;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Notification;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;



import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class AboutActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ActionBar bar = getActionBar();
        bar.setDisplayShowHomeEnabled(false);
        bar.setTitle("About");

        GcmMessageHandler msg = new GcmMessageHandler();
        String mes = null;
        TextView gcmView;
        gcmView = (TextView) findViewById(R.id.gcmView);
        gcmView.setText(msg.getMsg(mes));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
