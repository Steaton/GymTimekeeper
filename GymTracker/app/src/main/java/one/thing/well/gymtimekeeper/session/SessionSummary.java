package one.thing.well.gymtimekeeper.session;

import android.location.Location;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import one.thing.well.gymtimekeeper.GymTimekeeperApplication;
import one.thing.well.gymtimekeeper.datastore.AbstractFileReader;
import one.thing.well.gymtimekeeper.datastore.FileConstants;
import one.thing.well.gymtimekeeper.datastore.locationevent.LocationEventEntry;
import one.thing.well.gymtimekeeper.datastore.locationevent.LocationEventFile;
import one.thing.well.gymtimekeeper.datastore.locationevent.LocationEventFileReader;
import one.thing.well.gymtimekeeper.datastore.locationfix.LocationFixFile;
import one.thing.well.gymtimekeeper.datastore.locationfix.LocationFixFileReader;
import one.thing.well.gymtimekeeper.datastore.sessionsummary.SessionSummaryEntry;
import one.thing.well.gymtimekeeper.datastore.sessionsummary.SessionSummaryFile;
import one.thing.well.gymtimekeeper.util.DateUtils;

public class SessionSummary {

    private Queue<LocationEventEntry> locationQueue;

    private Double fixLatitude;

    private Double fixLongitude;

    private SessionSummaryFile sessionSummaryFile = new SessionSummaryFile();

    public void loadData() throws IOException, ParseException {
        try {
            readLocationFiles();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            // Do nothing
        } catch (NumberFormatException e) {

        }
    }

    private void readLocationFiles() throws IOException {
        readLocationFixData();
        readLocationEventData();
    }

    private void readLocationEventData() throws IOException {
        LocationEventFileReader locationEventFileReader = new LocationEventFileReader(GymTimekeeperApplication.getAppContext());
        LocationEventFile locationEventFile = (LocationEventFile) locationEventFileReader.readFile(FileConstants.LOCATION_EVENTS_FILENAME);
        locationQueue = new ArrayBlockingQueue<LocationEventEntry>(100000, true, locationEventFile.getLocationEventList());
    }

    private void readLocationFixData() throws IOException {
        AbstractFileReader locationFixFileReader = new LocationFixFileReader(GymTimekeeperApplication.getAppContext());
        LocationFixFile locationFixFile = (LocationFixFile) locationFixFileReader.readFile(FileConstants.LOCATION_FIX_FILENAME);
        fixLatitude = locationFixFile.getLatitude();
        fixLongitude = locationFixFile.getLongitude();
    }

    public SessionSummaryFile summariseSessions() throws ParseException {
        if(hasDataToSummarise()) {
            summariseLocations();
        }
        return sessionSummaryFile;
    }

    private boolean hasDataToSummarise() {
        return fixLatitude != null && fixLongitude != null && locationQueue != null;
    }

    private void summariseLocations() throws ParseException {
        while(!locationQueue.isEmpty()) {
            SessionSummaryEntry nextSession = findNextSession();
            if (nextSession != null) {
                sessionSummaryFile.add(nextSession);
            }
        }
    }


    private SessionSummaryEntry findNextSession() throws ParseException {
        Date sessionStartStartTime = findSessionStartTime();
        if (sessionStartStartTime == null) return null;
        Date sessionEndTime = findSessionEndTime();
        return new SessionSummaryEntry(sessionStartStartTime, sessionEndTime);
    }

    private Date findSessionStartTime() throws ParseException {
        LocationEventEntry locationEventEntry = locationQueue.peek();
        while (!locationQueue.isEmpty() && distanceFromFixLocation(locationEventEntry) > 100.0) {
            locationEventEntry = locationQueue.remove();
        }
        if (locationQueue.isEmpty()) return null;
        return DateUtils.parseDateString(locationEventEntry.getTime());
    }

    private Date findSessionEndTime() throws ParseException {
        LocationEventEntry locationEventEntry = locationQueue.peek();
        while (!locationQueue.isEmpty() && distanceFromFixLocation(locationEventEntry) <= 100.0) {
            locationEventEntry = locationQueue.remove();
        }
        return DateUtils.parseDateString(locationEventEntry.getTime());
    }

    private float distanceFromFixLocation(LocationEventEntry locationEntry) {
        float[] results = new float[3];
        Location.distanceBetween(fixLatitude, fixLongitude, locationEntry.getLatitude(), locationEntry.getLongitude(), results);
        return results[0];
    }

    public SessionSummaryFile getSessionSummaryFile() {
        return sessionSummaryFile;
    }

    public void setLocationQueue(Queue<LocationEventEntry> locationQueue) {
        this.locationQueue = locationQueue;
    }

    public void setFixLatitude(Double fixLatitude) {
        this.fixLatitude = fixLatitude;
    }

    public void setFixLongitude(Double fixLongitude) {
        this.fixLongitude = fixLongitude;
    }

}
