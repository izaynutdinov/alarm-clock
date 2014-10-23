package net.iskandar.alarmclock;

import android.util.Log;

/**
 * Created by iskandar on 10/21/14.
 */
public class Logger {

    private String tag;

    public Logger(String tag) {
        this.tag = tag;
    }

    public void d(String message){
        Log.d(tag, message);
    }

    public void e(String message, Throwable t){
        Log.e(tag, message, t);
    }




}
