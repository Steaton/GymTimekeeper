package one.thing.well.gymtimekeeper.persist.sessionsummary;

import java.util.ArrayList;
import java.util.List;

import one.thing.well.gymtimekeeper.datastore.sessionsummary.SessionSummaryEntry;

public class SessionSummaryFile {

    private List<SessionSummaryEntry> summaryEntries;

    public SessionSummaryFile() {
        summaryEntries = new ArrayList<SessionSummaryEntry>();
    }

    public void add(SessionSummaryEntry sessionSummaryEntry) {
        summaryEntries.add(sessionSummaryEntry);
    }
}

