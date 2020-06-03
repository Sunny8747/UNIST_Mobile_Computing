package com.example.localarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class WifiActivity extends AppCompatActivity {
    WifiManager wifiMan;
    List<ScanResult> results;
    String wifi_log;
    int datacount = 0;
    int jogCount = 0;
    int samplingPeriodUs=50000;
    private float[] accelerometerReading = new float[3];
    long startTime;
    long startTime2;
    SensorManager SM;
    SensorEventListener sL = new SensorEventListener() {
        @Override
        public void onAccuracyChanged(Sensor arg0, int arg1) {
        }
        @Override
        public void onSensorChanged(SensorEvent se) {
            switch(se.sensor.getType()){
                case Sensor.TYPE_ACCELEROMETER :
                    float[] temp1 = {se.values[0], se.values[1], se.values[2]};
                    System.arraycopy(se.values, 0, accelerometerReading,0, accelerometerReading.length);
                    double tempmag = Math.sqrt(temp1[0] * temp1[0] + temp1[1] * temp1[1] + temp1[2] * temp1[2]);
                    long currentTime = System.currentTimeMillis()/1000 - startTime;
                    if(currentTime > 0) {
                        if (tempmag > 49.8f) {
                            jogCount++;
                            break;
                        }
                        if (jogCount > 6) {
                            Toast.makeText(getApplicationContext(), "Running makes 1min later", Toast.LENGTH_SHORT).show();
                            Toast.makeText(getApplicationContext(), "Running makes 1min later", Toast.LENGTH_SHORT).show();
                            Toast.makeText(getApplicationContext(), "Running makes 1min later", Toast.LENGTH_SHORT).show();
                            Toast.makeText(getApplicationContext(), "Running makes 1min later", Toast.LENGTH_SHORT).show();
                            Toast.makeText(getApplicationContext(), "Running makes 1min later", Toast.LENGTH_SHORT).show();
                            Toast.makeText(getApplicationContext(), "Running makes 1min later", Toast.LENGTH_SHORT).show();
                            startTime += 60; //add 1 min
                            jogCount = 0;
                        }
                    }
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        startTime = System.currentTimeMillis()/1000;
        startTime2 = startTime;
        SM = (SensorManager)getSystemService(SENSOR_SERVICE);
        SM.registerListener(sL, SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),samplingPeriodUs);

        wifiMan = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        registerReceiver(wifiReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        scanning();
    }
    int checkbit = 0;
    private BroadcastReceiver wifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            long currentTime = System.currentTimeMillis()/1000 - startTime;
            final String action = intent.getAction();
            if(currentTime > 50){
                if(action.equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)){
                    results = wifiMan.getScanResults();
                    //Toast.makeText(context,"Alarm is on", Toast.LENGTH_SHORT).show();
                    String test = "90:94:e4:fd:eb:bc";
                    for(ScanResult result : results) {
                        String tempS = result.BSSID;
                        //Toast.makeText(context,"inside for loop", Toast.LENGTH_SHORT).show();
                        if (tempS.equals(test)) {//alarm on
                            final Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                            Toast.makeText(context, "Alarm is on", Toast.LENGTH_SHORT).show();
                            Intent Blackboard = getPackageManager().getLaunchIntentForPackage("com.blackboard.android.bbstudent");
                            vibrator.vibrate(1000);
                            if (Blackboard != null) {
                                // We found the activity now start the activity
                                Blackboard.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(Blackboard);
                            } else {
                                Toast.makeText(context, "BlackBoard is null", Toast.LENGTH_SHORT).show();
                            }
                            finish();
                        } else {
                            //Toast.makeText(context,"Not equal", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }else if(action.equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)){
                    context.sendBroadcast(new Intent("wifi.ON_NETWORK_STATE_CHANGED"));
                }
            }

        }
    };
    int scanCount = 0;
    private void scanning() {
        final Handler handler = new Handler();
        final int delay = 5000; // milliseconds
        long currentTime = System.currentTimeMillis()/1000 - startTime;
        if(currentTime > 50) {//1min
            boolean success = wifiMan.startScan();
            scanCount++;
            if(!success) {
                System.out.println("Failure");
                Toast.makeText(this,"scan fail", Toast.LENGTH_SHORT).show();
            }
        }
        if(scanCount < 5) {
            handler.postDelayed(new Runnable() {
                public void run() {
                    scanning();
                    //handler.postDelayed(this, delay);
                }
            }, delay);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(wifiReceiver);
    }
}
