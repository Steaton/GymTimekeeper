package one.thing.well.gymtimekeeper.persist.locationevent;

import android.content.Context;
import android.support.annotation.NonNull;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import one.thing.well.gymtimekeeper.persist.locationevent.LocationEventFile;

public class LocationEventFileReader {

    private Context context;

    public LocationEventFileReader(Context context) {
        this.context = context;
    }

    public LocationEventFile readGymLocationEventFile() {

        try {
            return readGymLocationEventFile("InTheGymLocationEvents");
        } catch (IOException e) {
            e.printStackTrace();
            return new LocationEventFile();
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return new LocationEventFile();
        }
    }

    private LocationEventFile readGymLocationEventFile(String filename) throws IOException {
        BufferedReader bufferedReader = getBufferedReader(filename);
        return readFile(bufferedReader);
    }

    @NonNull
    private LocationEventFile readFile(BufferedReader bufferedReader) throws IOException {
        LocationEventFile locationEventFile = new LocationEventFile();
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            locationEventFile.addEvent(line);
        }
        return locationEventFile;
    }

    @NonNull
    private BufferedReader getBufferedReader(String filename) throws FileNotFoundException {
        FileInputStream fileInputStream = context.getApplicationContext().openFileInput(filename);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
        return new BufferedReader(inputStreamReader);
    }
}
