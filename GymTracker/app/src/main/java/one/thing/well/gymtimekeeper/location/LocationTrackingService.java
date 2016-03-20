package one.thing.well.gymtimekeeper.location;

import android.location.Location;

import one.thing.well.gymtimekeeper.GymTimekeeperApplication;
import one.thing.well.gymtimekeeper.persist.locationevent.LocationEventFileWriter;

public class LocationTrackingService extends AbstractLocationService {

    private LocationEventFileWriter fileWriter = new LocationEventFileWriter(GymTimekeeperApplication.getAppContext());

    public LocationTrackingService() {
        super();
    }

    @Override
    public void processLocationChangedEvent(Location location) {
        writeInTheGymEvent(location);
    }

    private void writeInTheGymEvent(Location location) {
        fileWriter.writeIsInTheGymEvent(location);
    }
}
