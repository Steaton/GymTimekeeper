package com.example.zeko.gymtracker;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    TextView lblLat;
    TextView lblLon;
    TextView lblTime;
    Button btnUpdate;
    GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        lblLat = (TextView) findViewById(R.id.lblLat);
        lblLon = (TextView) findViewById(R.id.lblLon);
        lblTime = (TextView) findViewById(R.id.lblTime);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);


        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        mGoogleApiClient.connect();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

    }


    @Override
    public void onConnected(Bundle connectionHint) {
        try {
            Location mLastLocation;
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
            if (mLastLocation != null) {
                lblLat.setText(String.valueOf(mLastLocation.getLatitude()));
                lblLon.setText(String.valueOf(mLastLocation.getLongitude()));
            }



        }catch (SecurityException f){

            f.printStackTrace();
        }
    }







    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
