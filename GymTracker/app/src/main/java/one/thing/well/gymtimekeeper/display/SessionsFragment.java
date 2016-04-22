package one.thing.well.gymtimekeeper.display;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import one.thing.well.gymtimekeeper.GymTimekeeperApplication;
import one.thing.well.gymtimekeeper.R;
import one.thing.well.gymtimekeeper.datastore.sessionsummary.SessionSummaryEntry;
import one.thing.well.gymtimekeeper.datastore.sessionsummary.SessionSummaryFile;
import one.thing.well.gymtimekeeper.session.SessionSummary;

public class SessionsFragment extends android.support.v4.app.Fragment {

    private ListView sessionsListView;

    private ArrayList<String> mylist = new ArrayList<>();

    private ArrayAdapter<String> arrayAdapterForSessions;

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
        sessionsListView = (ListView) rootView.findViewById(R.id.SessionsListView);
        arrayAdapterForSessions = new ArrayAdapter<String>(GymTimekeeperApplication.getAppContext(),R.layout.css_for_the_sessions_fragment_list_view, mylist);
        sessionsListView.setAdapter(arrayAdapterForSessions);
        arrayAdapterForSessions.notifyDataSetChanged();
        return rootView;
    }

    private void GatheringDataForSessionsFragment() throws IOException, ParseException {
        SessionSummary sessionSummary =  new SessionSummary();
        sessionSummary.loadLocationData();
        SessionSummaryFile sessionSummaryFile = sessionSummary.summariseSessions();
        List<SessionSummaryEntry> sessionsList = sessionSummaryFile.getSessionsList();
        for (SessionSummaryEntry session : sessionsList) {
            mylist.add(session.displayString());
        }
    }
}
