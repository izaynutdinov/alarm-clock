package net.iskandar.alarmclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {

    private static final String ACTION = "net.iskandar.ALARM_FIRED";

    public static Intent newIntent(){
        return new Intent(ACTION);
    }

    public AlarmReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("AlarmReceiver",  "onReceive(intent.action=" + intent.getAction());
        if(ACTION.equals(intent.getAction())){
            context.startActivity(new Intent());
        }
    }

}
