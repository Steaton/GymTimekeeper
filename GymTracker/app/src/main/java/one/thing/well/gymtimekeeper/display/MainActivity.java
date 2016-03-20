package one.thing.well.gymtimekeeper.display;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import one.thing.well.gymtimekeeper.R;
import one.thing.well.gymtimekeeper.datastore.AbstractFileWriter;
import one.thing.well.gymtimekeeper.datastore.FileReader;
import one.thing.well.gymtimekeeper.datastore.FileConstants;
import one.thing.well.gymtimekeeper.datastore.locationfix.LocationFixFileWriter;
import one.thing.well.gymtimekeeper.locationservice.LocationTrackingService;
import one.thing.well.gymtimekeeper.datastore.locationevent.LocationEventFile;

public class MainActivity extends AppCompatActivity {

    private TextView timeTextView;

    private TextView latTextView;

    private TextView lonTextView;

    private Button updateButton;

    private FileReader fileReader;

    private Double lastLongitude;

    private Double lastLatitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialisePanel(savedInstanceState);
        setupUpdateButtonOnClickListener();
        launchLocationIntentService();
        startAutomaticLocationDisplayUpdatesThread();
        fileReader = new FileReader(getApplicationContext());
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
        fileWriter.writeFileEntry(lastLatitude, lastLongitude, FileConstants.LOCATION_FIX_FILENAME);
    }

    private void launchLocationIntentService() {
        Intent locationService = new Intent(this, LocationTrackingService.class);
        startService(locationService);
    }

    private void displayLocationEvents() throws IOException {
        LocationEventFile file = (LocationEventFile) fileReader.readFile(FileConstants.LOCATION_EVENTS_FILENAME);
        displayEvents(file);
    }

    private void displayEvents(LocationEventFile file) {
        lastLatitude = file.getLastLatitude();
        lastLongitude = file.getLastLongitude();
        timeTextView.setText("Time\n" + file.buildDateList());
        latTextView.setText("Lat\n" + file.buildLatitudeList());
        lonTextView.setText("Lon\n" + file.buildLongitudeList());
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
                                } catch (IOException e) {
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



