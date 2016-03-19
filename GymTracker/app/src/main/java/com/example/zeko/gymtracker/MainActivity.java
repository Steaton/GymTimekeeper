package com.example.zeko.gymtracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.FileOutputStream;


public class MainActivity extends AppCompatActivity {

    Button btnUpdate;
    TextView lblLat;
    TextView lblLon;
    TextView lblTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialisePanel(savedInstanceState);
        setupButton();
        launchLocationIntentService();
    }

    private void initialisePanel(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        lblLat = (TextView) findViewById(R.id.lblLat);
        lblLon = (TextView) findViewById(R.id.lblLon);
        lblTime = (TextView) findViewById(R.id.lblTime);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
    }

    private void setupButton() {
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent locationService = new Intent(MainActivity.this, GymTimekeeperLocationTrackingService.class);
                stopService(locationService);
            }
        });
    }

    private void launchLocationIntentService() {
        Intent locationService = new Intent(this, GymTimekeeperLocationTrackingService.class);
        startService(locationService);
    }
}
