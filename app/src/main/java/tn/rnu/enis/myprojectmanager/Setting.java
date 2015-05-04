package tn.rnu.enis.myprojectmanager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.widget.Toast;

import java.util.Calendar;

import tn.rnu.enis.myprojectmanager.data.Contract;
import tn.rnu.enis.myprojectmanager.services.Notify;

/**
 * Created by Mohamed on 05/05/2015.
 */
public class Setting extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String KEY_NOTIF = "pref_sync";

        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.setting);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        if(s.equals(KEY_NOTIF)){
            boolean b = sharedPreferences.getBoolean(s, false) ;
            Intent intent = new Intent(this, Notify.class);
            Bundle extras = new Bundle();
            extras.putString(Contract.NAME, Contract.Status.WAITING);
            intent.putExtras(extras);
            PendingIntent pintent = PendingIntent.getService(this, 0, intent, 0);
            if(b){
                AlarmManager alarm = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                alarm.setRepeating(AlarmManager.RTC, Calendar.getInstance().getTimeInMillis() , AlarmManager.INTERVAL_DAY, pintent);
                Toast.makeText(this,"Notification Activated",Toast.LENGTH_SHORT).show();
            }else
            {
                pintent.cancel();
                Toast.makeText(this,"Notification Stopped",Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }
}
