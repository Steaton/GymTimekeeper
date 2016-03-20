package one.thing.well.gymtimekeeper.persist.sessionsummary;

import java.util.ArrayList;
import java.util.List;

public class GymSessionSummaryFile {

    private List<GymSessionSummaryEntry> locationEventList;

    public GymSessionSummaryFile() {
        locationEventList = new ArrayList<GymSessionSummaryEntry>();
    }
}

