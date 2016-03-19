package com.example.zeko.gymtracker;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class GymTimekeeperLocationTrackingService extends Service
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleApiClient googleApiClient;

    private LocationRequest locationRequest;

    public GymTimekeeperLocationTrackingService() {
        super();
    }

    @Override
    public void onCreate() {
        connectGoogleApi();
        System.out.println("This is the location:  XXXX");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onConnected(Bundle bundle) {
        setupLocationTracking();
        System.out.println("This is the location:  XXXXX");
    }

    @Override
    public void onConnectionSuspended(int i) {
        System.out.println("This is the location: Connection Suspended Stopping");
                stopSelf();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        System.out.println("This is the location: Connection Failed Stopping");
        stopSelf();
    }

    @Override
    public void onLocationChanged(Location location) {
        System.out.println("This is the location:  "
                + location.getLatitude() + ", "
                + location.getLongitude() + ", "
                + location.getAccuracy());
    }

    private void connectGoogleApi() {
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        googleApiClient.connect();
    }

    private void setupLocationTracking() {
        try {
            locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(10000);
            locationRequest.setFastestInterval(2000);
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);

        } catch (SecurityException f) {
            f.printStackTrace();
        }
    }
}
