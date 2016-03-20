package one.thing.well.gymtimekeeper.display;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import one.thing.well.gymtimekeeper.R;
import one.thing.well.gymtimekeeper.location.GymTimekeeperLocationTrackingService;
import one.thing.well.gymtimekeeper.persist.InTheGymFileReader;
import one.thing.well.gymtimekeeper.persist.InTheGymLocationEventFile;

public class MainActivity extends AppCompatActivity {

    private TextView timeTextView;

    private TextView latTextView;

    private TextView lonTextView;

    private Button updateButton;

    private InTheGymFileReader fileReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialisePanel(savedInstanceState);
        launchLocationIntentService();
        startAutomaticLocationDisplayUpdatesThread();
        fileReader = new InTheGymFileReader(getApplicationContext());
    }

    private void initialisePanel(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        timeTextView = (TextView) findViewById(R.id.timeTextView);
        latTextView = (TextView) findViewById(R.id.latTextView);
        lonTextView = (TextView) findViewById(R.id.lonTextView);
        updateButton = (Button) findViewById(R.id.updateButton);
    }

    private void launchLocationIntentService() {
        Intent locationService = new Intent(this, GymTimekeeperLocationTrackingService.class);
        startService(locationService);
    }

    public void displayGymLocationEvents() {
        InTheGymLocationEventFile file = fileReader.readGymLocationEventFile();
        displayEvents(file);
    }

    private void displayEvents(InTheGymLocationEventFile file) {
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



