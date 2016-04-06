package one.thing.well.gymtimekeeper.datastore;

import android.content.Context;
import android.location.Location;
import android.support.annotation.NonNull;

import java.io.FileOutputStream;
import java.io.IOException;

public abstract class AbstractFileWriter {

    private Context context;

    public AbstractFileWriter(Context context) {
        this.context = context;
    }

    @NonNull
    protected abstract String buildFileEntry(Double longitude, Double latitude);

    public void writeFileEntry(Double longitude, Double latitude, String filename) {
        String fileEntry = buildFileEntry(latitude, longitude);
        writeInTheGymLocationEvent(fileEntry, filename);
    }

    public void deleteFile(String filename) {
        context.deleteFile(filename);
    }

    private void writeInTheGymLocationEvent(String inTheGymNowString, String filename) {
        FileOutputStream outputStream = null;
        try {
            outputStream = writeFileEntry(inTheGymNowString + "\n", filename);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeOutputStream(outputStream);
        }
    }

    @NonNull
    private FileOutputStream writeFileEntry(String inTheGymNowString, String filename) throws IOException {
        FileOutputStream outputStream = context.openFileOutput(filename, Context.MODE_APPEND);
        outputStream.write(inTheGymNowString.getBytes());
        return outputStream;
    }

    private void closeOutputStream(FileOutputStream outputStream) {
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
