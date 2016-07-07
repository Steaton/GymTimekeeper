package one.thing.well.gymtimekeeper.display.mapOfGym;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.yelp.clientlib.entities.SearchResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

import static com.google.android.gms.internal.zzir.runOnUiThread;

/**
 * Created by zeko on 01/07/2016.
 */
public class MarkerData {


    static Response<SearchResponse> Bussines;
    static LatLng ZoomArea;
    static GoogleMap googleMap;


    static   List<String> getBusinessURL(){

        List<String> BusinessURL = new ArrayList<String>();

        for(int i = 0 ; i <MarkerData.Bussines.body().businesses().size();i++){

            BusinessURL.add(i,Bussines.body().businesses().get(i).url());
        }
        return BusinessURL;
    }











    static   List<LatLng> getBusinessLocations(){



            List<LatLng> Locations = new ArrayList<LatLng>();

            for (int i = 0; i < MarkerData.Bussines.body().businesses().size(); i++) {

                Locations.add(i, new LatLng(Bussines.body().businesses().get(i).location().coordinate().latitude(), Bussines.body().businesses().get(i).location().coordinate().longitude()));

            }


            return Locations;


    }



    static List<String> getBusinessName(){


            List<String> BusinessNames = new ArrayList<String>() {
            };

            for (int i = 0; i < MarkerData.Bussines.body().businesses().size(); i++) {


                BusinessNames.add(i, Bussines.body().businesses().get(i).name());
            }

            return BusinessNames;

    }

}
