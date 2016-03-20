package one.thing.well.gymtimekeeper.persist.sessionsummary;

import java.util.ArrayList;
import java.util.List;

public class GymSessionSummaryFile {

    private List<GymSessionSummary> locationEventList;

    public GymSessionSummaryFile() {
        locationEventList = new ArrayList<GymSessionSummary>();
    }
}

