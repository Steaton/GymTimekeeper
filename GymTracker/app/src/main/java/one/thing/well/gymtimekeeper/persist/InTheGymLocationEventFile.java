package one.thing.well.gymtimekeeper.persist;

import java.util.ArrayList;
import java.util.List;

public class InTheGymLocationEventFile {

    private List<InTheGymLocationEvent> locationEventList;

    public InTheGymLocationEventFile() {
        locationEventList = new ArrayList<InTheGymLocationEvent>();
    }

    public InTheGymLocationEventFile(List<InTheGymLocationEvent> locationEventList) {
        this.locationEventList = locationEventList;
    }

    public List<InTheGymLocationEvent> getLocationEventList() {
        return locationEventList;
    }

    public String buildDateList() {
        StringBuilder dateList = new StringBuilder();
        for (InTheGymLocationEvent locationEvent : locationEventList) {
            dateList.append(locationEvent.getTime() + "\n");
        }
        return dateList.toString();
    }

    public String buildLatitudeList() {
        StringBuilder latitudeList = new StringBuilder();
        for (InTheGymLocationEvent locationEvent : locationEventList) {
            latitudeList.append(locationEvent.getLatitude() + "\n");
        }
        return latitudeList.toString();
    }

    public String buildLongitudeList() {
        StringBuilder longitudeList = new StringBuilder();
        for (InTheGymLocationEvent locationEvent : locationEventList) {
            longitudeList.append(locationEvent.getLongitude() + "\n");
        }
        return longitudeList.toString();
    }

    public void addEvent(String line) {
        InTheGymLocationEvent inTheGymLocationEvent = new InTheGymLocationEvent(line);
        locationEventList.add(inTheGymLocationEvent);
    }
}
