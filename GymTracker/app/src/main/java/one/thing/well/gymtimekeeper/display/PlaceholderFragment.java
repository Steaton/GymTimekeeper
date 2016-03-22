package one.thing.well.gymtimekeeper.display;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.R.layout;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import one.thing.well.gymtimekeeper.GymTimekeeperApplication;
import one.thing.well.gymtimekeeper.R;
import one.thing.well.gymtimekeeper.datastore.locationevent.LocationEventFile;
import one.thing.well.gymtimekeeper.datastore.locationevent.LocationEventFile;


import static com.google.android.gms.internal.zzir.runOnUiThread;


/**
 * Created by zeko on 20/03/2016.
 */


public class PlaceholderFragment extends android.support.v4.app.Fragment {



    private LocationEventFile fileReaderForStackViewFragment;
    ListView TodayListView;
    ArrayAdapter<String> ArrayAdapterForToday;

    ArrayList<String> mylist = new ArrayList<String>();
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
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);


            fileReaderForStackViewFragment = new LocationEventFile();

            TextView TodayTextView = (TextView) rootView.findViewById(R.id.TodayDetailedTextView);
            TodayListView = (ListView) rootView.findViewById(R.id.TodayListView);

            startAutomaticLocationDisplayUpdatesThread();



            ArrayAdapterForToday = new ArrayAdapter<String>(GymTimekeeperApplication.getAppContext(), layout.simple_list_item_1,mylist);

            TodayListView.setAdapter(ArrayAdapterForToday);

            ArrayAdapterForToday.notifyDataSetChanged();


            return rootView;
        }




    private void startAutomaticLocationDisplayUpdatesThread() {
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {



                                displayGymLocationEventsForTheFragment();



                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        t.start();
    }


    private void displayGymLocationEventsForTheFragment(){


        try {












            mylist.clear();



            LocationEventFile file = (LocationEventFile) fileReaderForStackViewFragment.getLocationEventList();

            mylist.add(file.buildDateList() + " " + file.buildLatitudeList() + " " + file.buildLongitudeList());


            ArrayAdapterForToday.notifyDataSetChanged();



        }catch (Exception e){
            e.printStackTrace();
        }

       // "Time\n" + file.buildDateList();
       // "Lat\n" + file.buildLatitudeList();
       // "Lon\n" + file.buildLongitudeList();

    }

}





