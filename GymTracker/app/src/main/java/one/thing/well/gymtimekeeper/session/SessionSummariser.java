package one.thing.well.gymtimekeeper.session;

import one.thing.well.gymtimekeeper.GymTimekeeperApplication;
import one.thing.well.gymtimekeeper.persist.InTheGymFileReader;
import one.thing.well.gymtimekeeper.persist.locationevent.InTheGymLocationEventFile;
import one.thing.well.gymtimekeeper.persist.sessionsummary.GymSessionSummaryFile;

public class SessionSummariser {

    private InTheGymFileReader reader = new InTheGymFileReader(GymTimekeeperApplication.getAppContext());

    private InTheGymLocationEventFile locationEventFile;

    public GymSessionSummaryFile summariseGymSessions() {
        locationEventFile = reader.readGymLocationEventFile();

        return null;
    }
}
