package com.example.zeko.gymtracker;


import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;


public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    Button btnUpdate;
    TextView lblLat;
    TextView lblLon;
    TextView lblTime;
    LocationRequest mLocationRequest ;
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


        try{

            mLocationRequest = LocationRequest.create();
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            mLocationRequest.setInterval(500);
            mLocationRequest.setFastestInterval(100);
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }catch (SecurityException f)
        {f.printStackTrace();}
        

    }



    protected void startLocationUpdates() {

        try{LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest,this
        );

        }
        catch (SecurityException f)
        {f.printStackTrace();}

    }







    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

        Location mLastLocation = location;

        lblLat.setText(Double.toString( mLastLocation.getLatitude()));
        lblLon.setText(Double.toString( mLastLocation.getLongitude()));


    }


}
