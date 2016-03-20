package com.example.zeko.gymtracker;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Timer;
import java.util.TimerTask;


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
        AutoMater();

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

                InfoReader();


                Intent locationService = new Intent(MainActivity.this, GymTimekeeperLocationTrackingService.class);
                stopService(locationService);


            }
        });
    }

    private void launchLocationIntentService() {
        Intent locationService = new Intent(this, GymTimekeeperLocationTrackingService.class);
        startService(locationService);
    }


     public void InfoReader(){


        lblLat.setText("lat");
        lblLon.setText("Lon");
        lblTime.setText("Time");

        try {
        FileInputStream fis = getApplicationContext().openFileInput("InTheGym");
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader bufferedReader = new BufferedReader(isr);
        StringBuilder sb = new StringBuilder();
        String line,i1 = "",i2 = "",i3 = "";
        while ((line = bufferedReader.readLine()) != null) {

            String[] Items = line.split("#");

            for(int i = 0;i< Items.length;i++){

                String[] Singles = Items[i].split(",");

                i1 = i1 + "\n" + Singles[0].replace("#","");
                i2 = i2 + "\n" + Singles[1];
                i3 = i3 + "\n" + Singles[2];


            }




        }

            lblTime.setText(lblTime.getText() + "\n" + i1);
            lblLat.setText(lblLat.getText() + "\n" + i2);
            lblLon.setText(lblLon.getText() + "\n" + i3);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }

     }


    void AutoMater(){


        Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                InfoReader();
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        t.start();
    }

}



