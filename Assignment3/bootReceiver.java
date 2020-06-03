package com.example.localarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;
public class bootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Objects.equals(intent.getAction(), "android.intent.action.BOOT_COMPLETED")) {
            Intent alarmIntent = new Intent(context, alarmReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            SharedPreferences sharedPreferences = context.getSharedPreferences("daily alarm", MODE_PRIVATE);
            long mill = sharedPreferences.getLong("nextTime", Calendar.getInstance().getTimeInMillis());
            Calendar current_calendar = Calendar.getInstance();
            Calendar nextTime = new GregorianCalendar();
            nextTime.setTimeInMillis(sharedPreferences.getLong("nextTime", mill));
            if (current_calendar.after(nextTime)) {
                nextTime.add(Calendar.DATE, 1);
            }
            Toast.makeText(context.getApplicationContext(),"재부팅후 알람세팅완료", Toast.LENGTH_SHORT).show();
            if (alarmManager != null) {
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, nextTime.getTimeInMillis(),AlarmManager.INTERVAL_DAY, pendingIntent);
                if(Build.VERSION.SDK_INT >= 23) {
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, nextTime.getTimeInMillis(), pendingIntent);
                }
            }
        }
    }
}
