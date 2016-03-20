package one.thing.well.gymtimekeeper.persist;

public class InTheGymLocationEvent {

    public static final String SPLITTER = "#";

    private String time;

    private Double latitude;

    private Double longitude;

    public InTheGymLocationEvent(String time, Double latitude, Double longitude) {
        this.time = time;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public InTheGymLocationEvent(String line) {
        parseString(line);
    }

    public void parseString(String line) {
        String[] fields = line.split(SPLITTER);
        time = fields[0];
        latitude = Double.valueOf(fields[1]);
        longitude = Double.valueOf(fields[2]);
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return  time + SPLITTER + latitude + SPLITTER + longitude;
    }
}
