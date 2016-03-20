package one.thing.well.gymtimekeeper.persist;

import android.content.Context;
import android.location.Location;
import android.support.annotation.NonNull;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import one.thing.well.gymtimekeeper.GymTimekeeperApplication;

public class InTheGymFileWriter {

    private Context context;

    public InTheGymFileWriter(Context context) {
        this.context = context;
    }

    public void writeIsInTheGymEvent(FileOutputStream outputStream, Location location,String FullText) {
        String inTheGymNowString = buildFileEntry(location);
        writeFileEntry(outputStream, inTheGymNowString,FullText);
    }

    @NonNull
    private String buildFileEntry(Location location) {
        String inTheGymNowString = getTimeInMillis() + "," + getLocationInformation(location);
        System.out.println(inTheGymNowString);
        return inTheGymNowString;
    }

    @NonNull
    private String getLocationInformation(Location location) {
        return location.getLatitude() + ","
                + location.getLongitude();
    }

    private String getTimeInMillis() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    private void writeFileEntry(FileOutputStream outputStream, String inTheGymNowString,String FullText) {
        try {
            String TheNewFile =  inTheGymNowString + "#"+ FullText;
            outputStream.write( TheNewFile.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
