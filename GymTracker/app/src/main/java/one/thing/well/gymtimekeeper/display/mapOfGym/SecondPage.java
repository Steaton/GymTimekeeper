package one.thing.well.gymtimekeeper.display.mapOfGym;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;

import java.io.IOException;

import one.thing.well.gymtimekeeper.GymTimekeeperApplication;
import one.thing.well.gymtimekeeper.R;
import one.thing.well.gymtimekeeper.datastore.FileConstants;
import one.thing.well.gymtimekeeper.datastore.locationfix.LocationFixFileWriter;

public class SecondPage extends android.support.v4.app.Fragment {

    MapView mMapView;
    GoogleMap googleMap;

    Button btnSetGym;
    ImageView refreshMapLocation;






    public  static SecondPage newInstance(){

        SecondPage fragment = new SecondPage();

        return fragment;
    }


    public SecondPage(){

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View v = inflater.inflate(R.layout.second_page, container,
                false);

        btnSetGym = (Button) v.findViewById(R.id.btnSetGym);
        refreshMapLocation = (ImageView) v.findViewById(R.id.locationForMapUpdate);

        refreshMapLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    GymSearch();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });


        btnSetGym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationReceiverForMap locationReceiverForMap = new LocationReceiverForMap(GymTimekeeperApplication.getAppContext());
                LocationFixFileWriter fixFileWriter = new LocationFixFileWriter(GymTimekeeperApplication.getAppContext());
                fixFileWriter.deleteFile(FileConstants.LOCATION_FIX_FILENAME);
                fixFileWriter.writeFileEntry(locationReceiverForMap.getLatitude(),locationReceiverForMap.getLongitude(),FileConstants.LOCATION_FIX_FILENAME);
                Toast.makeText(GymTimekeeperApplication.getAppContext(),"Your gym location has been saved",Toast.LENGTH_SHORT).show();
            }
        });






        mapSetup(v, savedInstanceState);

        return v;
    }


    private void GymSearch() throws IOException, InterruptedException {

        googleMap.clear();

        SetupMap setupMap = new SetupMap(googleMap);

        setupMap.setlocation();

    }




    private void mapSetup(View v, Bundle savedInstanceState){


        mMapView = (MapView) v.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();// needed to get the map to display immediately

        try {
            MapsInitializer.initialize(GymTimekeeperApplication.getAppContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        googleMap = mMapView.getMap();
        // latitude and longitude
    }



    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }


    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}
