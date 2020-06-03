package com.example.activitprediction;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    double threshold=0.1;
    TextView tv, tvLatency;
    double time=8;
    int samplingPeriodUs=50000;

    private LinkedList<float[]> Adata=new LinkedList<float[]>();
    SensorManager SA; //accelometer

    SensorEventListener sL = new SensorEventListener() {
        @Override
        public void onAccuracyChanged(Sensor arg0, int arg1) {
        }
        @Override
        public void onSensorChanged(SensorEvent se) {
            switch(se.sensor.getType()){
                case Sensor.TYPE_ACCELEROMETER :
                    float[] temp1={se.values[0],se.values[1],se.values[2]};
                    Adata.add(temp1);
                    if(Adata.size()>time*1000000/samplingPeriodUs){
                        Adata.remove();
                    }
            }
        }
    };

    int jogCount = 0;
    int walkCount = 0;
    int sitCount = 0;
    int standCount = 0;
    double tempmag = 0;
    int stairCount = 0;
    int upCount = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SA = (SensorManager)getSystemService(SENSOR_SERVICE);
        SA.registerListener(sL, SA.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),samplingPeriodUs);

        tv=findViewById(R.id.textResult);
        tvLatency=findViewById(R.id.textView2);
        tv.setText(R.string.defo);
        Button bt=findViewById(R.id.button);
        bt.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Long LTime = System.nanoTime();
                double magnitude = 0;
                jogCount = 0;
                sitCount = 0;
                standCount = 0;
                walkCount = 0;
                upCount = 0;
                stairCount = 0;
                for (int i = 0; i < 80; i++) {
                    float[] temp = Adata.get(i);
                    tempmag = Math.sqrt(temp[0] * temp[0] + temp[1] * temp[1] + temp[2] * temp[2]);
                    if(i>59) {
                        magnitude += tempmag - 9.8;
                    }
                    if(tempmag > 40) { //jog
                        jogCount++;
                    }
                    else if(tempmag > 20) { //smaller than jog bigger than stair
                        walkCount++;
                    }
                    else if(tempmag > 12 && tempmag < 15) { //smaller than walk
                        stairCount++;
                    }
                    else if(tempmag < 3.5 && tempmag > 1.5) { // up has leg free fall sequence
                        upCount = 1;
                    }
                    else {
                            if(temp[1] > temp[2]) { //accelerometer level change
                                sitCount = 1;
                                standCount = 0;
                            }
                            else {
                                sitCount=0;
                                standCount=1;
                            }
                    }
                } //for loop and
                magnitude = magnitude / 20;
                if(jogCount > 6) {
                    tv.setText("Jogging\n");
                }
                else if(walkCount > 5) {
                    tv.setText("Walking\n");
                }
                else if(stairCount > 8) {
                    if(upCount == 1) {
                        tv.setText("UpStairs");
                    }
                    else {
                        tv.setText("DownStiars");
                    }
                }
                else if (magnitude < threshold && magnitude > - threshold) {
                    tv.setText("No move\n");
                }
                else {
                    if(sitCount == 1) {
                        tv.setText("Sitting\n");
                    }
                    else if(standCount == 1) {
                        tv.setText("Standing\n");
                    }
                    else {
                        tv.setText("UnKnown\n");
                    }
                }
                LTime = (System.nanoTime() - LTime)/1000000;
                tvLatency.setText(LTime.toString() + "ms"); //no problem
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_setting) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
