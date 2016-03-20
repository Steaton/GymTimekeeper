package one.thing.well.gymtimekeeper.location;

import android.location.Location;

import java.util.ArrayList;
import java.util.Collection;

public class LocationFixService extends AbstractLocationService {

    private int numberOfLocationsSoFar;

    private Collection<Location> locations = new ArrayList<Location>();

    @Override
    public void processLocationChangedEvent(Location location) {
        trackTenLocationsAndCalculateCentreLocation(location);
    }

    private void trackTenLocationsAndCalculateCentreLocation(Location location) {
        numberOfLocationsSoFar++;
        if (numberOfLocationsSoFar < 10) {
            locations.add(location);
        } else {
            calculateCentrePoint();
            stopSelf();
        }
    }

    private void calculateCentrePoint() {
        for (Location location : locations) {

        }
    }
}
