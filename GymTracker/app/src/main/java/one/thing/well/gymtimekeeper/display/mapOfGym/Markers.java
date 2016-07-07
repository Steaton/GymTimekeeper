package one.thing.well.gymtimekeeper.display.mapOfGym;

import android.location.Address;
import android.location.Geocoder;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.yelp.clientlib.connection.YelpAPI;
import com.yelp.clientlib.connection.YelpAPIFactory;
import com.yelp.clientlib.entities.Business;
import com.yelp.clientlib.entities.SearchResponse;
import com.yelp.clientlib.entities.options.BoundingBoxOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import one.thing.well.gymtimekeeper.GymTimekeeperApplication;
import retrofit2.Call;
import retrofit2.Response;

import static com.google.android.gms.internal.zzir.runOnUiThread;

/**
 * Created by zeko on 30/06/2016.
 */
public class Markers implements Runnable {

    private  Response<SearchResponse> response ;
    private  Call<SearchResponse> call;
    private String UserInput;

    public Markers(String LocationName){

        UserInput = LocationName;
    }


     public void AddMarker(String UserInput) throws IOException, InterruptedException {

       try {
           getBusinessInfo(getCoordinates(UserInput));
       }catch (Exception e){
           e.printStackTrace();
       }
         addthemarkersToTheMap();


    }




    public void addthemarkersToTheMap(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if( MarkerData.Bussines != null){
                    for (int i = 0; i < MarkerData.Bussines.body().businesses().size(); i++) {


                        MarkerOptions marker = new MarkerOptions().position(
                                MarkerData.getBusinessLocations().get(i)).title(MarkerData.getBusinessName().get(i));

                        //Changing marker icon
                        marker.icon(BitmapDescriptorFactory
                                .defaultMarker(BitmapDescriptorFactory.HUE_ROSE));


                        // adding marker
                        MarkerData.googleMap.addMarker(marker);

                    }
                }else{



                    MarkerOptions marker = new MarkerOptions().position(
                          MarkerData.ZoomArea).title("Gym").draggable(true);

                    //Changing marker icon
                    marker.icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_ROSE));


                    // adding marker
                    MarkerData.googleMap.addMarker(marker);



                    MarkerData.googleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                        @Override
                        public void onMarkerDragStart(Marker marker) {

                        }

                        @Override
                        public void onMarkerDrag(Marker marker) {

                        }

                        @Override
                        public void onMarkerDragEnd(Marker marker) {
                            Toast.makeText(GymTimekeeperApplication.getAppContext(),marker.getPosition().toString(),Toast.LENGTH_SHORT).show();
                        }
                    });



                }


                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(MarkerData.ZoomArea).zoom(12).build();
                MarkerData.googleMap.animateCamera(CameraUpdateFactory
                        .newCameraPosition(cameraPosition));
            }

        });



    }





     public LatLng getCoordinates(String userInput) throws IOException {

        Geocoder geocoder = new Geocoder(GymTimekeeperApplication.getAppContext());
        List<Address> addresses = geocoder.getFromLocationName(userInput, 1);
        LatLng Geocoordinates = new LatLng(addresses.get(0).getLatitude(),addresses.get(0).getLongitude());

        return Geocoordinates;
    }


     public void getBusinessInfo (LatLng locationInfo) throws IOException, InterruptedException {


        MarkerData.ZoomArea = locationInfo;
        double alph = 1.000000;
        double beta = 1.000000;


        YelpAPIFactory apiFactory = new YelpAPIFactory("Zk6fS02q1WkHY-rSh-TAQQ", "tyIpCOj6HErhhkXbg9q77UtO0Qk", "VBTKoipnO37v3sXuU2rQ6k2Ftgk9fpde", "zGV1xkn0CpRD1Yg8-cuLTYqew4M");
        YelpAPI yelpAPI = apiFactory.createAPI();


        Map<String, String> params = new HashMap<>();

        // general params
        params.put("term", "gym");
        params.put("limit", "10");

        // locale params
        params.put("lang", "en");

        BoundingBoxOptions bounds = BoundingBoxOptions.builder()
                .swLatitude(locationInfo.latitude +alph)
                .swLongitude(locationInfo.longitude -beta)
                .neLatitude(locationInfo.latitude -alph)
                .neLongitude(locationInfo.longitude +beta).build();

        call = yelpAPI.search(bounds, params);

         response = call.execute();

         MarkerData.Bussines = response;


    }


    @Override
    public void run() {
        try {
            AddMarker(UserInput);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
