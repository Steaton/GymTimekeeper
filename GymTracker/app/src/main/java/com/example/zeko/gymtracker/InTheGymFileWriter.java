package com.example.zeko.gymtracker;

import android.location.Location;
import android.support.annotation.NonNull;
import android.text.format.Time;

import java.io.FileOutputStream;
import java.io.IOException;

public class InTheGymFileWriter {

    public void writeIsInTheGymEvent(FileOutputStream outputStream, Location location) {
        String inTheGymNowString = buildFileEntry(location);
        writeFileEntry(outputStream, inTheGymNowString);
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
                + location.getLongitude() + ","
                + location.getAccuracy();
    }

    private long getTimeInMillis() {
        Time now = new Time();
        now.setToNow();
        return now.toMillis(true);
    }

    private void writeFileEntry(FileOutputStream outputStream, String inTheGymNowString) {
        try {
            outputStream.write(inTheGymNowString.getBytes());
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
