package one.thing.well.gymtimekeeper.locationservice;

import android.location.Location;

import one.thing.well.gymtimekeeper.GymTimekeeperApplication;
import one.thing.well.gymtimekeeper.datastore.FileWritingConstants;
import one.thing.well.gymtimekeeper.datastore.locationevent.LocationEventFileWriter;

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
        fileWriter.writeFileEntry(location.getLatitude(), location.getLongitude(), FileWritingConstants.LOCATION_EVENTS_FILENAME);
    }
}
