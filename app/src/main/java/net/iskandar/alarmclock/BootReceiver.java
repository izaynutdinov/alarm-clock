package net.iskandar.alarmclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class BootReceiver extends BroadcastReceiver {
    public BootReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("BootReceiver", "onReceive");
        Toast.makeText(context, "BootReceiver.onReceive", Toast.LENGTH_LONG);
        AlarmModel.instance(context).setAlarm();
    }
}
