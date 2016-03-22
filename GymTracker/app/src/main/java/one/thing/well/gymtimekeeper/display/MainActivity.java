package one.thing.well.gymtimekeeper.display;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import one.thing.well.gymtimekeeper.R;
import one.thing.well.gymtimekeeper.location.LocationTrackingService;
import one.thing.well.gymtimekeeper.persist.locationevent.LocationEventFileReader;
import one.thing.well.gymtimekeeper.persist.locationevent.LocationEventFile;


public class MainActivity extends AppCompatActivity {

    private TextView timeTextView;

    private TextView latTextView;

    private TextView lonTextView;

    private Button updateButton;

    private LocationEventFileReader fileReader;


    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialisePanel(savedInstanceState);
  //      setupUpdateButtonOnClickListener();
        CreateTabsForTheApp();
        launchLocationIntentService();
    //    startAutomaticLocationDisplayUpdatesThread();
        fileReader = new LocationEventFileReader(getApplicationContext());

    }





    private void initialisePanel(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
      /*
        timeTextView = (TextView) findViewById(R.id.timeTextView);
        latTextView = (TextView) findViewById(R.id.latTextView);
        lonTextView = (TextView) findViewById(R.id.lonTextView);
        updateButton = (Button) findViewById(R.id.updateButton);

       */

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
     //   timeTextView.setText("Time\n" + file.buildDateList());
     //   latTextView.setText("Lat\n" + file.buildLatitudeList());
     //   lonTextView.setText("Lon\n" + file.buildLongitudeList());
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


    @Override
      public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



     private void CreateTabsForTheApp(){



        try{

         Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
         setSupportActionBar(toolbar);


         mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());


         mViewPager = (ViewPager) findViewById(R.id.container);
         if (mViewPager != null) {
             mViewPager.setAdapter(mSectionsPagerAdapter);
         }


         mViewPager = (ViewPager) findViewById(R.id.buttons);
         if (mViewPager != null) {
             mViewPager.setAdapter(mSectionsPagerAdapter);
         }


        }catch (Exception e){
        e.printStackTrace();}
     }


}



