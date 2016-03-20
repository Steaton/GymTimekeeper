package one.thing.well.gymtimekeeper.display;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.zeko.gymtimekeeper.R;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import one.thing.well.gymtimekeeper.location.GymTimekeeperLocationTrackingService;


public class MainActivity extends AppCompatActivity {

    Button updateButton;
    TextView latTextView;
    TextView lonTextView;
    TextView timeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialisePanel(savedInstanceState);
        launchLocationIntentService();
        startAutomaticLocationDisplayUpdatesThread();
    }

    private void initialisePanel(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        latTextView = (TextView) findViewById(R.id.latTextView);
        lonTextView = (TextView) findViewById(R.id.lonTextView);
        timeTextView = (TextView) findViewById(R.id.timeTextView);
        updateButton = (Button) findViewById(R.id.updateButton);
    }

    private void launchLocationIntentService() {
        Intent locationService = new Intent(this, GymTimekeeperLocationTrackingService.class);
        startService(locationService);
    }


    public void readInTheGymLocationFile() {

        latTextView.setText("Lat");
        lonTextView.setText("Lon");
        timeTextView.setText("Time");

        try {
            FileInputStream fileInputStream = getApplicationContext().openFileInput("InTheGym");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line, i1 = "", i2 = "", i3 = "";
            while ((line = bufferedReader.readLine()) != null) {
                String[] Items = line.split("#");
                for (int i = 0; i < Items.length; i++) {
                    String[] Singles = Items[i].split(",");
                    i1 = i1 + "\n" + Singles[0].replace("#", "");
                    i2 = i2 + "\n" + Singles[1];
                    i3 = i3 + "\n" + Singles[2];
                }
            }
            timeTextView.setText(timeTextView.getText() + "\n" + i1);
            latTextView.setText(latTextView.getText() + "\n" + i2);
            lonTextView.setText(lonTextView.getText() + "\n" + i3);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

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
                                readInTheGymLocationFile();
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



