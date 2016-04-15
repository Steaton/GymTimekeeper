package one.thing.well.gymtimekeeper.display;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import one.thing.well.gymtimekeeper.R;
import one.thing.well.gymtimekeeper.datastore.locationevent.LocationEventFile;

public class FirstPage extends android.support.v4.app.Fragment {

    private LocationEventFile fileReaderForStackViewFragment;
    private ListView TodayListView;
    private ArrayAdapter<String> ArrayAdapterForToday;

    ArrayList<String> mylist = new ArrayList<>();

    /**
     * A placeholder fragment containing a simple view.
     */

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static FirstPage newInstance() {
        FirstPage fragment = new FirstPage();
        return fragment;
    }

    public FirstPage() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.first_page, container, false);
        return rootView;
    }
}





 /*   private void displayGymLocationEventsForTheFragment(){

        try {


            mylist.clear();

            LocationEventFile file = (LocationEventFile) fileReaderForStackViewFragment.getLocationEventList();

            mylist.add(file.buildDateList() + " " + file.buildLatitudeList() + " " + file.buildLongitudeList());

            ArrayAdapterForToday.notifyDataSetChanged();


        }catch (Exception e){
            e.printStackTrace();
        }


    }*/



