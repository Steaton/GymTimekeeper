package one.thing.well.gymtimekeeper.session;

import one.thing.well.gymtimekeeper.GymTimekeeperApplication;
import one.thing.well.gymtimekeeper.persist.LocationEventFileReader;
import one.thing.well.gymtimekeeper.persist.locationevent.LocationEventFile;
import one.thing.well.gymtimekeeper.persist.sessionsummary.SessionSummaryFile;

public class SessionSummariser {

    private LocationEventFileReader reader = new LocationEventFileReader(GymTimekeeperApplication.getAppContext());

    private LocationEventFile locationEventFile;

    public SessionSummaryFile summariseGymSessions() {
        locationEventFile = reader.readGymLocationEventFile();

        return null;
    }
}
