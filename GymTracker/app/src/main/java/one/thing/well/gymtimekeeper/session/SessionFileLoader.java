package one.thing.well.gymtimekeeper.session;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
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

public class SessionFileLoader {

    private Queue<LocationEventEntry> locationQueue;

    private Double fixLatitude;

    private Double fixLongitude;

    public void loadData() throws IOException, ParseException {
        readLocationFiles();
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

    public Queue<LocationEventEntry> getLocationQueue() {
        return locationQueue;
    }

    public Double getFixLatitude() {
        return fixLatitude;
    }

    public Double getFixLongitude() {
        return fixLongitude;
    }
}
