package one.thing.well.gymtimekeeper.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.test.AndroidTestCase;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

import one.thing.well.gymtimekeeper.GymTimekeeperApplication;
import one.thing.well.gymtimekeeper.datastore.FileConstants;
import one.thing.well.gymtimekeeper.datastore.locationevent.LocationEventEntry;
import one.thing.well.gymtimekeeper.datastore.locationfix.LocationFixFileWriter;


public class CreateTestLocationEventFile extends AndroidTestCase {

    private ArrayBlockingQueue<LocationEventEntry> locationQueue;

    private Date currentSessionDate;

    private Context context;

    public void shouldWriteTestDataFiles() throws ParseException, IOException {
        context = GymTimekeeperApplication.getAppContext();
        System.out.println("#sesh1" + context.getApplicationContext());
        currentSessionDate = DateUtils.parseDateString("10/03 10:00:00");
        locationQueue = new ArrayBlockingQueue<>(50, true);
        addTenMinutesOutsideLocation();
        addThirtyMinutesAtLocation();
        addTenMinutesOutsideLocation();
        addThirtyMinutesAtLocation();
        writeQueueToNewFile();
    }

    private void addThirtyMinutesAtLocation() {
        addTimeAtLocation( -2.1913555, 53.4461826, 30);
    }

    private void addTenMinutesOutsideLocation() {
        addTimeAtLocation( -0.188636,51.569969, 10);
    }

    private void addTimeAtLocation(double latitude, double longitude, int mins) {
        while(mins > 0) {
            locationQueue.offer(new LocationEventEntry(DateUtils.formatDate(currentSessionDate), latitude, longitude));
            currentSessionDate = DateUtils.addMinutesToDate(currentSessionDate, 2);
            mins -= 2;
        }
    }

    private void writeQueueToNewFile() throws IOException {
        writeFixLocationFile();
        writeLocationEventFile();
    }

    private void writeFixLocationFile() {
        LocationFixFileWriter fixFileWriter = new LocationFixFileWriter(context);
        fixFileWriter.deleteFile(FileConstants.LOCATION_FIX_FILENAME);
        fixFileWriter.writeFileEntry(53.4461826, -2.1913555, FileConstants.LOCATION_FIX_FILENAME);
    }

    private void writeLocationEventFile() throws IOException {
        FileOutputStream outputStream = null;
        try {

            context.deleteFile(FileConstants.LOCATION_EVENTS_FILENAME);

            outputStream = context.openFileOutput(FileConstants.LOCATION_EVENTS_FILENAME, Context.MODE_PRIVATE);

            for (LocationEventEntry locationEventEntry : locationQueue) {
                String locationEventString = locationEventEntry.toString() + "\n";
                outputStream.write(locationEventString.getBytes());
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            outputStream.close();
        }




    }
}