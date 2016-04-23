package one.thing.well.gymtimekeeper.display;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.IOException;
import java.text.ParseException;

import one.thing.well.gymtimekeeper.R;
import one.thing.well.gymtimekeeper.datastore.sessionsummary.SessionSummaryFile;
import one.thing.well.gymtimekeeper.session.SessionSummary;
import one.thing.well.gymtimekeeper.session.WeekSummariser;

public class SummaryFragment extends Fragment {

    private WeekSummariser weekSummariser;

    public static SummaryFragment newInstance() throws IOException, ParseException {
        SummaryFragment fragment = new SummaryFragment();
        return fragment;
    }

    public SummaryFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.summary_fragment, container, false);
        TextView todayValue = (TextView) rootView.findViewById(R.id.todayValue);
        TextView thisWeekValue = (TextView) rootView.findViewById(R.id.thisWeekValue);
        try {
            loadSessionData();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        todayValue.setText(weekSummariser.today());
        thisWeekValue.setText(weekSummariser.thisWeek());
        return rootView;
    }

    private void loadSessionData() throws ParseException, IOException {
        SessionSummary sessionSummary =  new SessionSummary();
        sessionSummary.loadLocationData();
        SessionSummaryFile sessionSummaryFile = sessionSummary.summariseSessions();
        weekSummariser = new WeekSummariser(sessionSummaryFile);
    }
}
