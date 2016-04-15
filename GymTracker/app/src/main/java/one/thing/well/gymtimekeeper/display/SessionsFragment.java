package one.thing.well.gymtimekeeper.display;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import one.thing.well.gymtimekeeper.GymTimekeeperApplication;
import one.thing.well.gymtimekeeper.R;


/**
 * Created by zeko on 05/04/2016.
 */
public class SessionsFragment extends android.support.v4.app.Fragment{

    ListView SessionsListVIew;
    ArrayList<String> mylist = new ArrayList<>();
    private ArrayAdapter<String> ArraAdapterForSessions;

    public static SessionsFragment newInstance(){

        SessionsFragment fragment = new SessionsFragment();

        return fragment;
    }


    public SessionsFragment(){


        // Here I need to set the mylist list to have the correct info


            mylist.add(0, "06/04" + "                          " + " 02:17" + "                 " + "02:24:23");
            mylist.add(1, "08/04" + "                          " + " 10:23" + "                 " + "00:50:09");


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View rootView = inflater.inflate(R.layout.sessions_fragment,container,false);

        TextView SessionsTextView = (TextView) rootView.findViewById(R.id.SessionsTextView);
        SessionsListVIew = (ListView) rootView.findViewById(R.id.SessionsListView);

        ArraAdapterForSessions = new ArrayAdapter<String>(GymTimekeeperApplication.getAppContext(),R.layout.css_for_the_sessions_fragment_list_view, mylist);

        SessionsListVIew.setAdapter(ArraAdapterForSessions);

        ArraAdapterForSessions.notifyDataSetChanged();

        return rootView;
    }


}
