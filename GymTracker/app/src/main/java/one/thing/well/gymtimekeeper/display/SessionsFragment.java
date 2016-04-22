package one.thing.well.gymtimekeeper.display;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.R.layout;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import one.thing.well.gymtimekeeper.GymTimekeeperApplication;
import one.thing.well.gymtimekeeper.R;
import one.thing.well.gymtimekeeper.datastore.locationevent.LocationEventFile;
import one.thing.well.gymtimekeeper.datastore.sessionsummary.SessionSummaryEntry;
import one.thing.well.gymtimekeeper.datastore.sessionsummary.SessionSummaryFile;
import one.thing.well.gymtimekeeper.session.SessionSummary;
import one.thing.well.gymtimekeeper.util.DateUtils;

/**
 * Created by zeko on 05/04/2016.
 */
public class SessionsFragment extends android.support.v4.app.Fragment{

    ListView SessionsListVIew;
    ArrayList<String> mylist = new ArrayList<>();
    private ArrayAdapter<String> ArraAdapterForSessions;

    public static SessionsFragment newInstance() throws IOException, ParseException {
        SessionsFragment fragment = new SessionsFragment();
        return fragment;
    }

    public SessionsFragment() throws IOException, ParseException {
        GatheringDataForSessionsFragment();
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


    private void GatheringDataForSessionsFragment() throws IOException, ParseException {
        SessionSummary sessionSummary =  new SessionSummary();
        SessionSummaryFile sessionSummaryFile = sessionSummary.summariseSessions();
        List<SessionSummaryEntry> sessionsList = sessionSummaryFile.getSessionsList();
        for (SessionSummaryEntry session : sessionsList) {
            Date startTime = session.getSessionStartTime();
            Date endTime = session.getSessionEndTime();
            mylist.add(session.displayString());
        }
    }
}
