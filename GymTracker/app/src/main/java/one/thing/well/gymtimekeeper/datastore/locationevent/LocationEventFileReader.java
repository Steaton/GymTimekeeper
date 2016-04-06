package one.thing.well.gymtimekeeper.datastore.locationevent;

import android.content.Context;
import android.support.annotation.NonNull;

import java.io.BufferedReader;
import java.io.IOException;

import one.thing.well.gymtimekeeper.datastore.AbstractFileReader;

public class LocationEventFileReader extends AbstractFileReader {

    public LocationEventFileReader(Context context) {
        super(context);
    }

    @NonNull
    protected LocationEventFile readFile(BufferedReader bufferedReader) throws IOException {
        LocationEventFile locationEventFile = new LocationEventFile();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            locationEventFile.addEvent(line);
        }
        return locationEventFile;
    }
}
