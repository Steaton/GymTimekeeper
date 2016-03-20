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
}
