package one.thing.well.gymtimekeeper.datastore;

import android.content.Context;
import android.support.annotation.NonNull;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import one.thing.well.gymtimekeeper.datastore.locationevent.LocationEventFile;

public abstract class AbstractFileReader {

    private Context context;

    public AbstractFileReader(Context context) {
        this.context = context;
    }

    public Object readFile(String filename) throws IOException {
        BufferedReader bufferedReader = getBufferedReader(filename);
        return readFile(bufferedReader);
    }

    @NonNull
    protected abstract Object readFile(BufferedReader bufferedReader) throws IOException;

    @NonNull
    private BufferedReader getBufferedReader(String filename) throws FileNotFoundException {
        FileInputStream fileInputStream = context.getApplicationContext().openFileInput(filename);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
        return new BufferedReader(inputStreamReader);
    }
}
