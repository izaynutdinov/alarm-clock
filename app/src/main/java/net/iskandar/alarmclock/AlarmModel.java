package net.iskandar.alarmclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Calendar;

/**
 * Created by iskandar on 9/8/14.
 */
public class AlarmModel {

    public static final String ALARM_SHARED_PREFS_NAME = "AlarmPrefs";
    public static final String HOURS_PREF_NAME = "HOURS";
    public static final String MINUTES_PREF_NAME = "MINUTES";
    private static final String NEXT_ALARM_PREF_NAME = "NEXT_ALARM";
    private static final int DEFAULT_HOURS = 7;
    private static final int DEFAULT_MINUTES = 0;

    private SharedPreferences prefs;
    private Context context;
    private PendingIntent pi;

    private int hours = DEFAULT_HOURS;
    private int minutes = DEFAULT_MINUTES;
    private long nextAlarm = 0;

    private AlarmModel(Context context){
        this.context = context;
        prefs = context.getSharedPreferences(ALARM_SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        hours = prefs.getInt(HOURS_PREF_NAME, DEFAULT_HOURS);
        minutes = prefs.getInt(MINUTES_PREF_NAME, DEFAULT_MINUTES);
        nextAlarm = prefs.getLong(NEXT_ALARM_PREF_NAME, -1);
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public long getNextAlarm() {
        return nextAlarm;
    }

    public void setTime(int hours, int minutes) {
        this.hours = hours;
        this.minutes = minutes;

        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(HOURS_PREF_NAME, hours);
        editor.putInt(MINUTES_PREF_NAME, minutes);
        editor.commit();

        setAlarm();
    }

    private PendingIntent createPendingIntent(){
        Intent i = PlayActivity.newPlayIntent();
        PendingIntent pi = PendingIntent.getActivity(context, 0, i, 0);
        return pi;
    }

    public void setRepeat(){
        Calendar cal = Calendar.getInstance();
        int hours = cal.get(Calendar.HOUR_OF_DAY);
        int minutes = cal.get(Calendar.MINUTE) + (hours * 60) + 5;
        hours = minutes / 60;
        minutes = minutes % 60;
        if(hours > 23)
        {
            hours -= 24;
        }
        setAlarm(hours, minutes);
    }

    public void setAlarm(){
        setAlarm(this.hours, this.minutes);
    }

    private void setAlarm(int hours, int minutes){
        Log.d("AlarmModel", "setAlarm " + hours + ":" + minutes);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Calendar cal = Calendar.getInstance();
        Log.d("AlarmModel", "Current time is: " + cal.getTime());
        int milliseconds  = cal.get(Calendar.HOUR_OF_DAY) * (60 * 60 * 1000);
        milliseconds += cal.get(Calendar.MINUTE) * 60 * 1000;
        milliseconds += cal.get(Calendar.SECOND) * 1000;
        milliseconds += cal.get(Calendar.MILLISECOND);

        if(milliseconds > (((hours * 60) + minutes)) * 60 * 1000){
            Log.d("AlarmModel", "adding 1 day to current time");
            cal.add(Calendar.DAY_OF_YEAR, 1);
            Log.d("AlarmModel", "After add time is: " + cal.getTime());
        }
        cal.set(Calendar.HOUR_OF_DAY, hours);
        cal.set(Calendar.MINUTE, minutes);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        if(pi != null)
            alarmManager.cancel(pi);

        pi = createPendingIntent();
        Log.d("AlarmModel", "Setting alarm to " + cal.getTime());
        nextAlarm = cal.getTimeInMillis();
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(NEXT_ALARM_PREF_NAME, nextAlarm);
        editor.commit();
        alarmManager.set(AlarmManager.RTC_WAKEUP, nextAlarm, pi);
    }

    private static AlarmModel instance;

    public synchronized static AlarmModel instance(Context context){
        if(instance == null){
            instance = new AlarmModel(context.getApplicationContext());
        }
        return instance;
    }

}
