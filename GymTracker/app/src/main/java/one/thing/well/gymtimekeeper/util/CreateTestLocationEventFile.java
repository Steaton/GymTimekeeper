package one.thing.well.gymtimekeeper.util;

import android.content.Context;
import android.test.AndroidTestCase;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
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

        currentSessionDate = DateUtils.parseDateString("29/06/16 15:32");
        locationQueue = new ArrayBlockingQueue<>(100, true);
        addTimeAtLocation(-2.1913555, 53.4461826, 10);
        addTimeOutsideLocation(10);
        addTimeAtLocation(-2.1913555, 53.4461826, 10);
        addTimeOutsideLocation(10);
        addTimeAtLocation(-2.1913555, 53.4461826, 10);
        addTimeOutsideLocation(10);
        addTimeAtLocation(-2.1913555, 53.4461826, 10);
        addTimeOutsideLocation(10);
        writeQueueToNewFile();
    }

    private void addThirtyMinutesAtLocation() {
        addTimeAtLocation( -2.1913555, 53.4461826, 30);
    }

    private void addTimeOutsideLocation(int mins) {
        addTimeAtLocation( -0.188636,51.569969,mins);
    }

    private void addTimeAtLocation(double latitude, double longitude, int mins) {


        while(mins >= 0 ) {
            locationQueue.offer(new LocationEventEntry(DateUtils.formatDate(currentSessionDate), latitude, longitude));
            currentSessionDate = DateUtils.addMinutesToDate(currentSessionDate, 1);
            mins -= 1;
        }
        currentSessionDate = DateUtils.addMinutesToDate(currentSessionDate,-1) ;
    }

    private void writeQueueToNewFile() throws IOException {
        writeFixLocationFile();
        writeLocationEventFile();
    }

    private void writeFixLocationFile() {
        LocationFixFileWriter fixFileWriter = new LocationFixFileWriter(context);
        fixFileWriter.deleteFile(FileConstants.LOCATION_FIX_FILENAME);
        fixFileWriter.writeFileEntry(53.4462326, -2.1916000, FileConstants.LOCATION_FIX_FILENAME);
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