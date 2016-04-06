package one.thing.well.gymtimekeeper.datastore.locationfix;

import android.location.Location;

import one.thing.well.gymtimekeeper.datastore.FileConstants;

public class LocationFixFile {

    private Double latitude;
    private Double longitude;

    public LocationFixFile(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public LocationFixFile(String locationFixString) {
        parseLocationFixString(locationFixString);
    }

    private void parseLocationFixString(String gymLocationString) {
        String[] fields = gymLocationString.split(FileConstants.SPLITTER);
        latitude = Double.valueOf(fields[0]);
        longitude = Double.valueOf(fields[1]);
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return latitude + FileConstants.SPLITTER + longitude;
    }

    public void addEvent(String locationFixString) {

    }
}
