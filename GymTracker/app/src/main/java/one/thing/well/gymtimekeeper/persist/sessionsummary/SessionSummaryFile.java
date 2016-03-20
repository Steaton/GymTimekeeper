package one.thing.well.gymtimekeeper.persist.sessionsummary;

import java.util.ArrayList;
import java.util.List;

public class SessionSummaryFile {

    private List<SessionSummaryEntry> locationEventList;

    public SessionSummaryFile() {
        locationEventList = new ArrayList<SessionSummaryEntry>();
    }
}

