package one.thing.well.gymtimekeeper.display;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import one.thing.well.gymtimekeeper.R;
import one.thing.well.gymtimekeeper.location.LocationTrackingService;
import one.thing.well.gymtimekeeper.persist.LocationEventFileReader;
import one.thing.well.gymtimekeeper.persist.locationevent.LocationEventFile;

public class MainActivity extends AppCompatActivity {

    private TextView timeTextView;

    private TextView latTextView;

    private TextView lonTextView;

    private Button updateButton;

    private LocationEventFileReader fileReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialisePanel(savedInstanceState);
        setupUpdateButtonOnClickListener();
        launchLocationIntentService();
        startAutomaticLocationDisplayUpdatesThread();
        fileReader = new LocationEventFileReader(getApplicationContext());
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
        System.out.println("This is: Update Button Clicked");
    }

    private void launchLocationIntentService() {
        Intent locationService = new Intent(this, LocationTrackingService.class);
        startService(locationService);
    }

    private void displayGymLocationEvents() {
        LocationEventFile file = fileReader.readGymLocationEventFile();
        displayEvents(file);
    }

    private void displayEvents(LocationEventFile file) {
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
                                displayGymLocationEvents();
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



