package one.thing.well.gymtimekeeper.session;

import android.location.Location;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import one.thing.well.gymtimekeeper.GymTimekeeperApplication;
import one.thing.well.gymtimekeeper.datastore.FileConstants;
import one.thing.well.gymtimekeeper.datastore.FileReader;
import one.thing.well.gymtimekeeper.datastore.locationevent.LocationEventEntry;
import one.thing.well.gymtimekeeper.datastore.locationevent.LocationEventFile;
import one.thing.well.gymtimekeeper.datastore.locationfix.LocationFixFile;
import one.thing.well.gymtimekeeper.datastore.sessionsummary.SessionSummaryEntry;
import one.thing.well.gymtimekeeper.datastore.sessionsummary.SessionSummaryFile;
import one.thing.well.gymtimekeeper.util.DateUtils;

public class SessionSummariser {

    private FileReader reader = new FileReader(GymTimekeeperApplication.getAppContext());

    private LocationEventFile locationEventFile;

    private LocationFixFile locationFixFile;

    public SessionSummaryFile summariseSession() throws IOException, ParseException {
        readLocationFiles();
        return createSummaryFile();
    }

    private void readLocationFiles() throws IOException {
        locationEventFile = (LocationEventFile) reader.readFile(FileConstants.LOCATION_EVENTS_FILENAME);
        locationFixFile = (LocationFixFile) reader.readFile(FileConstants.LOCATION_FIX_FILENAME);
    }

    private SessionSummaryFile createSummaryFile() throws ParseException {
        Double fixLatitude = locationFixFile.getLatitude();
        Double fixLongitude = locationFixFile.getLongitude();
        List<LocationEventEntry> locationEventList = locationEventFile.getLocationEventList();
        boolean isInGym = false;
        SessionSummaryEntry sessionSummaryEntry = new SessionSummaryEntry();
        SessionSummaryFile sessionSummaryFile = new SessionSummaryFile();
        for (LocationEventEntry locationEntry : locationEventList) {
            float[] results = null;
            Location.distanceBetween(fixLatitude, fixLongitude, locationEntry.getLatitude(), locationEntry.getLongitude(), results);
            float distance = results[0];
            if (!isInGym && distance <= 100.0) {
                sessionSummaryEntry.setSessionStartTime(DateUtils.parseDateString(locationEntry.getTime()));
                isInGym = true;
            } else if (isInGym && distance > 100.0) {
                sessionSummaryEntry.setSessionEndTime(DateUtils.parseDateString(locationEntry.getTime()));
                sessionSummaryFile.add(sessionSummaryEntry);
                sessionSummaryEntry = new SessionSummaryEntry();
                isInGym = false;
            }
        }
        return sessionSummaryFile;
    }
}
