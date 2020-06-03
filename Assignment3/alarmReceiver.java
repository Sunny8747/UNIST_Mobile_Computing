package com.example.localarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class alarmReceiver extends BroadcastReceiver {
    Context context;
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"알람 리시버 작동", Toast.LENGTH_SHORT).show();
        this.context = context;
        Intent alarmIntent = new Intent("android.intent.action.sec");
        alarmIntent.setClass(context, WifiActivity.class);
        alarmIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.context.startActivity(alarmIntent);


    }
}
