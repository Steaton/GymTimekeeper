package one.thing.well.gymtimekeeper.session;

import android.test.AndroidTestCase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;

import one.thing.well.gymtimekeeper.datastore.locationevent.LocationEventEntry;
import one.thing.well.gymtimekeeper.datastore.sessionsummary.SessionSummaryFile;
import one.thing.well.gymtimekeeper.session.SessionSummary;
import one.thing.well.gymtimekeeper.util.DateUtils;

public class SessionSummaryTest extends AndroidTestCase {

    private SessionSummary sessionSummary;

    private ArrayBlockingQueue<LocationEventEntry> locationQueue;

    private Date currentSessionDate;

    @Before
    public void setUp() throws ParseException {
        currentSessionDate = DateUtils.parseDateString("10/03 10:00:00");
        sessionSummary = new SessionSummary();
        locationQueue = new ArrayBlockingQueue<>(50, true);
        sessionSummary.setFixLatitude(53.4461826);
        sessionSummary.setFixLongitude(-2.1913555);
        sessionSummary.setLocationQueue(locationQueue);
    }

    @Test
    public void shouldNotReturnAnySessionsIfSessionSummaryNotInitialised() throws ParseException {
        SessionSummaryFile sessionSummaryFile = sessionSummary.summariseSessions();
        Assert.assertNotNull(sessionSummaryFile);
    }

    @Test
    public void shouldNotReturnAnySessionsIfLocationNotYetFixed() throws ParseException {
        sessionSummary.setLocationQueue(locationQueue);
        SessionSummaryFile sessionSummaryFile = sessionSummary.summariseSessions();
        assertNotNull(sessionSummaryFile);
        assertTrue(sessionSummaryFile.getSessionsList().isEmpty());
    }

    @Test
    public void shouldFind30MinuteSessionStartingAtLocation() throws ParseException {
        addThirtyMinutesAtLocation();
        addTenMinutesOutsideLocation();
        SessionSummaryFile sessionSummaryFile = sessionSummary.summariseSessions();
        Assert.assertEquals("10/03 10:00:00\n", sessionSummaryFile.buildStartTimeList());
        Assert.assertEquals("10/03 10:30:00\n", sessionSummaryFile.buildEndTimeList());
    }

    @Test
    public void shouldFind30MinSessionStartingOutsideAndEndingOutside() throws ParseException {
        addTenMinutesOutsideLocation();
        addThirtyMinutesAtLocation();
        addTenMinutesOutsideLocation();
        SessionSummaryFile sessionSummaryFile = sessionSummary.summariseSessions();
        Assert.assertEquals("10/03 10:10:00\n", sessionSummaryFile.buildStartTimeList());
        Assert.assertEquals("10/03 10:40:00\n", sessionSummaryFile.buildEndTimeList());
    }

    @Test
    public void shouldFind30MinSessionStartingOutsideAndEndingAtLocation() throws ParseException {
        addTenMinutesOutsideLocation();
        addThirtyMinutesAtLocation();
        SessionSummaryFile sessionSummaryFile = sessionSummary.summariseSessions();
        Assert.assertEquals("10/03 10:10:00\n", sessionSummaryFile.buildStartTimeList());
        Assert.assertEquals("10/03 10:38:00\n", sessionSummaryFile.buildEndTimeList());
    }

    @Test
    public void shouldFind2x30MinSessionsWith2MinGap() throws ParseException {
        addTenMinutesOutsideLocation();
        addThirtyMinutesAtLocation();
        addTenMinutesOutsideLocation();
        addThirtyMinutesAtLocation();
        SessionSummaryFile sessionSummaryFile = sessionSummary.summariseSessions();
        Assert.assertEquals("10/03 10:10:00\n10/03 10:50:00\n", sessionSummaryFile.buildStartTimeList());
        Assert.assertEquals("10/03 10:40:00\n10/03 11:18:00\n", sessionSummaryFile.buildEndTimeList());
    }

    private void addThirtyMinutesAtLocation() {
        addTimeAtLocation(53.4461826, -2.1913555, 30);
    }

    private void addTenMinutesOutsideLocation() {
        addTimeAtLocation(51.569969, -0.188636, 10);
    }

    private void addTimeAtLocation(double latitude, double longitude, int mins) {
        while(mins > 0) {
            System.out.println("locationQueueSize=" + locationQueue.size() + " mins=" + mins);
            locationQueue.offer(new LocationEventEntry(DateUtils.formatDate(currentSessionDate), latitude, longitude));
            currentSessionDate = DateUtils.addMinutesToDate(currentSessionDate, 2);
            mins -= 2;
        }
    }
}