package one.thing.well.gymtimekeeper.session;

import android.test.AndroidTestCase;

import org.junit.Test;

import java.util.Date;

import one.thing.well.gymtimekeeper.datastore.sessionsummary.SessionSummaryEntry;
import one.thing.well.gymtimekeeper.datastore.sessionsummary.SessionSummaryFile;

public class WeekSummariserTest extends AndroidTestCase {

    private SessionSummaryFile sessionSummaryFile = new SessionSummaryFile();

    @Test
    public void testThisWeekWithNoSessions() {
        WeekSummariser weekSummariser = new WeekSummariser(sessionSummaryFile);
        String timeSpentThisWeek = weekSummariser.thisWeek();
        assertEquals("0h 0m", timeSpentThisWeek);
    }

    @Test
    public void testThisWeekWithOneHourSession() {
        Date now = new Date();
        Date andHourAgo = new Date(now.getTime() - 3600000);
        sessionSummaryFile.add(new SessionSummaryEntry(andHourAgo, now));
        WeekSummariser weekSummariser = new WeekSummariser(sessionSummaryFile);
        String timeSpentThisWeek = weekSummariser.thisWeek();
        assertEquals("1h 0m", timeSpentThisWeek);
    }

//    @Test
//    public void testThisWeekWithMultipleSessions() {
//        Date now = new Date();
//        Date twentyFiveHoursAgo = new Date(now.getTime() - (25 * 3600000));
//        Date fiveMinutesAgo = new Date(now.getTime() - (5 * 60000));
//        sessionSummaryFile.add(new SessionSummaryEntry(twentyFiveHoursAgo, now));
//        sessionSummaryFile.add(new SessionSummaryEntry(fiveMinutesAgo, now));
//        WeekSummariser weekSummariser = new WeekSummariser(sessionSummaryFile);
//        String timeSpentThisWeek = weekSummariser.thisWeek();
//        assertEquals("1d 1h 5m", timeSpentThisWeek);
//    }

    @Test
    public void testTodayWithMultipleSessions() {
        Date now = new Date();
        Date fiveHoursAgo = new Date(now.getTime() - (5 * 3600000));
        Date fiveMinutesAgo = new Date(now.getTime() - (5 * 60000));
        sessionSummaryFile.add(new SessionSummaryEntry(fiveHoursAgo, now));
        sessionSummaryFile.add(new SessionSummaryEntry(fiveMinutesAgo, now));
        WeekSummariser weekSummariser = new WeekSummariser(sessionSummaryFile);
        String timeSpentThisWeek = weekSummariser.thisWeek();
        assertEquals("5h 5m", timeSpentThisWeek);
    }
}