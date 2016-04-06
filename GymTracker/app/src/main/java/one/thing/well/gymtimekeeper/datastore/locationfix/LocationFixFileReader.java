package one.thing.well.gymtimekeeper.datastore.locationfix;

import android.content.Context;
import android.support.annotation.NonNull;

import java.io.BufferedReader;
import java.io.IOException;

import one.thing.well.gymtimekeeper.datastore.AbstractFileReader;

public class LocationFixFileReader extends AbstractFileReader {
    public LocationFixFileReader(Context context) {
        super(context);
    }

    @NonNull
    protected LocationFixFile readFile(BufferedReader bufferedReader) throws IOException {
        String locationFixString = bufferedReader.readLine();
        return new LocationFixFile(locationFixString);
    }
}
