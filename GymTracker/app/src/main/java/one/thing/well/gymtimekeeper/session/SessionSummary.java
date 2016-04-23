package one.thing.well.gymtimekeeper.session;

import android.location.Location;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
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

    public static final double RANGE = 70.0;

    private Queue<LocationEventEntry> locationQueue;

    private Double fixLatitude;

    private Double fixLongitude;

    private SessionSummaryFile sessionSummaryFile = new SessionSummaryFile();

    private SessionFileLoader sessionFileLoader = new SessionFileLoader();

    public SessionSummary() {

    }

    public void loadLocationData() throws IOException, ParseException {
        sessionFileLoader.loadData();
        locationQueue = sessionFileLoader.getLocationQueue();
        fixLatitude = sessionFileLoader.getFixLatitude();
        fixLongitude = sessionFileLoader.getFixLongitude();
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
        while (!locationQueue.isEmpty() && distanceFromFixLocation(locationEventEntry) > RANGE) {
            locationEventEntry = locationQueue.remove();
            System.out.println("LocationEventFile: " + locationEventEntry.toString() + " Distance: " + distanceFromFixLocation(locationEventEntry));
        }
        if (locationQueue.isEmpty()) return null;
        return DateUtils.parseDateString(locationEventEntry.getTime());
    }

    private Date findSessionEndTime() throws ParseException {
        LocationEventEntry locationEventEntry = locationQueue.peek();
        Date previousEventTime = DateUtils.parseDateString(locationEventEntry.getTime());
        while (!locationQueue.isEmpty() && distanceFromFixLocation(locationEventEntry) <= RANGE) {
            previousEventTime = DateUtils.parseDateString(locationEventEntry.getTime());
            locationEventEntry = locationQueue.remove();
            System.out.println("LocationEventFile: " + locationEventEntry.toString() + " Distance: " + distanceFromFixLocation(locationEventEntry));
        }
        return previousEventTime;
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
