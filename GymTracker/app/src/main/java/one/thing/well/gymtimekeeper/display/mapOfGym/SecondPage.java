package one.thing.well.gymtimekeeper.display.mapOfGym;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import one.thing.well.gymtimekeeper.GymTimekeeperApplication;
import one.thing.well.gymtimekeeper.R;

public class SecondPage extends android.support.v4.app.Fragment {

    MapView mMapView;
    GoogleMap googleMap;

    Button btnsearch;
    EditText editTextGymLocation;





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

        btnsearch = (Button) v.findViewById(R.id.btnSearch);
        editTextGymLocation = (EditText) v.findViewById(R.id.editTextgymLocation);

        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    GymSearch();
                }catch (Exception e){

                    e.printStackTrace();
                }
            }
        });

        mapSetup(v, savedInstanceState);

        return v;
    }


    private void GymSearch() throws IOException, InterruptedException {

        googleMap.clear();

        MarkerData.googleMap = googleMap;

        Thread t = new Thread(new Markers(editTextGymLocation.getText().toString()));

        t.start();







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
