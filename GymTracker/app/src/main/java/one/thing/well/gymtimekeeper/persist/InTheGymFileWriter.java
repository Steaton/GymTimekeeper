package one.thing.well.gymtimekeeper.persist;

import android.content.Context;
import android.location.Location;
import android.support.annotation.NonNull;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import one.thing.well.gymtimekeeper.persist.locationevent.InTheGymLocationEvent;

public class InTheGymFileWriter {

    public static final String DATE_FORMAT = "dd/MM HH:mm:ss";

    private Context context;

    public InTheGymFileWriter(Context context) {
        this.context = context;
    }

    public void writeIsInTheGymEvent(Location location) {
        String inTheGymNowString = buildFileEntry(location);
        writeInTheGymLocationEvent(inTheGymNowString);
    }

    @NonNull
    private String buildFileEntry(Location location) {
        InTheGymLocationEvent locationEvent = new InTheGymLocationEvent(getFormattedTime(), location.getLatitude(), location.getLongitude());
        return locationEvent.toString();
    }

    private String getFormattedTime() {
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        Date date = new Date();
        return dateFormat.format(date);
    }

    private void writeInTheGymLocationEvent(String inTheGymNowString) {
        FileOutputStream outputStream = null;
        try {
            outputStream = writeFileEntry(inTheGymNowString + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeOutputStream(outputStream);
        }
    }

    @NonNull
    private FileOutputStream writeFileEntry(String inTheGymNowString) throws IOException {
        FileOutputStream outputStream = context.openFileOutput("InTheGymLocationEvents", Context.MODE_APPEND);
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
