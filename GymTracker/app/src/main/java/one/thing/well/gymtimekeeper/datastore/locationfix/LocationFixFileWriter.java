package one.thing.well.gymtimekeeper.datastore.locationfix;

import android.content.Context;
import android.location.Location;
import android.support.annotation.NonNull;

import one.thing.well.gymtimekeeper.datastore.AbstractFileWriter;

public class LocationFixFileWriter extends AbstractFileWriter {

    public LocationFixFileWriter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    protected String buildFileEntry(Double latitude, Double longitude) {
        LocationFixFile locationEvent = new LocationFixFile(latitude, longitude);
        return locationEvent.toString();
    }
}
