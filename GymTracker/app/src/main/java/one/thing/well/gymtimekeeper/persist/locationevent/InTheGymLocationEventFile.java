package one.thing.well.gymtimekeeper.persist.locationevent;

import java.util.ArrayList;
import java.util.List;

public class InTheGymLocationEventFile {

    private List<InTheGymLocationEventEntry> locationEventList;

    public InTheGymLocationEventFile() {
        locationEventList = new ArrayList<InTheGymLocationEventEntry>();
    }

    public String buildDateList() {
        StringBuilder dateList = new StringBuilder();
        for (InTheGymLocationEventEntry locationEvent : locationEventList) {
            dateList.append(locationEvent.getTime() + "\n");
        }
        return dateList.toString();
    }

    public String buildLatitudeList() {
        StringBuilder latitudeList = new StringBuilder();
        for (InTheGymLocationEventEntry locationEvent : locationEventList) {
            latitudeList.append(locationEvent.getLatitude() + "\n");
        }
        return latitudeList.toString();
    }

    public String buildLongitudeList() {
        StringBuilder longitudeList = new StringBuilder();
        for (InTheGymLocationEventEntry locationEvent : locationEventList) {
            longitudeList.append(locationEvent.getLongitude() + "\n");
        }
        return longitudeList.toString();
    }

    public void addEvent(String line) {
        InTheGymLocationEventEntry inTheGymLocationEventEntry = new InTheGymLocationEventEntry(line);
        locationEventList.add(inTheGymLocationEventEntry);
    }
}
