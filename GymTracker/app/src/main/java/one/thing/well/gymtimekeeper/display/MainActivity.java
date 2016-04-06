package one.thing.well.gymtimekeeper.display;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.text.ParseException;

import one.thing.well.gymtimekeeper.R;
import one.thing.well.gymtimekeeper.datastore.AbstractFileWriter;
import one.thing.well.gymtimekeeper.datastore.FileConstants;
import one.thing.well.gymtimekeeper.datastore.locationevent.LocationEventFileReader;
import one.thing.well.gymtimekeeper.datastore.locationfix.LocationFixFileReader;
import one.thing.well.gymtimekeeper.datastore.locationfix.LocationFixFileWriter;
import one.thing.well.gymtimekeeper.datastore.sessionsummary.SessionSummaryFile;
import one.thing.well.gymtimekeeper.locationservice.LocationTrackingService;
import one.thing.well.gymtimekeeper.datastore.locationevent.LocationEventFile;
import one.thing.well.gymtimekeeper.session.SessionSummary;

public class MainActivity extends AppCompatActivity {

    private TextView timeTextView;

    private TextView latTextView;

    private TextView lonTextView;

    private Button updateButton;

    private LocationFixFileReader locationFixFileReader;

    private LocationEventFileReader locationEventFileReader;

    private Double lastLongitude;

    private Double lastLatitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialisePanel(savedInstanceState);
        setupUpdateButtonOnClickListener();
        launchLocationIntentService();
        startAutomaticLocationDisplayUpdatesThread();
        locationFixFileReader = new LocationFixFileReader(getApplicationContext());
        locationEventFileReader = new LocationEventFileReader(getApplicationContext());
    }

    private void initialisePanel(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        timeTextView = (TextView) findViewById(R.id.timeTextView);
        latTextView = (TextView) findViewById(R.id.latTextView);
        lonTextView = (TextView) findViewById(R.id.lonTextView);
        updateButton = (Button) findViewById(R.id.updateButton);
    }

    private void setupUpdateButtonOnClickListener() {
        updateButton.setOnClickListener(
                new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateButtonClicked();
                    }
                }
        );
    }

    public void updateButtonClicked() {
        System.out.println("Location fixed to be: " + lastLatitude + "," + lastLongitude);
        AbstractFileWriter fileWriter = new LocationFixFileWriter(getApplicationContext());
        fileWriter.deleteFile(FileConstants.LOCATION_FIX_FILENAME);
        fileWriter.writeFileEntry(lastLatitude, lastLongitude, FileConstants.LOCATION_FIX_FILENAME);
    }

    private void launchLocationIntentService() {
        Intent locationService = new Intent(this, LocationTrackingService.class);
        startService(locationService);
    }

    private void displayLocationEvents() throws IOException {
        LocationEventFile file = (LocationEventFile) locationEventFileReader.readFile(FileConstants.LOCATION_EVENTS_FILENAME);
        displayLocationEvents(file);
    }

    private void displaySessionSummary() throws IOException, ParseException {
        //SessionSummaryFile file = (SessionSummaryFile) locationFixFileReader.readFile(FileConstants.SESSION_SUMMARY_FILE);
//        SessionSummaryFile file = new SessionSummary().loadData();
//        displaySessionSummary(file);
    }

    private void displayLocationEvents(LocationEventFile file) {
        lastLatitude = file.getLastLatitude();
        lastLongitude = file.getLastLongitude();
//        timeTextView.setText("Time\n" + file.buildDateList());
//        latTextView.setText("Lat\n" + file.buildLatitudeList());
//        lonTextView.setText("Lon\n" + file.buildLongitudeList());
    }

    private void displaySessionSummary(SessionSummaryFile file) {
        timeTextView.setText("Start Time\n" + file.buildStartTimeList());
        latTextView.setText("End Time\n" + file.buildEndTimeList());
        lonTextView.setText("Session Duration\n" + file.buildSessionDurationList());
    }

    private void startAutomaticLocationDisplayUpdatesThread() {
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    displayLocationEvents();
                                    displaySessionSummary();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        t.start();
    }
}



