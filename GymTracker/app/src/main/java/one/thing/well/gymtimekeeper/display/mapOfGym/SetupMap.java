package one.thing.well.gymtimekeeper.display.mapOfGym;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import one.thing.well.gymtimekeeper.GymTimekeeperApplication;
import one.thing.well.gymtimekeeper.locationservice.AbstractLocationService;
import one.thing.well.gymtimekeeper.locationservice.LocationTrackingService;

/**
 * Created by zeko on 09/07/2016.
 */
public class SetupMap {

    public GoogleMap googleMap ;




    public SetupMap(GoogleMap map){
        googleMap = map;
    }

    public void setlocation(){

        LatLng location = getlocation();

        MarkerOptions markerOptions = new MarkerOptions().position(location);

        googleMap.addMarker(markerOptions);



        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(location)
                .zoom(15)
                .bearing(90)
                .tilt(30)
                .build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


    }


    private LatLng getlocation(){

        LocationReceiverForMap gps = new LocationReceiverForMap(GymTimekeeperApplication.getAppContext());
        gps.getLocation();
        if(gps.canGetLocation())
        {
            return new LatLng(gps.getLatitude(),gps.getLongitude());

        }else  {
         return null;
        }
    }





}
