package one.thing.well.gymtimekeeper.session;

import android.location.Location;

import java.io.IOException;
import java.util.List;

import one.thing.well.gymtimekeeper.GymTimekeeperApplication;
import one.thing.well.gymtimekeeper.datastore.FileConstants;
import one.thing.well.gymtimekeeper.datastore.FileReader;
import one.thing.well.gymtimekeeper.datastore.locationevent.LocationEventEntry;
import one.thing.well.gymtimekeeper.datastore.locationevent.LocationEventFile;
import one.thing.well.gymtimekeeper.datastore.locationfix.LocationFixFile;
import one.thing.well.gymtimekeeper.datastore.sessionsummary.SessionSummaryFile;

public class SessionSummariser {

    private FileReader reader = new FileReader(GymTimekeeperApplication.getAppContext());

    private LocationEventFile locationEventFile;

    private LocationFixFile locationFixFile;

    public SessionSummaryFile summariseSession() throws IOException {
        readLocationFiles();
        return createSummaryFile();
    }

    private void readLocationFiles() throws IOException {
        locationEventFile = (LocationEventFile) reader.readFile(FileConstants.LOCATION_EVENTS_FILENAME);
        locationFixFile = (LocationFixFile) reader.readFile(FileConstants.LOCATION_FIX_FILENAME);
    }

    private SessionSummaryFile createSummaryFile() {
        Double fixLatitude = locationFixFile.getLatitude();
        Double fixLongitude = locationFixFile.getLongitude();
        List<LocationEventEntry> locationEventList = locationEventFile.getLocationEventList();
        for (LocationEventEntry locationEntry : locationEventList) {
            float[] results = null;
            Location.distanceBetween(fixLatitude, fixLongitude, locationEntry.getLatitude(), locationEntry.getLongitude(), results);
            float distance = results[0];
            
        }
        return null;
    }
}
