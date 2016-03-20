package one.thing.well.gymtimekeeper.persist.locationevent;

import java.util.ArrayList;
import java.util.List;

public class LocationEventFile {

    private List<LocationEventEntry> locationEventList;

    public LocationEventFile() {
        locationEventList = new ArrayList<LocationEventEntry>();
    }

    public String buildDateList() {
        StringBuilder dateList = new StringBuilder();
        for (LocationEventEntry locationEvent : locationEventList) {
            dateList.append(locationEvent.getTime() + "\n");
        }
        return dateList.toString();
    }

    public String buildLatitudeList() {
        StringBuilder latitudeList = new StringBuilder();
        for (LocationEventEntry locationEvent : locationEventList) {
            latitudeList.append(locationEvent.getLatitude() + "\n");
        }
        return latitudeList.toString();
    }

    public String buildLongitudeList() {
        StringBuilder longitudeList = new StringBuilder();
        for (LocationEventEntry locationEvent : locationEventList) {
            longitudeList.append(locationEvent.getLongitude() + "\n");
        }
        return longitudeList.toString();
    }

    public void addEvent(String line) {
        LocationEventEntry locationEventEntry = new LocationEventEntry(line);
        locationEventList.add(locationEventEntry);
    }
}
