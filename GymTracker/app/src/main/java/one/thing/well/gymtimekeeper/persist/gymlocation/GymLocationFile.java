package one.thing.well.gymtimekeeper.persist.gymlocation;

import one.thing.well.gymtimekeeper.persist.FileWritingConstants;

public class GymLocationFile {
    private Double latitude;
    private Double longitude;

    public GymLocationFile(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public GymLocationFile(String gymLocationString) {
        parseGymLocationString(gymLocationString);
    }

    private void parseGymLocationString(String gymLocationString) {
        String[] fields = gymLocationString.split(FileWritingConstants.SPLITTER);
        latitude = Double.valueOf(fields[0]);
        longitude = Double.valueOf(fields[1]);
    }

    @Override
    public String toString() {
        return latitude + FileWritingConstants.SPLITTER + longitude;
    }
}
