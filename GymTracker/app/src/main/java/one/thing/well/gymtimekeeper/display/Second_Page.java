package one.thing.well.gymtimekeeper.display;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.R.layout;
import one.thing.well.gymtimekeeper.GymTimekeeperApplication;
import one.thing.well.gymtimekeeper.R;
import one.thing.well.gymtimekeeper.datastore.locationevent.LocationEventFile;



/**
 * Created by zeko on 05/04/2016.
 */
public class Second_Page extends android.support.v4.app.Fragment {


    public  static Second_Page newInstance(){

        Second_Page fragment = new Second_Page();


        return fragment;
    }



    public Second_Page(){



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){


        View rootView = inflater.inflate(R.layout.second_page,container,false);



        return rootView;
    }





}
