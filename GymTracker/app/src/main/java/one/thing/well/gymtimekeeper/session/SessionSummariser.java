package one.thing.well.gymtimekeeper.session;

import java.io.IOException;

import one.thing.well.gymtimekeeper.GymTimekeeperApplication;
import one.thing.well.gymtimekeeper.datastore.FileReader;
import one.thing.well.gymtimekeeper.datastore.locationevent.LocationEventFile;
import one.thing.well.gymtimekeeper.datastore.sessionsummary.SessionSummaryFile;

public class SessionSummariser {

    private FileReader reader = new FileReader(GymTimekeeperApplication.getAppContext());

    private LocationEventFile locationEventFile;

    public SessionSummaryFile summariseGymSessions() throws IOException {
        locationEventFile = (LocationEventFile) reader.readFile("InTheGymLocationFix");

        return null;
    }
}
