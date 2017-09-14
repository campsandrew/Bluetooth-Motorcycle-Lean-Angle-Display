package com.example.andrew.bluetoothmotorcycletiltmeter;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /* Called when To Meter button is clicked */
    public void toMeter(View view){
        Intent intent = new Intent(this, TiltMeter.class);
        startActivity(intent);
    }
}
