package one.thing.well.gymtimekeeper.locationservice;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import one.thing.well.gymtimekeeper.GymTimekeeperApplication;

public abstract class AbstractLocationService extends Service
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {


    private GoogleApiClient googleApiClient;

    private LocationRequest locationRequest;

    @Override
    public void onCreate() {
        connectGoogleApi();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onConnected(Bundle bundle) {
        System.out.println("Connected: " + this);
        setupLocationTracking();
    }

    @Override
    public void onConnectionSuspended(int i) {
        System.out.println("Connection Suspended Stopping " + this);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        System.out.println("Connection Failed Stopping " + this);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    @Override
    public void onLocationChanged(Location location) {
        System.out.println("LocationServiceReport: " + location.getLatitude() + "," + location.getLongitude() + "," + location.getAccuracy());
        processLocationChangedEvent(location);
    }

    protected abstract void processLocationChangedEvent(Location location);

    private void connectGoogleApi() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
    }

    private void setupLocationTracking() {
        try {
            configureLocationTrackingRequest();
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        } catch (SecurityException f) {
            f.printStackTrace();
        }
    }

    private void configureLocationTrackingRequest() {
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(60000);
        locationRequest.setFastestInterval(60000);
    }
}
