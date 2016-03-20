package one.thing.well.gymtimekeeper.datastore.locationfix;

import one.thing.well.gymtimekeeper.datastore.FileWritingConstants;

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
        String[] fields = gymLocationString.split(FileWritingConstants.SPLITTER);
        latitude = Double.valueOf(fields[0]);
        longitude = Double.valueOf(fields[1]);
    }

    @Override
    public String toString() {
        return latitude + FileWritingConstants.SPLITTER + longitude;
    }
}
