package one.thing.well.gymtimekeeper.datastore;

import android.content.Context;
import android.support.annotation.NonNull;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import one.thing.well.gymtimekeeper.datastore.locationevent.LocationEventFile;

public class FileReader {

    private Context context;

    public FileReader(Context context) {
        this.context = context;
    }

    public Object readFile(String filename) throws IOException {
        BufferedReader bufferedReader = getBufferedReader(filename);
        return readFile(bufferedReader);
    }

    @NonNull
    private LocationEventFile readFile(BufferedReader bufferedReader) throws IOException {
        LocationEventFile locationEventFile = new LocationEventFile();
        String line;
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
