package one.thing.well.gymtimekeeper.display;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;

import java.io.IOException;
import java.text.ParseException;

import one.thing.well.gymtimekeeper.GymTimekeeperApplication;
import one.thing.well.gymtimekeeper.R;
import one.thing.well.gymtimekeeper.display.Setup.SectionsPagerAdapter;
import one.thing.well.gymtimekeeper.locationservice.LocationTrackingService;
import one.thing.well.gymtimekeeper.util.CreateTestLocationEventFile;


public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    private CreateTestLocationEventFile createTestLocationEventFile = new CreateTestLocationEventFile();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialisePanel(savedInstanceState);
        CreateTabsForTheApp();


        System.out.println("#sesh" + GymTimekeeperApplication.getAppContext());
        try {
            createTestLocationEventFile.shouldWriteTestDataFiles();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        launchLocationIntentService();
    }


    private void initialisePanel(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
    }


    private void launchLocationIntentService() {
        Intent locationService = new Intent(this, LocationTrackingService.class);
        startService(locationService);
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

    // This actually makes the TABs and allows for the app to call the other class
    private void CreateTabsForTheApp() {
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



