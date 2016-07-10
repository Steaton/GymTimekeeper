package one.thing.well.gymtimekeeper.locationservice;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
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

    Location location;
    double latitude;
    double longitude;

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;

    private static final long MIN_TIME_BW_UPDATES = 60000;

    protected LocationManager locationManager;
    boolean isGPSEnabled = false;


    boolean isNetworkEnabled = false;

    boolean canGetLocation = false;


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




    public Location getLocation() {
        try {
            locationManager = (LocationManager) GymTimekeeperApplication.getAppContext()
                    .getSystemService(LOCATION_SERVICE);
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
            } else {
                this.canGetLocation = true;
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, (android.location.LocationListener) this);
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, (android.location.LocationListener) this);
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }


    public void stopUsingGPS(){
        if(locationManager != null){
            locationManager.removeUpdates((android.location.LocationListener) AbstractLocationService.this);
        }
    }



    public double getLatitude(){
        if(location != null){
            latitude = location.getLatitude();
        }
        return latitude;
    }

    public double getLongitude(){
        if(location != null){
            longitude = location.getLongitude();
        }
        return longitude;
    }


    public boolean canGetLocation() {
        return this.canGetLocation;
    }


}
