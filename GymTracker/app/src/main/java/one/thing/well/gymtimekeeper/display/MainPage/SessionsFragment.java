package one.thing.well.gymtimekeeper.display.mainPage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import one.thing.well.gymtimekeeper.R;
import one.thing.well.gymtimekeeper.datastore.sessionsummary.SessionSummaryEntry;
import one.thing.well.gymtimekeeper.datastore.sessionsummary.SessionSummaryFile;
import one.thing.well.gymtimekeeper.display.Setup.ArrayAdapterForSessionsFragment;
import one.thing.well.gymtimekeeper.session.SessionSummary;
import one.thing.well.gymtimekeeper.util.DateUtils;

public class SessionsFragment extends Fragment {

    private ListView sessionsListView;

    private String[][] sessionData;

    private ArrayAdapter<String> arrayAdapterForSessions;

    public static SessionsFragment newInstance() throws IOException, ParseException {
        SessionsFragment fragment = new SessionsFragment();
        return fragment;
    }

    public SessionsFragment() throws IOException, ParseException {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.sessions_fragment,container,false);
        TextView SessionsTextView = (TextView) rootView.findViewById(R.id.SessionsTextView);
        sessionsListView = (ListView) rootView.findViewById(R.id.SessionsListView);

        try {
            loadSessionData();
            arrayAdapterForSessions = new ArrayAdapterForSessionsFragment(getActivity(), sessionData);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        sessionsListView.setAdapter(arrayAdapterForSessions);
        return rootView;
    }

    private void loadSessionData() throws IOException, ParseException {
        SessionSummary sessionSummary =  new SessionSummary();
        sessionSummary.loadLocationData();
        SessionSummaryFile sessionSummaryFile = sessionSummary.summariseSessions();
        List<SessionSummaryEntry> sessionsList = sessionSummaryFile.getSessionsList();
        sessionData = new String[sessionsList.size()][4];
        for (int i = 0 ; i < sessionsList.size(); i++) {
            sessionData[i][0] = DateUtils.formatDay(sessionsList.get(i).getSessionStartTime());
            sessionData[i][1] = DateUtils.getMonth(sessionsList.get(i).getSessionStartTime());
            sessionData[i][2] = DateUtils.formatTime(sessionsList.get(i).getSessionStartTime()) + "  -  " + DateUtils.formatTime(sessionsList.get(i).getSessionEndTime());
            sessionData[i][3] = DateUtils.calculateDuration(sessionsList.get(i).getSessionStartTime(), (sessionsList.get(i).getSessionEndTime()));
        }
    }
}
