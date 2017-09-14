package com.example.andrew.bluetoothmotorcycletiltmeter;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class TiltMeter extends Activity implements SensorEventListener{

    private static final String TAG = "TiltMeter";
    private static final int DEG_PER_BAR = 3;

    private TextView meterBars[] = new TextView[36];
    private TextView angle;

    private SensorManager mSensorManager;

    private float mGravity[];
    private float mGeomagnetic[];


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tilt_meter);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        initialize();

    }

    private void initialize(){

        int barId;

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE); //Initialize sensors
        angle = findViewById(R.id.angle);

        for(int i = 0; i < 36; i++){

            barId = getResources().getIdentifier("t" + (i + 1), "id", getPackageName()); //Get ids for all TextViews
            meterBars[i] = findViewById(barId); //Initialize the TextViews for use
            meterBars[i].setText("");
            meterBars[i].setBackgroundColor(Color.CYAN);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();

        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Don't receive any more updates from either sensor.
        mSensorManager.unregisterListener(this);
    }

    // Get readings from accelerometer and magnetometer. To simplify calculations,
    // consider storing these readings as unit vectors.
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
            mGravity = event.values;
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
            mGeomagnetic = event.values;
        if (mGravity != null && mGeomagnetic != null) {
            float R[] = new float[9];
            float I[] = new float[9];
            boolean success = SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic);
            if (success) {
                float orientation[] = new float[3];
                SensorManager.getOrientation(R, orientation);

                int degreeX = (int) Math.round(Math.toDegrees(orientation[2]));
                int degreeY = (int) Math.round(Math.toDegrees(orientation[1]));
                int degreeZ = (int) Math.round(Math.toDegrees(orientation[0]));

                //Log.d(TAG, degreeX + " " + degreeY + " " + degreeZ);

                colorMeter(degreeY);

                angle.setText("Pos: " + degreeX + " " + degreeY + " " + degreeZ);

                //tX.setText(Double.toString(Math.round(Math.toDegrees(orientation[2])))); //Side Orientation angle
                //tY.setText(Double.toString(Math.round(Math.toDegrees(orientation[1])))); //Back, Forth, Side, Side
                //tZ.setText(Double.toString(Math.round(Math.toDegrees(orientation[0])))); //North, South, East West
            }
        }
    }

    private void colorMeter(int angle){
        int barNum = (Math.abs(angle) / DEG_PER_BAR);

        if(angle < 0) {

            ColorDrawable cd = (ColorDrawable) meterBars[16].getBackground();
            int colorCode = cd.getColor();

            if(colorCode == Color.RED){
                for (int i = 16; i >= 0; i--) {
                    meterBars[i].setBackgroundColor(Color.CYAN);

                }
                Log.d(TAG, "Left");
            }

            for(int i = 18; i < 36; i++){

                if(i <= barNum + 18){
                    meterBars[i].setBackgroundColor(Color.RED);
                }else{
                    meterBars[i].setBackgroundColor(Color.CYAN);
                }
            }
        }else{
            ColorDrawable cd = (ColorDrawable) meterBars[19].getBackground();
            int colorCode = cd.getColor();

            if(colorCode == Color.RED){
                for (int i = 19; i < 36; i++) {
                    meterBars[i].setBackgroundColor(Color.CYAN);
                }
                Log.d(TAG, "Right");
            }

            for (int i = 17; i >= 0; i--) {

                if(i >= 17 - barNum){
                    meterBars[i].setBackgroundColor(Color.RED);
                }else{
                    meterBars[i].setBackgroundColor(Color.CYAN);
                }
            }
        }




    }


}
