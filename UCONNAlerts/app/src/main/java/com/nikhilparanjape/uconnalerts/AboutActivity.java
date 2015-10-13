package com.nikhilparanjape.uconnalerts;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;



import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


public class AboutActivity extends Activity {
    String stam = "stamford";
    String stor = "storrs";
    static String choice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ActionBar bar = getActionBar();
        bar.setDisplayShowHomeEnabled(false);
        bar.setTitle("About");

        RadioGroup rg = (RadioGroup) findViewById(R.id.campusGroup);
        SharedPreferences sp = getSharedPreferences("setting", MODE_PRIVATE);

        if(sp.getInt("checked", 2131558509) != 2131558509) {

            rg.check(sp.getInt("checked", 0));
        }
        else if(sp.getInt("checked", 2131558510) != 2131558510){
            rg.check(sp.getInt("checked", 1));
        }
    }
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?

        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_storrs:
                if (checked)

                    choice = "storrs";
                    break;
            case R.id.radio_stamford:
                if (checked)
                    choice = "stamford";
                    break;
        }
    }

    public static String getCampus(){
            return choice;
    }
    public String stamChoice(){
        return stam;
    }
    public String storChoice(){
        return stor;
    }
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        RadioGroup rg = (RadioGroup) findViewById(R.id.campusGroup);
        int id = rg.getCheckedRadioButtonId();
        SharedPreferences sp = getSharedPreferences("setting", MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        String ed = Integer.toString(id);
        Log.i("RadioID", ed);
        e.putInt("checked", id);
        e.commit();
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
