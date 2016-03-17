package com.example.zeko.gymtracker;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.google.android.gms.location.LocationRequest;

public class LocationGetter extends Service {
    public LocationGetter() {


    }


    protected void createLocationRequest() {

    }



    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
