package one.thing.well.gymtimekeeper.location;

import android.app.Service;
import android.content.Context;
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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import one.thing.well.gymtimekeeper.GymTimekeeperApplication;
import one.thing.well.gymtimekeeper.persist.InTheGymFileWriter;

public class GymTimekeeperLocationTrackingService extends Service
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleApiClient googleApiClient;

    private LocationRequest locationRequest;

    private InTheGymFileWriter fileWriter = new InTheGymFileWriter(GymTimekeeperApplication.getAppContext());

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
        //android.os.Process.killProcess(android.os.Process.myPid());
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
        FileOutputStream outputStream = null;
        try {
            writeInTheGymEvent(location);
        } catch (FileNotFoundException e) {
            closeTheOutputStream(outputStream);
        }
    }

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
            stopSelf();
        }
    }

    private void configureLocationTrackingRequest() {
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(20000);
        locationRequest.setFastestInterval(20000);
    }

    private void writeInTheGymEvent(Location location) throws FileNotFoundException {

        String TextAtFile = TextAtFile();

        if(TextAtFile != "Error") {
            FileOutputStream outputStream = openFileOutput("InTheGym", Context.MODE_PRIVATE);
            fileWriter.writeIsInTheGymEvent(outputStream, location, TextAtFile);
        }else{
            Toast.makeText(GymTimekeeperLocationTrackingService.this, "Error", Toast.LENGTH_SHORT).show();
        }

    }

    private void closeTheOutputStream(FileOutputStream outputStream) {
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }




    // Might need its own class as its used twice here and in main activity
    private String TextAtFile(){

        String Result  ;

        try{
            FileInputStream fis = getApplicationContext().openFileInput("InTheGym");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line,FullText= "";

            while ((line = bufferedReader.readLine()) != null) {
                FullText = FullText + line;
            }
            bufferedReader.close();
            isr.close();
            fis.close();

            Result = FullText;


        }catch (FileNotFoundException e) {
            e.printStackTrace();
            Result = "";
        } catch (IOException e) {
            e.printStackTrace();
            Result = "Error";
        }

        return Result;

    }
}
